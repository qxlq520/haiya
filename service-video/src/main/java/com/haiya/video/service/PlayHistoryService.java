package com.haiya.video.service;

import com.haiya.video.entity.PlayHistory;
import com.haiya.video.repository.PlayHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PlayHistoryService {

    @Autowired
    private PlayHistoryRepository playHistoryRepository;
    
    @Autowired
    private VideoStatsService videoStatsService;

    public List<PlayHistory> getPlayHistoryByUserId(Long userId) {
        return playHistoryRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    public Optional<PlayHistory> getPlayHistoryByUserAndVideo(Long userId, Long videoId) {
        return playHistoryRepository.findByUserIdAndVideoId(userId, videoId);
    }

    @Transactional
    public PlayHistory recordPlayHistory(Long userId, Long videoId, Long playDuration, Long playProgress) {
        Optional<PlayHistory> existingHistory = playHistoryRepository.findByUserIdAndVideoId(userId, videoId);
        
        PlayHistory playHistory;
        if (existingHistory.isPresent()) {
            // 更新现有记录
            playHistory = existingHistory.get();
            playHistory.setPlayDuration(playDuration);
            playHistory.setPlayProgress(playProgress);
        } else {
            // 创建新记录
            playHistory = new PlayHistory(userId, videoId);
            playHistory.setPlayDuration(playDuration);
            playHistory.setPlayProgress(playProgress);
        }
        
        // 增加视频观看次数
        videoStatsService.incrementViewCount(videoId);
        
        return playHistoryRepository.save(playHistory);
    }

    @Transactional
    public void deletePlayHistory(Long userId, Long videoId) {
        playHistoryRepository.findByUserIdAndVideoId(userId, videoId)
                .ifPresent(playHistoryRepository::delete);
    }

    @Transactional
    public void clearPlayHistory(Long userId) {
        List<PlayHistory> histories = playHistoryRepository.findByUserIdOrderByUpdatedAtDesc(userId);
        playHistoryRepository.deleteAll(histories);
    }
}