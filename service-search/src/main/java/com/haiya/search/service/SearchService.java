package com.haiya.search.service;

import com.haiya.search.entity.SearchDocument;
import com.haiya.search.repository.SearchDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchDocumentRepository searchDocumentRepository;

    public List<SearchDocument> searchAll(String keyword) {
        return searchDocumentRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    public List<SearchDocument> searchByType(String type, String keyword) {
        return searchDocumentRepository.findByTypeAndTitleContainingOrContentContaining(type, keyword, keyword);
    }

    public List<SearchDocument> searchByTypeOnly(String type) {
        return searchDocumentRepository.findByType(type);
    }

    public SearchDocument saveDocument(SearchDocument document) {
        return searchDocumentRepository.save(document);
    }

    public void deleteDocument(String id) {
        searchDocumentRepository.deleteById(id);
    }

    public List<SearchDocument> getAllDocuments() {
        return searchDocumentRepository.findAll();
    }
}