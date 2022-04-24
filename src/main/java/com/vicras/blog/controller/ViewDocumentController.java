package com.vicras.blog.controller;

import com.vicras.blog.dto.DocumentDTO;
import com.vicras.blog.service.DocumentViewService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ViewDocumentController {

    private final DocumentViewService documentService;

    @GetMapping("/author/docs")
    List<DocumentDTO> getAuthorDocs(Principal principal) {
        return documentService.getAuthorDocuments(principal);
    }

    @GetMapping("/redactor/docs")
    List<DocumentDTO> getRedactorDocs(Principal principal) {
        return documentService.getWithSameDepartment(principal);
    }

    @GetMapping("/reader/docs")
    List<DocumentDTO> getReaderDocs() {
        return documentService.getApprovedDocs();
    }
}
