package com.haiya.video.service;

import com.haiya.video.entity.UserProfile;
import com.haiya.video.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class UserPortraitService {

    private static final Logger logger = Logger.getLogger(UserPortraitService.class.getName());

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * 更新用户兴趣标签
     * @param userId 用户ID
     * @param interestTags 兴趣标签（JSON格式）
     */
    @Transactional
    public void updateInterestTags(Long userId, String interestTags) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setInterestTags(interestTags);
                userProfileRepository.save(profile);
                logger.info("Updated interest tags for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating interest tags for user " + userId, e);
            throw new RuntimeException("Failed to update interest tags for user: " + userId, e);
        }
    }

    /**
     * 更新用户行为轨迹
     * @param userId 用户ID
     * @param behaviorPath 行为轨迹（JSON格式）
     */
    @Transactional
    public void updateUserBehaviorPath(Long userId, String behaviorPath) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setUserBehaviorPath(behaviorPath);
                userProfileRepository.save(profile);
                logger.info("Updated user behavior path for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating user behavior path for user " + userId, e);
            throw new RuntimeException("Failed to update user behavior path for user: " + userId, e);
        }
    }

    /**
     * 更新内容偏好
     * @param userId 用户ID
     * @param preferences 内容偏好（JSON格式）
     */
    @Transactional
    public void updateContentPreferences(Long userId, String preferences) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setContentPreferences(preferences);
                userProfileRepository.save(profile);
                logger.info("Updated content preferences for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating content preferences for user " + userId, e);
            throw new RuntimeException("Failed to update content preferences for user: " + userId, e);
        }
    }

    /**
     * 更新用户群体分类
     * @param userId 用户ID
     * @param segmentType 用户群体分类
     */
    @Transactional
    public void updateUserSegmentType(Long userId, String segmentType) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setUserSegmentType(segmentType);
                userProfileRepository.save(profile);
                logger.info("Updated user segment type for user " + userId + " to " + segmentType);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating user segment type for user " + userId, e);
            throw new RuntimeException("Failed to update user segment type for user: " + userId, e);
        }
    }
}