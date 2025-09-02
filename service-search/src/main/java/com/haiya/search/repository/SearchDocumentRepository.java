package com.haiya.search.repository;

import com.haiya.search.entity.SearchDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchDocumentRepository extends JpaRepository<SearchDocument, String> {
    List<SearchDocument> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
    List<SearchDocument> findByType(String type);
    List<SearchDocument> findByTypeAndTitleContainingOrContentContaining(String type, String titleKeyword, String contentKeyword);
}