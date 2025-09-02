package com.haiya.video.repository;

import com.haiya.video.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndVideoId(Long userId, Long videoId);
    Long countByVideoId(Long videoId);
    boolean existsByUserIdAndVideoId(Long userId, Long videoId);
}