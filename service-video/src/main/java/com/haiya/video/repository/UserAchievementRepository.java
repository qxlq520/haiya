package com.haiya.video.repository;

import com.haiya.video.entity.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    List<UserAchievement> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<UserAchievement> findByUserIdAndIsAchievedTrueOrderByAchievedAtDesc(Long userId);
    List<UserAchievement> findByUserIdAndIsAchievedTrueAndIsRewardClaimedFalseOrderByAchievedAtDesc(Long userId);
    Optional<UserAchievement> findByUserIdAndAchievementType(Long userId, String achievementType);
}