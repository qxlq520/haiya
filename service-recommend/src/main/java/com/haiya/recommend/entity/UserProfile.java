package com.haiya.recommend.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户画像实体类
 */
public class UserProfile {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户兴趣标签及权重
     */
    private Map<String, Double> interests;
    
    /**
     * 用户基本属性
     */
    private Map<String, String> attributes;
    
    /**
     * 最近观看的视频类别
     */
    private List<String> recentCategories;
    
    /**
     * 用户活跃时间段
     */
    private List<Integer> activeHours;
    
    /**
     * 平均观看时长
     */
    private Double avgWatchTime;
    
    /**
     * 完播率
     */
    private Double completionRate;
    
    /**
     * 用户价值得分
     */
    private Double valueScore;
    
    /**
     * 特征向量（用于深度学习模型）
     */
    private List<Double> featureVector;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Map<String, Double> getInterests() {
        return interests;
    }
    
    public void setInterests(Map<String, Double> interests) {
        this.interests = interests;
    }
    
    public Map<String, String> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
    
    public List<String> getRecentCategories() {
        return recentCategories;
    }
    
    public void setRecentCategories(List<String> recentCategories) {
        this.recentCategories = recentCategories;
    }
    
    public List<Integer> getActiveHours() {
        return activeHours;
    }
    
    public void setActiveHours(List<Integer> activeHours) {
        this.activeHours = activeHours;
    }
    
    public Double getAvgWatchTime() {
        return avgWatchTime;
    }
    
    public void setAvgWatchTime(Double avgWatchTime) {
        this.avgWatchTime = avgWatchTime;
    }
    
    public Double getCompletionRate() {
        return completionRate;
    }
    
    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }
    
    public Double getValueScore() {
        return valueScore;
    }
    
    public void setValueScore(Double valueScore) {
        this.valueScore = valueScore;
    }
    
    public List<Double> getFeatureVector() {
        return featureVector;
    }
    
    public void setFeatureVector(List<Double> featureVector) {
        this.featureVector = featureVector;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}