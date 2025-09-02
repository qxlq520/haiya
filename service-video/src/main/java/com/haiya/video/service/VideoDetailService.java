package com.haiya.video.service;

import com.haiya.video.dto.VideoDetailDTO;
import com.haiya.video.dto.VideoStatsDTO;
import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;
import com.haiya.video.entity.VideoStats;
import com.haiya.video.repository.VideoRepository;
import com.haiya.video.repository.UserProfileRepository;
import com.haiya.video.repository.VideoStatsRepository;
import com.haiya.video.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoDetailService {

    @Autowired
    private VideoRepository videoRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private VideoStatsRepository videoStatsRepository;
    
    @Autowired
    private LikeRepository likeRepository;

    public Optional<VideoDetailDTO> getVideoDetail(Long videoId) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (!videoOpt.isPresent()) {
            return Optional.empty();
        }
        
        Video video = videoOpt.get();
        Optional<UserProfile> authorOpt = userProfileRepository.findByUserId(video.getUserId());
        Optional<VideoStats> statsOpt = videoStatsRepository.findByVideoId(videoId);
        
        // 构建统计数据DTO
        VideoStatsDTO statsDTO = new VideoStatsDTO();
        statsDTO.setVideoId(videoId);
        statsDTO.setViewCount(statsOpt.map(VideoStats::getViewCount).orElse(0L));
        statsDTO.setLikeCount(statsOpt.map(VideoStats::getLikeCount).orElse(0L));
        statsDTO.setCommentCount(statsOpt.map(VideoStats::getCommentCount).orElse(0L));
        statsDTO.setShareCount(statsOpt.map(VideoStats::getShareCount).orElse(0L));
        
        // 构建视频详情DTO
        VideoDetailDTO detailDTO = new VideoDetailDTO();
        detailDTO.setVideo(video);
        detailDTO.setAuthor(authorOpt.orElse(null));
        detailDTO.setStats(statsDTO);
        
        return Optional.of(detailDTO);
    }
    
    public Optional<VideoDetailDTO> getVideoDetailWithUserLikeStatus(Long videoId, Long userId) {
        Optional<VideoDetailDTO> detailOpt = getVideoDetail(videoId);
        if (!detailOpt.isPresent()) {
            return Optional.empty();
        }
        
        VideoDetailDTO detailDTO = detailOpt.get();
        boolean isLiked = likeRepository.existsByUserIdAndVideoId(userId, videoId);
        detailDTO.setIsLiked(isLiked);
        
        return Optional.of(detailDTO);
    }
}