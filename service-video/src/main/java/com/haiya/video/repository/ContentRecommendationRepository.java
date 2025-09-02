package com.haiya.video.repository;

import com.haiya.video.entity.ContentRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContentRecommendationRepository extends JpaRepository<ContentRecommendation, Long> {
    List<ContentRecommendation> findByUserIdOrderByScoreDesc(Long userId);
    List<ContentRecommendation> findByUserIdAndIsInteractedFalseOrderByScoreDesc(Long userId);
    List<ContentRecommendation> findByRecommendationTypeOrderByScoreDesc(String recommendationType);
    
    @Query("SELECT cr FROM ContentRecommendation cr WHERE cr.userId = ?1 AND cr.recommendationType = 'PERSONALIZED' ORDER BY cr.score DESC")
    List<ContentRecommendation> findPersonalizedRecommendations(Long userId, int limit);
    
    @Query("SELECT cr FROM ContentRecommendation cr WHERE cr.userId = ?1 AND cr.recommendationType = 'POPULAR' ORDER BY cr.score DESC")
    List<ContentRecommendation> findPopularRecommendations(Long userId, int limit);
    
    @Query("SELECT cr FROM ContentRecommendation cr WHERE cr.userId = ?1 AND cr.recommendationType = 'TRENDING' ORDER BY cr.score DESC")
    List<ContentRecommendation> findTrendingRecommendations(Long userId, int limit);
    
    int deleteByCreatedAtBefore(LocalDateTime dateTime);
}