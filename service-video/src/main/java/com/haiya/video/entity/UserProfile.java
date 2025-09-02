package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "location")
    private String location;

    @Column(name = "website")
    private String website;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "follower_count")
    private Long followerCount = 0L;

    @Column(name = "following_count")
    private Long followingCount = 0L;

    @Column(name = "video_count")
    private Long videoCount = 0L;

    // 新增：用户活跃度相关字段
    @Column(name = "consecutive_login_days")
    private Integer consecutiveLoginDays = 0;

    @Column(name = "online_duration")
    private Long onlineDuration = 0L;

    @Column(name = "weekly_activity_score")
    private Double weeklyActivityScore = 0.0;

    @Column(name = "monthly_activity_score")
    private Double monthlyActivityScore = 0.0;

    // 新增：内容表现数据字段
    @Column(name = "avg_watch_duration")
    private Long avgWatchDuration = 0L;

    @Column(name = "completion_rate")
    private Double completionRate = 0.0;

    @Column(name = "share_conversion_rate")
    private Double shareConversionRate = 0.0;

    // 新增：社交影响力字段
    @Column(name = "spread_index")
    private Double spreadIndex = 0.0;

    @Column(name = "influence_index")
    private Double influenceIndex = 0.0;

    @Column(name = "creativity_index")
    private Double creativityIndex = 0.0;

    // 新增：用户成长体系字段
    @Column(name = "user_level")
    private Integer userLevel = 0;

    @Column(name = "creator_level")
    private Integer creatorLevel = 0;

    // 新增：商业数据统计字段
    @Column(name = "product_views")
    private Long productViews = 0L;

    @Column(name = "sales_amount")
    private Double salesAmount = 0.0;

    // ==================== 新增功能字段 ====================

    // 内容创作相关字段
    @Column(name = "video_quality_score")
    private Double videoQualityScore = 0.0; // 视频质量评分

    @Column(name = "video_creativity_score")
    private Double videoCreativityScore = 0.0; // 视频创意度评分

    @Column(name = "video_technical_score")
    private Double videoTechnicalScore = 0.0; // 视频技术评分

    @Column(name = "content_category")
    private String contentCategory; // 内容分类（搞笑、教育、生活等）

    @Column(name = "is_hot_content")
    private Boolean isHotContent = false; // 是否为热门内容

    @Column(name = "best_publishing_hour")
    private Integer bestPublishingHour; // 最佳发布时间（小时）

    @Column(name = "content_tags")
    private String contentTags; // 内容标签（JSON格式存储）

    // 用户画像与兴趣分析字段
    @Column(name = "interest_tags")
    private String interestTags; // 兴趣标签（JSON格式存储）

    @Column(name = "user_behavior_path")
    private String userBehaviorPath; // 用户行为轨迹（JSON格式存储）

    @Column(name = "content_preferences")
    private String contentPreferences; // 内容偏好（JSON格式存储）

    @Column(name = "user_segment_type")
    private String userSegmentType; // 用户群体分类（创作者、消费者、互动者等）

    // 社交网络分析字段
    @Column(name = "social_relationship_graph")
    private String socialRelationshipGraph; // 社交关系图谱（JSON格式存储）

    @Column(name = "community_affiliation")
    private String communityAffiliation; // 社区归属（JSON格式存储）

    @Column(name = "interaction_quality_score")
    private Double interactionQualityScore = 0.0; // 互动质量评分

    @Column(name = "friend_recommendations")
    private String friendRecommendations; // 好友推荐（JSON格式存储）

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public UserProfile() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UserProfile(Long userId, String username) {
        this.userId = userId != null ? userId : 0L;
        this.username = username != null ? username : "";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public Long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Long followerCount) {
        this.followerCount = followerCount != null ? followerCount : 0L;
    }

    public Long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount != null ? followingCount : 0L;
    }

    public Long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Long videoCount) {
        this.videoCount = videoCount != null ? videoCount : 0L;
    }

    // 新增字段的Getters和Setters
    public Integer getConsecutiveLoginDays() {
        return consecutiveLoginDays;
    }

    public void setConsecutiveLoginDays(Integer consecutiveLoginDays) {
        this.consecutiveLoginDays = consecutiveLoginDays != null ? consecutiveLoginDays : 0;
    }

    // 内容创作相关字段
    public Double getVideoQualityScore() {
        return videoQualityScore;
    }

    public void setVideoQualityScore(Double videoQualityScore) {
        this.videoQualityScore = videoQualityScore;
    }

    public Double getVideoCreativityScore() {
        return videoCreativityScore;
    }

    public void setVideoCreativityScore(Double videoCreativityScore) {
        this.videoCreativityScore = videoCreativityScore;
    }

    public Double getVideoTechnicalScore() {
        return videoTechnicalScore;
    }

    public void setVideoTechnicalScore(Double videoTechnicalScore) {
        this.videoTechnicalScore = videoTechnicalScore;
    }

    public String getContentCategory() {
        return contentCategory;
    }

    public void setContentCategory(String contentCategory) {
        this.contentCategory = contentCategory;
    }

    public Boolean getIsHotContent() {
        return isHotContent;
    }

    public void setIsHotContent(Boolean isHotContent) {
        this.isHotContent = isHotContent;
    }

    public Integer getBestPublishingHour() {
        return bestPublishingHour;
    }

    public void setBestPublishingHour(Integer bestPublishingHour) {
        this.bestPublishingHour = bestPublishingHour;
    }

    public String getContentTags() {
        return contentTags;
    }

    public void setContentTags(String contentTags) {
        this.contentTags = contentTags;
    }

    // 用户画像与兴趣分析字段
    public String getInterestTags() {
        return interestTags;
    }

    public void setInterestTags(String interestTags) {
        this.interestTags = interestTags;
    }

    public String getUserBehaviorPath() {
        return userBehaviorPath;
    }

    public void setUserBehaviorPath(String userBehaviorPath) {
        this.userBehaviorPath = userBehaviorPath;
    }

    public String getContentPreferences() {
        return contentPreferences;
    }

    public void setContentPreferences(String contentPreferences) {
        this.contentPreferences = contentPreferences;
    }

    public String getUserSegmentType() {
        return userSegmentType;
    }

    public void setUserSegmentType(String userSegmentType) {
        this.userSegmentType = userSegmentType;
    }

    // 社交网络分析字段
    public String getSocialRelationshipGraph() {
        return socialRelationshipGraph;
    }

    public void setSocialRelationshipGraph(String socialRelationshipGraph) {
        this.socialRelationshipGraph = socialRelationshipGraph;
    }

    public String getCommunityAffiliation() {
        return communityAffiliation;
    }

    public void setCommunityAffiliation(String communityAffiliation) {
        this.communityAffiliation = communityAffiliation;
    }

    public Double getInteractionQualityScore() {
        return interactionQualityScore;
    }

    public void setInteractionQualityScore(Double interactionQualityScore) {
        this.interactionQualityScore = interactionQualityScore;
    }

    public String getFriendRecommendations() {
        return friendRecommendations;
    }

    public void setFriendRecommendations(String friendRecommendations) {
        this.friendRecommendations = friendRecommendations;
    }

    public Long getOnlineDuration() {
        return onlineDuration;
    }

    public void setOnlineDuration(Long onlineDuration) {
        this.onlineDuration = onlineDuration != null ? onlineDuration : 0L;
    }

    public Double getWeeklyActivityScore() {
        return weeklyActivityScore;
    }

    public void setWeeklyActivityScore(Double weeklyActivityScore) {
        this.weeklyActivityScore = weeklyActivityScore != null ? weeklyActivityScore : 0.0;
    }

    public Double getMonthlyActivityScore() {
        return monthlyActivityScore;
    }

    public void setMonthlyActivityScore(Double monthlyActivityScore) {
        this.monthlyActivityScore = monthlyActivityScore != null ? monthlyActivityScore : 0.0;
    }

    public Long getAvgWatchDuration() {
        return avgWatchDuration;
    }

    public void setAvgWatchDuration(Long avgWatchDuration) {
        this.avgWatchDuration = avgWatchDuration != null ? avgWatchDuration : 0L;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate != null ? completionRate : 0.0;
    }

    public Double getShareConversionRate() {
        return shareConversionRate;
    }

    public void setShareConversionRate(Double shareConversionRate) {
        this.shareConversionRate = shareConversionRate != null ? shareConversionRate : 0.0;
    }

    public Double getSpreadIndex() {
        return spreadIndex;
    }

    public void setSpreadIndex(Double spreadIndex) {
        this.spreadIndex = spreadIndex != null ? spreadIndex : 0.0;
    }

    public Double getInfluenceIndex() {
        return influenceIndex;
    }

    public void setInfluenceIndex(Double influenceIndex) {
        this.influenceIndex = influenceIndex != null ? influenceIndex : 0.0;
    }

    public Double getCreativityIndex() {
        return creativityIndex;
    }

    public void setCreativityIndex(Double creativityIndex) {
        this.creativityIndex = creativityIndex != null ? creativityIndex : 0.0;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel != null ? userLevel : 0;
    }

    public Integer getCreatorLevel() {
        return creatorLevel;
    }

    public void setCreatorLevel(Integer creatorLevel) {
        this.creatorLevel = creatorLevel != null ? creatorLevel : 0;
    }

    public Long getProductViews() {
        return productViews;
    }

    public void setProductViews(Long productViews) {
        this.productViews = productViews != null ? productViews : 0L;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount != null ? salesAmount : 0.0;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", followerCount=" + followerCount +
                ", followingCount=" + followingCount +
                ", videoCount=" + videoCount +
                ", consecutiveLoginDays=" + consecutiveLoginDays +
                ", onlineDuration=" + onlineDuration +
                ", weeklyActivityScore=" + weeklyActivityScore +
                ", monthlyActivityScore=" + monthlyActivityScore +
                ", avgWatchDuration=" + avgWatchDuration +
                ", completionRate=" + completionRate +
                ", shareConversionRate=" + shareConversionRate +
                ", spreadIndex=" + spreadIndex +
                ", influenceIndex=" + influenceIndex +
                ", creativityIndex=" + creativityIndex +
                ", userLevel=" + userLevel +
                ", creatorLevel=" + creatorLevel +
                ", productViews=" + productViews +
                ", salesAmount=" + salesAmount +
                ", videoQualityScore=" + videoQualityScore +
                ", videoCreativityScore=" + videoCreativityScore +
                ", videoTechnicalScore=" + videoTechnicalScore +
                ", contentCategory='" + contentCategory + '\'' +
                ", isHotContent=" + isHotContent +
                ", bestPublishingHour=" + bestPublishingHour +
                ", contentTags='" + contentTags + '\'' +
                ", interestTags='" + interestTags + '\'' +
                ", userBehaviorPath='" + userBehaviorPath + '\'' +
                ", contentPreferences='" + contentPreferences + '\'' +
                ", userSegmentType='" + userSegmentType + '\'' +
                ", socialRelationshipGraph='" + socialRelationshipGraph + '\'' +
                ", communityAffiliation='" + communityAffiliation + '\'' +
                ", interactionQualityScore=" + interactionQualityScore +
                ", friendRecommendations='" + friendRecommendations + '\'' +
                '}';
    }
}