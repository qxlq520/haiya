package com.haiya.video.service;

import com.haiya.video.entity.UserProfile;
import com.haiya.video.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class UserProfileStatsService {

    private static final Logger logger = Logger.getLogger(UserProfileStatsService.class.getName());

    @Autowired
    private UserProfileRepository userProfileRepository;

    // ==================== 原有方法 ====================

    /**
     * 异步更新用户的视频数量
     * @param userId 用户ID
     * @param count 视频数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> updateVideoCountAsync(Long userId, Long count) {
        updateVideoCount(userId, count);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 更新用户的视频数量
     * @param userId 用户ID
     * @param count 视频数量
     */
    @Transactional
    public void updateVideoCount(Long userId, Long count) {
        try {
            if (count < 0) {
                throw new IllegalArgumentException("Video count cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setVideoCount(count);
                userProfileRepository.save(profile);
                logger.info("Updated video count for user " + userId + " to " + count);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating video count for user " + userId, e);
            throw new RuntimeException("Failed to update video count for user: " + userId, e);
        }
    }

    /**
     * 异步增加用户的视频数量
     * @param userId 用户ID
     * @param increment 增加的数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> incrementVideoCountAsync(Long userId, Long increment) {
        incrementVideoCount(userId, increment);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 增加用户的视频数量
     * @param userId 用户ID
     * @param increment 增加的数量
     */
    @Transactional
    public void incrementVideoCount(Long userId, Long increment) {
        try {
            if (increment < 0) {
                throw new IllegalArgumentException("Increment cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentCount = profile.getVideoCount();
                profile.setVideoCount(currentCount + increment);
                userProfileRepository.save(profile);
                logger.info("Incremented video count for user " + userId + " by " + increment);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error incrementing video count for user " + userId, e);
            throw new RuntimeException("Failed to increment video count for user: " + userId, e);
        }
    }

    /**
     * 异步减少用户的视频数量
     * @param userId 用户ID
     * @param decrement 减少的数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> decrementVideoCountAsync(Long userId, Long decrement) {
        decrementVideoCount(userId, decrement);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 减少用户的视频数量
     * @param userId 用户ID
     * @param decrement 减少的数量
     */
    @Transactional
    public void decrementVideoCount(Long userId, Long decrement) {
        try {
            if (decrement < 0) {
                throw new IllegalArgumentException("Decrement cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentCount = profile.getVideoCount();
                profile.setVideoCount(Math.max(0, currentCount - decrement));
                userProfileRepository.save(profile);
                logger.info("Decremented video count for user " + userId + " by " + decrement);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error decrementing video count for user " + userId, e);
            throw new RuntimeException("Failed to decrement video count for user: " + userId, e);
        }
    }

    /**
     * 异步更新用户的关注数量
     * @param userId 用户ID
     * @param count 关注数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> updateFollowingCountAsync(Long userId, Long count) {
        updateFollowingCount(userId, count);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 更新用户的关注数量
     * @param userId 用户ID
     * @param count 关注数量
     */
    @Transactional
    public void updateFollowingCount(Long userId, Long count) {
        try {
            if (count < 0) {
                throw new IllegalArgumentException("Following count cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setFollowingCount(count);
                userProfileRepository.save(profile);
                logger.info("Updated following count for user " + userId + " to " + count);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating following count for user " + userId, e);
            throw new RuntimeException("Failed to update following count for user: " + userId, e);
        }
    }

    /**
     * 异步增加用户的关注数量
     * @param userId 用户ID
     * @param increment 增加的数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> incrementFollowingCountAsync(Long userId, Long increment) {
        incrementFollowingCount(userId, increment);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 增加用户的关注数量
     * @param userId 用户ID
     * @param increment 增加的数量
     */
    @Transactional
    public void incrementFollowingCount(Long userId, Long increment) {
        try {
            if (increment < 0) {
                throw new IllegalArgumentException("Increment cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentCount = profile.getFollowingCount();
                profile.setFollowingCount(currentCount + increment);
                userProfileRepository.save(profile);
                logger.info("Incremented following count for user " + userId + " by " + increment);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error incrementing following count for user " + userId, e);
            throw new RuntimeException("Failed to increment following count for user: " + userId, e);
        }
    }

    /**
     * 异步减少用户的关注数量
     * @param userId 用户ID
     * @param decrement 减少的数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> decrementFollowingCountAsync(Long userId, Long decrement) {
        decrementFollowingCount(userId, decrement);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 减少用户的关注数量
     * @param userId 用户ID
     * @param decrement 减少的数量
     */
    @Transactional
    public void decrementFollowingCount(Long userId, Long decrement) {
        try {
            if (decrement < 0) {
                throw new IllegalArgumentException("Decrement cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentCount = profile.getFollowingCount();
                profile.setFollowingCount(Math.max(0, currentCount - decrement));
                userProfileRepository.save(profile);
                logger.info("Decremented following count for user " + userId + " by " + decrement);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error decrementing following count for user " + userId, e);
            throw new RuntimeException("Failed to decrement following count for user: " + userId, e);
        }
    }

    /**
     * 异步更新用户的粉丝数量
     * @param userId 用户ID
     * @param count 粉丝数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> updateFollowerCountAsync(Long userId, Long count) {
        updateFollowerCount(userId, count);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 更新用户的粉丝数量
     * @param userId 用户ID
     * @param count 粉丝数量
     */
    @Transactional
    public void updateFollowerCount(Long userId, Long count) {
        try {
            if (count < 0) {
                throw new IllegalArgumentException("Follower count cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setFollowerCount(count);
                userProfileRepository.save(profile);
                logger.info("Updated follower count for user " + userId + " to " + count);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating follower count for user " + userId, e);
            throw new RuntimeException("Failed to update follower count for user: " + userId, e);
        }
    }

    /**
     * 异步增加用户的粉丝数量
     * @param userId 用户ID
     * @param increment 增加的数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> incrementFollowerCountAsync(Long userId, Long increment) {
        incrementFollowerCount(userId, increment);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 增加用户的粉丝数量
     * @param userId 用户ID
     * @param increment 增加的数量
     */
    @Transactional
    public void incrementFollowerCount(Long userId, Long increment) {
        try {
            if (increment < 0) {
                throw new IllegalArgumentException("Increment cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentCount = profile.getFollowerCount();
                profile.setFollowerCount(currentCount + increment);
                userProfileRepository.save(profile);
                logger.info("Incremented follower count for user " + userId + " by " + increment);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error incrementing follower count for user " + userId, e);
            throw new RuntimeException("Failed to increment follower count for user: " + userId, e);
        }
    }

    /**
     * 异步减少用户的粉丝数量
     * @param userId 用户ID
     * @param decrement 减少的数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> decrementFollowerCountAsync(Long userId, Long decrement) {
        decrementFollowerCount(userId, decrement);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 减少用户的粉丝数量
     * @param userId 用户ID
     * @param decrement 减少的数量
     */
    @Transactional
    public void decrementFollowerCount(Long userId, Long decrement) {
        try {
            if (decrement < 0) {
                throw new IllegalArgumentException("Decrement cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentCount = profile.getFollowerCount();
                profile.setFollowerCount(Math.max(0, currentCount - decrement));
                userProfileRepository.save(profile);
                logger.info("Decremented follower count for user " + userId + " by " + decrement);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error decrementing follower count for user " + userId, e);
            throw new RuntimeException("Failed to decrement follower count for user: " + userId, e);
        }
    }

    /**
     * 异步批量更新用户的统计数据
     * @param userId 用户ID
     * @param videoCount 视频数量
     * @param followingCount 关注数量
     * @param followerCount 粉丝数量
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> updateAllStatsAsync(Long userId, Long videoCount, Long followingCount, Long followerCount) {
        updateAllStats(userId, videoCount, followingCount, followerCount);
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 批量更新用户的统计数据
     * @param userId 用户ID
     * @param videoCount 视频数量
     * @param followingCount 关注数量
     * @param followerCount 粉丝数量
     */
    @Transactional
    public void updateAllStats(Long userId, Long videoCount, Long followingCount, Long followerCount) {
        try {
            if (videoCount < 0 || followingCount < 0 || followerCount < 0) {
                throw new IllegalArgumentException("Stats counts cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setVideoCount(videoCount);
                profile.setFollowingCount(followingCount);
                profile.setFollowerCount(followerCount);
                userProfileRepository.save(profile);
                logger.info("Updated all stats for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating all stats for user " + userId, e);
            throw new RuntimeException("Failed to update all stats for user: " + userId, e);
        }
    }

    /**
     * 获取用户统计数据
     * @param userId 用户ID
     * @return 用户统计数据
     */
    public Optional<UserProfile> getUserStats(Long userId) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (!userProfile.isPresent()) {
                logger.warning("User profile not found for user ID: " + userId);
            }
            return userProfile;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting user stats for user " + userId, e);
            throw new RuntimeException("Failed to get user stats for user: " + userId, e);
        }
    }

    /**
     * 获取用户视频数量
     * @param userId 用户ID
     * @return 视频数量
     */
    public Long getVideoCount(Long userId) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (!userProfile.isPresent()) {
                logger.warning("User profile not found for user ID: " + userId);
            }
            return userProfile.map(UserProfile::getVideoCount).orElse(0L);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting video count for user " + userId, e);
            throw new RuntimeException("Failed to get video count for user: " + userId, e);
        }
    }

    /**
     * 获取用户关注数量
     * @param userId 用户ID
     * @return 关注数量
     */
    public Long getFollowingCount(Long userId) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (!userProfile.isPresent()) {
                logger.warning("User profile not found for user ID: " + userId);
            }
            return userProfile.map(UserProfile::getFollowingCount).orElse(0L);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting following count for user " + userId, e);
            throw new RuntimeException("Failed to get following count for user: " + userId, e);
        }
    }

    /**
     * 获取用户粉丝数量
     * @param userId 用户ID
     * @return 粉丝数量
     */
    public Long getFollowerCount(Long userId) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (!userProfile.isPresent()) {
                logger.warning("User profile not found for user ID: " + userId);
            }
            return userProfile.map(UserProfile::getFollowerCount).orElse(0L);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting follower count for user " + userId, e);
            throw new RuntimeException("Failed to get follower count for user: " + userId, e);
        }
    }

    // ==================== 新增功能模块 ====================

    // ==================== 精细化用户行为分析 ====================

    /**
     * 更新用户连续登录天数
     * @param userId 用户ID
     * @param consecutiveDays 连续登录天数
     */
    @Transactional
    public void updateConsecutiveLoginDays(Long userId, Integer consecutiveDays) {
        try {
            if (consecutiveDays < 0) {
                throw new IllegalArgumentException("Consecutive login days cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setConsecutiveLoginDays(consecutiveDays);
                userProfileRepository.save(profile);
                logger.info("Updated consecutive login days for user " + userId + " to " + consecutiveDays);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating consecutive login days for user " + userId, e);
            throw new RuntimeException("Failed to update consecutive login days for user: " + userId, e);
        }
    }

    /**
     * 增加用户在线时长
     * @param userId 用户ID
     * @param duration 在线时长（秒）
     */
    @Transactional
    public void incrementOnlineDuration(Long userId, Long duration) {
        try {
            if (duration < 0) {
                throw new IllegalArgumentException("Duration cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentDuration = profile.getOnlineDuration();
                profile.setOnlineDuration(currentDuration + duration);
                userProfileRepository.save(profile);
                logger.info("Incremented online duration for user " + userId + " by " + duration);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error incrementing online duration for user " + userId, e);
            throw new RuntimeException("Failed to increment online duration for user: " + userId, e);
        }
    }

    /**
     * 更新用户每周活跃度评分
     * @param userId 用户ID
     * @param score 活跃度评分
     */
    @Transactional
    public void updateWeeklyActivityScore(Long userId, Double score) {
        try {
            if (score < 0) {
                throw new IllegalArgumentException("Activity score cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setWeeklyActivityScore(score);
                userProfileRepository.save(profile);
                logger.info("Updated weekly activity score for user " + userId + " to " + score);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating weekly activity score for user " + userId, e);
            throw new RuntimeException("Failed to update weekly activity score for user: " + userId, e);
        }
    }

    /**
     * 更新用户每月活跃度评分
     * @param userId 用户ID
     * @param score 活跃度评分
     */
    @Transactional
    public void updateMonthlyActivityScore(Long userId, Double score) {
        try {
            if (score < 0) {
                throw new IllegalArgumentException("Activity score cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setMonthlyActivityScore(score);
                userProfileRepository.save(profile);
                logger.info("Updated monthly activity score for user " + userId + " to " + score);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating monthly activity score for user " + userId, e);
            throw new RuntimeException("Failed to update monthly activity score for user: " + userId, e);
        }
    }

    // ==================== 用户互动统计 ====================

    /**
     * 增加用户间互相点赞次数（需要额外的实体和Repository支持）
     * @param userId 用户ID
     * @param targetUserId 目标用户ID
     * @param count 点赞次数
     */
    @Transactional
    public void incrementMutualLikes(Long userId, Long targetUserId, Long count) {
        // 这个功能需要创建额外的实体和Repository来存储用户间的互动数据
        // 暂时记录日志，实际实现需要额外的数据表支持
        logger.info("Incrementing mutual likes between user " + userId + " and user " + targetUserId + " by " + count);
    }

    /**
     * 增加用户间互相评论次数（需要额外的实体和Repository支持）
     * @param userId 用户ID
     * @param targetUserId 目标用户ID
     * @param count 评论次数
     */
    @Transactional
    public void incrementMutualComments(Long userId, Long targetUserId, Long count) {
        // 这个功能需要创建额外的实体和Repository来存储用户间的互动数据
        // 暂时记录日志，实际实现需要额外的数据表支持
        logger.info("Incrementing mutual comments between user " + userId + " and user " + targetUserId + " by " + count);
    }

    /**
     * 增加用户间私信互动次数（需要额外的实体和Repository支持）
     * @param userId 用户ID
     * @param targetUserId 目标用户ID
     * @param count 私信次数
     */
    @Transactional
    public void incrementPrivateMessages(Long userId, Long targetUserId, Long count) {
        // 这个功能需要创建额外的实体和Repository来存储用户间的互动数据
        // 暂时记录日志，实际实现需要额外的数据表支持
        logger.info("Incrementing private messages between user " + userId + " and user " + targetUserId + " by " + count);
    }

    // ==================== 内容表现数据 ====================

    /**
     * 更新用户视频的平均观看时长
     * @param userId 用户ID
     * @param avgWatchDuration 平均观看时长（秒）
     */
    @Transactional
    public void updateAvgWatchDuration(Long userId, Long avgWatchDuration) {
        try {
            if (avgWatchDuration < 0) {
                throw new IllegalArgumentException("Average watch duration cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setAvgWatchDuration(avgWatchDuration);
                userProfileRepository.save(profile);
                logger.info("Updated average watch duration for user " + userId + " to " + avgWatchDuration);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating average watch duration for user " + userId, e);
            throw new RuntimeException("Failed to update average watch duration for user: " + userId, e);
        }
    }

    /**
     * 更新用户视频的完整观看率
     * @param userId 用户ID
     * @param completionRate 完整观看率（百分比）
     */
    @Transactional
    public void updateCompletionRate(Long userId, Double completionRate) {
        try {
            if (completionRate < 0 || completionRate > 100) {
                throw new IllegalArgumentException("Completion rate must be between 0 and 100");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setCompletionRate(completionRate);
                userProfileRepository.save(profile);
                logger.info("Updated completion rate for user " + userId + " to " + completionRate);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating completion rate for user " + userId, e);
            throw new RuntimeException("Failed to update completion rate for user: " + userId, e);
        }
    }

    /**
     * 更新用户视频的分享转化率
     * @param userId 用户ID
     * @param conversionRate 分享转化率（百分比）
     */
    @Transactional
    public void updateShareConversionRate(Long userId, Double conversionRate) {
        try {
            if (conversionRate < 0 || conversionRate > 100) {
                throw new IllegalArgumentException("Share conversion rate must be between 0 and 100");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setShareConversionRate(conversionRate);
                userProfileRepository.save(profile);
                logger.info("Updated share conversion rate for user " + userId + " to " + conversionRate);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating share conversion rate for user " + userId, e);
            throw new RuntimeException("Failed to update share conversion rate for user: " + userId, e);
        }
    }

    // ==================== 社交影响力评估 ====================

    /**
     * 更新用户传播力指数
     * @param userId 用户ID
     * @param spreadIndex 传播力指数
     */
    @Transactional
    public void updateSpreadIndex(Long userId, Double spreadIndex) {
        try {
            if (spreadIndex < 0) {
                throw new IllegalArgumentException("Spread index cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setSpreadIndex(spreadIndex);
                userProfileRepository.save(profile);
                logger.info("Updated spread index for user " + userId + " to " + spreadIndex);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating spread index for user " + userId, e);
            throw new RuntimeException("Failed to update spread index for user: " + userId, e);
        }
    }

    /**
     * 更新用户影响力指数
     * @param userId 用户ID
     * @param influenceIndex 影响力指数
     */
    @Transactional
    public void updateInfluenceIndex(Long userId, Double influenceIndex) {
        try {
            if (influenceIndex < 0) {
                throw new IllegalArgumentException("Influence index cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setInfluenceIndex(influenceIndex);
                userProfileRepository.save(profile);
                logger.info("Updated influence index for user " + userId + " to " + influenceIndex);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating influence index for user " + userId, e);
            throw new RuntimeException("Failed to update influence index for user: " + userId, e);
        }
    }

    /**
     * 更新用户创造力指数
     * @param userId 用户ID
     * @param creativityIndex 创造力指数
     */
    @Transactional
    public void updateCreativityIndex(Long userId, Double creativityIndex) {
        try {
            if (creativityIndex < 0) {
                throw new IllegalArgumentException("Creativity index cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setCreativityIndex(creativityIndex);
                userProfileRepository.save(profile);
                logger.info("Updated creativity index for user " + userId + " to " + creativityIndex);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating creativity index for user " + userId, e);
            throw new RuntimeException("Failed to update creativity index for user: " + userId, e);
        }
    }

    // ==================== 用户成长体系 ====================

    /**
     * 更新用户等级
     * @param userId 用户ID
     * @param level 用户等级
     */
    @Transactional
    public void updateUserLevel(Long userId, Integer level) {
        try {
            if (level < 0) {
                throw new IllegalArgumentException("User level cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setUserLevel(level);
                userProfileRepository.save(profile);
                logger.info("Updated user level for user " + userId + " to " + level);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating user level for user " + userId, e);
            throw new RuntimeException("Failed to update user level for user: " + userId, e);
        }
    }

    /**
     * 更新创作者等级
     * @param userId 用户ID
     * @param level 创作者等级
     */
    @Transactional
    public void updateCreatorLevel(Long userId, Integer level) {
        try {
            if (level < 0) {
                throw new IllegalArgumentException("Creator level cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setCreatorLevel(level);
                userProfileRepository.save(profile);
                logger.info("Updated creator level for user " + userId + " to " + level);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating creator level for user " + userId, e);
            throw new RuntimeException("Failed to update creator level for user: " + userId, e);
        }
    }

    // ==================== 商业数据统计 ====================

    /**
     * 增加商品浏览量
     * @param userId 用户ID
     * @param increment 增加数量
     */
    @Transactional
    public void incrementProductViews(Long userId, Long increment) {
        try {
            if (increment < 0) {
                throw new IllegalArgumentException("Increment cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Long currentViews = profile.getProductViews();
                profile.setProductViews(currentViews + increment);
                userProfileRepository.save(profile);
                logger.info("Incremented product views for user " + userId + " by " + increment);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error incrementing product views for user " + userId, e);
            throw new RuntimeException("Failed to increment product views for user: " + userId, e);
        }
    }

    /**
     * 增加商品销售额
     * @param userId 用户ID
     * @param amount 销售额
     */
    @Transactional
    public void incrementSalesAmount(Long userId, Double amount) {
        try {
            if (amount < 0) {
                throw new IllegalArgumentException("Sales amount cannot be negative");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                Double currentAmount = profile.getSalesAmount();
                profile.setSalesAmount(currentAmount + amount);
                userProfileRepository.save(profile);
                logger.info("Incremented sales amount for user " + userId + " by " + amount);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error incrementing sales amount for user " + userId, e);
            throw new RuntimeException("Failed to increment sales amount for user: " + userId, e);
        }
    }

    // ==================== 大数据分析能力 ====================

    /**
     * 更新用户活跃时间段分布（需要额外的实体和Repository支持）
     * @param userId 用户ID
     * @param hour 小时（0-23）
     * @param count 活跃次数
     */
    @Transactional
    public void updateActiveHourDistribution(Long userId, Integer hour, Long count) {
        // 这个功能需要创建额外的实体和Repository来存储用户活跃时间分布数据
        // 暂时记录日志，实际实现需要额外的数据表支持
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        logger.info("Updating active hour distribution for user " + userId + " at hour " + hour + " with count " + count);
    }

    /**
     * 更新用户地域分布（需要额外的实体和Repository支持）
     * @param userId 用户ID
     * @param region 地域
     * @param count 活跃次数
     */
    @Transactional
    public void updateRegionDistribution(Long userId, String region, Long count) {
        // 这个功能需要创建额外的实体和Repository来存储用户地域分布数据
        // 暂时记录日志，实际实现需要额外的数据表支持
        logger.info("Updating region distribution for user " + userId + " in region " + region + " with count " + count);
    }

    /**
     * 更新用户设备类型分布（需要额外的实体和Repository支持）
     * @param userId 用户ID
     * @param deviceType 设备类型
     * @param count 使用次数
     */
    @Transactional
    public void updateDeviceTypeDistribution(Long userId, String deviceType, Long count) {
        // 这个功能需要创建额外的实体和Repository来存储用户设备类型分布数据
        // 暂时记录日志，实际实现需要额外的数据表支持
        logger.info("Updating device type distribution for user " + userId + " on device " + deviceType + " with count " + count);
    }
}