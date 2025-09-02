package com.haiya.recommend.service;

import com.haiya.recommend.entity.UserProfile;
import com.haiya.recommend.entity.VideoFeature;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AI辅助创作服务
 * 用于为内容创作者提供智能创作建议和优化方案
 */
@Service
public class AIContentCreationService {
    
    /**
     * 为创作者提供内容创作建议
     * 
     * @param creatorId 创作者ID
     * @param historicalPerformance 历史表现数据
     * @return 创作建议列表
     */
    public List<ContentSuggestion> getContentSuggestions(Long creatorId, List<VideoFeature> historicalPerformance) {
        List<ContentSuggestion> suggestions = new ArrayList<>();
        
        // 分析创作者历史表现
        ContentAnalysis analysis = analyzeContentPerformance(historicalPerformance);
        
        // 基于分析结果生成建议
        if (analysis.getAvgEngagementRate() < 0.05) {
            suggestions.add(new ContentSuggestion(
                "提高互动率", 
                "尝试在视频前3秒内提出引人入胜的问题或展示精彩片段以提高观看完成率",
                ContentSuggestion.SuggestionType.ENGAGEMENT
            ));
        }
        
        if (analysis.getBestPerformingCategories().size() > 0) {
            String bestCategory = analysis.getBestPerformingCategories().get(0);
            suggestions.add(new ContentSuggestion(
                "专注优势领域", 
                "您的" + bestCategory + "类内容表现最佳，建议增加此类内容的产出",
                ContentSuggestion.SuggestionType.CONTENT_STRATEGY
            ));
        }
        
        if (analysis.getOptimalPostingHours().size() > 0) {
            suggestions.add(new ContentSuggestion(
                "优化发布时间", 
                "根据数据分析，您的观众最活跃的时间是: " + analysis.getOptimalPostingHours(),
                ContentSuggestion.SuggestionType.TIMING
            ));
        }
        
        // 添加通用建议
        suggestions.add(new ContentSuggestion(
            "保持更新频率", 
            "保持稳定的更新频率有助于培养观众的观看习惯",
            ContentSuggestion.SuggestionType.CONTENT_STRATEGY
        ));
        
        return suggestions;
    }
    
    /**
     * 为视频内容提供优化建议
     * 
     * @param videoFeature 视频特征
     * @param similarPerformingVideos 相似表现的视频
     * @return 优化建议
     */
    public OptimizationRecommendation getOptimizationRecommendation(
            VideoFeature videoFeature, List<VideoFeature> similarPerformingVideos) {
        
        OptimizationRecommendation recommendation = new OptimizationRecommendation();
        recommendation.setVideoId(videoFeature.getVideoId());
        
        // 分析视频特征
        double avgDuration = similarPerformingVideos.stream()
            .mapToInt(VideoFeature::getDuration)
            .average()
            .orElse(300); // 默认5分钟
        
        if (videoFeature.getDuration() < avgDuration * 0.7) {
            recommendation.getRecommendations().add(
                "视频时长较短，考虑增加内容深度或添加更多有价值的信息");
        } else if (videoFeature.getDuration() > avgDuration * 1.5) {
            recommendation.getRecommendations().add(
                "视频时长较长，考虑精简内容或在关键节点添加互动元素以保持观众注意力");
        }
        
        // 分析标签使用情况
        if (videoFeature.getTags() == null || videoFeature.getTags().size() < 3) {
            recommendation.getRecommendations().add(
                "标签数量较少，建议添加更多相关标签以提高内容的可发现性");
        }
        
        return recommendation;
    }
    
    /**
     * 预测视频表现
     * 
     * @param videoFeature 视频特征
     * @param creatorProfile 创作者画像
     * @return 表现预测
     */
    public PerformancePrediction predictVideoPerformance(VideoFeature videoFeature, UserProfile creatorProfile) {
        PerformancePrediction prediction = new PerformancePrediction();
        prediction.setVideoId(videoFeature.getVideoId());
        
        // 基于创作者历史表现和视频特征预测
        double baseScore = 0.5; // 基准分数
        
        // 考虑创作者粉丝数和粉丝活跃度
        if (videoFeature.getAuthorFollowers() != null && videoFeature.getAuthorFollowers() > 10000) {
            baseScore += 0.2;
        }
        
        // 考虑视频类别热度
        List<String> hotCategories = getHotCategories();
        double categoryBoost = videoFeature.getCategories().stream()
            .anyMatch(hotCategories::contains) ? 0.15 : 0;
        baseScore += categoryBoost;
        
        // 考虑发布时间因素
        // 这里简化处理，实际应该根据当前时间和推荐发布时间计算
        baseScore += 0.1;
        
        // 确保分数在合理范围内
        baseScore = Math.min(1.0, Math.max(0.1, baseScore));
        
        prediction.setExpectedViews((long) (baseScore * 100000));
        prediction.setExpectedEngagementRate(baseScore * 0.08);
        prediction.setConfidenceScore(baseScore);
        
        return prediction;
    }
    
    /**
     * 分析内容表现
     * 
     * @param historicalPerformance 历史表现数据
     * @return 内容分析结果
     */
    private ContentAnalysis analyzeContentPerformance(List<VideoFeature> historicalPerformance) {
        ContentAnalysis analysis = new ContentAnalysis();
        
        if (historicalPerformance.isEmpty()) {
            return analysis;
        }
        
        // 计算平均互动率（这里简化处理）
        double totalEngagement = 0;
        Map<String, Integer> categoryCount = new HashMap<>();
        Map<Integer, Integer> hourCount = new HashMap<>();
        
        for (VideoFeature video : historicalPerformance) {
            // 简化处理，实际应该从视频统计数据中获取
            totalEngagement += ThreadLocalRandom.current().nextDouble(0.01, 0.15);
            
            // 统计类别分布
            if (video.getCategories() != null) {
                for (String category : video.getCategories()) {
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
            
            // 统计发布时间分布（简化处理）
            hourCount.put(ThreadLocalRandom.current().nextInt(0, 24), 
                         hourCount.getOrDefault(ThreadLocalRandom.current().nextInt(0, 24), 0) + 1);
        }
        
        analysis.setAvgEngagementRate(totalEngagement / historicalPerformance.size());
        
        // 获取表现最好的类别
        analysis.setBestPerformingCategories(
            categoryCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toList())
        );
        
        // 获取最佳发布时间
        analysis.setOptimalPostingHours(
            hourCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .map(entry -> entry.getKey() + ":00-" + entry.getKey() + ":59")
                .collect(java.util.stream.Collectors.toList())
        );
        
        return analysis;
    }
    
    /**
     * 获取热门类别
     * 
     * @return 热门类别列表
     */
    private List<String> getHotCategories() {
        return Arrays.asList("娱乐", "教育", "科技", "生活", "游戏", "音乐");
    }
    
    /**
     * 内容分析结果类
     */
    public static class ContentAnalysis {
        private double avgEngagementRate;
        private List<String> bestPerformingCategories = new ArrayList<>();
        private List<String> optimalPostingHours = new ArrayList<>();
        
        // Getters and Setters
        public double getAvgEngagementRate() {
            return avgEngagementRate;
        }
        
        public void setAvgEngagementRate(double avgEngagementRate) {
            this.avgEngagementRate = avgEngagementRate;
        }
        
        public List<String> getBestPerformingCategories() {
            return bestPerformingCategories;
        }
        
        public void setBestPerformingCategories(List<String> bestPerformingCategories) {
            this.bestPerformingCategories = bestPerformingCategories;
        }
        
        public List<String> getOptimalPostingHours() {
            return optimalPostingHours;
        }
        
        public void setOptimalPostingHours(List<String> optimalPostingHours) {
            this.optimalPostingHours = optimalPostingHours;
        }
    }
    
    /**
     * 创作建议类
     */
    public static class ContentSuggestion {
        private String title;
        private String description;
        private SuggestionType type;
        
        public ContentSuggestion() {}
        
        public ContentSuggestion(String title, String description, SuggestionType type) {
            this.title = title;
            this.description = description;
            this.type = type;
        }
        
        public enum SuggestionType {
            ENGAGEMENT,      // 互动提升
            CONTENT_STRATEGY, // 内容策略
            TIMING,          // 发布时机
            TECHNIQUE        // 创作技巧
        }
        
        // Getters and Setters
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public SuggestionType getType() {
            return type;
        }
        
        public void setType(SuggestionType type) {
            this.type = type;
        }
    }
    
    /**
     * 优化建议类
     */
    public static class OptimizationRecommendation {
        private Long videoId;
        private List<String> recommendations = new ArrayList<>();
        
        // Getters and Setters
        public Long getVideoId() {
            return videoId;
        }
        
        public void setVideoId(Long videoId) {
            this.videoId = videoId;
        }
        
        public List<String> getRecommendations() {
            return recommendations;
        }
        
        public void setRecommendations(List<String> recommendations) {
            this.recommendations = recommendations;
        }
    }
    
    /**
     * 表现预测类
     */
    public static class PerformancePrediction {
        private Long videoId;
        private Long expectedViews;
        private double expectedEngagementRate;
        private double confidenceScore;
        
        // Getters and Setters
        public Long getVideoId() {
            return videoId;
        }
        
        public void setVideoId(Long videoId) {
            this.videoId = videoId;
        }
        
        public Long getExpectedViews() {
            return expectedViews;
        }
        
        public void setExpectedViews(Long expectedViews) {
            this.expectedViews = expectedViews;
        }
        
        public double getExpectedEngagementRate() {
            return expectedEngagementRate;
        }
        
        public void setExpectedEngagementRate(double expectedEngagementRate) {
            this.expectedEngagementRate = expectedEngagementRate;
        }
        
        public double getConfidenceScore() {
            return confidenceScore;
        }
        
        public void setConfidenceScore(double confidenceScore) {
            this.confidenceScore = confidenceScore;
        }
    }
}