package com.haiya.user.entity;

import java.time.LocalDateTime;

/**
 * 账户安全实体类
 */
public class AccountSecurity {
    /**
     * 安全记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 登录保护状态
     */
    private Boolean loginProtectionEnabled;
    
    /**
     * 异常登录检测状态
     */
    private Boolean anomalyDetectionEnabled;
    
    /**
     * 双因素认证状态
     */
    private Boolean twoFactorEnabled;
    
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    
    /**
     * 最后登录设备
     */
    private String lastLoginDevice;
    
    /**
     * 安全评分
     */
    private Integer securityScore;
    
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
    
    public Boolean getLoginProtectionEnabled() {
        return loginProtectionEnabled;
    }
    
    public void setLoginProtectionEnabled(Boolean loginProtectionEnabled) {
        this.loginProtectionEnabled = loginProtectionEnabled;
    }
    
    public Boolean getAnomalyDetectionEnabled() {
        return anomalyDetectionEnabled;
    }
    
    public void setAnomalyDetectionEnabled(Boolean anomalyDetectionEnabled) {
        this.anomalyDetectionEnabled = anomalyDetectionEnabled;
    }
    
    public Boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }
    
    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }
    
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public String getLastLoginIp() {
        return lastLoginIp;
    }
    
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    
    public String getLastLoginDevice() {
        return lastLoginDevice;
    }
    
    public void setLastLoginDevice(String lastLoginDevice) {
        this.lastLoginDevice = lastLoginDevice;
    }
    
    public Integer getSecurityScore() {
        return securityScore;
    }
    
    public void setSecurityScore(Integer securityScore) {
        this.securityScore = securityScore;
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