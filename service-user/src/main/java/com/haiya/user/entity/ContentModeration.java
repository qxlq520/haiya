package com.haiya.user.entity;

import java.time.LocalDateTime;

/**
 * 内容安全审核实体类
 */
public class ContentModeration {
    /**
     * 审核ID
     */
    private Long id;
    
    /**
     * 内容ID（视频、图片、文本等）
     */
    private Long contentId;
    
    /**
     * 内容类型
     */
    private ContentType contentType;
    
    /**
     * 作者ID
     */
    private Long authorId;
    
    /**
     * 审核结果
     */
    private ModerationResult result;
    
    /**
     * AI审核得分
     */
    private Double aiScore;
    
    /**
     * 违规类型
     */
    private ViolationType violationType;
    
    /**
     * 审核方式
     */
    private ModerationMethod method;
    
    /**
     * 审核员ID（人工审核时）
     */
    private Long moderatorId;
    
    /**
     * 审核备注
     */
    private String remarks;
    
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
    
    public Long getContentId() {
        return contentId;
    }
    
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
    
    public ContentType getContentType() {
        return contentType;
    }
    
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
    
    public Long getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    
    public ModerationResult getResult() {
        return result;
    }
    
    public void setResult(ModerationResult result) {
        this.result = result;
    }
    
    public Double getAiScore() {
        return aiScore;
    }
    
    public void setAiScore(Double aiScore) {
        this.aiScore = aiScore;
    }
    
    public ViolationType getViolationType() {
        return violationType;
    }
    
    public void setViolationType(ViolationType violationType) {
        this.violationType = violationType;
    }
    
    public ModerationMethod getMethod() {
        return method;
    }
    
    public void setMethod(ModerationMethod method) {
        this.method = method;
    }
    
    public Long getModeratorId() {
        return moderatorId;
    }
    
    public void setModeratorId(Long moderatorId) {
        this.moderatorId = moderatorId;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        VIDEO,     // 视频
        IMAGE,     // 图片
        TEXT,      // 文本
        COMMENT,   // 评论
        PROFILE    // 个人资料
    }
    
    /**
     * 审核结果枚举
     */
    public enum ModerationResult {
        APPROVED,     // 通过
        REJECTED,     // 拒绝
        PENDING,      // 待审核
        SCHEDULED     // 已计划
    }
    
    /**
     * 违规类型枚举
     */
    public enum ViolationType {
        NONE,              // 无违规
        VIOLENCE,          // 暴力
        PORNOGRAPHY,       // 色情
        POLITICS,          // 政治敏感
        ADVERTISING,       // 广告
        COPYRIGHT,         // 版权
        HARASSMENT,        // 骚扰
        FRAUD,             // 诈骗
        PRIVACY,           // 隐私
        OTHER              // 其他
    }
    
    /**
     * 审核方式枚举
     */
    public enum ModerationMethod {
        AI,          // AI审核
        MANUAL,      // 人工审核
        AI_MANUAL    // AI+人工审核
    }
}