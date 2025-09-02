package com.haiya.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * VIP会员等级实体类
 */
public class VipLevel {
    /**
     * 等级ID
     */
    private Long id;
    
    /**
     * 等级名称
     */
    private String name;
    
    /**
     * 等级级别（1,2,3...）
     */
    private Integer level;
    
    /**
     * 月费价格
     */
    private BigDecimal monthlyPrice;
    
    /**
     * 年费价格
     */
    private BigDecimal yearlyPrice;
    
    /**
     * 特权描述
     */
    private String privileges;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
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
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }
    
    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
    
    public BigDecimal getYearlyPrice() {
        return yearlyPrice;
    }
    
    public void setYearlyPrice(BigDecimal yearlyPrice) {
        this.yearlyPrice = yearlyPrice;
    }
    
    public String getPrivileges() {
        return privileges;
    }
    
    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
}