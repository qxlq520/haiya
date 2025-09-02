package com.haiya.comment.entity;

import javax.persistence.*;

@Entity
@Table(name = "comment_likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"comment_id", "user_id"})
})
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at")
    private Long createdAt;

    // Constructors
    public CommentLike() {}

    public CommentLike(Long commentId, Long userId) {
        this.commentId = commentId;
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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}