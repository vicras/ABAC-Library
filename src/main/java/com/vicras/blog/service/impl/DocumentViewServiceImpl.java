package com.vicras.blog.service.impl;

import static com.vicras.blog.model.Action.VIEW_AUTHOR;
import static com.vicras.blog.model.Action.VIEW_READER;
import static com.vicras.blog.model.Action.VIEW_REDACTOR;

import com.vicras.blog.abac.aspect.annotation.AbacSecure;
import com.vicras.blog.dto.DocumentDTO;
import com.vicras.blog.mapper.DocumentMapper;
import com.vicras.blog.model.CommonUser;
import com.vicras.blog.repository.DocumentsRepository;
import com.vicras.blog.service.DocumentViewService;
import com.vicras.blog.service.UserService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentViewServiceImpl implements DocumentViewService {

    private final DocumentsRepository repository;
    private final UserService userService;
    private final DocumentMapper mapper;

    @Override
    @AbacSecure(VIEW_AUTHOR)
    public List<DocumentDTO> getAuthorDocuments(Principal principal) {
        CommonUser user = userService.findUserByLogin(principal.getName());
        var docs = repository.findAllByCreator(user);
        return mapper.toResponse(docs);
    }

    @Override
    @AbacSecure(VIEW_REDACTOR)
    public List<DocumentDTO> getWithSameDepartment(Principal principal) {
        CommonUser user = userService.findUserByLogin(principal.getName());
        var docs = repository.findAllByDepartment(user.getDepartment());
        return mapper.toResponse(docs);
    }

    @Override
    @AbacSecure(VIEW_READER)
    public List<DocumentDTO> getApprovedDocs() {
        return mapper.toResponse(repository.findAllByApproved(true));
    }
}
