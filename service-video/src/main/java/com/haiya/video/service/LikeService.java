package com.haiya.video.service;

import com.haiya.video.entity.Like;
import com.haiya.video.entity.Video;
import com.haiya.video.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private VideoStatsService videoStatsService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private VideoService videoService;

    public Long getLikeCountByVideoId(Long videoId) {
        return likeRepository.countByVideoId(videoId);
    }

    public boolean isVideoLikedByUser(Long userId, Long videoId) {
        return likeRepository.existsByUserIdAndVideoId(userId, videoId);
    }

    @Transactional
    public Like likeVideo(Long userId, Long videoId) {
        // 检查是否已经点赞
        if (likeRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new RuntimeException("User already liked this video");
        }
        
        Like like = new Like(userId, videoId);
        Like savedLike = likeRepository.save(like);
        
        // 更新视频统计信息
        videoStatsService.incrementLikeCount(videoId);
        
        // 发送通知给视频作者
        try {
            Optional<Video> video = videoService.getVideoById(videoId);
            if (video.isPresent() && !video.get().getUserId().equals(userId)) {
                notificationService.createLikeNotification(
                    video.get().getUserId(), 
                    userId, 
                    videoId
                );
            }
        } catch (Exception e) {
            // 忽略通知发送错误，不影响主要流程
        }
        
        return savedLike;
    }

    @Transactional
    public boolean unlikeVideo(Long userId, Long videoId) {
        Optional<Like> like = likeRepository.findByUserIdAndVideoId(userId, videoId);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
            // 更新视频统计信息
            videoStatsService.decrementLikeCount(videoId);
            return true;
        }
        return false;
    }
}