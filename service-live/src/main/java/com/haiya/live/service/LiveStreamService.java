package com.haiya.live.service;

import com.haiya.live.entity.LiveStream;
import com.haiya.live.repository.LiveStreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LiveStreamService {

    @Autowired
    private LiveStreamRepository liveStreamRepository;

    public List<LiveStream> getAllLiveStreams() {
        return liveStreamRepository.findAll();
    }

    public List<LiveStream> getLiveStreamsByUserId(Long userId) {
        return liveStreamRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<LiveStream> getLiveStreamsByStatus(String status) {
        return liveStreamRepository.findByStatusOrderByStartedAtDesc(status);
    }

    public Optional<LiveStream> getLiveStreamById(Long id) {
        return liveStreamRepository.findById(id);
    }

    public LiveStream createLiveStream(LiveStream liveStream) {
        // 生成唯一的推流密钥
        String streamKey = UUID.randomUUID().toString().replace("-", "");
        liveStream.setStreamKey(streamKey);
        
        // 生成推流地址 (示例)
        String streamUrl = "rtmp://live.haiya.com/live/" + streamKey;
        liveStream.setStreamUrl(streamUrl);
        
        return liveStreamRepository.save(liveStream);
    }

    public Optional<LiveStream> updateLiveStream(Long id, LiveStream liveStreamDetails) {
        return liveStreamRepository.findById(id).map(liveStream -> {
            liveStream.setTitle(liveStreamDetails.getTitle());
            return liveStreamRepository.save(liveStream);
        });
    }

    public boolean deleteLiveStream(Long id) {
        return liveStreamRepository.findById(id).map(liveStream -> {
            liveStreamRepository.delete(liveStream);
            return true;
        }).orElse(false);
    }

    public Optional<LiveStream> startLiveStream(String streamKey) {
        return liveStreamRepository.findByStreamKey(streamKey).map(liveStream -> {
            liveStream.setStatus("LIVE");
            liveStream.setStartedAt(new Date());
            return liveStreamRepository.save(liveStream);
        });
    }

    public Optional<LiveStream> stopLiveStream(String streamKey) {
        return liveStreamRepository.findByStreamKey(streamKey).map(liveStream -> {
            liveStream.setStatus("FINISHED");
            liveStream.setEndedAt(new Date());
            return liveStreamRepository.save(liveStream);
        });
    }

    public List<LiveStream> getTopLiveStreams() {
        return liveStreamRepository.findTop10ByStatusOrderByViewerCountDesc("LIVE");
    }
}