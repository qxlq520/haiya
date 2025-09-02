package com.haiya.comment.service;

import com.haiya.comment.entity.Comment;
import com.haiya.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByVideoId(Long videoId) {
        return commentRepository.findByVideoIdOrderByCreatedAtDesc(videoId);
    }

    public List<Comment> getRepliesByCommentId(Long commentId) {
        return commentRepository.findByParentIdOrderByCreatedAtDesc(commentId);
    }

    public List<Comment> getUserComments(Long userId) {
        return commentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Comment createComment(Comment comment) {
        if (comment.getParentId() != null) {
            // 如果是回复评论，更新父评论的回复数
            Optional<Comment> parentComment = commentRepository.findById(comment.getParentId());
            if (parentComment.isPresent()) {
                Comment parent = parentComment.get();
                parent.setReplyCount(parent.getReplyCount() + 1);
                commentRepository.save(parent);
            }
        }
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setUpdatedAt(System.currentTimeMillis());
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment updateComment(Comment comment) {
        comment.setUpdatedAt(System.currentTimeMillis());
        return commentRepository.save(comment);
    }

    public boolean deleteComment(Long commentId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            // 使用软删除，将内容替换为已删除标识
            comment.setContent("此评论已被删除");
            comment.setUpdatedAt(System.currentTimeMillis());
            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    public Integer getVideoCommentCount(Long videoId) {
        return commentRepository.countByVideoId(videoId);
    }

    // 移除了CommentRepository中不存在的方法

    public Optional<Comment> updateComment(Long commentId, String content) {
        return commentRepository.findById(commentId).map(comment -> {
            comment.setContent(content);
            comment.setUpdatedAt(System.currentTimeMillis());
            return commentRepository.save(comment);
        });
    }

    public Optional<Comment> likeComment(Long commentId) {
        return commentRepository.findById(commentId).map(comment -> {
            comment.setLikeCount(comment.getLikeCount() + 1);
            comment.setUpdatedAt(System.currentTimeMillis());
            return commentRepository.save(comment);
        });
    }

    // 移除了CommentRepository中不存在的方法
}