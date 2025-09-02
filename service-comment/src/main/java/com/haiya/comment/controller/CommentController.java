package com.haiya.comment.controller;

import com.haiya.comment.entity.Comment;
import com.haiya.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取视频评论列表
     * GET /api/comments?videoId={videoId}
     */
    @GetMapping
    public List<Comment> getCommentsByVideoId(@RequestParam Long videoId) {
        return commentService.getCommentsByVideoId(videoId);
    }

    /**
     * 获取评论的回复列表
     */
    @GetMapping("/{id}/replies")
    public List<Comment> getRepliesByCommentId(@PathVariable Long id) {
        return commentService.getRepliesByCommentId(id);
    }

    /**
     * 发布评论
     * POST /api/comments
     */
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    /**
     * 获取评论详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            return ResponseEntity.ok(comment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新评论
     */
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Optional<Comment> existingComment = commentService.getCommentById(id);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            comment.setContent(commentDetails.getContent());
            comment.setUpdatedAt(System.currentTimeMillis());
            Comment updatedComment = commentService.updateComment(comment);
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除评论
     * DELETE /api/comments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteComment(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 获取用户评论列表
     */
    @GetMapping("/user/{userId}")
    public List<Comment> getUserComments(@PathVariable Long userId) {
        return commentService.getUserComments(userId);
    }

    /**
     * 获取视频评论数
     */
    @GetMapping("/video/{videoId}/count")
    public Integer getVideoCommentCount(@PathVariable Long videoId) {
        return commentService.getVideoCommentCount(videoId);
    }
}