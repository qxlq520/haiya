package com.haiya.live.repository;

import com.haiya.live.entity.LiveStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LiveStreamRepository extends JpaRepository<LiveStream, Long> {
    List<LiveStream> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<LiveStream> findByStatusOrderByStartedAtDesc(String status);
    Optional<LiveStream> findByStreamKey(String streamKey);
    List<LiveStream> findTop10ByStatusOrderByViewerCountDesc(String status);
}