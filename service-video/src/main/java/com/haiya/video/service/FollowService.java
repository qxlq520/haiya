package com.haiya.video.service;

import com.haiya.video.entity.Follow;
import com.haiya.video.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Long getFollowingCount(Long userId) {
        return followRepository.countByFollowerId(userId);
    }

    public Long getFollowerCount(Long userId) {
        return followRepository.countByFollowingId(userId);
    }

    public List<Follow> getFollowingList(Long userId) {
        return followRepository.findByFollowerId(userId);
    }

    public List<Follow> getFollowerList(Long userId) {
        return followRepository.findByFollowingId(userId);
    }

    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Transactional
    public Follow followUser(Long followerId, Long followingId) {
        // 检查是否已经关注
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new RuntimeException("User already follows this user");
        }
        
        // 不能关注自己
        if (followerId.equals(followingId)) {
            throw new RuntimeException("Cannot follow yourself");
        }
        
        Follow follow = new Follow(followerId, followingId);
        return followRepository.save(follow);
    }

    @Transactional
    public boolean unfollowUser(Long followerId, Long followingId) {
        Optional<Follow> follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        if (follow.isPresent()) {
            followRepository.delete(follow.get());
            return true;
        }
        return false;
    }
}