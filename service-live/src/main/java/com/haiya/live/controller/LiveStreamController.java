package com.haiya.live.controller;

import com.haiya.live.entity.LiveStream;
import com.haiya.live.service.LiveStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livestreams")
public class LiveStreamController {

    @Autowired
    private LiveStreamService liveStreamService;

    /**
     * 获取所有直播流
     */
    @GetMapping
    public List<LiveStream> getAllLiveStreams() {
        return liveStreamService.getAllLiveStreams();
    }

    /**
     * 根据用户ID获取直播流
     */
    @GetMapping("/user/{userId}")
    public List<LiveStream> getLiveStreamsByUserId(@PathVariable Long userId) {
        return liveStreamService.getLiveStreamsByUserId(userId);
    }

    /**
     * 根据状态获取直播流
     */
    @GetMapping("/status/{status}")
    public List<LiveStream> getLiveStreamsByStatus(@PathVariable String status) {
        return liveStreamService.getLiveStreamsByStatus(status);
    }

    /**
     * 获取热门直播流
     */
    @GetMapping("/top")
    public List<LiveStream> getTopLiveStreams() {
        return liveStreamService.getTopLiveStreams();
    }

    /**
     * 根据ID获取直播流详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<LiveStream> getLiveStreamById(@PathVariable Long id) {
        Optional<LiveStream> liveStream = liveStreamService.getLiveStreamById(id);
        if (liveStream.isPresent()) {
            return ResponseEntity.ok(liveStream.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 创建直播流
     */
    @PostMapping
    public LiveStream createLiveStream(@RequestBody LiveStream liveStream) {
        return liveStreamService.createLiveStream(liveStream);
    }

    /**
     * 更新直播流
     */
    @PutMapping("/{id}")
    public ResponseEntity<LiveStream> updateLiveStream(@PathVariable Long id, @RequestBody LiveStream liveStreamDetails) {
        Optional<LiveStream> updatedLiveStream = liveStreamService.updateLiveStream(id, liveStreamDetails);
        if (updatedLiveStream.isPresent()) {
            return ResponseEntity.ok(updatedLiveStream.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除直播流
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiveStream(@PathVariable Long id) {
        boolean deleted = liveStreamService.deleteLiveStream(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 开始直播
     */
    @PostMapping("/start/{streamKey}")
    public ResponseEntity<LiveStream> startLiveStream(@PathVariable String streamKey) {
        Optional<LiveStream> liveStream = liveStreamService.startLiveStream(streamKey);
        if (liveStream.isPresent()) {
            return ResponseEntity.ok(liveStream.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 停止直播
     */
    @PostMapping("/stop/{streamKey}")
    public ResponseEntity<LiveStream> stopLiveStream(@PathVariable String streamKey) {
        Optional<LiveStream> liveStream = liveStreamService.stopLiveStream(streamKey);
        if (liveStream.isPresent()) {
            return ResponseEntity.ok(liveStream.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}