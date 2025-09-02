package com.haiya.video.service;

import com.haiya.video.entity.Video;
import com.haiya.video.entity.UserProfile;
import com.haiya.video.repository.VideoRepository;
import com.haiya.video.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private VideoRepository videoRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<Video> searchVideos(String keyword) {
        return videoRepository.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(keyword);
    }

    public List<UserProfile> searchUsers(String keyword) {
        return userProfileRepository.findByUsernameContainingIgnoreCase(keyword);
    }
    
    public List<Video> searchVideosByTag(String tag) {
        // 在实际项目中，这里会搜索视频标签
        // 由于Video实体中没有标签字段，暂时返回空列表
        return List.of();
    }
}