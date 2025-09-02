package com.haiya.video.controller;

import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;
import com.haiya.video.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/videos/hot")
    public List<Video> getHotVideoRanking(@RequestParam(defaultValue = "10") int limit) {
        return rankingService.getHotVideoRanking(limit);
    }

    @GetMapping("/videos/latest")
    public List<Video> getLatestVideoRanking(@RequestParam(defaultValue = "10") int limit) {
        return rankingService.getLatestVideoRanking(limit);
    }

    @GetMapping("/users/active")
    public List<UserProfile> getActiveUserRanking(@RequestParam(defaultValue = "10") int limit) {
        return rankingService.getActiveUserRanking(limit);
    }

    @GetMapping("/users/popular")
    public List<UserProfile> getPopularUserRanking(@RequestParam(defaultValue = "10") int limit) {
        return rankingService.getPopularUserRanking(limit);
    }
}