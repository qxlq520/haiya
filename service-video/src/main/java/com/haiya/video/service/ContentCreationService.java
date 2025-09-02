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
public class ContentCreationService {

    private static final Logger logger = Logger.getLogger(ContentCreationService.class.getName());

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * 更新视频质量评分
     * @param userId 用户ID
     * @param qualityScore 质量评分
     */
    @Transactional
    public void updateVideoQualityScore(Long userId, Double qualityScore) {
        try {
            if (qualityScore < 0 || qualityScore > 100) {
                throw new IllegalArgumentException("Quality score must be between 0 and 100");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setVideoQualityScore(qualityScore);
                userProfileRepository.save(profile);
                logger.info("Updated video quality score for user " + userId + " to " + qualityScore);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating video quality score for user " + userId, e);
            throw new RuntimeException("Failed to update video quality score for user: " + userId, e);
        }
    }

    /**
     * 更新视频创意度评分
     * @param userId 用户ID
     * @param creativityScore 创意度评分
     */
    @Transactional
    public void updateVideoCreativityScore(Long userId, Double creativityScore) {
        try {
            if (creativityScore < 0 || creativityScore > 100) {
                throw new IllegalArgumentException("Creativity score must be between 0 and 100");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setVideoCreativityScore(creativityScore);
                userProfileRepository.save(profile);
                logger.info("Updated video creativity score for user " + userId + " to " + creativityScore);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating video creativity score for user " + userId, e);
            throw new RuntimeException("Failed to update video creativity score for user: " + userId, e);
        }
    }

    /**
     * 更新视频技术评分
     * @param userId 用户ID
     * @param technicalScore 技术评分
     */
    @Transactional
    public void updateVideoTechnicalScore(Long userId, Double technicalScore) {
        try {
            if (technicalScore < 0 || technicalScore > 100) {
                throw new IllegalArgumentException("Technical score must be between 0 and 100");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setVideoTechnicalScore(technicalScore);
                userProfileRepository.save(profile);
                logger.info("Updated video technical score for user " + userId + " to " + technicalScore);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating video technical score for user " + userId, e);
            throw new RuntimeException("Failed to update video technical score for user: " + userId, e);
        }
    }

    /**
     * 更新内容分类
     * @param userId 用户ID
     * @param category 内容分类
     */
    @Transactional
    public void updateContentCategory(Long userId, String category) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setContentCategory(category);
                userProfileRepository.save(profile);
                logger.info("Updated content category for user " + userId + " to " + category);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating content category for user " + userId, e);
            throw new RuntimeException("Failed to update content category for user: " + userId, e);
        }
    }

    /**
     * 设置内容是否为热门内容
     * @param userId 用户ID
     * @param isHot 是否为热门内容
     */
    @Transactional
    public void updateHotContentStatus(Long userId, Boolean isHot) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setIsHotContent(isHot);
                userProfileRepository.save(profile);
                logger.info("Updated hot content status for user " + userId + " to " + isHot);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating hot content status for user " + userId, e);
            throw new RuntimeException("Failed to update hot content status for user: " + userId, e);
        }
    }

    /**
     * 更新最佳发布时间
     * @param userId 用户ID
     * @param hour 小时（0-23）
     */
    @Transactional
    public void updateBestPublishingHour(Long userId, Integer hour) {
        try {
            if (hour < 0 || hour > 23) {
                throw new IllegalArgumentException("Hour must be between 0 and 23");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setBestPublishingHour(hour);
                userProfileRepository.save(profile);
                logger.info("Updated best publishing hour for user " + userId + " to " + hour);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating best publishing hour for user " + userId, e);
            throw new RuntimeException("Failed to update best publishing hour for user: " + userId, e);
        }
    }

    /**
     * 更新内容标签
     * @param userId 用户ID
     * @param tags 内容标签（JSON格式）
     */
    @Transactional
    public void updateContentTags(Long userId, String tags) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setContentTags(tags);
                userProfileRepository.save(profile);
                logger.info("Updated content tags for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating content tags for user " + userId, e);
            throw new RuntimeException("Failed to update content tags for user: " + userId, e);
        }
    }
}