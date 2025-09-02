package com.haiya.user.entity;

import java.time.LocalDateTime;

/**
 * 数据安全实体类
 */
public class DataSecurity {
    /**
     * 数据安全记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 数据加密状态
     */
    private Boolean encryptionEnabled;
    
    /**
     * 隐私设置
     */
    private PrivacySetting privacySetting;
    
    /**
     * 数据访问日志保留天数
     */
    private Integer logRetentionDays;
    
    /**
     * 最后数据访问时间
     */
    private LocalDateTime lastDataAccessTime;
    
    /**
     * 最后数据访问IP
     */
    private String lastDataAccessIp;
    
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
    
    public Boolean getEncryptionEnabled() {
        return encryptionEnabled;
    }
    
    public void setEncryptionEnabled(Boolean encryptionEnabled) {
        this.encryptionEnabled = encryptionEnabled;
    }
    
    public PrivacySetting getPrivacySetting() {
        return privacySetting;
    }
    
    public void setPrivacySetting(PrivacySetting privacySetting) {
        this.privacySetting = privacySetting;
    }
    
    public Integer getLogRetentionDays() {
        return logRetentionDays;
    }
    
    public void setLogRetentionDays(Integer logRetentionDays) {
        this.logRetentionDays = logRetentionDays;
    }
    
    public LocalDateTime getLastDataAccessTime() {
        return lastDataAccessTime;
    }
    
    public void setLastDataAccessTime(LocalDateTime lastDataAccessTime) {
        this.lastDataAccessTime = lastDataAccessTime;
    }
    
    public String getLastDataAccessIp() {
        return lastDataAccessIp;
    }
    
    public void setLastDataAccessIp(String lastDataAccessIp) {
        this.lastDataAccessIp = lastDataAccessIp;
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
     * 隐私设置枚举
     */
    public enum PrivacySetting {
        PUBLIC,         // 公开
        FRIENDS_ONLY,   // 仅好友
        PRIVATE         // 私密
    }
}