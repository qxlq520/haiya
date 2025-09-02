package com.haiya.recommend.entity;

/**
 * 用户评分实体类
 */
public class UserRating {
    private Long userId;
    private Long videoId;
    private Double score;
    private String category;
    
    public UserRating() {
    }
    
    public UserRating(Long userId, Long videoId, Double score, String category) {
        this.userId = userId;
        this.videoId = videoId;
        this.score = score;
        this.category = category;
    }
    
    // Getters and Setters
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
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}