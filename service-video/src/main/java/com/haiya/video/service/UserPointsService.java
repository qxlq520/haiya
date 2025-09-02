package com.haiya.video.service;

import com.haiya.video.entity.UserPoints;
import com.haiya.video.repository.UserPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class UserPointsService {

    private static final Logger logger = Logger.getLogger(UserPointsService.class.getName());

    @Autowired
    private UserPointsRepository userPointsRepository;

    /**
     * 获取用户积分信息
     * @param userId 用户ID
     * @return 用户积分信息
     */
    public Optional<UserPoints> getUserPoints(Long userId) {
        try {
            Optional<UserPoints> userPoints = userPointsRepository.findByUserId(userId);
            if (userPoints.isPresent()) {
                logger.info("Retrieved points for user: " + userId);
            } else {
                logger.info("No points record found for user: " + userId);
            }
            return userPoints;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving points for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user points", e);
        }
    }

    /**
     * 创建或更新用户积分记录
     * @param userId 用户ID
     * @param points 积分变化数量
     * @return 更新后的用户积分信息
     */
    @Transactional
    public UserPoints updateUserPoints(Long userId, Integer points) {
        try {
            // 获取或创建用户积分记录
            Optional<UserPoints> userPointsOptional = userPointsRepository.findByUserId(userId);
            UserPoints userPoints;
            
            if (userPointsOptional.isPresent()) {
                userPoints = userPointsOptional.get();
            } else {
                userPoints = new UserPoints(userId);
            }
            
            // 更新积分
            if (points != null && points > 0) {
                userPoints.addPoints(points);
            }
            
            UserPoints savedUserPoints = userPointsRepository.save(userPoints);
            logger.info("Updated points for user: " + userId + " with " + points + " points");
            return savedUserPoints;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating points for user: " + userId, e);
            throw new RuntimeException("Failed to update user points", e);
        }
    }

    /**
     * 消费用户积分
     * @param userId 用户ID
     * @param points 消费的积分数
     * @return true表示消费成功，false表示积分不足
     */
    @Transactional
    public boolean consumeUserPoints(Long userId, Integer points) {
        try {
            Optional<UserPoints> userPointsOptional = userPointsRepository.findByUserId(userId);
            if (!userPointsOptional.isPresent()) {
                logger.info("No points record found for user: " + userId);
                return false;
            }
            
            UserPoints userPoints = userPointsOptional.get();
            boolean consumed = userPoints.consumePoints(points);
            
            if (consumed) {
                userPointsRepository.save(userPoints);
                logger.info("Consumed " + points + " points for user: " + userId);
            } else {
                logger.info("Insufficient points for user: " + userId + ", requested: " + points);
            }
            
            return consumed;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error consuming points for user: " + userId, e);
            throw new RuntimeException("Failed to consume user points", e);
        }
    }

    /**
     * 检查用户积分是否足够
     * @param userId 用户ID
     * @param points 需要的积分数
     * @return true表示积分足够
     */
    public boolean hasEnoughPoints(Long userId, Integer points) {
        try {
            Optional<UserPoints> userPointsOptional = userPointsRepository.findByUserId(userId);
            if (!userPointsOptional.isPresent()) {
                return false;
            }
            
            boolean hasEnough = userPointsOptional.get().hasEnoughPoints(points);
            logger.info("User " + userId + " has enough points: " + hasEnough + ", requested: " + points);
            return hasEnough;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking points for user: " + userId, e);
            throw new RuntimeException("Failed to check user points", e);
        }
    }

    /**
     * 获取用户的可用积分
     * @param userId 用户ID
     * @return 可用积分数
     */
    public Integer getAvailablePoints(Long userId) {
        try {
            Optional<UserPoints> userPointsOptional = userPointsRepository.findByUserId(userId);
            int availablePoints = userPointsOptional.map(UserPoints::getAvailablePoints).orElse(0);
            logger.info("Retrieved available points for user " + userId + ": " + availablePoints);
            return availablePoints;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving available points for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve available points", e);
        }
    }
}