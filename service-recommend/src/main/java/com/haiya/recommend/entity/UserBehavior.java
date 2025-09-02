package com.haiya.recommend.entity;

import java.time.LocalDateTime;

/**
 * 用户行为实体类
 */
public class UserBehavior {
    /**
     * 行为ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 视频ID
     */
    private Long videoId;
    
    /**
     * 行为类型
     */
    private BehaviorType type;
    
    /**
     * 行为值（如观看时长）
     */
    private Double value;
    
    /**
     * 时间戳
     */
    private LocalDateTime timestamp;
    
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
    
    public BehaviorType getType() {
        return type;
    }
    
    public void setType(BehaviorType type) {
        this.type = type;
    }
    
    public Double getValue() {
        return value;
    }
    
    public void setValue(Double value) {
        this.value = value;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * 行为类型枚举
     */
    public enum BehaviorType {
        VIEW,        // 观看
        LIKE,        // 点赞
        COMMENT,     // 评论
        SHARE,       // 分享
        FOLLOW,      // 关注
        COMPLETE,    // 完播
        REWATCH      // 重播
    }
}