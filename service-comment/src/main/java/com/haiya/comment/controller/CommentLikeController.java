package com.haiya.comment.controller;

import com.haiya.comment.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentLikeController {
    
    @Autowired
    private CommentLikeService commentLikeService;
    
    /**
     * 点赞评论
     * POST /api/comments/{id}/like
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> likeComment(@PathVariable Long id, @RequestParam Long userId) {
        boolean success = commentLikeService.likeComment(id, userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "点赞成功" : "已经点赞");
        
        if (success) {
            Integer likeCount = commentLikeService.getCommentLikeCount(id);
            response.put("likeCount", likeCount);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 取消点赞评论
     * DELETE /api/comments/{id}/like
     */
    @DeleteMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> unlikeComment(@PathVariable Long id, @RequestParam Long userId) {
        boolean success = commentLikeService.unlikeComment(id, userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "取消点赞成功" : "未找到点赞记录");
        
        if (success) {
            Integer likeCount = commentLikeService.getCommentLikeCount(id);
            response.put("likeCount", likeCount);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取评论点赞数
     */
    @GetMapping("/{id}/like/count")
    public Integer getCommentLikeCount(@PathVariable Long id) {
        return commentLikeService.getCommentLikeCount(id);
    }
    
    /**
     * 获取评论点赞状态
     */
    @GetMapping("/{id}/like/status")
    public Boolean isCommentLikedByUser(@PathVariable Long id, @RequestParam Long userId) {
        return commentLikeService.isCommentLikedByUser(id, userId);
    }
}