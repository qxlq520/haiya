package com.haiya.recommend.service;

import com.haiya.recommend.entity.UserRating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 协同过滤推荐服务实现
 */
@Service
public class CollaborativeFilteringService {
    
    private static final Logger logger = LoggerFactory.getLogger(CollaborativeFilteringService.class);
    
    /**
     * 基于用户评分的协同过滤推荐
     * @param userId 用户ID
     * @param count 推荐数量
     * @param ratings 所有用户评分数据
     * @return 推荐的视频ID列表
     */
    public List<Long> recommendByUserRatings(Long userId, int count, List<UserRating> ratings) {
        try {
            // 构建用户-物品评分矩阵
            Map<Long, Map<Long, Double>> userItemMatrix = buildUserItemMatrix(ratings);
            
            // 计算用户相似度
            Map<Long, Double> userSimilarities = calculateUserSimilarities(userId, userItemMatrix);
            
            // 基于相似用户生成推荐
            List<Long> recommendations = generateRecommendations(userId, userSimilarities, userItemMatrix, count);
            
            return recommendations;
        } catch (Exception e) {
            logger.error("Failed to generate collaborative filtering recommendations", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * 基于物品的协同过滤推荐
     * @param videoId 视频ID
     * @param count 推荐数量
     * @param ratings 所有用户评分数据
     * @return 推荐的视频ID列表
     */
    public List<Long> recommendByItemSimilarity(Long videoId, int count, List<UserRating> ratings) {
        try {
            // 构建物品-用户评分矩阵
            Map<Long, Map<Long, Double>> itemUserMatrix = buildItemUserMatrix(ratings);
            
            // 计算物品相似度
            Map<Long, Double> itemSimilarities = calculateItemSimilarities(videoId, itemUserMatrix);
            
            // 基于相似物品生成推荐
            List<Long> recommendations = generateItemRecommendations(videoId, itemSimilarities, itemUserMatrix, count);
            
            return recommendations;
        } catch (Exception e) {
            logger.error("Failed to generate item-based collaborative filtering recommendations", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * 实时协同过滤推荐
     * @param userId 用户ID
     * @param count 推荐数量
     * @param recentRatings 用户最近的评分数据
     * @return 推荐的视频ID列表
     */
    public List<Long> realTimeRecommendations(Long userId, int count, List<UserRating> recentRatings) {
        try {
            // 对于实时推荐，我们使用更简单的启发式方法
            // 基于用户最近的评分行为进行推荐
            
            // 获取用户最近评分的视频类别
            List<String> recentCategories = recentRatings.stream()
                .map(UserRating::getCategory)
                .distinct()
                .limit(3)
                .collect(Collectors.toList());
            
            // 基于类别推荐相似视频
            List<Long> recommendations = recentRatings.stream()
                .filter(rating -> recentCategories.contains(rating.getCategory()))
                .sorted((r1, r2) -> Double.compare(r2.getScore(), r1.getScore()))
                .map(UserRating::getVideoId)
                .distinct()
                .limit(count * 2)
                .collect(Collectors.toList());
            
            // 如果推荐数量不足，补充热门视频
            if (recommendations.size() < count) {
                List<Long> popularVideos = getPopularVideos();
                for (Long videoId : popularVideos) {
                    if (!recommendations.contains(videoId)) {
                        recommendations.add(videoId);
                        if (recommendations.size() >= count) {
                            break;
                        }
                    }
                }
            }
            
            return recommendations.stream().limit(count).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to generate real-time collaborative filtering recommendations", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * 构建用户-物品评分矩阵
     */
    private Map<Long, Map<Long, Double>> buildUserItemMatrix(List<UserRating> ratings) {
        Map<Long, Map<Long, Double>> userItemMatrix = new HashMap<>();
        
        for (UserRating rating : ratings) {
            userItemMatrix.computeIfAbsent(rating.getUserId(), k -> new HashMap<>())
                         .put(rating.getVideoId(), rating.getScore());
        }
        
        return userItemMatrix;
    }
    
    /**
     * 构建物品-用户评分矩阵
     */
    private Map<Long, Map<Long, Double>> buildItemUserMatrix(List<UserRating> ratings) {
        Map<Long, Map<Long, Double>> itemUserMatrix = new HashMap<>();
        
        for (UserRating rating : ratings) {
            itemUserMatrix.computeIfAbsent(rating.getVideoId(), k -> new HashMap<>())
                         .put(rating.getUserId(), rating.getScore());
        }
        
        return itemUserMatrix;
    }
    
    /**
     * 计算用户相似度（简化版）
     */
    private Map<Long, Double> calculateUserSimilarities(Long targetUserId, Map<Long, Map<Long, Double>> userItemMatrix) {
        Map<Long, Double> similarities = new HashMap<>();
        Map<Long, Double> targetUserRatings = userItemMatrix.get(targetUserId);
        
        if (targetUserRatings == null) {
            return similarities;
        }
        
        for (Map.Entry<Long, Map<Long, Double>> entry : userItemMatrix.entrySet()) {
            Long userId = entry.getKey();
            if (userId.equals(targetUserId)) {
                continue;
            }
            
            Map<Long, Double> userRatings = entry.getValue();
            double similarity = calculateCosineSimilarity(targetUserRatings, userRatings);
            similarities.put(userId, similarity);
        }
        
        return similarities;
    }
    
    /**
     * 计算物品相似度（简化版）
     */
    private Map<Long, Double> calculateItemSimilarities(Long targetVideoId, Map<Long, Map<Long, Double>> itemUserMatrix) {
        Map<Long, Double> similarities = new HashMap<>();
        Map<Long, Double> targetItemRatings = itemUserMatrix.get(targetVideoId);
        
        if (targetItemRatings == null) {
            return similarities;
        }
        
        for (Map.Entry<Long, Map<Long, Double>> entry : itemUserMatrix.entrySet()) {
            Long videoId = entry.getKey();
            if (videoId.equals(targetVideoId)) {
                continue;
            }
            
            Map<Long, Double> itemRatings = entry.getValue();
            double similarity = calculateCosineSimilarity(targetItemRatings, itemRatings);
            similarities.put(videoId, similarity);
        }
        
        return similarities;
    }
    
    /**
     * 计算余弦相似度
     */
    private double calculateCosineSimilarity(Map<Long, Double> ratings1, Map<Long, Double> ratings2) {
        // 找到共同评分的项目
        List<Long> commonItems = ratings1.keySet().stream()
            .filter(ratings2::containsKey)
            .collect(Collectors.toList());
        
        if (commonItems.isEmpty()) {
            return 0.0;
        }
        
        // 计算点积
        double dotProduct = commonItems.stream()
            .mapToDouble(itemId -> ratings1.get(itemId) * ratings2.get(itemId))
            .sum();
        
        // 计算向量的模
        double norm1 = Math.sqrt(ratings1.values().stream()
            .mapToDouble(score -> score * score)
            .sum());
        
        double norm2 = Math.sqrt(ratings2.values().stream()
            .mapToDouble(score -> score * score)
            .sum());
        
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }
        
        return dotProduct / (norm1 * norm2);
    }
    
    /**
     * 基于相似用户生成推荐
     */
    private List<Long> generateRecommendations(Long userId, Map<Long, Double> userSimilarities, 
                                             Map<Long, Map<Long, Double>> userItemMatrix, int count) {
        Map<Long, Double> targetUserRatings = userItemMatrix.get(userId);
        if (targetUserRatings == null) {
            targetUserRatings = new HashMap<>();
        }
        
        // 收集相似用户评分过的但目标用户未评分的物品
        Map<Long, Double> recommendationScores = new HashMap<>();
        
        for (Map.Entry<Long, Double> similarityEntry : userSimilarities.entrySet()) {
            Long similarUserId = similarityEntry.getKey();
            Double similarity = similarityEntry.getValue();
            
            Map<Long, Double> similarUserRatings = userItemMatrix.get(similarUserId);
            if (similarUserRatings == null) {
                continue;
            }
            
            for (Map.Entry<Long, Double> ratingEntry : similarUserRatings.entrySet()) {
                Long videoId = ratingEntry.getKey();
                Double rating = ratingEntry.getValue();
                
                // 如果目标用户已经评分过该视频，则跳过
                if (targetUserRatings.containsKey(videoId)) {
                    continue;
                }
                
                // 累加加权评分
                recommendationScores.merge(videoId, similarity * rating, Double::sum);
            }
        }
        
        // 按评分排序并返回topN
        return recommendationScores.entrySet().stream()
            .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
            .limit(count)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
    /**
     * 基于相似物品生成推荐
     */
    private List<Long> generateItemRecommendations(Long videoId, Map<Long, Double> itemSimilarities,
                                                 Map<Long, Map<Long, Double>> itemUserMatrix, int count) {
        // 简化实现：返回最相似的物品
        return itemSimilarities.entrySet().stream()
            .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
            .limit(count)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
    /**
     * 获取热门视频（简化实现）
     */
    private List<Long> getPopularVideos() {
        // 在实际应用中，这应该从数据库或缓存中获取
        List<Long> popularVideos = new ArrayList<>();
        for (long i = 1; i <= 100; i++) {
            popularVideos.add(i);
        }
        return popularVideos;
    }
}