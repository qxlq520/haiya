package com.haiya.video.repository;

import com.haiya.video.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndVideoId(Long userId, Long videoId);
    List<Favorite> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Favorite> findByVideoId(Long videoId);
    boolean existsByUserIdAndVideoId(Long userId, Long videoId);
    Long countByVideoId(Long videoId);
}