package com.haiya.recommend.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 视频特征实体类
 */
public class VideoFeature {
    /**
     * 视频ID
     */
    private Long videoId;
    
    /**
     * 视频类别
     */
    private List<String> categories;
    
    /**
     * 视频标签
     */
    private List<String> tags;
    
    /**
     * 视频时长（秒）
     */
    private Integer duration;
    
    /**
     * 发布者ID
     */
    private Long authorId;
    
    /**
     * 发布者粉丝数
     */
    private Long authorFollowers;
    
    /**
     * 视频质量得分
     */
    private Double qualityScore;
    
    /**
     * 热度得分
     */
    private Double popularityScore;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 特征向量（用于深度学习模型）
     */
    private List<Double> featureVector;
    
    // Getters and Setters
    public Long getVideoId() {
        return videoId;
    }
    
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
    
    public List<String> getCategories() {
        return categories;
    }
    
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public Long getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    
    public Long getAuthorFollowers() {
        return authorFollowers;
    }
    
    public void setAuthorFollowers(Long authorFollowers) {
        this.authorFollowers = authorFollowers;
    }
    
    public Double getQualityScore() {
        return qualityScore;
    }
    
    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }
    
    public Double getPopularityScore() {
        return popularityScore;
    }
    
    public void setPopularityScore(Double popularityScore) {
        this.popularityScore = popularityScore;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Double> getFeatureVector() {
        return featureVector;
    }
    
    public void setFeatureVector(List<Double> featureVector) {
        this.featureVector = featureVector;
    }
}