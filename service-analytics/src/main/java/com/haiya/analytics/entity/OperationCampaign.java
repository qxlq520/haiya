package com.haiya.analytics.entity;

import java.time.LocalDateTime;

/**
 * 运营活动实体类
 */
public class OperationCampaign {
    /**
     * 活动ID
     */
    private Long id;
    
    /**
     * 活动名称
     */
    private String name;
    
    /**
     * 活动描述
     */
    private String description;
    
    /**
     * 目标用户群ID
     */
    private Long segmentId;
    
    /**
     * 活动策略
     */
    private String strategy;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 活动状态
     */
    private CampaignStatus status;
    
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getSegmentId() {
        return segmentId;
    }
    
    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }
    
    public String getStrategy() {
        return strategy;
    }
    
    public void setStrategy(String strategy) {
        this.strategy = strategy;
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
    
    public CampaignStatus getStatus() {
        return status;
    }
    
    public void setStatus(CampaignStatus status) {
        this.status = status;
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
     * 活动状态枚举
     */
    public enum CampaignStatus {
        DRAFT,     // 草稿
        PENDING,   // 待执行
        RUNNING,   // 运行中
        FINISHED,  // 已结束
        CANCELLED  // 已取消
    }
}