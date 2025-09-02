package com.haiya.like.repository;

import com.haiya.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByTargetIdAndTargetTypeAndUserId(Long targetId, String targetType, Long userId);
    
    Integer countByTargetIdAndTargetType(Long targetId, String targetType);
    
    boolean existsByTargetIdAndTargetTypeAndUserId(Long targetId, String targetType, Long userId);
    
    void deleteByTargetIdAndTargetTypeAndUserId(Long targetId, String targetType, Long userId);
    
    List<Like> findByUserIdOrderByCreatedAtDesc(Long userId);
}