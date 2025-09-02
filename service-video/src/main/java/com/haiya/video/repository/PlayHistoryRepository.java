package com.haiya.video.repository;

import com.haiya.video.entity.PlayHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayHistoryRepository extends JpaRepository<PlayHistory, Long> {
    Optional<PlayHistory> findByUserIdAndVideoId(Long userId, Long videoId);
    List<PlayHistory> findByUserIdOrderByUpdatedAtDesc(Long userId);
    List<PlayHistory> findByVideoId(Long videoId);
}