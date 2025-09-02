package com.haiya.video.dto;

public class VideoStatsDTO {
    private Long videoId;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
    private Long shareCount;
    
    // Constructors
    public VideoStatsDTO() {}
    
    public VideoStatsDTO(Long videoId, Long viewCount, Long likeCount, Long commentCount, Long shareCount) {
        this.videoId = videoId;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }
    
    // Getters and Setters
    public Long getVideoId() {
        return videoId;
    }
    
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
    
    public Long getViewCount() {
        return viewCount;
    }
    
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
    
    public Long getLikeCount() {
        return likeCount;
    }
    
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
    
    public Long getCommentCount() {
        return commentCount;
    }
    
    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
    
    public Long getShareCount() {
        return shareCount;
    }
    
    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }
}