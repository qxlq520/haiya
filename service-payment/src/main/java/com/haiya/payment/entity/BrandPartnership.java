package com.haiya.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 品牌合作实体类
 */
public class BrandPartnership {
    /**
     * 合作ID
     */
    private Long id;
    
    /**
     * 品牌ID
     */
    private Long brandId;
    
    /**
     * 创作者ID
     */
    private Long creatorId;
    
    /**
     * 合作类型
     */
    private PartnershipType type;
    
    /**
     * 合作内容描述
     */
    private String content;
    
    /**
     * 合作费用
     */
    private BigDecimal fee;
    
    /**
     * 合作状态
     */
    private PartnershipStatus status;
    
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
    
    public Long getBrandId() {
        return brandId;
    }
    
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
    
    public Long getCreatorId() {
        return creatorId;
    }
    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    
    public PartnershipType getType() {
        return type;
    }
    
    public void setType(PartnershipType type) {
        this.type = type;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public BigDecimal getFee() {
        return fee;
    }
    
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    
    public PartnershipStatus getStatus() {
        return status;
    }
    
    public void setStatus(PartnershipStatus status) {
        this.status = status;
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
     * 合作类型枚举
     */
    public enum PartnershipType {
        PRODUCT_PLACEMENT,  // 产品植入
        SPONSORSHIP,        // 赞助
        BRAND_VIDEO,        // 品牌视频
        LIVE_STREAM         // 直播合作
    }
    
    /**
     * 合作状态枚举
     */
    public enum PartnershipStatus {
        PENDING,    // 待确认
        ACTIVE,     // 进行中
        COMPLETED,  // 已完成
        CANCELLED   // 已取消
    }
}