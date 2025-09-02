package com.haiya.analytics.entity;

import java.time.LocalDateTime;

/**
 * 内容质量评估实体类
 */
public class ContentQuality {
    /**
     * 评估ID
     */
    private Long id;
    
    /**
     * 视频ID
     */
    private Long videoId;
    
    /**
     * 质量得分
     */
    private Double qualityScore;
    
    /**
     * 各维度得分
     */
    private Double creativityScore;   // 创意得分
    private Double technicalScore;    // 技术得分
    private Double engagementScore;   // 互动得分
    private Double popularityScore;   // 热度得分
    
    /**
     * 评估时间
     */
    private LocalDateTime assessedAt;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getVideoId() {
        return videoId;
    }
    
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
    
    public Double getQualityScore() {
        return qualityScore;
    }
    
    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }
    
    public Double getCreativityScore() {
        return creativityScore;
    }
    
    public void setCreativityScore(Double creativityScore) {
        this.creativityScore = creativityScore;
    }
    
    public Double getTechnicalScore() {
        return technicalScore;
    }
    
    public void setTechnicalScore(Double technicalScore) {
        this.technicalScore = technicalScore;
    }
    
    public Double getEngagementScore() {
        return engagementScore;
    }
    
    public void setEngagementScore(Double engagementScore) {
        this.engagementScore = engagementScore;
    }
    
    public Double getPopularityScore() {
        return popularityScore;
    }
    
    public void setPopularityScore(Double popularityScore) {
        this.popularityScore = popularityScore;
    }
    
    public LocalDateTime getAssessedAt() {
        return assessedAt;
    }
    
    public void setAssessedAt(LocalDateTime assessedAt) {
        this.assessedAt = assessedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}