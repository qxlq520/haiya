package com.haiya.video.dto;

import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;

public class FavoriteDTO {
    private Long id;
    private Long userId;
    private Video video;
    private UserProfile userProfile;
    private Long createdAt;

    // Constructors
    public FavoriteDTO() {}

    public FavoriteDTO(Long id, Long userId, Video video, UserProfile userProfile, Long createdAt) {
        this.id = id;
        this.userId = userId;
        this.video = video;
        this.userProfile = userProfile;
        this.createdAt = createdAt;
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

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}