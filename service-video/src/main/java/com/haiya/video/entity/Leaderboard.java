package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboards")
public class Leaderboard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "leaderboard_type", nullable = false)
    private String leaderboardType; // 排行榜类型：ACTIVE(活跃榜), CREATOR(创作榜), REGION(地区榜)
    
    @Column(name = "period_type")
    private String periodType; // 周期类型：DAILY(日榜), WEEKLY(周榜), MONTHLY(月榜), ALL_TIME(总榜)
    
    @Column(name = "rank_value", nullable = false)
    private Double rankValue = 0.0; // 排行值
    
    @Column(name = "rank_position")
    private Integer rankPosition; // 排名位置
    
    @Column(name = "region")
    private String region; // 地区（用于地区榜）
    
    @Column(name = "calculated_at", nullable = false)
    private LocalDateTime calculatedAt; // 计算时间
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.calculatedAt = LocalDateTime.now();
        if (this.rankValue == null) this.rankValue = 0.0;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.calculatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Leaderboard() {}
    
    public Leaderboard(Long userId, String username, String leaderboardType, 
                      String periodType, Double rankValue) {
        this.userId = userId;
        this.username = username;
        this.leaderboardType = leaderboardType;
        this.periodType = periodType;
        this.rankValue = rankValue != null ? rankValue : 0.0;
        this.createdAt = LocalDateTime.now();
        this.calculatedAt = LocalDateTime.now();
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
    
    public String getLeaderboardType() {
        return leaderboardType;
    }
    
    public void setLeaderboardType(String leaderboardType) {
        this.leaderboardType = leaderboardType;
    }
    
    public String getPeriodType() {
        return periodType;
    }
    
    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
    
    public Double getRankValue() {
        return rankValue;
    }
    
    public void setRankValue(Double rankValue) {
        this.rankValue = rankValue != null ? rankValue : 0.0;
    }
    
    public Integer getRankPosition() {
        return rankPosition;
    }
    
    public void setRankPosition(Integer rankPosition) {
        this.rankPosition = rankPosition;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
    
    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}