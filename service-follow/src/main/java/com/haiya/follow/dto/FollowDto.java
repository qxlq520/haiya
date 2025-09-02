package com.haiya.follow.dto;

public class FollowDto {
    private Long id;
    private Long followerId;
    private Long followedId;
    private Long createdAt;
    
    // Constructors
    public FollowDto() {}
    
    public FollowDto(Long id, Long followerId, Long followedId, Long createdAt) {
        this.id = id;
        this.followerId = followerId;
        this.followedId = followedId;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getFollowerId() {
        return followerId;
    }
    
    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
    
    public Long getFollowedId() {
        return followedId;
    }
    
    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }
    
    public Long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}