package com.haiya.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 广告收入实体类
 */
public class AdRevenue {
    /**
     * 收入ID
     */
    private Long id;
    
    /**
     * 创作者ID
     */
    private Long creatorId;
    
    /**
     * 视频ID
     */
    private Long videoId;
    
    /**
     * 广告ID
     */
    private Long adId;
    
    /**
     * 总收入
     */
    private BigDecimal totalRevenue;
    
    /**
     * 创作者分成比例
     */
    private BigDecimal creatorShareRatio;
    
    /**
     * 创作者实际收入
     */
    private BigDecimal creatorRevenue;
    
    /**
     * 平台收入
     */
    private BigDecimal platformRevenue;
    
    /**
     * 结算状态
     */
    private SettlementStatus status;
    
    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;
    
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
    
    public Long getCreatorId() {
        return creatorId;
    }
    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    
    public Long getVideoId() {
        return videoId;
    }
    
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
    
    public Long getAdId() {
        return adId;
    }
    
    public void setAdId(Long adId) {
        this.adId = adId;
    }
    
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    public BigDecimal getCreatorShareRatio() {
        return creatorShareRatio;
    }
    
    public void setCreatorShareRatio(BigDecimal creatorShareRatio) {
        this.creatorShareRatio = creatorShareRatio;
    }
    
    public BigDecimal getCreatorRevenue() {
        return creatorRevenue;
    }
    
    public void setCreatorRevenue(BigDecimal creatorRevenue) {
        this.creatorRevenue = creatorRevenue;
    }
    
    public BigDecimal getPlatformRevenue() {
        return platformRevenue;
    }
    
    public void setPlatformRevenue(BigDecimal platformRevenue) {
        this.platformRevenue = platformRevenue;
    }
    
    public SettlementStatus getStatus() {
        return status;
    }
    
    public void setStatus(SettlementStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getSettlementTime() {
        return settlementTime;
    }
    
    public void setSettlementTime(LocalDateTime settlementTime) {
        this.settlementTime = settlementTime;
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
     * 结算状态枚举
     */
    public enum SettlementStatus {
        PENDING,    // 待结算
        SETTLED,    // 已结算
        CANCELLED   // 已取消
    }
}