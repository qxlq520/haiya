package com.haiya.analytics.controller;

import com.haiya.analytics.entity.UserActivity;
import com.haiya.analytics.service.impl.AnalyticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsServiceImpl analyticsService;

    @PostMapping("/activity")
    public UserActivity recordActivity(@RequestBody UserActivity activity) {
        return analyticsService.recordActivity(activity);
    }

    @GetMapping("/user/{userId}")
    public List<UserActivity> getUserActivities(@PathVariable Long userId) {
        return analyticsService.getUserActivities(userId);
    }

    @GetMapping("/user/{userId}/type/{activityType}")
    public List<UserActivity> getUserActivitiesByType(@PathVariable Long userId, @PathVariable String activityType) {
        return analyticsService.getUserActivitiesByType(userId, activityType);
    }

    @GetMapping("/user/{userId}/summary")
    public Map<String, Object> getUserActivitySummary(@PathVariable Long userId) {
        return analyticsService.getUserActivitySummary(userId);
    }

    @GetMapping("/platform")
    public Map<String, Object> getPlatformAnalytics() {
        return analyticsService.getPlatformAnalytics();
    }

    @GetMapping("/content/{targetId}/{targetType}")
    public List<UserActivity> getActivitiesForContent(
            @PathVariable Long targetId, 
            @PathVariable String targetType) {
        return analyticsService.getActivitiesForContent(targetId, targetType);
    }

    @GetMapping("/popular-content")
    public Map<String, Long> getPopularContent(@RequestParam(defaultValue = "10") int limit) {
        return analyticsService.getPopularContent(limit);
    }
}