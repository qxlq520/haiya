package com.haiya.video.service;

import com.haiya.video.entity.VideoStats;
import com.haiya.video.repository.VideoStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class VideoStatsService {

    @Autowired
    private VideoStatsRepository videoStatsRepository;

    public Optional<VideoStats> getVideoStats(Long videoId) {
        return videoStatsRepository.findByVideoId(videoId);
    }

    public VideoStats createVideoStats(Long videoId) {
        VideoStats videoStats = new VideoStats(videoId);
        return videoStatsRepository.save(videoStats);
    }

    @Transactional
    public VideoStats incrementViewCount(Long videoId) {
        VideoStats videoStats = videoStatsRepository.findByVideoId(videoId)
                .orElseGet(() -> new VideoStats(videoId));
        videoStats.setViewCount(videoStats.getViewCount() + 1);
        return videoStatsRepository.save(videoStats);
    }

    @Transactional
    public VideoStats incrementLikeCount(Long videoId) {
        VideoStats videoStats = videoStatsRepository.findByVideoId(videoId)
                .orElseGet(() -> new VideoStats(videoId));
        videoStats.setLikeCount(videoStats.getLikeCount() + 1);
        return videoStatsRepository.save(videoStats);
    }

    @Transactional
    public VideoStats incrementCommentCount(Long videoId) {
        VideoStats videoStats = videoStatsRepository.findByVideoId(videoId)
                .orElseGet(() -> new VideoStats(videoId));
        videoStats.setCommentCount(videoStats.getCommentCount() + 1);
        return videoStatsRepository.save(videoStats);
    }

    @Transactional
    public VideoStats incrementShareCount(Long videoId) {
        VideoStats videoStats = videoStatsRepository.findByVideoId(videoId)
                .orElseGet(() -> new VideoStats(videoId));
        videoStats.setShareCount(videoStats.getShareCount() + 1);
        return videoStatsRepository.save(videoStats);
    }
    
    @Transactional
    public VideoStats decrementLikeCount(Long videoId) {
        VideoStats videoStats = videoStatsRepository.findByVideoId(videoId)
                .orElseGet(() -> new VideoStats(videoId));
        // 确保不会出现负数
        if (videoStats.getLikeCount() > 0) {
            videoStats.setLikeCount(videoStats.getLikeCount() - 1);
        }
        return videoStatsRepository.save(videoStats);
    }
    
    @Transactional
    public VideoStats decrementCommentCount(Long videoId) {
        VideoStats videoStats = videoStatsRepository.findByVideoId(videoId)
                .orElseGet(() -> new VideoStats(videoId));
        // 确保不会出现负数
        if (videoStats.getCommentCount() > 0) {
            videoStats.setCommentCount(videoStats.getCommentCount() - 1);
        }
        return videoStatsRepository.save(videoStats);
    }
}