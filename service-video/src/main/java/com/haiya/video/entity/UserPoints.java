package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_points")
public class UserPoints {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;
    
    @Column(name = "total_points")
    private Integer totalPoints = 0; // 总积分
    
    @Column(name = "available_points")
    private Integer availablePoints = 0; // 可用积分
    
    @Column(name = "consumed_points")
    private Integer consumedPoints = 0; // 已消费积分
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.totalPoints == null) this.totalPoints = 0;
        if (this.availablePoints == null) this.availablePoints = 0;
        if (this.consumedPoints == null) this.consumedPoints = 0;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public UserPoints() {}
    
    public UserPoints(Long userId) {
        this.userId = userId;
        this.totalPoints = 0;
        this.availablePoints = 0;
        this.consumedPoints = 0;
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
    
    public Integer getTotalPoints() {
        return totalPoints;
    }
    
    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints != null ? totalPoints : 0;
    }
    
    public Integer getAvailablePoints() {
        return availablePoints;
    }
    
    public void setAvailablePoints(Integer availablePoints) {
        this.availablePoints = availablePoints != null ? availablePoints : 0;
    }
    
    public Integer getConsumedPoints() {
        return consumedPoints;
    }
    
    public void setConsumedPoints(Integer consumedPoints) {
        this.consumedPoints = consumedPoints != null ? consumedPoints : 0;
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
     * 增加积分
     * @param points 增加的积分数
     */
    public void addPoints(Integer points) {
        if (points == null || points <= 0) {
            return;
        }
        
        this.totalPoints += points;
        this.availablePoints += points;
    }
    
    /**
     * 消费积分
     * @param points 消费的积分数
     * @return true表示消费成功，false表示积分不足
     */
    public boolean consumePoints(Integer points) {
        if (points == null || points <= 0) {
            return false;
        }
        
        if (this.availablePoints < points) {
            return false; // 积分不足
        }
        
        this.availablePoints -= points;
        this.consumedPoints += points;
        return true;
    }
    
    /**
     * 检查积分是否足够
     * @param points 需要的积分数
     * @return true表示积分足够
     */
    public boolean hasEnoughPoints(Integer points) {
        if (points == null || points <= 0) {
            return true;
        }
        
        return this.availablePoints >= points;
    }
}