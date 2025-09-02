package com.haiya.video.controller;

import com.haiya.video.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/video/{videoId}/count")
    public Long getLikeCountByVideoId(@PathVariable Long videoId) {
        return likeService.getLikeCountByVideoId(videoId);
    }

    @GetMapping("/video/{videoId}/user/{userId}")
    public boolean isVideoLikedByUser(@PathVariable Long videoId, @PathVariable Long userId) {
        return likeService.isVideoLikedByUser(userId, videoId);
    }

    @PostMapping("/video/{videoId}/user/{userId}")
    public ResponseEntity<?> likeVideo(@PathVariable Long videoId, @PathVariable Long userId) {
        try {
            likeService.likeVideo(userId, videoId);
            return ResponseEntity.ok("Video liked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/video/{videoId}/user/{userId}")
    public ResponseEntity<?> unlikeVideo(@PathVariable Long videoId, @PathVariable Long userId) {
        boolean unliked = likeService.unlikeVideo(userId, videoId);
        if (unliked) {
            return ResponseEntity.ok("Video unliked successfully");
        } else {
            return ResponseEntity.badRequest().body("User has not liked this video");
        }
    }
}