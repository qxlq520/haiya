package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "content_recommendations")
public class ContentRecommendation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "content_id", nullable = false)
    private Long contentId;
    
    @Column(name = "content_type")
    private String contentType; // 内容类型：VIDEO, ARTICLE等
    
    @Column(name = "recommendation_type")
    private String recommendationType; // 推荐类型：POPULAR(热门), PERSONALIZED(个性化), TRENDING(趋势)
    
    @Column(name = "recommendation_reason")
    private String recommendationReason; // 推荐理由
    
    @Column(name = "score")
    private Double score = 0.0; // 推荐分数
    
    @Column(name = "is_interacted")
    private Boolean isInteracted = false; // 用户是否已互动（观看、点赞等）
    
    @Column(name = "interacted_at")
    private LocalDateTime interactedAt; // 互动时间
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.score == null) this.score = 0.0;
        if (this.isInteracted == null) this.isInteracted = false;
    }
    
    // Constructors
    public ContentRecommendation() {}
    
    public ContentRecommendation(Long userId, Long contentId, String contentType, 
                                String recommendationType, Double score) {
        this.userId = userId;
        this.contentId = contentId;
        this.contentType = contentType;
        this.recommendationType = recommendationType;
        this.score = score != null ? score : 0.0;
        this.isInteracted = false;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getContentId() {
        return contentId;
    }
    
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public String getRecommendationType() {
        return recommendationType;
    }
    
    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }
    
    public String getRecommendationReason() {
        return recommendationReason;
    }
    
    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score != null ? score : 0.0;
    }
    
    public Boolean getIsInteracted() {
        return isInteracted;
    }
    
    public void setIsInteracted(Boolean isInteracted) {
        this.isInteracted = isInteracted;
    }
    
    public LocalDateTime getInteractedAt() {
        return interactedAt;
    }
    
    public void setInteractedAt(LocalDateTime interactedAt) {
        this.interactedAt = interactedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 标记为已互动
     */
    public void markAsInteracted() {
        this.isInteracted = true;
        this.interactedAt = LocalDateTime.now();
    }
}