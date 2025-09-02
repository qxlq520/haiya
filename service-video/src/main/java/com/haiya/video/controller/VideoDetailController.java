package com.haiya.video.controller;

import com.haiya.video.dto.VideoDetailDTO;
import com.haiya.video.service.VideoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/video-details")
public class VideoDetailController {

    @Autowired
    private VideoDetailService videoDetailService;

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDetailDTO> getVideoDetail(@PathVariable Long videoId) {
        Optional<VideoDetailDTO> detail = videoDetailService.getVideoDetail(videoId);
        if (detail.isPresent()) {
            return ResponseEntity.ok(detail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{videoId}/user/{userId}")
    public ResponseEntity<VideoDetailDTO> getVideoDetailWithUserStatus(
            @PathVariable Long videoId,
            @PathVariable Long userId) {
        Optional<VideoDetailDTO> detail = videoDetailService.getVideoDetailWithUserLikeStatus(videoId, userId);
        if (detail.isPresent()) {
            return ResponseEntity.ok(detail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}