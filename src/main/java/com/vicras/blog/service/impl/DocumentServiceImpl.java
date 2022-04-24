package com.vicras.blog.service.impl;

import static com.vicras.blog.model.Action.APPROVE_DOCUMENT;
import static com.vicras.blog.model.Action.CREATE_DOCUMENT;
import static com.vicras.blog.model.Action.DELETE_DOCUMENT;
import static com.vicras.blog.model.Action.EDIT_DOCUMENT;
import static com.vicras.blog.model.Action.NOBODY;
import static com.vicras.blog.model.Action.VIEW_DOCUMENT_BY_ID;

import com.vicras.blog.abac.aspect.annotation.AbacSecure;
import com.vicras.blog.dto.DocumentDTO;
import com.vicras.blog.exception.exceptions.business.EntityNotFoundException;
import com.vicras.blog.mapper.DocumentMapper;
import com.vicras.blog.model.Document;
import com.vicras.blog.repository.DocumentsRepository;
import com.vicras.blog.service.DocumentService;
import com.vicras.blog.service.UserService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentsRepository repository;
    private final UserService userService;
    private final DocumentMapper mapper;

    @Override
    @AbacSecure(CREATE_DOCUMENT)
    public DocumentDTO addDocument(DocumentDTO docDto, Principal user) {
        var doc = mapper.toEntity(docDto);
        var cUser = userService.findUserByLogin(user.getName());
        doc.setCreator(cUser);
        repository.save(doc);
        return mapper.toResponse(doc);
    }

    @Override
    @Transactional
    @AbacSecure(EDIT_DOCUMENT)
    public DocumentDTO updateDocuments(Long id, DocumentDTO docs) {
        var oldDoc = getDocument(id);
        var newDoc = mapper.merge(oldDoc, docs);
        repository.save(newDoc);
        return mapper.toResponse(newDoc);
    }

    @Override
    @AbacSecure(NOBODY)
    public List<DocumentDTO> getAllDocuments() {
        return mapper.toResponse(repository.findAll());
    }

    @Override
    @AbacSecure(VIEW_DOCUMENT_BY_ID)
    public DocumentDTO getDocumentDtoById(Long id) {
        return mapper.toResponse(getDocument(id));
    }

    @Override
    public Document getDocument(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Document.class, id.toString()));
    }

    @Override
    @AbacSecure(DELETE_DOCUMENT)
    public DocumentDTO deleteDocumentById(Long id) {
        Document document = getDocument(id);
        repository.deleteById(id);
        return mapper.toResponse(document);
    }

    @Override
    @AbacSecure(APPROVE_DOCUMENT)
    public DocumentDTO approveDocument(Long id) {
        Document document = getDocument(id);
        document.setApproved(true);
        repository.save(document);
        return mapper.toResponse(document);
    }
}
