package com.haiya.video.service;

import com.haiya.video.entity.UserAchievement;
import com.haiya.video.entity.UserPoints;
import com.haiya.video.repository.UserAchievementRepository;
import com.haiya.video.repository.UserPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class UserAchievementService {

    private static final Logger logger = Logger.getLogger(UserAchievementService.class.getName());

    @Autowired
    private UserAchievementRepository userAchievementRepository;
    
    @Autowired
    private UserPointsRepository userPointsRepository;

    /**
     * 为用户创建成就
     * @param userAchievement 成就信息
     * @return 创建的成就
     */
    @Transactional
    public UserAchievement createUserAchievement(UserAchievement userAchievement) {
        try {
            UserAchievement savedAchievement = userAchievementRepository.save(userAchievement);
            logger.info("Created user achievement with ID: " + savedAchievement.getId() + 
                       " for user: " + userAchievement.getUserId());
            return savedAchievement;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating user achievement for user: " + userAchievement.getUserId(), e);
            throw new RuntimeException("Failed to create user achievement", e);
        }
    }

    /**
     * 获取用户的所有成就
     * @param userId 用户ID
     * @return 成就列表
     */
    public List<UserAchievement> getUserAchievements(Long userId) {
        try {
            List<UserAchievement> achievements = userAchievementRepository.findByUserIdOrderByCreatedAtDesc(userId);
            logger.info("Retrieved " + achievements.size() + " achievements for user: " + userId);
            return achievements;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving achievements for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user achievements", e);
        }
    }

    /**
     * 获取用户已达成的成就
     * @param userId 用户ID
     * @return 已达成的成就列表
     */
    public List<UserAchievement> getUserAchievedAchievements(Long userId) {
        try {
            List<UserAchievement> achievements = userAchievementRepository.findByUserIdAndIsAchievedTrueOrderByAchievedAtDesc(userId);
            logger.info("Retrieved " + achievements.size() + " achieved achievements for user: " + userId);
            return achievements;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving achieved achievements for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user achieved achievements", e);
        }
    }

    /**
     * 获取用户未领取奖励的已达成成就
     * @param userId 用户ID
     * @return 未领取奖励的成就列表
     */
    public List<UserAchievement> getUserUnclaimedAchievements(Long userId) {
        try {
            List<UserAchievement> achievements = userAchievementRepository.findByUserIdAndIsAchievedTrueAndIsRewardClaimedFalseOrderByAchievedAtDesc(userId);
            logger.info("Retrieved " + achievements.size() + " unclaimed achievements for user: " + userId);
            return achievements;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving unclaimed achievements for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user unclaimed achievements", e);
        }
    }

    /**
     * 更新成就进度
     * @param achievementId 成就ID
     * @param value 新的数值
     * @return 更新后的成就
     */
    @Transactional
    public UserAchievement updateAchievementProgress(Long achievementId, Integer value) {
        try {
            Optional<UserAchievement> achievementOptional = userAchievementRepository.findById(achievementId);
            if (!achievementOptional.isPresent()) {
                throw new RuntimeException("Achievement not found with ID: " + achievementId);
            }
            
            UserAchievement achievement = achievementOptional.get();
            achievement.updateValue(value);
            UserAchievement updatedAchievement = userAchievementRepository.save(achievement);
            
            // 如果成就已达成，检查是否需要自动发放奖励
            if (updatedAchievement.getIsAchieved() && !updatedAchievement.getIsRewardClaimed()) {
                // 可以选择自动发放奖励或者等待用户手动领取
                logger.info("Achievement achieved: " + achievementId + " for user: " + achievement.getUserId());
            }
            
            logger.info("Updated achievement progress for achievement ID: " + achievementId + " to value: " + value);
            return updatedAchievement;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating achievement progress for achievement: " + achievementId, e);
            throw new RuntimeException("Failed to update achievement progress", e);
        }
    }

    /**
     * 领取成就奖励
     * @param achievementId 成就ID
     * @return 更新后的成就
     */
    @Transactional
    public UserAchievement claimAchievementReward(Long achievementId) {
        try {
            Optional<UserAchievement> achievementOptional = userAchievementRepository.findById(achievementId);
            if (!achievementOptional.isPresent()) {
                throw new RuntimeException("Achievement not found with ID: " + achievementId);
            }
            
            UserAchievement achievement = achievementOptional.get();
            if (!achievement.getIsAchieved()) {
                throw new RuntimeException("Achievement not yet achieved");
            }
            
            if (achievement.getIsRewardClaimed()) {
                throw new RuntimeException("Reward already claimed");
            }
            
            achievement.claimReward();
            UserAchievement updatedAchievement = userAchievementRepository.save(achievement);
            
            // 发放奖励积分和金币
            awardAchievementRewards(achievement.getUserId(), achievement.getRewardPoints(), achievement.getRewardCoins());
            
            logger.info("Claimed reward for achievement ID: " + achievementId + " for user: " + achievement.getUserId());
            return updatedAchievement;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error claiming achievement reward for achievement: " + achievementId, e);
            throw new RuntimeException("Failed to claim achievement reward", e);
        }
    }

    /**
     * 发放成就奖励
     * @param userId 用户ID
     * @param points 积分奖励
     * @param coins 金币奖励
     */
    @Transactional
    private void awardAchievementRewards(Long userId, Integer points, Integer coins) {
        try {
            // 获取或创建用户积分记录
            Optional<UserPoints> userPointsOptional = userPointsRepository.findByUserId(userId);
            UserPoints userPoints;
            
            if (userPointsOptional.isPresent()) {
                userPoints = userPointsOptional.get();
            } else {
                userPoints = new UserPoints(userId);
            }
            
            // 添加积分奖励
            if (points != null && points > 0) {
                userPoints.addPoints(points);
            }
            
            // 保存用户积分记录
            userPointsRepository.save(userPoints);
            
            logger.info("Awarded achievement rewards to user " + userId + ": " + points + " points");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error awarding achievement rewards to user: " + userId, e);
            throw new RuntimeException("Failed to award achievement rewards", e);
        }
    }

    /**
     * 删除成就
     * @param achievementId 成就ID
     */
    @Transactional
    public void deleteAchievement(Long achievementId) {
        try {
            userAchievementRepository.deleteById(achievementId);
            logger.info("Deleted achievement with ID: " + achievementId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting achievement: " + achievementId, e);
            throw new RuntimeException("Failed to delete achievement", e);
        }
    }
}