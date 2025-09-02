package com.haiya.like.entity;

import javax.persistence.*;

@Entity
@Table(name = "likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"target_id", "target_type", "user_id"})
})
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_id", nullable = false)
    private Long targetId; // 被点赞目标的ID（视频ID、评论ID等）

    @Column(name = "target_type", nullable = false)
    private String targetType; // 被点赞目标的类型（VIDEO、COMMENT等）

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at")
    private Long createdAt;

    // Constructors
    public Like() {}

    public Like(Long targetId, String targetType, Long userId) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.userId = userId;
        this.createdAt = System.currentTimeMillis();
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

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}