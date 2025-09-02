package com.haiya.user.entity;

import java.time.LocalDateTime;

/**
 * 作弊检测记录实体类
 */
public class CheatDetection {
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 作弊类型
     */
    private CheatType cheatType;
    
    /**
     * 证据详情
     */
    private String evidence;
    
    /**
     * 风险等级
     */
    private RiskLevel riskLevel;
    
    /**
     * 处理状态
     */
    private DetectionStatus status;
    
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
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public CheatType getCheatType() {
        return cheatType;
    }
    
    public void setCheatType(CheatType cheatType) {
        this.cheatType = cheatType;
    }
    
    public String getEvidence() {
        return evidence;
    }
    
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
    
    public RiskLevel getRiskLevel() {
        return riskLevel;
    }
    
    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }
    
    public DetectionStatus getStatus() {
        return status;
    }
    
    public void setStatus(DetectionStatus status) {
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
     * 作弊类型枚举
     */
    public enum CheatType {
        VIEW_COUNT_FRAUD,     // 刷播放量
        LIKE_FRAUD,           // 刷点赞
        FOLLOW_FRAUD,         // 刷粉丝
        COMMENT_FRAUD,        // 刷评论
        SHARE_FRAUD,          // 刷分享
        ACCOUNT_FARMING       // 账号农场
    }
    
    /**
     * 风险等级枚举
     */
    public enum RiskLevel {
        LOW,     // 低风险
        MEDIUM,  // 中风险
        HIGH     // 高风险
    }
    
    /**
     * 处理状态枚举
     */
    public enum DetectionStatus {
        DETECTED,    // 已检测
        REVIEWING,   // 审核中
        CONFIRMED,   // 已确认
        HANDLED      // 已处理
    }
}