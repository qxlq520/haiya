package com.haiya.analytics.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user_activities")
public class UserActivity {
    @Id
    private String id;
    
    private Long userId;
    private String activityType; // VIEW_VIDEO, LIKE, COMMENT, SHARE, FOLLOW
    private Long targetId; // 目标ID（视频ID、用户ID等）
    private String targetType; // VIDEO, USER, COMMENT
    private Long timestamp;
    private String userAgent;
    private String ipAddress;
    private Long duration; // 观看时长（毫秒）

    // Constructors
    public UserActivity() {}

    public UserActivity(Long userId, String activityType, Long targetId, String targetType) {
        this.userId = userId;
        this.activityType = activityType;
        this.targetId = targetId;
        this.targetType = targetType;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}