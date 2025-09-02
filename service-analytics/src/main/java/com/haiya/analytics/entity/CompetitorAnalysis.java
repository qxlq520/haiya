package com.haiya.analytics.entity;

import java.time.LocalDateTime;

/**
 * 竞品分析实体类
 */
public class CompetitorAnalysis {
    /**
     * 分析ID
     */
    private Long id;
    
    /**
     * 竞品名称
     */
    private String competitorName;
    
    /**
     * 指标类型
     */
    private MetricType metricType;
    
    /**
     * 我方数值
     */
    private Double ourValue;
    
    /**
     * 对方数值
     */
    private Double competitorValue;
    
    /**
     * 差异值
     */
    private Double difference;
    
    /**
     * 分析时间
     */
    private LocalDateTime analyzedAt;
    
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
    
    public String getCompetitorName() {
        return competitorName;
    }
    
    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }
    
    public MetricType getMetricType() {
        return metricType;
    }
    
    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }
    
    public Double getOurValue() {
        return ourValue;
    }
    
    public void setOurValue(Double ourValue) {
        this.ourValue = ourValue;
    }
    
    public Double getCompetitorValue() {
        return competitorValue;
    }
    
    public void setCompetitorValue(Double competitorValue) {
        this.competitorValue = competitorValue;
    }
    
    public Double getDifference() {
        return difference;
    }
    
    public void setDifference(Double difference) {
        this.difference = difference;
    }
    
    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }
    
    public void setAnalyzedAt(LocalDateTime analyzedAt) {
        this.analyzedAt = analyzedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 指标类型枚举
     */
    public enum MetricType {
        DAILY_ACTIVE_USERS,      // 日活用户
        MONTHLY_ACTIVE_USERS,    // 月活用户
        AVG_WATCH_TIME,          // 平均观看时长
        VIDEO_UPLOADS,           // 视频上传量
        USER_RETENTION,          // 用户留存率
        REVENUE                  // 收入
    }
}