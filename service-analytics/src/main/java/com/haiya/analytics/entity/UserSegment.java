package com.haiya.analytics.entity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户分群实体类
 */
public class UserSegment {
    /**
     * 分群ID
     */
    private Long id;
    
    /**
     * 分群名称
     */
    private String name;
    
    /**
     * 分群条件
     */
    private Map<String, Object> conditions;
    
    /**
     * 用户数量
     */
    private Long userCount;
    
    /**
     * 分群类型
     */
    private SegmentType type;
    
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
    
    public Map<String, Object> getConditions() {
        return conditions;
    }
    
    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }
    
    public Long getUserCount() {
        return userCount;
    }
    
    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }
    
    public SegmentType getType() {
        return type;
    }
    
    public void setType(SegmentType type) {
        this.type = type;
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
     * 分群类型枚举
     */
    public enum SegmentType {
        STATIC,   // 静态分群
        DYNAMIC   // 动态分群
    }
}