package com.haiya.video.controller;

import com.haiya.video.entity.UserProfile;
import com.haiya.video.service.UserProfileService;
import com.haiya.video.service.UserProfileStatsService;
import com.haiya.video.service.ContentCreationService;
import com.haiya.video.service.UserPortraitService;
import com.haiya.video.service.SocialNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user-profiles/stats")
public class UserProfileStatsController {

    @Autowired
    private UserProfileService userProfileService;
    
    @Autowired
    private UserProfileStatsService userProfileStatsService;
    
    @Autowired
    private ContentCreationService contentCreationService;
    
    @Autowired
    private UserPortraitService userPortraitService;
    
    @Autowired
    private SocialNetworkService socialNetworkService;

    /**
     * 获取用户统计数据
     * @param userId 用户ID
     * @return 用户统计数据
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserStats(@PathVariable Long userId) {
        UserProfile profile = userProfileService.getUserStatsSummary(userId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("userId", profile.getUserId());
        stats.put("username", profile.getUsername());
        stats.put("followerCount", profile.getFollowerCount());
        stats.put("followingCount", profile.getFollowingCount());
        stats.put("videoCount", profile.getVideoCount());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * 获取用户活跃度统计数据
     * @param userId 用户ID
     * @return 用户活跃度统计数据
     */
    @GetMapping("/{userId}/activity")
    public ResponseEntity<Map<String, Object>> getUserActivityStats(@PathVariable Long userId) {
        UserProfile profile = userProfileService.getUserStatsSummary(userId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("consecutiveLoginDays", profile.getConsecutiveLoginDays());
        stats.put("onlineDuration", profile.getOnlineDuration());
        stats.put("weeklyActivityScore", profile.getWeeklyActivityScore());
        stats.put("monthlyActivityScore", profile.getMonthlyActivityScore());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * 获取用户内容表现统计数据
     * @param userId 用户ID
     * @return 用户内容表现统计数据
     */
    @GetMapping("/{userId}/content")
    public ResponseEntity<Map<String, Object>> getUserContentStats(@PathVariable Long userId) {
        UserProfile profile = userProfileService.getUserStatsSummary(userId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("avgWatchDuration", profile.getAvgWatchDuration());
        stats.put("completionRate", profile.getCompletionRate());
        stats.put("shareConversionRate", profile.getShareConversionRate());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * 获取用户社交影响力统计数据
     * @param userId 用户ID
     * @return 用户社交影响力统计数据
     */
    @GetMapping("/{userId}/influence")
    public ResponseEntity<Map<String, Object>> getUserInfluenceStats(@PathVariable Long userId) {
        UserProfile profile = userProfileService.getUserStatsSummary(userId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("spreadIndex", profile.getSpreadIndex());
        stats.put("influenceIndex", profile.getInfluenceIndex());
        stats.put("creativityIndex", profile.getCreativityIndex());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * 获取用户成长体系数据
     * @param userId 用户ID
     * @return 用户成长体系数据
     */
    @GetMapping("/{userId}/growth")
    public ResponseEntity<Map<String, Object>> getUserGrowthStats(@PathVariable Long userId) {
        UserProfile profile = userProfileService.getUserStatsSummary(userId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("userLevel", profile.getUserLevel());
        stats.put("creatorLevel", profile.getCreatorLevel());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * 获取用户商业数据统计
     * @param userId 用户ID
     * @return 用户商业数据统计
     */
    @GetMapping("/{userId}/business")
    public ResponseEntity<Map<String, Object>> getUserBusinessStats(@PathVariable Long userId) {
        UserProfile profile = userProfileService.getUserStatsSummary(userId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("productViews", profile.getProductViews());
        stats.put("salesAmount", profile.getSalesAmount());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * 更新用户视频数量
     * @param userId 用户ID
     * @param count 视频数量
     * @return 更新结果
     */
    @PutMapping("/{userId}/video-count")
    public ResponseEntity<Map<String, String>> updateVideoCount(@PathVariable Long userId, @RequestParam Long count) {
        userProfileStatsService.updateVideoCount(userId, count);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Video count updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户关注数量
     * @param userId 用户ID
     * @param count 关注数量
     * @return 更新结果
     */
    @PutMapping("/{userId}/following-count")
    public ResponseEntity<Map<String, String>> updateFollowingCount(@PathVariable Long userId, @RequestParam Long count) {
        userProfileStatsService.updateFollowingCount(userId, count);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Following count updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户粉丝数量
     * @param userId 用户ID
     * @param count 粉丝数量
     * @return 更新结果
     */
    @PutMapping("/{userId}/follower-count")
    public ResponseEntity<Map<String, String>> updateFollowerCount(@PathVariable Long userId, @RequestParam Long count) {
        userProfileStatsService.updateFollowerCount(userId, count);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Follower count updated successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 新增API接口 ====================

    // ==================== 精细化用户行为分析 ====================

    /**
     * 更新用户连续登录天数
     * @param userId 用户ID
     * @param consecutiveDays 连续登录天数
     * @return 更新结果
     */
    @PutMapping("/{userId}/consecutive-login-days")
    public ResponseEntity<Map<String, String>> updateConsecutiveLoginDays(
            @PathVariable Long userId, 
            @RequestParam Integer consecutiveDays) {
        userProfileStatsService.updateConsecutiveLoginDays(userId, consecutiveDays);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Consecutive login days updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 增加用户在线时长
     * @param userId 用户ID
     * @param duration 在线时长（秒）
     * @return 更新结果
     */
    @PostMapping("/{userId}/online-duration")
    public ResponseEntity<Map<String, String>> incrementOnlineDuration(
            @PathVariable Long userId, 
            @RequestParam Long duration) {
        userProfileStatsService.incrementOnlineDuration(userId, duration);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Online duration incremented successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户每周活跃度评分
     * @param userId 用户ID
     * @param score 活跃度评分
     * @return 更新结果
     */
    @PutMapping("/{userId}/weekly-activity-score")
    public ResponseEntity<Map<String, String>> updateWeeklyActivityScore(
            @PathVariable Long userId, 
            @RequestParam Double score) {
        userProfileStatsService.updateWeeklyActivityScore(userId, score);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Weekly activity score updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户每月活跃度评分
     * @param userId 用户ID
     * @param score 活跃度评分
     * @return 更新结果
     */
    @PutMapping("/{userId}/monthly-activity-score")
    public ResponseEntity<Map<String, String>> updateMonthlyActivityScore(
            @PathVariable Long userId, 
            @RequestParam Double score) {
        userProfileStatsService.updateMonthlyActivityScore(userId, score);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Monthly activity score updated successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 内容表现数据 ====================

    /**
     * 更新用户视频的平均观看时长
     * @param userId 用户ID
     * @param avgWatchDuration 平均观看时长（秒）
     * @return 更新结果
     */
    @PutMapping("/{userId}/avg-watch-duration")
    public ResponseEntity<Map<String, String>> updateAvgWatchDuration(
            @PathVariable Long userId, 
            @RequestParam Long avgWatchDuration) {
        userProfileStatsService.updateAvgWatchDuration(userId, avgWatchDuration);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Average watch duration updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户视频的完整观看率
     * @param userId 用户ID
     * @param completionRate 完整观看率（百分比）
     * @return 更新结果
     */
    @PutMapping("/{userId}/completion-rate")
    public ResponseEntity<Map<String, String>> updateCompletionRate(
            @PathVariable Long userId, 
            @RequestParam Double completionRate) {
        userProfileStatsService.updateCompletionRate(userId, completionRate);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Completion rate updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户视频的分享转化率
     * @param userId 用户ID
     * @param conversionRate 分享转化率（百分比）
     * @return 更新结果
     */
    @PutMapping("/{userId}/share-conversion-rate")
    public ResponseEntity<Map<String, String>> updateShareConversionRate(
            @PathVariable Long userId, 
            @RequestParam Double conversionRate) {
        userProfileStatsService.updateShareConversionRate(userId, conversionRate);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Share conversion rate updated successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 社交影响力评估 ====================

    /**
     * 更新用户传播力指数
     * @param userId 用户ID
     * @param spreadIndex 传播力指数
     * @return 更新结果
     */
    @PutMapping("/{userId}/spread-index")
    public ResponseEntity<Map<String, String>> updateSpreadIndex(
            @PathVariable Long userId, 
            @RequestParam Double spreadIndex) {
        userProfileStatsService.updateSpreadIndex(userId, spreadIndex);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Spread index updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户影响力指数
     * @param userId 用户ID
     * @param influenceIndex 影响力指数
     * @return 更新结果
     */
    @PutMapping("/{userId}/influence-index")
    public ResponseEntity<Map<String, String>> updateInfluenceIndex(
            @PathVariable Long userId, 
            @RequestParam Double influenceIndex) {
        userProfileStatsService.updateInfluenceIndex(userId, influenceIndex);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Influence index updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户创造力指数
     * @param userId 用户ID
     * @param creativityIndex 创造力指数
     * @return 更新结果
     */
    @PutMapping("/{userId}/creativity-index")
    public ResponseEntity<Map<String, String>> updateCreativityIndex(
            @PathVariable Long userId, 
            @RequestParam Double creativityIndex) {
        userProfileStatsService.updateCreativityIndex(userId, creativityIndex);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Creativity index updated successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 用户成长体系 ====================

    /**
     * 更新用户等级
     * @param userId 用户ID
     * @param level 用户等级
     * @return 更新结果
     */
    @PutMapping("/{userId}/user-level")
    public ResponseEntity<Map<String, String>> updateUserLevel(
            @PathVariable Long userId, 
            @RequestParam Integer level) {
        userProfileStatsService.updateUserLevel(userId, level);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User level updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新创作者等级
     * @param userId 用户ID
     * @param level 创作者等级
     * @return 更新结果
     */
    @PutMapping("/{userId}/creator-level")
    public ResponseEntity<Map<String, String>> updateCreatorLevel(
            @PathVariable Long userId, 
            @RequestParam Integer level) {
        userProfileStatsService.updateCreatorLevel(userId, level);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Creator level updated successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 商业数据统计 ====================

    /**
     * 增加商品浏览量
     * @param userId 用户ID
     * @param increment 增加数量
     * @return 更新结果
     */
    @PostMapping("/{userId}/product-views")
    public ResponseEntity<Map<String, String>> incrementProductViews(
            @PathVariable Long userId, 
            @RequestParam Long increment) {
        userProfileStatsService.incrementProductViews(userId, increment);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product views incremented successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 增加商品销售额
     * @param userId 用户ID
     * @param amount 销售额
     * @return 更新结果
     */
    @PostMapping("/{userId}/sales-amount")
    public ResponseEntity<Map<String, String>> incrementSalesAmount(
            @PathVariable Long userId, 
            @RequestParam Double amount) {
        userProfileStatsService.incrementSalesAmount(userId, amount);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sales amount incremented successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 内容创作相关功能 ====================

    /**
     * 更新视频质量评分
     * @param userId 用户ID
     * @param qualityScore 质量评分
     * @return 更新结果
     */
    @PutMapping("/{userId}/video-quality-score")
    public ResponseEntity<Map<String, String>> updateVideoQualityScore(
            @PathVariable Long userId,
            @RequestParam Double qualityScore) {
        contentCreationService.updateVideoQualityScore(userId, qualityScore);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Video quality score updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新视频创意度评分
     * @param userId 用户ID
     * @param creativityScore 创意度评分
     * @return 更新结果
     */
    @PutMapping("/{userId}/video-creativity-score")
    public ResponseEntity<Map<String, String>> updateVideoCreativityScore(
            @PathVariable Long userId,
            @RequestParam Double creativityScore) {
        contentCreationService.updateVideoCreativityScore(userId, creativityScore);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Video creativity score updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新视频技术评分
     * @param userId 用户ID
     * @param technicalScore 技术评分
     * @return 更新结果
     */
    @PutMapping("/{userId}/video-technical-score")
    public ResponseEntity<Map<String, String>> updateVideoTechnicalScore(
            @PathVariable Long userId,
            @RequestParam Double technicalScore) {
        contentCreationService.updateVideoTechnicalScore(userId, technicalScore);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Video technical score updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新内容分类
     * @param userId 用户ID
     * @param category 内容分类
     * @return 更新结果
     */
    @PutMapping("/{userId}/content-category")
    public ResponseEntity<Map<String, String>> updateContentCategory(
            @PathVariable Long userId,
            @RequestParam String category) {
        contentCreationService.updateContentCategory(userId, category);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Content category updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新热门内容状态
     * @param userId 用户ID
     * @param isHot 是否为热门内容
     * @return 更新结果
     */
    @PutMapping("/{userId}/hot-content-status")
    public ResponseEntity<Map<String, String>> updateHotContentStatus(
            @PathVariable Long userId,
            @RequestParam Boolean isHot) {
        contentCreationService.updateHotContentStatus(userId, isHot);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hot content status updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新最佳发布时间
     * @param userId 用户ID
     * @param hour 小时（0-23）
     * @return 更新结果
     */
    @PutMapping("/{userId}/best-publishing-hour")
    public ResponseEntity<Map<String, String>> updateBestPublishingHour(
            @PathVariable Long userId,
            @RequestParam Integer hour) {
        contentCreationService.updateBestPublishingHour(userId, hour);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Best publishing hour updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新内容标签
     * @param userId 用户ID
     * @param tags 内容标签（JSON格式）
     * @return 更新结果
     */
    @PutMapping("/{userId}/content-tags")
    public ResponseEntity<Map<String, String>> updateContentTags(
            @PathVariable Long userId,
            @RequestBody String tags) {
        contentCreationService.updateContentTags(userId, tags);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Content tags updated successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 用户画像与兴趣分析 ====================

    /**
     * 更新兴趣标签
     * @param userId 用户ID
     * @param interestTags 兴趣标签（JSON格式）
     * @return 更新结果
     */
    @PutMapping("/{userId}/interest-tags")
    public ResponseEntity<Map<String, String>> updateInterestTags(
            @PathVariable Long userId,
            @RequestBody String interestTags) {
        userPortraitService.updateInterestTags(userId, interestTags);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Interest tags updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户行为轨迹
     * @param userId 用户ID
     * @param behaviorPath 行为轨迹（JSON格式）
     * @return 更新结果
     */
    @PutMapping("/{userId}/behavior-path")
    public ResponseEntity<Map<String, String>> updateUserBehaviorPath(
            @PathVariable Long userId,
            @RequestBody String behaviorPath) {
        userPortraitService.updateUserBehaviorPath(userId, behaviorPath);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User behavior path updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新内容偏好
     * @param userId 用户ID
     * @param preferences 内容偏好（JSON格式）
     * @return 更新结果
     */
    @PutMapping("/{userId}/content-preferences")
    public ResponseEntity<Map<String, String>> updateContentPreferences(
            @PathVariable Long userId,
            @RequestBody String preferences) {
        userPortraitService.updateContentPreferences(userId, preferences);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Content preferences updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户群体分类
     * @param userId 用户ID
     * @param segmentType 用户群体分类
     * @return 更新结果
     */
    @PutMapping("/{userId}/user-segment-type")
    public ResponseEntity<Map<String, String>> updateUserSegmentType(
            @PathVariable Long userId,
            @RequestParam String segmentType) {
        userPortraitService.updateUserSegmentType(userId, segmentType);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User segment type updated successfully");
        return ResponseEntity.ok(response);
    }

    // ==================== 社交网络分析 ====================

    /**
     * 更新社交关系图谱
     * @param userId 用户ID
     * @param relationshipGraph 社交关系图谱（JSON格式）
     * @return 更新结果
     */
    @PutMapping("/{userId}/social-relationship-graph")
    public ResponseEntity<Map<String, String>> updateSocialRelationshipGraph(
            @PathVariable Long userId,
            @RequestBody String relationshipGraph) {
        socialNetworkService.updateSocialRelationshipGraph(userId, relationshipGraph);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Social relationship graph updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新社区归属
     * @param userId 用户ID
     * @param communityAffiliation 社区归属（JSON格式）
     * @return 更新结果
     */
    @PutMapping("/{userId}/community-affiliation")
    public ResponseEntity<Map<String, String>> updateCommunityAffiliation(
            @PathVariable Long userId,
            @RequestBody String communityAffiliation) {
        socialNetworkService.updateCommunityAffiliation(userId, communityAffiliation);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Community affiliation updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新互动质量评分
     * @param userId 用户ID
     * @param qualityScore 互动质量评分
     * @return 更新结果
     */
    @PutMapping("/{userId}/interaction-quality-score")
    public ResponseEntity<Map<String, String>> updateInteractionQualityScore(
            @PathVariable Long userId,
            @RequestParam Double qualityScore) {
        socialNetworkService.updateInteractionQualityScore(userId, qualityScore);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Interaction quality score updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 更新好友推荐
     * @param userId 用户ID
     * @param friendRecommendations 好友推荐（JSON格式）
     * @return 更新结果
     */
    @PutMapping("/{userId}/friend-recommendations")
    public ResponseEntity<Map<String, String>> updateFriendRecommendations(
            @PathVariable Long userId,
            @RequestBody String friendRecommendations) {
        socialNetworkService.updateFriendRecommendations(userId, friendRecommendations);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Friend recommendations updated successfully");
        return ResponseEntity.ok(response);
    }
}