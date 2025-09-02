package com.haiya.search.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 发现页内容实体类
 */
public class DiscoveryContent {
    /**
     * 内容ID
     */
    private Long id;
    
    /**
     * 内容类型
     */
    private ContentType contentType;
    
    /**
     * 内容ID（关联到具体的内容）
     */
    private Long contentId;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 标签
     */
    private List<String> tags;
    
    /**
     * 热度得分
     */
    private Double hotScore;
    
    /**
     * 推荐权重
     */
    private Double weight;
    
    /**
     * 目标用户群
     */
    private List<Long> targetSegments;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public ContentType getContentType() {
        return contentType;
    }
    
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
    
    public Long getContentId() {
        return contentId;
    }
    
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public Double getHotScore() {
        return hotScore;
    }
    
    public void setHotScore(Double hotScore) {
        this.hotScore = hotScore;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public List<Long> getTargetSegments() {
        return targetSegments;
    }
    
    public void setTargetSegments(List<Long> targetSegments) {
        this.targetSegments = targetSegments;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * 内容类型枚举
     */
    public enum ContentType {
        VIDEO,       // 视频
        USER,        // 用户
        HASHTAG,     // 话题标签
        CHALLENGE,   // 挑战
        MUSIC,       // 音乐
        ARTICLE      // 文章
    }
}