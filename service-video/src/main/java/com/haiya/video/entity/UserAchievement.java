package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_achievements")
public class UserAchievement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "achievement_type", nullable = false)
    private String achievementType; // 成就类型
    
    @Column(name = "achievement_name", nullable = false)
    private String achievementName;
    
    @Column(name = "achievement_description")
    private String achievementDescription;
    
    @Column(name = "achievement_level")
    private Integer achievementLevel = 1; // 成就等级
    
    @Column(name = "required_value")
    private Integer requiredValue; // 达成成就需要的数值
    
    @Column(name = "current_value")
    private Integer currentValue = 0; // 当前数值
    
    @Column(name = "reward_points")
    private Integer rewardPoints = 0; // 奖励积分
    
    @Column(name = "reward_coins")
    private Integer rewardCoins = 0; // 奖励金币
    
    @Column(name = "is_achieved")
    private Boolean isAchieved = false; // 是否已达成
    
    @Column(name = "is_reward_claimed")
    private Boolean isRewardClaimed = false; // 奖励是否已领取
    
    @Column(name = "achieved_at")
    private LocalDateTime achievedAt; // 达成时间
    
    @Column(name = "claimed_at")
    private LocalDateTime claimedAt; // 奖励领取时间
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public UserAchievement() {}
    
    public UserAchievement(Long userId, String achievementType, String achievementName, 
                          String achievementDescription, Integer requiredValue, 
                          Integer rewardPoints, Integer rewardCoins) {
        this.userId = userId;
        this.achievementType = achievementType;
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.requiredValue = requiredValue;
        this.rewardPoints = rewardPoints != null ? rewardPoints : 0;
        this.rewardCoins = rewardCoins != null ? rewardCoins : 0;
        this.currentValue = 0;
        this.isAchieved = false;
        this.isRewardClaimed = false;
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
    
    public String getAchievementType() {
        return achievementType;
    }
    
    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }
    
    public String getAchievementName() {
        return achievementName;
    }
    
    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }
    
    public String getAchievementDescription() {
        return achievementDescription;
    }
    
    public void setAchievementDescription(String achievementDescription) {
        this.achievementDescription = achievementDescription;
    }
    
    public Integer getAchievementLevel() {
        return achievementLevel;
    }
    
    public void setAchievementLevel(Integer achievementLevel) {
        this.achievementLevel = achievementLevel;
    }
    
    public Integer getRequiredValue() {
        return requiredValue;
    }
    
    public void setRequiredValue(Integer requiredValue) {
        this.requiredValue = requiredValue;
    }
    
    public Integer getCurrentValue() {
        return currentValue;
    }
    
    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }
    
    public Integer getRewardPoints() {
        return rewardPoints;
    }
    
    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
    
    public Integer getRewardCoins() {
        return rewardCoins;
    }
    
    public void setRewardCoins(Integer rewardCoins) {
        this.rewardCoins = rewardCoins;
    }
    
    public Boolean getIsAchieved() {
        return isAchieved;
    }
    
    public void setIsAchieved(Boolean isAchieved) {
        this.isAchieved = isAchieved;
    }
    
    public Boolean getIsRewardClaimed() {
        return isRewardClaimed;
    }
    
    public void setIsRewardClaimed(Boolean isRewardClaimed) {
        this.isRewardClaimed = isRewardClaimed;
    }
    
    public LocalDateTime getAchievedAt() {
        return achievedAt;
    }
    
    public void setAchievedAt(LocalDateTime achievedAt) {
        this.achievedAt = achievedAt;
    }
    
    public LocalDateTime getClaimedAt() {
        return claimedAt;
    }
    
    public void setClaimedAt(LocalDateTime claimedAt) {
        this.claimedAt = claimedAt;
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
    
    /**
     * 更新当前数值
     * @param value 新的数值
     */
    public void updateValue(Integer value) {
        if (value == null || value < 0) {
            return;
        }
        
        this.currentValue = value;
        
        // 检查是否达成成就
        if (this.currentValue >= this.requiredValue && !this.isAchieved) {
            this.isAchieved = true;
            this.achievedAt = LocalDateTime.now();
        }
    }
    
    /**
     * 领取奖励
     */
    public void claimReward() {
        if (this.isAchieved && !this.isRewardClaimed) {
            this.isRewardClaimed = true;
            this.claimedAt = LocalDateTime.now();
        }
    }
}