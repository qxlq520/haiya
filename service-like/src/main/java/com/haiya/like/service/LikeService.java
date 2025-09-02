package com.haiya.like.service;

import com.haiya.like.entity.Like;
import com.haiya.like.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    
    @Autowired
    private LikeRepository likeRepository;
    
    public boolean likeTarget(Long targetId, String targetType, Long userId) {
        // 检查是否已经点赞
        if (likeRepository.existsByTargetIdAndTargetTypeAndUserId(targetId, targetType, userId)) {
            return false; // 已经点赞
        }
        
        // 创建点赞记录
        Like like = new Like(targetId, targetType, userId);
        likeRepository.save(like);
        return true;
    }
    
    public boolean unlikeTarget(Long targetId, String targetType, Long userId) {
        // 检查点赞是否存在
        if (!likeRepository.existsByTargetIdAndTargetTypeAndUserId(targetId, targetType, userId)) {
            return false; // 点赞不存在
        }
        
        // 删除点赞记录
        likeRepository.deleteByTargetIdAndTargetTypeAndUserId(targetId, targetType, userId);
        return true;
    }
    
    public Integer getTargetLikeCount(Long targetId, String targetType) {
        return likeRepository.countByTargetIdAndTargetType(targetId, targetType);
    }
    
    public boolean isTargetLikedByUser(Long targetId, String targetType, Long userId) {
        return likeRepository.existsByTargetIdAndTargetTypeAndUserId(targetId, targetType, userId);
    }
    
    public Optional<Like> getLikeByTargetAndUser(Long targetId, String targetType, Long userId) {
        return likeRepository.findByTargetIdAndTargetTypeAndUserId(targetId, targetType, userId);
    }
    
    public List<Like> getUserLikes(Long userId) {
        return likeRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}