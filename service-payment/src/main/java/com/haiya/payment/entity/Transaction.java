package com.haiya.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 虚拟货币交易记录实体类
 */
public class Transaction {
    /**
     * 交易ID
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
     * 交易类型（充值、消费、转账等）
     */
    private TransactionType type;
    
    /**
     * 交易金额
     */
    private BigDecimal amount;
    
    /**
     * 交易前余额
     */
    private BigDecimal balanceBefore;
    
    /**
     * 交易后余额
     */
    private BigDecimal balanceAfter;
    
    /**
     * 关联订单ID
     */
    private Long relatedOrderId;
    
    /**
     * 交易状态
     */
    private TransactionStatus status;
    
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
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }
    
    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }
    
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }
    
    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    
    public Long getRelatedOrderId() {
        return relatedOrderId;
    }
    
    public void setRelatedOrderId(Long relatedOrderId) {
        this.relatedOrderId = relatedOrderId;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public void setStatus(TransactionStatus status) {
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
     * 交易类型枚举
     */
    public enum TransactionType {
        RECHARGE,      // 充值
        CONSUME,       // 消费
        TRANSFER_IN,   // 转入
        TRANSFER_OUT,  // 转出
        REFUND         // 退款
    }
    
    /**
     * 交易状态枚举
     */
    public enum TransactionStatus {
        PENDING,   // 处理中
        SUCCESS,   // 成功
        FAILED     // 失败
    }
}