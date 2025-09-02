package com.haiya.video.controller;

import com.haiya.video.entity.Favorite;
import com.haiya.video.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/user/{userId}")
    public List<Favorite> getFavoritesByUserId(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUserId(userId);
    }

    @GetMapping("/video/{videoId}/count")
    public Long getFavoriteCountByVideoId(@PathVariable Long videoId) {
        return favoriteService.getFavoriteCountByVideoId(videoId);
    }

    @GetMapping("/user/{userId}/video/{videoId}")
    public boolean isVideoFavoritedByUser(@PathVariable Long userId, @PathVariable Long videoId) {
        return favoriteService.isVideoFavoritedByUser(userId, videoId);
    }

    @PostMapping("/user/{userId}/video/{videoId}")
    public ResponseEntity<?> favoriteVideo(@PathVariable Long userId, @PathVariable Long videoId) {
        try {
            Favorite favorite = favoriteService.favoriteVideo(userId, videoId);
            return ResponseEntity.ok(favorite);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}/video/{videoId}")
    public ResponseEntity<?> unfavoriteVideo(@PathVariable Long userId, @PathVariable Long videoId) {
        boolean unfavorited = favoriteService.unfavoriteVideo(userId, videoId);
        if (unfavorited) {
            return ResponseEntity.ok("Video unfavorited successfully");
        } else {
            return ResponseEntity.badRequest().body("Video not favorited by user");
        }
    }
}