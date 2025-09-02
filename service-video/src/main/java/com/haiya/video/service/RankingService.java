package com.haiya.video.service;

import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;
import com.haiya.video.repository.VideoRepository;
import com.haiya.video.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {

    @Autowired
    private VideoRepository videoRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * 获取热门视频排行榜
     * @param limit 返回的视频数量
     * @return 按点赞数排序的视频列表
     */
    public List<Video> getHotVideoRanking(int limit) {
        return videoRepository.findAll()
                .stream()
                .sorted((v1, v2) -> v2.getLikeCount().compareTo(v1.getLikeCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 获取最新视频排行榜
     * @param limit 返回的视频数量
     * @return 按创建时间排序的视频列表
     */
    public List<Video> getLatestVideoRanking(int limit) {
        return videoRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 获取活跃用户排行榜
     * @param limit 返回的用户数量
     * @return 按粉丝数排序的用户列表
     */
    public List<UserProfile> getActiveUserRanking(int limit) {
        return userProfileRepository.findAll()
                .stream()
                .sorted((u1, u2) -> u2.getFollowerCount().compareTo(u1.getFollowerCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 获取热门用户排行榜（按视频总点赞数）
     * @param limit 返回的用户数量
     * @return 按视频总点赞数排序的用户列表
     */
    public List<UserProfile> getPopularUserRanking(int limit) {
        return userProfileRepository.findAll()
                .stream()
                .sorted((u1, u2) -> u2.getVideoCount().compareTo(u1.getVideoCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}