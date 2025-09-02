package com.haiya.video.service;

import com.haiya.video.entity.Video;
import com.haiya.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Video> getVideosByUserId(Long userId) {
        return videoRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    public Optional<Video> updateVideo(Long id, Video videoDetails) {
        return videoRepository.findById(id).map(video -> {
            video.setTitle(videoDetails.getTitle());
            video.setVideoUrl(videoDetails.getVideoUrl());
            video.setCoverUrl(videoDetails.getCoverUrl());
            return videoRepository.save(video);
        });
    }

    public boolean deleteVideo(Long id) {
        return videoRepository.findById(id).map(video -> {
            videoRepository.delete(video);
            return true;
        }).orElse(false);
    }
}