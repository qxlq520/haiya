package com.haiya.video.controller;

import com.haiya.video.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/platform")
    public Map<String, Object> getPlatformStats() {
        return statsService.getPlatformStats();
    }

    @GetMapping("/user/{userId}")
    public Map<String, Object> getUserStats(@PathVariable Long userId) {
        return statsService.getUserStats(userId);
    }

    @GetMapping("/video/{videoId}")
    public Map<String, Object> getVideoStats(@PathVariable Long videoId) {
        return statsService.getVideoStats(videoId);
    }

    @GetMapping("/videos/trend")
    public Map<String, Integer> getVideoTrendStats(@RequestParam(defaultValue = "7") int days) {
        return statsService.getVideoTrendStats(days);
    }
}