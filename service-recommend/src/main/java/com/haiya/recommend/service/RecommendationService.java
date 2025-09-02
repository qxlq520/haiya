package com.haiya.recommend.service;

import com.haiya.recommend.entity.UserBehavior;
import com.haiya.recommend.entity.VideoFeature;
import com.haiya.recommend.entity.UserProfile;
import java.util.List;
import java.util.Map;

/**
 * 推荐服务接口
 */
public interface RecommendationService {
    
    /**
     * 基于协同过滤的推荐
     * @param userId 用户ID
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> collaborativeFilteringRecommend(Long userId, int count);
    
    /**
     * 基于内容的推荐
     * @param userId 用户ID
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> contentBasedRecommend(Long userId, int count);
    
    /**
     * 基于深度学习的推荐
     * @param userId 用户ID
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> deepLearningRecommend(Long userId, int count);
    
    /**
     * 实时推荐
     * @param userId 用户ID
     * @param recentBehaviors 最近行为
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> realTimeRecommend(Long userId, List<UserBehavior> recentBehaviors, int count);
    
    /**
     * 冷启动推荐（新用户）
     * @param userId 用户ID
     * @param userFeatures 用户基本特征
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> coldStartUserRecommend(Long userId, Map<String, String> userFeatures, int count);
    
    /**
     * 冷启动推荐（新视频）
     * @param videoId 视频ID
     * @param videoFeature 视频特征
     * @param count 推荐用户数量
     * @return 用户ID列表
     */
    List<Long> coldStartVideoRecommend(Long videoId, VideoFeature videoFeature, int count);
    
    /**
     * 多样性推荐
     * @param userId 用户ID
     * @param baseRecommendations 基础推荐列表
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> diversityRecommend(Long userId, List<Long> baseRecommendations, int count);
    
    /**
     * 热门推荐
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> trendingRecommend(int count);
    
    /**
     * 社交推荐
     * @param userId 用户ID
     * @param count 推荐数量
     * @return 视频ID列表
     */
    List<Long> socialRecommend(Long userId, int count);
    
    /**
     * 更新用户画像
     * @param userId 用户ID
     * @param behaviors 用户行为
     */
    void updateUserProfile(Long userId, List<UserBehavior> behaviors);
    
    /**
     * 更新视频特征
     * @param videoId 视频ID
     * @param features 视频特征
     */
    void updateVideoFeature(Long videoId, VideoFeature features);
    
    /**
     * 获取用户画像
     * @param userId 用户ID
     * @return 用户画像
     */
    UserProfile getUserProfile(Long userId);
    
    /**
     * 获取视频特征
     * @param videoId 视频ID
     * @return 视频特征
     */
    VideoFeature getVideoFeature(Long videoId);
}