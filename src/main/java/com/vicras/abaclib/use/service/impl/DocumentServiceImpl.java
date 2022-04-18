package com.vicras.abaclib.use.service.impl;

import static com.vicras.abaclib.use.model.Action.CREATE_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.DELETE_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.EDIT_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.VIEW_DOCUMENT;

import com.vicras.abaclib.use.abac.aspect.annotation.AbacSecure;
import com.vicras.abaclib.use.dto.DocumentDTO;
import com.vicras.abaclib.use.exception.exceptions.business.EntityNotFoundException;
import com.vicras.abaclib.use.mapper.DocumentMapper;
import com.vicras.abaclib.use.model.Document;
import com.vicras.abaclib.use.repository.DocumentsRepository;
import com.vicras.abaclib.use.service.DocumentService;
import com.vicras.abaclib.use.service.UserService;
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
    @AbacSecure(VIEW_DOCUMENT)
    public List<DocumentDTO> getAllDocuments() {
        return mapper.toResponse(repository.findAll());
    }

    @Override
    @AbacSecure(VIEW_DOCUMENT)
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
}
