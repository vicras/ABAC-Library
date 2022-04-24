package com.vicras.blog.service;

import com.vicras.blog.dto.DocumentDTO;
import com.vicras.blog.model.Document;

import java.security.Principal;
import java.util.List;

public interface DocumentService {
    DocumentDTO addDocument(DocumentDTO docs, Principal user);

    DocumentDTO updateDocuments(Long id, DocumentDTO docs);

    List<DocumentDTO> getAllDocuments();

    DocumentDTO getDocumentDtoById(Long id);

    Document getDocument(Long id);

    DocumentDTO deleteDocumentById(Long id);

    DocumentDTO approveDocument(Long id);
}
