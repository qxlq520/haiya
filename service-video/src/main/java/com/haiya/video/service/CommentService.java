package com.haiya.video.service;

import com.haiya.video.entity.Comment;
import com.haiya.video.entity.Video;
import com.haiya.video.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private VideoStatsService videoStatsService;

    public List<Comment> getCommentsByVideoId(Long videoId) {
        return commentRepository.findByVideoIdOrderByCreatedAtAsc(videoId);
    }

    public List<Comment> getRootCommentsByVideoId(Long videoId) {
        return commentRepository.findByVideoIdAndParentIdIsNullOrderByCreatedAtAsc(videoId);
    }

    public List<Comment> getRepliesByCommentId(Long commentId) {
        return commentRepository.findByParentIdOrderByCreatedAtAsc(commentId);
    }

    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public Comment createComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        // 更新视频统计信息
        videoStatsService.incrementCommentCount(comment.getVideoId());
        
        // 发送通知给视频作者
        try {
            Optional<Video> video = videoService.getVideoById(comment.getVideoId());
            if (video.isPresent() && !video.get().getUserId().equals(comment.getUserId())) {
                notificationService.createCommentNotification(
                    video.get().getUserId(), 
                    comment.getUserId(), 
                    comment.getVideoId()
                );
            }
        } catch (Exception e) {
            // 忽略通知发送错误，不影响主要流程
        }
        
        return savedComment;
    }

    @Transactional
    public Optional<Comment> updateComment(Long id, String content) {
        return commentRepository.findById(id).map(comment -> {
            comment.setContent(content);
            return commentRepository.save(comment);
        });
    }

    @Transactional
    public boolean deleteComment(Long id) {
        return commentRepository.findById(id).map(comment -> {
            commentRepository.delete(comment);
            // 更新视频统计信息
            videoStatsService.decrementCommentCount(comment.getVideoId());
            return true;
        }).orElse(false);
    }
}