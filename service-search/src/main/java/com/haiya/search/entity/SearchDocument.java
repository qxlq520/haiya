package com.haiya.search.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_document")
public class SearchDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "type")
    private String type; // VIDEO, USER, LIVE

    @Column(name = "reference_id")
    private Long referenceId; // 关联的实体ID

    @Column(name = "created_at")
    private Long createdAt;

    // Constructors
    public SearchDocument() {}

    public SearchDocument(String title, String content, String type, Long referenceId) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.referenceId = referenceId;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}