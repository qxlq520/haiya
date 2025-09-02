package com.haiya.video.repository;

import com.haiya.video.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    Optional<Leaderboard> findByUserIdAndLeaderboardTypeAndPeriodTypeAndRegion(
        Long userId, String leaderboardType, String periodType, String region);
    
    List<Leaderboard> findTop10ByLeaderboardTypeAndPeriodTypeOrderByRankValueDesc(
        String leaderboardType, String periodType);
    
    List<Leaderboard> findTop10ByLeaderboardTypeAndPeriodTypeAndRegionOrderByRankValueDesc(
        String leaderboardType, String periodType, String region);
    
    int deleteByCalculatedAtBefore(LocalDateTime dateTime);
}