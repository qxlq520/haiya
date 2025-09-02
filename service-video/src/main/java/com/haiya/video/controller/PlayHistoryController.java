package com.haiya.video.controller;

import com.haiya.video.entity.PlayHistory;
import com.haiya.video.service.PlayHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/play-history")
public class PlayHistoryController {

    @Autowired
    private PlayHistoryService playHistoryService;

    @GetMapping("/user/{userId}")
    public List<PlayHistory> getPlayHistoryByUserId(@PathVariable Long userId) {
        return playHistoryService.getPlayHistoryByUserId(userId);
    }

    @GetMapping("/user/{userId}/video/{videoId}")
    public Optional<PlayHistory> getPlayHistoryByUserAndVideo(
            @PathVariable Long userId, 
            @PathVariable Long videoId) {
        return playHistoryService.getPlayHistoryByUserAndVideo(userId, videoId);
    }

    @PostMapping
    public PlayHistory recordPlayHistory(
            @RequestParam Long userId,
            @RequestParam Long videoId,
            @RequestParam(required = false, defaultValue = "0") Long playDuration,
            @RequestParam(required = false, defaultValue = "0") Long playProgress) {
        return playHistoryService.recordPlayHistory(userId, videoId, playDuration, playProgress);
    }

    @DeleteMapping("/user/{userId}/video/{videoId}")
    public void deletePlayHistory(
            @PathVariable Long userId, 
            @PathVariable Long videoId) {
        playHistoryService.deletePlayHistory(userId, videoId);
    }

    @DeleteMapping("/user/{userId}")
    public void clearPlayHistory(@PathVariable Long userId) {
        playHistoryService.clearPlayHistory(userId);
    }
}