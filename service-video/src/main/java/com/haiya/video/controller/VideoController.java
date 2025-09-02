package com.haiya.video.controller;

import com.haiya.video.entity.Video;
import com.haiya.video.service.VideoService;
import com.haiya.video.service.VideoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;
    
    @Autowired
    private VideoUploadService videoUploadService;

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/user/{userId}")
    public List<Video> getVideosByUserId(@PathVariable Long userId) {
        return videoService.getVideosByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Optional<Video> video = videoService.getVideoById(id);
        if (video.isPresent()) {
            return ResponseEntity.ok(video.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Video createVideo(@RequestBody Video video) {
        return videoService.createVideo(video);
    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("video") MultipartFile videoFile,
            @RequestParam("cover") MultipartFile coverFile) {
        try {
            String videoFileName = videoUploadService.storeVideoFile(videoFile);
            String coverFileName = videoUploadService.storeCoverFile(coverFile);
            
            return ResponseEntity.ok("Video uploaded successfully. Video: " + videoFileName + ", Cover: " + coverFileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload video: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video videoDetails) {
        Optional<Video> updatedVideo = videoService.updateVideo(id, videoDetails);
        if (updatedVideo.isPresent()) {
            return ResponseEntity.ok(updatedVideo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        boolean deleted = videoService.deleteVideo(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}