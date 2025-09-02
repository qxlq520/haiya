package com.haiya.video.service;

import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;
import com.haiya.video.entity.VideoStats;
import com.haiya.video.repository.VideoRepository;
import com.haiya.video.repository.UserProfileRepository;
import com.haiya.video.repository.VideoStatsRepository;
import com.haiya.video.repository.CommentRepository;
import com.haiya.video.repository.LikeRepository;
import com.haiya.video.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class StatsService {

    @Autowired
    private VideoRepository videoRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private VideoStatsRepository videoStatsRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private FollowRepository followRepository;

    /**
     * 获取平台总体统计数据
     * @return 包含各项统计数据的Map
     */
    public Map<String, Object> getPlatformStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalVideos", videoRepository.count());
        stats.put("totalUsers", userProfileRepository.count());
        stats.put("totalComments", commentRepository.count());
        stats.put("totalLikes", likeRepository.count());
        stats.put("totalFollows", followRepository.count());
        
        return stats;
    }

    /**
     * 获取用户统计数据
     * @param userId 用户ID
     * @return 包含用户各项统计数据的Map
     */
    public Map<String, Object> getUserStats(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        
        // 用户基本信息
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile.isPresent()) {
            stats.put("username", userProfile.get().getUsername());
            stats.put("followerCount", userProfile.get().getFollowerCount());
            stats.put("followingCount", userProfile.get().getFollowingCount());
            stats.put("videoCount", userProfile.get().getVideoCount());
        }
        
        // 用户视频统计
        List<Video> userVideos = videoRepository.findByUserIdOrderByCreatedAtDesc(userId);
        stats.put("userVideos", userVideos.size());
        
        // 用户获赞总数
        long totalLikes = userVideos.stream()
                .mapToLong(Video::getLikeCount)
                .sum();
        stats.put("totalLikesReceived", totalLikes);
        
        // 用户评论数
        int commentCount = commentRepository.findByUserIdOrderByCreatedAtDesc(userId).size();
        stats.put("totalCommentsMade", commentCount);
        
        return stats;
    }

    /**
     * 获取视频统计数据
     * @param videoId 视频ID
     * @return 包含视频各项统计数据的Map
     */
    public Map<String, Object> getVideoStats(Long videoId) {
        Map<String, Object> stats = new HashMap<>();
        
        Optional<Video> video = videoRepository.findById(videoId);
        if (!video.isPresent()) {
            return stats;
        }
        
        stats.put("video", video.get());
        
        Optional<VideoStats> videoStats = videoStatsRepository.findByVideoId(videoId);
        if (videoStats.isPresent()) {
            stats.put("viewCount", videoStats.get().getViewCount());
            stats.put("likeCount", videoStats.get().getLikeCount());
            stats.put("commentCount", videoStats.get().getCommentCount());
            stats.put("shareCount", videoStats.get().getShareCount());
        } else {
            stats.put("viewCount", video.get().getViewCount());
            stats.put("likeCount", video.get().getLikeCount());
            stats.put("commentCount", video.get().getCommentCount());
            stats.put("shareCount", 0L);
        }
        
        return stats;
    }

    /**
     * 获取最近几天的视频发布趋势
     * @param days 天数
     * @return 每天的视频发布数量
     */
    public Map<String, Integer> getVideoTrendStats(int days) {
        Map<String, Integer> trend = new LinkedHashMap<>();
        
        List<Video> allVideos = videoRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        
        // 初始化日期
        for (int i = days - 1; i >= 0; i--) {
            LocalDateTime date = now.minusDays(i);
            String dateStr = date.toLocalDate().toString();
            trend.put(dateStr, 0);
        }
        
        // 统计每天的视频数量
        for (Video video : allVideos) {
            Long createdAtMillis = video.getCreatedAt();
            if (createdAtMillis != null) {
                LocalDateTime createdAt = LocalDateTime.ofInstant(
                    java.time.Instant.ofEpochMilli(createdAtMillis), 
                    ZoneId.systemDefault());
                String dateStr = createdAt.toLocalDate().toString();
                if (trend.containsKey(dateStr)) {
                    trend.put(dateStr, trend.get(dateStr) + 1);
                }
            }
        }
        
        return trend;
    }
}