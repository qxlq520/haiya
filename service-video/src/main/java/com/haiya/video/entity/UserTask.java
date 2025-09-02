package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_tasks")
public class UserTask {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "task_type", nullable = false)
    private String taskType; // 任务类型：DAILY(日常), NEWBIE(新手), CHALLENGE(挑战)
    
    @Column(name = "task_name", nullable = false)
    private String taskName;
    
    @Column(name = "task_description")
    private String taskDescription;
    
    @Column(name = "required_progress")
    private Integer requiredProgress = 1; // 完成任务需要的进度数
    
    @Column(name = "current_progress")
    private Integer currentProgress = 0; // 当前进度
    
    @Column(name = "reward_points")
    private Integer rewardPoints = 0; // 奖励积分
    
    @Column(name = "reward_coins")
    private Integer rewardCoins = 0; // 奖励金币
    
    @Column(name = "status")
    private String status; // 任务状态：IN_PROGRESS(进行中), COMPLETED(已完成), CLAIMED(已领取奖励)
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt; // 完成时间
    
    @Column(name = "claimed_at")
    private LocalDateTime claimedAt; // 奖励领取时间
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "IN_PROGRESS";
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public UserTask() {}
    
    public UserTask(Long userId, String taskType, String taskName, String taskDescription, 
                   Integer requiredProgress, Integer rewardPoints, Integer rewardCoins) {
        this.userId = userId;
        this.taskType = taskType;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.requiredProgress = requiredProgress != null ? requiredProgress : 1;
        this.rewardPoints = rewardPoints != null ? rewardPoints : 0;
        this.rewardCoins = rewardCoins != null ? rewardCoins : 0;
        this.currentProgress = 0;
        this.status = "IN_PROGRESS";
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
    
    public String getTaskType() {
        return taskType;
    }
    
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskDescription() {
        return taskDescription;
    }
    
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    
    public Integer getRequiredProgress() {
        return requiredProgress;
    }
    
    public void setRequiredProgress(Integer requiredProgress) {
        this.requiredProgress = requiredProgress;
    }
    
    public Integer getCurrentProgress() {
        return currentProgress;
    }
    
    public void setCurrentProgress(Integer currentProgress) {
        this.currentProgress = currentProgress;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public LocalDateTime getClaimedAt() {
        return claimedAt;
    }
    
    public void setClaimedAt(LocalDateTime claimedAt) {
        this.claimedAt = claimedAt;
    }
    
    /**
     * 增加任务进度
     * @param increment 增加的进度数
     */
    public void incrementProgress(Integer increment) {
        if (increment == null || increment <= 0) {
            return;
        }
        
        this.currentProgress += increment;
        
        // 检查任务是否完成
        if (this.currentProgress >= this.requiredProgress && !"COMPLETED".equals(this.status)) {
            this.status = "COMPLETED";
            this.completedAt = LocalDateTime.now();
        }
    }
    
    /**
     * 领取奖励
     */
    public void claimReward() {
        if ("COMPLETED".equals(this.status)) {
            this.status = "CLAIMED";
            this.claimedAt = LocalDateTime.now();
        }
    }
    
    /**
     * 检查任务是否已完成但未领取奖励
     * @return true表示已完成但未领取奖励
     */
    public boolean isCompletedButNotClaimed() {
        return "COMPLETED".equals(this.status);
    }
}