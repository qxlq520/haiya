package com.haiya.video.repository;

import com.haiya.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Video> findAllByOrderByCreatedAtDesc();
    List<Video> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);
    List<Video> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds);
}