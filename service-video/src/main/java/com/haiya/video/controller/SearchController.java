package com.haiya.video.controller;

import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;
import com.haiya.video.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/videos")
    public List<Video> searchVideos(@RequestParam String keyword) {
        return searchService.searchVideos(keyword);
    }

    @GetMapping("/users")
    public List<UserProfile> searchUsers(@RequestParam String keyword) {
        return searchService.searchUsers(keyword);
    }
    
    @GetMapping("/videos/tag")
    public List<Video> searchVideosByTag(@RequestParam String tag) {
        return searchService.searchVideosByTag(tag);
    }
}