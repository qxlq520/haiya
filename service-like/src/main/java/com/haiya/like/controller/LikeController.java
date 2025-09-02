package com.haiya.like.controller;

import com.haiya.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    
    @Autowired
    private LikeService likeService;
    
    /**
     * 点赞
     * POST /api/likes
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> likeTarget(
            @RequestParam Long targetId,
            @RequestParam String targetType,
            @RequestParam Long userId) {
        
        boolean success = likeService.likeTarget(targetId, targetType, userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "点赞成功" : "已经点赞");
        
        if (success) {
            Integer likeCount = likeService.getTargetLikeCount(targetId, targetType);
            response.put("likeCount", likeCount);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 取消点赞
     * DELETE /api/likes
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> unlikeTarget(
            @RequestParam Long targetId,
            @RequestParam String targetType,
            @RequestParam Long userId) {
        
        boolean success = likeService.unlikeTarget(targetId, targetType, userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "取消点赞成功" : "未找到点赞记录");
        
        if (success) {
            Integer likeCount = likeService.getTargetLikeCount(targetId, targetType);
            response.put("likeCount", likeCount);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取点赞数
     * GET /api/likes/count?targetId={targetId}&type={type}
     */
    @GetMapping("/count")
    public Integer getTargetLikeCount(
            @RequestParam Long targetId,
            @RequestParam String type) {
        return likeService.getTargetLikeCount(targetId, type);
    }
    
    /**
     * 获取用户点赞列表
     * GET /api/likes?userId={userId}
     */
    @GetMapping
    public Object getUserLikes(@RequestParam Long userId) {
        return likeService.getUserLikes(userId);
    }
    
    /**
     * 获取点赞状态
     */
    @GetMapping("/status")
    public Boolean isTargetLikedByUser(
            @RequestParam Long targetId,
            @RequestParam String targetType,
            @RequestParam Long userId) {
        return likeService.isTargetLikedByUser(targetId, targetType, userId);
    }
}