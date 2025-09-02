package com.haiya.video.dto;

import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;

public class VideoDetailDTO {
    private Video video;
    private UserProfile author;
    private VideoStatsDTO stats;
    private Boolean isLiked; // 当前用户是否点赞了该视频
    
    // Constructors
    public VideoDetailDTO() {}
    
    public VideoDetailDTO(Video video, UserProfile author, VideoStatsDTO stats) {
        this.video = video;
        this.author = author;
        this.stats = stats;
    }
    
    public VideoDetailDTO(Video video, UserProfile author, VideoStatsDTO stats, Boolean isLiked) {
        this.video = video;
        this.author = author;
        this.stats = stats;
        this.isLiked = isLiked;
    }
    
    // Getters and Setters
    public Video getVideo() {
        return video;
    }
    
    public void setVideo(Video video) {
        this.video = video;
    }
    
    public UserProfile getAuthor() {
        return author;
    }
    
    public void setAuthor(UserProfile author) {
        this.author = author;
    }
    
    public VideoStatsDTO getStats() {
        return stats;
    }
    
    public void setStats(VideoStatsDTO stats) {
        this.stats = stats;
    }
    
    public Boolean getIsLiked() {
        return isLiked;
    }
    
    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}