package com.haiya.like.dto;

public class LikeDto {
    private Long id;
    private Long targetId;
    private String targetType;
    private Long userId;
    private Long createdAt;
    
    // Constructors
    public LikeDto() {}
    
    public LikeDto(Long id, Long targetId, String targetType, Long userId, Long createdAt) {
        this.id = id;
        this.targetId = targetId;
        this.targetType = targetType;
        this.userId = userId;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTargetId() {
        return targetId;
    }
    
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
    
    public String getTargetType() {
        return targetType;
    }
    
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}