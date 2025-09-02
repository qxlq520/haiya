package com.haiya.analytics.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 热点实体类
 */
public class Trend {
    /**
     * 热点ID
     */
    private Long id;
    
    /**
     * 热点标题
     */
    private String title;
    
    /**
     * 热点类型
     */
    private TrendType type;
    
    /**
     * 关键词列表
     */
    private List<String> keywords;
    
    /**
     * 热度指数
     */
    private Double heatIndex;
    
    /**
     * 热点描述
     */
    private String description;
    
    /**
     * 相关视频数量
     */
    private Long videoCount;
    
    /**
     * 相关用户数量
     */
    private Long userCount;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 峰值时间
     */
    private LocalDateTime peakTime;
    
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public TrendType getType() {
        return type;
    }
    
    public void setType(TrendType type) {
        this.type = type;
    }
    
    public List<String> getKeywords() {
        return keywords;
    }
    
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    
    public Double getHeatIndex() {
        return heatIndex;
    }
    
    public void setHeatIndex(Double heatIndex) {
        this.heatIndex = heatIndex;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getVideoCount() {
        return videoCount;
    }
    
    public void setVideoCount(Long videoCount) {
        this.videoCount = videoCount;
    }
    
    public Long getUserCount() {
        return userCount;
    }
    
    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getPeakTime() {
        return peakTime;
    }
    
    public void setPeakTime(LocalDateTime peakTime) {
        this.peakTime = peakTime;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 热点类型枚举
     */
    public enum TrendType {
        HASHTAG,     // 话题标签
        CHALLENGE,   // 挑战
        MUSIC,       // 音乐
        EVENT,       // 事件
        CELEBRITY    // 明星
    }
}