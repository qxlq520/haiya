package com.haiya.video.controller;

import com.haiya.video.entity.ContentRecommendation;
import com.haiya.video.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    /**
     * 生成个性化推荐
     * @param userId 用户ID
     * @param limit 推荐数量
     * @return 推荐内容列表
     */
    @GetMapping("/personalized")
    public ResponseEntity<List<ContentRecommendation>> getPersonalizedRecommendations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        List<ContentRecommendation> recommendations = recommendationService
            .generatePersonalizedRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 生成热门内容推荐
     * @param userId 用户ID
     * @param limit 推荐数量
     * @return 推荐内容列表
     */
    @GetMapping("/popular")
    public ResponseEntity<List<ContentRecommendation>> getPopularRecommendations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        List<ContentRecommendation> recommendations = recommendationService
            .generatePopularRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 生成趋势内容推荐
     * @param userId 用户ID
     * @param limit 推荐数量
     * @return 推荐内容列表
     */
    @GetMapping("/trending")
    public ResponseEntity<List<ContentRecommendation>> getTrendingRecommendations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        List<ContentRecommendation> recommendations = recommendationService
            .generateTrendingRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 获取用户的推荐内容
     * @param userId 用户ID
     * @param limit 数量限制
     * @return 推荐内容列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ContentRecommendation>> getUserRecommendations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        List<ContentRecommendation> recommendations = recommendationService
            .getUserRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 记录用户与推荐内容的互动
     * @param recommendationId 推荐ID
     * @return 操作结果
     */
    @PostMapping("/{recommendationId}/interact")
    public ResponseEntity<Map<String, String>> recordUserInteraction(@PathVariable Long recommendationId) {
        recommendationService.recordUserInteraction(recommendationId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User interaction recorded successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 批量创建推荐内容
     * @param recommendations 推荐内容列表
     * @return 创建的推荐内容列表
     */
    @PostMapping("/batch")
    public ResponseEntity<List<ContentRecommendation>> createRecommendations(
            @RequestBody List<ContentRecommendation> recommendations) {
        List<ContentRecommendation> createdRecommendations = recommendationService
            .createRecommendations(recommendations);
        return ResponseEntity.ok(createdRecommendations);
    }

    /**
     * 删除过期的推荐内容
     * @param days 天数
     * @return 删除结果
     */
    @DeleteMapping("/expired")
    public ResponseEntity<Map<String, Object>> deleteExpiredRecommendations(@RequestParam int days) {
        int deletedCount = recommendationService.deleteExpiredRecommendations(days);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expired recommendations deleted successfully");
        response.put("deletedCount", deletedCount);
        return ResponseEntity.ok(response);
    }
}