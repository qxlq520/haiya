package com.haiya.video.controller;

import com.haiya.video.entity.Follow;
import com.haiya.video.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/user/{userId}/following/count")
    public Long getFollowingCount(@PathVariable Long userId) {
        return followService.getFollowingCount(userId);
    }

    @GetMapping("/user/{userId}/followers/count")
    public Long getFollowerCount(@PathVariable Long userId) {
        return followService.getFollowerCount(userId);
    }

    @GetMapping("/user/{userId}/following")
    public List<Follow> getFollowingList(@PathVariable Long userId) {
        return followService.getFollowingList(userId);
    }

    @GetMapping("/user/{userId}/followers")
    public List<Follow> getFollowerList(@PathVariable Long userId) {
        return followService.getFollowerList(userId);
    }

    @GetMapping("/follower/{followerId}/following/{followingId}")
    public boolean isFollowing(@PathVariable Long followerId, @PathVariable Long followingId) {
        return followService.isFollowing(followerId, followingId);
    }

    @PostMapping("/follower/{followerId}/following/{followingId}")
    public ResponseEntity<?> followUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        try {
            Follow follow = followService.followUser(followerId, followingId);
            return ResponseEntity.ok(follow);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/follower/{followerId}/following/{followingId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        boolean unfollowed = followService.unfollowUser(followerId, followingId);
        if (unfollowed) {
            return ResponseEntity.ok("User unfollowed successfully");
        } else {
            return ResponseEntity.badRequest().body("User is not being followed");
        }
    }
}