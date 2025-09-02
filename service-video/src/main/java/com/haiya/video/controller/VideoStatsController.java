package com.haiya.video.controller;

import com.haiya.video.entity.VideoStats;
import com.haiya.video.service.VideoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/video-stats")
public class VideoStatsController {

    @Autowired
    private VideoStatsService videoStatsService;

    @GetMapping("/video/{videoId}")
    public ResponseEntity<VideoStats> getVideoStats(@PathVariable Long videoId) {
        Optional<VideoStats> videoStats = videoStatsService.getVideoStats(videoId);
        if (videoStats.isPresent()) {
            return ResponseEntity.ok(videoStats.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/video/{videoId}/view")
    public ResponseEntity<VideoStats> incrementViewCount(@PathVariable Long videoId) {
        VideoStats videoStats = videoStatsService.incrementViewCount(videoId);
        return ResponseEntity.ok(videoStats);
    }

    @PostMapping("/video/{videoId}/like")
    public ResponseEntity<VideoStats> incrementLikeCount(@PathVariable Long videoId) {
        VideoStats videoStats = videoStatsService.incrementLikeCount(videoId);
        return ResponseEntity.ok(videoStats);
    }

    @PostMapping("/video/{videoId}/comment")
    public ResponseEntity<VideoStats> incrementCommentCount(@PathVariable Long videoId) {
        VideoStats videoStats = videoStatsService.incrementCommentCount(videoId);
        return ResponseEntity.ok(videoStats);
    }

    @PostMapping("/video/{videoId}/share")
    public ResponseEntity<VideoStats> incrementShareCount(@PathVariable Long videoId) {
        VideoStats videoStats = videoStatsService.incrementShareCount(videoId);
        return ResponseEntity.ok(videoStats);
    }
}