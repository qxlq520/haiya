package com.haiya.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户虚拟货币账户实体类
 */
public class UserAccount {
    /**
     * 账户ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 货币ID
     */
    private Long currencyId;
    
    /**
     * 账户余额
     */
    private BigDecimal balance;
    
    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;
    
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
    
    public Long getCurrencyId() {
        return currencyId;
    }
    
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }
    
    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
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