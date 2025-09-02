package com.haiya.follow.controller;

import com.haiya.follow.entity.Follow;
import com.haiya.follow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * 关注用户
     * POST /api/follows
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> followUser(
            @RequestParam Long userId,
            @RequestParam Long targetUserId) {

        boolean success = followService.followUser(userId, targetUserId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "关注成功" : "关注失败");

        if (success) {
            Integer followingCount = followService.getFollowingCount(userId);
            Integer followersCount = followService.getFollowersCount(targetUserId);
            response.put("followingCount", followingCount);
            response.put("followersCount", followersCount);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 取消关注
     * DELETE /api/follows/{followedUserId}
     */
    @DeleteMapping("/{followedUserId}")
    public ResponseEntity<Map<String, Object>> unfollowUser(
            @RequestParam Long userId,
            @PathVariable Long followedUserId) {

        boolean success = followService.unfollowUser(userId, followedUserId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "取消关注成功" : "取消关注失败");

        if (success) {
            Integer followingCount = followService.getFollowingCount(userId);
            Integer followersCount = followService.getFollowersCount(followedUserId);
            response.put("followingCount", followingCount);
            response.put("followersCount", followersCount);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 获取关注列表
     * GET /api/follows/following?userId={userId}
     */
    @GetMapping("/following")
    public List<Follow> getFollowingList(@RequestParam Long userId) {
        return followService.getFollowingList(userId);
    }

    /**
     * 获取粉丝列表
     * GET /api/follows/followers?userId={userId}
     */
    @GetMapping("/followers")
    public List<Follow> getFollowersList(@RequestParam Long userId) {
        return followService.getFollowersList(userId);
    }

    /**
     * 获取关注状态
     * GET /api/follows/status?userId={userId}&targetUserId={targetUserId}
     */
    @GetMapping("/status")
    public Boolean isFollowing(
            @RequestParam Long userId,
            @RequestParam Long targetUserId) {
        return followService.isFollowing(userId, targetUserId);
    }
}