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
public class SocialNetworkService {

    private static final Logger logger = Logger.getLogger(SocialNetworkService.class.getName());

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * 更新社交关系图谱
     * @param userId 用户ID
     * @param relationshipGraph 社交关系图谱（JSON格式）
     */
    @Transactional
    public void updateSocialRelationshipGraph(Long userId, String relationshipGraph) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setSocialRelationshipGraph(relationshipGraph);
                userProfileRepository.save(profile);
                logger.info("Updated social relationship graph for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating social relationship graph for user " + userId, e);
            throw new RuntimeException("Failed to update social relationship graph for user: " + userId, e);
        }
    }

    /**
     * 更新社区归属
     * @param userId 用户ID
     * @param communityAffiliation 社区归属（JSON格式）
     */
    @Transactional
    public void updateCommunityAffiliation(Long userId, String communityAffiliation) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setCommunityAffiliation(communityAffiliation);
                userProfileRepository.save(profile);
                logger.info("Updated community affiliation for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating community affiliation for user " + userId, e);
            throw new RuntimeException("Failed to update community affiliation for user: " + userId, e);
        }
    }

    /**
     * 更新互动质量评分
     * @param userId 用户ID
     * @param qualityScore 互动质量评分
     */
    @Transactional
    public void updateInteractionQualityScore(Long userId, Double qualityScore) {
        try {
            if (qualityScore < 0 || qualityScore > 100) {
                throw new IllegalArgumentException("Quality score must be between 0 and 100");
            }
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setInteractionQualityScore(qualityScore);
                userProfileRepository.save(profile);
                logger.info("Updated interaction quality score for user " + userId + " to " + qualityScore);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating interaction quality score for user " + userId, e);
            throw new RuntimeException("Failed to update interaction quality score for user: " + userId, e);
        }
    }

    /**
     * 更新好友推荐
     * @param userId 用户ID
     * @param friendRecommendations 好友推荐（JSON格式）
     */
    @Transactional
    public void updateFriendRecommendations(Long userId, String friendRecommendations) {
        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
            if (userProfile.isPresent()) {
                UserProfile profile = userProfile.get();
                profile.setFriendRecommendations(friendRecommendations);
                userProfileRepository.save(profile);
                logger.info("Updated friend recommendations for user " + userId);
            } else {
                logger.warning("User profile not found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating friend recommendations for user " + userId, e);
            throw new RuntimeException("Failed to update friend recommendations for user: " + userId, e);
        }
    }
}