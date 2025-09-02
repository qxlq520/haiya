package com.haiya.follow.repository;

import com.haiya.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);
    
    List<Follow> findByFollowerIdOrderByCreatedAtDesc(Long followerId);
    
    List<Follow> findByFollowedIdOrderByCreatedAtDesc(Long followedId);
    
    Integer countByFollowerId(Long followerId);
    
    Integer countByFollowedId(Long followedId);
    
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);
    
    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);
}