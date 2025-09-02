package com.haiya.follow.service;

import com.haiya.follow.entity.Follow;
import com.haiya.follow.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    
    @Autowired
    private FollowRepository followRepository;
    
    public boolean followUser(Long followerId, Long followedId) {
        // 检查是否已经关注
        if (followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            return false; // 已经关注
        }
        
        // 不能关注自己
        if (followerId.equals(followedId)) {
            return false;
        }
        
        // 创建关注关系
        Follow follow = new Follow(followerId, followedId);
        followRepository.save(follow);
        return true;
    }
    
    public boolean unfollowUser(Long followerId, Long followedId) {
        // 检查关注关系是否存在
        if (!followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            return false; // 关注关系不存在
        }
        
        // 删除关注关系
        followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
        return true;
    }
    
    public List<Follow> getFollowingList(Long followerId) {
        return followRepository.findByFollowerIdOrderByCreatedAtDesc(followerId);
    }
    
    public List<Follow> getFollowersList(Long followedId) {
        return followRepository.findByFollowedIdOrderByCreatedAtDesc(followedId);
    }
    
    public Integer getFollowingCount(Long followerId) {
        return followRepository.countByFollowerId(followerId);
    }
    
    public Integer getFollowersCount(Long followedId) {
        return followRepository.countByFollowedId(followedId);
    }
    
    public boolean isFollowing(Long followerId, Long followedId) {
        return followRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }
    
    public Optional<Follow> getFollowRelation(Long followerId, Long followedId) {
        return followRepository.findByFollowerIdAndFollowedId(followerId, followedId);
    }
}