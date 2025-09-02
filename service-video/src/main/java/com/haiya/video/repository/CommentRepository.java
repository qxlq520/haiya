package com.haiya.video.repository;

import com.haiya.video.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideoIdOrderByCreatedAtAsc(Long videoId);
    List<Comment> findByVideoIdAndParentIdIsNullOrderByCreatedAtAsc(Long videoId);
    List<Comment> findByParentIdOrderByCreatedAtAsc(Long parentId);
    List<Comment> findByUserIdOrderByCreatedAtDesc(Long userId);
}