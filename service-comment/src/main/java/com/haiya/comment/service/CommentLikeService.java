package com.haiya.comment.service;

import com.haiya.comment.entity.CommentLike;
import com.haiya.comment.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {
    
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    
    public boolean likeComment(Long commentId, Long userId) {
        // 检查是否已经点赞
        if (commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            return false; // 已经点赞
        }
        
        // 创建点赞记录
        CommentLike commentLike = new CommentLike(commentId, userId);
        commentLikeRepository.save(commentLike);
        return true;
    }
    
    public boolean unlikeComment(Long commentId, Long userId) {
        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);
        if (commentLike.isPresent()) {
            commentLikeRepository.delete(commentLike.get());
            return true;
        }
        return false;
    }
    
    public Integer getCommentLikeCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
    
    public boolean isCommentLikedByUser(Long commentId, Long userId) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
    }
}