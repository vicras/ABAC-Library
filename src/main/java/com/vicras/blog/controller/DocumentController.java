package com.vicras.blog.controller;

import com.vicras.blog.dto.DocumentDTO;
import com.vicras.blog.service.DocumentService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/docs")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping()
    public DocumentDTO addDocument(
            @RequestBody @Validated(DocumentDTO.CreatePhase.class) DocumentDTO docs,
            Principal principal) {
        return documentService.addDocument(docs, principal);
    }

    @PutMapping("/{id}")
    public DocumentDTO updateDocument(
            @RequestBody @Validated(DocumentDTO.UpdatePhase.class) DocumentDTO docs,
            @PathVariable Long id
    ) {
        return documentService.updateDocuments(id, docs);
    }

    @GetMapping
    public List<DocumentDTO> getDocument() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public DocumentDTO getDocument(@PathVariable Long id) {
        return documentService.getDocumentDtoById(id);
    }

    @DeleteMapping("/{id}")
    public DocumentDTO deleteDocument(@PathVariable Long id) {
        return documentService.deleteDocumentById(id);
    }

    @PostMapping("/{id}/approve")
    public DocumentDTO approveDocument(@PathVariable Long id) {
        return documentService.approveDocument(id);
    }
}
