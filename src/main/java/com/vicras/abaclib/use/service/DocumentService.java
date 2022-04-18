package com.vicras.abaclib.use.service;

import com.vicras.abaclib.use.dto.DocumentDTO;
import com.vicras.abaclib.use.model.Document;

import java.security.Principal;
import java.util.List;

public interface DocumentService {
    DocumentDTO addDocument(DocumentDTO docs, Principal user);

    DocumentDTO updateDocuments(Long id, DocumentDTO docs);

    List<DocumentDTO> getAllDocuments();

    DocumentDTO getDocumentDtoById(Long id);

    Document getDocument(Long id);

    DocumentDTO deleteDocumentById(Long id);
}
