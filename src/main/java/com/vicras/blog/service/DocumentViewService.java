package com.vicras.blog.service;

import com.vicras.blog.dto.DocumentDTO;

import java.security.Principal;
import java.util.List;

public interface DocumentViewService {
    List<DocumentDTO> getAuthorDocuments(Principal principal);

    List<DocumentDTO> getWithSameDepartment(Principal principal);

    List<DocumentDTO> getApprovedDocs();
}
