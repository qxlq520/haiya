package com.haiya.video.service;

import com.haiya.video.entity.ContentRecommendation;
import com.haiya.video.repository.ContentRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class RecommendationService {

    private static final Logger logger = Logger.getLogger(RecommendationService.class.getName());

    @Autowired
    private ContentRecommendationRepository contentRecommendationRepository;

    /**
     * 为用户生成个性化推荐
     * @param userId 用户ID
     * @param limit 推荐数量
     * @return 推荐内容列表
     */
    public List<ContentRecommendation> generatePersonalizedRecommendations(Long userId, int limit) {
        try {
            // 这里应该实现复杂的推荐算法，目前使用简化的示例实现
            List<ContentRecommendation> recommendations = findRecommendationsByUserInterests(userId, limit);
            logger.info("Generated " + recommendations.size() + " personalized recommendations for user: " + userId);
            return recommendations;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating personalized recommendations for user: " + userId, e);
            throw new RuntimeException("Failed to generate personalized recommendations", e);
        }
    }

    /**
     * 生成热门内容推荐
     * @param userId 用户ID
     * @param limit 推荐数量
     * @return 推荐内容列表
     */
    public List<ContentRecommendation> generatePopularRecommendations(Long userId, int limit) {
        try {
            // 这里应该实现基于热度的推荐算法，目前使用简化的示例实现
            List<ContentRecommendation> recommendations = findPopularContent(userId, limit);
            logger.info("Generated " + recommendations.size() + " popular recommendations for user: " + userId);
            return recommendations;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating popular recommendations for user: " + userId, e);
            throw new RuntimeException("Failed to generate popular recommendations", e);
        }
    }

    /**
     * 生成趋势内容推荐
     * @param userId 用户ID
     * @param limit 推荐数量
     * @return 推荐内容列表
     */
    public List<ContentRecommendation> generateTrendingRecommendations(Long userId, int limit) {
        try {
            // 这里应该实现基于趋势的推荐算法，目前使用简化的示例实现
            List<ContentRecommendation> recommendations = findTrendingContent(userId, limit);
            logger.info("Generated " + recommendations.size() + " trending recommendations for user: " + userId);
            return recommendations;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating trending recommendations for user: " + userId, e);
            throw new RuntimeException("Failed to generate trending recommendations", e);
        }
    }

    /**
     * 获取用户的推荐内容
     * @param userId 用户ID
     * @param limit 数量限制
     * @return 推荐内容列表
     */
    public List<ContentRecommendation> getUserRecommendations(Long userId, int limit) {
        try {
            List<ContentRecommendation> recommendations = contentRecommendationRepository
                .findByUserIdOrderByScoreDesc(userId)
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
            
            logger.info("Retrieved " + recommendations.size() + " recommendations for user: " + userId);
            return recommendations;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving recommendations for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve recommendations", e);
        }
    }

    /**
     * 记录用户与推荐内容的互动
     * @param recommendationId 推荐ID
     */
    @Transactional
    public void recordUserInteraction(Long recommendationId) {
        try {
            Optional<ContentRecommendation> recommendationOptional = contentRecommendationRepository.findById(recommendationId);
            if (!recommendationOptional.isPresent()) {
                throw new RuntimeException("Recommendation not found with ID: " + recommendationId);
            }
            
            ContentRecommendation recommendation = recommendationOptional.get();
            recommendation.markAsInteracted();
            contentRecommendationRepository.save(recommendation);
            
            logger.info("Recorded user interaction for recommendation: " + recommendationId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error recording user interaction for recommendation: " + recommendationId, e);
            throw new RuntimeException("Failed to record user interaction", e);
        }
    }

    /**
     * 批量创建推荐内容
     * @param recommendations 推荐内容列表
     * @return 创建的推荐内容列表
     */
    @Transactional
    public List<ContentRecommendation> createRecommendations(List<ContentRecommendation> recommendations) {
        try {
            List<ContentRecommendation> savedRecommendations = contentRecommendationRepository.saveAll(recommendations);
            logger.info("Created " + savedRecommendations.size() + " recommendations");
            return savedRecommendations;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating recommendations", e);
            throw new RuntimeException("Failed to create recommendations", e);
        }
    }

    /**
     * 删除过期的推荐内容
     * @param days 天数
     * @return 删除的记录数
     */
    @Transactional
    public int deleteExpiredRecommendations(int days) {
        try {
            LocalDateTime expiryDate = LocalDateTime.now().minusDays(days);
            int deletedCount = contentRecommendationRepository.deleteByCreatedAtBefore(expiryDate);
            logger.info("Deleted " + deletedCount + " expired recommendations");
            return deletedCount;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting expired recommendations", e);
            throw new RuntimeException("Failed to delete expired recommendations", e);
        }
    }

    // ==================== 辅助方法 ====================

    /**
     * 根据用户兴趣推荐内容（简化实现）
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 推荐内容列表
     */
    private List<ContentRecommendation> findRecommendationsByUserInterests(Long userId, int limit) {
        // 简化的实现，实际应该根据用户兴趣标签等信息进行推荐
        return contentRecommendationRepository.findPersonalizedRecommendations(userId, limit);
    }

    /**
     * 查找热门内容（简化实现）
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 推荐内容列表
     */
    private List<ContentRecommendation> findPopularContent(Long userId, int limit) {
        // 简化的实现，实际应该根据内容的热度进行推荐
        return contentRecommendationRepository.findPopularRecommendations(userId, limit);
    }

    /**
     * 查找趋势内容（简化实现）
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 推荐内容列表
     */
    private List<ContentRecommendation> findTrendingContent(Long userId, int limit) {
        // 简化的实现，实际应该根据内容的趋势进行推荐
        return contentRecommendationRepository.findTrendingRecommendations(userId, limit);
    }
}