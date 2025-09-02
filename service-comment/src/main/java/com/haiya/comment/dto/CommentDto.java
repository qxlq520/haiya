package com.haiya.comment.dto;

public class CommentDto {
    private Long id;
    private Long userId;
    private Long videoId;
    private Long parentId;
    private String content;
    private Integer likeCount;
    private Integer replyCount;
    private Long createdAt;
    private Long updatedAt;
    
    // Constructors
    public CommentDto() {}
    
    public CommentDto(Long id, Long userId, Long videoId, Long parentId, String content, 
                     Integer likeCount, Integer replyCount, Long createdAt, Long updatedAt) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
        this.parentId = parentId;
        this.content = content;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
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
    
    public Long getVideoId() {
        return videoId;
    }
    
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Integer getLikeCount() {
        return likeCount;
    }
    
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
    
    public Integer getReplyCount() {
        return replyCount;
    }
    
    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
    
    public Long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    
    public Long getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}