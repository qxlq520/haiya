package com.haiya.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 虚拟货币实体类
 */
public class Currency {
    /**
     * 货币ID
     */
    private Long id;
    
    /**
     * 货币名称（如：抖币）
     */
    private String name;
    
    /**
     * 货币代码
     */
    private String code;
    
    /**
     * 汇率（相对于人民币）
     */
    private BigDecimal exchangeRate;
    
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
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
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