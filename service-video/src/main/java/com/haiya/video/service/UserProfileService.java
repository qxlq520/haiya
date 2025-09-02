package com.haiya.video.service;

import com.haiya.video.entity.UserProfile;
import com.haiya.video.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<UserProfile> getUserProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }

    public Optional<UserProfile> getUserProfileByUsername(String username) {
        return userProfileRepository.findByUsername(username);
    }

    public UserProfile createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Transactional
    public Optional<UserProfile> updateUserProfile(Long userId, UserProfile profileDetails) {
        return userProfileRepository.findByUserId(userId).map(profile -> {
            profile.setUsername(profileDetails.getUsername());
            profile.setAvatarUrl(profileDetails.getAvatarUrl());
            profile.setBio(profileDetails.getBio());
            profile.setLocation(profileDetails.getLocation());
            profile.setWebsite(profileDetails.getWebsite());
            profile.setBirthday(profileDetails.getBirthday());
            return userProfileRepository.save(profile);
        });
    }

    @Transactional
    public void updateFollowerCount(Long userId, Long count) {
        userProfileRepository.findByUserId(userId).ifPresent(profile -> {
            profile.setFollowerCount(count);
            userProfileRepository.save(profile);
        });
    }

    @Transactional
    public void updateFollowingCount(Long userId, Long count) {
        userProfileRepository.findByUserId(userId).ifPresent(profile -> {
            profile.setFollowingCount(count);
            userProfileRepository.save(profile);
        });
    }

    @Transactional
    public void updateVideoCount(Long userId, Long count) {
        userProfileRepository.findByUserId(userId).ifPresent(profile -> {
            profile.setVideoCount(count);
            userProfileRepository.save(profile);
        });
    }
    
    /**
     * 获取用户统计数据的摘要
     * @param userId 用户ID
     * @return 包含用户统计信息的UserProfile对象，如果用户不存在则返回null
     */
    public UserProfile getUserStatsSummary(Long userId) {
        return userProfileRepository.findByUserId(userId).orElse(null);
    }
    
    /**
     * 获取用户活跃度统计数据
     * @param userId 用户ID
     * @return 用户活跃度统计数据
     */
    public Map<String, Object> getUserActivityStats(Long userId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
        Map<String, Object> stats = new HashMap<>();
        
        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            stats.put("consecutiveLoginDays", profile.getConsecutiveLoginDays());
            stats.put("onlineDuration", profile.getOnlineDuration());
            stats.put("weeklyActivityScore", profile.getWeeklyActivityScore());
            stats.put("monthlyActivityScore", profile.getMonthlyActivityScore());
        }
        
        return stats;
    }
    
    /**
     * 获取用户内容表现统计数据
     * @param userId 用户ID
     * @return 用户内容表现统计数据
     */
    public Map<String, Object> getUserContentStats(Long userId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
        Map<String, Object> stats = new HashMap<>();
        
        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            stats.put("avgWatchDuration", profile.getAvgWatchDuration());
            stats.put("completionRate", profile.getCompletionRate());
            stats.put("shareConversionRate", profile.getShareConversionRate());
        }
        
        return stats;
    }
    
    /**
     * 获取用户社交影响力统计数据
     * @param userId 用户ID
     * @return 用户社交影响力统计数据
     */
    public Map<String, Object> getUserInfluenceStats(Long userId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
        Map<String, Object> stats = new HashMap<>();
        
        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            stats.put("spreadIndex", profile.getSpreadIndex());
            stats.put("influenceIndex", profile.getInfluenceIndex());
            stats.put("creativityIndex", profile.getCreativityIndex());
        }
        
        return stats;
    }
    
    /**
     * 获取用户成长体系数据
     * @param userId 用户ID
     * @return 用户成长体系数据
     */
    public Map<String, Object> getUserGrowthStats(Long userId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
        Map<String, Object> stats = new HashMap<>();
        
        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            stats.put("userLevel", profile.getUserLevel());
            stats.put("creatorLevel", profile.getCreatorLevel());
        }
        
        return stats;
    }
    
    /**
     * 获取用户商业数据统计
     * @param userId 用户ID
     * @return 用户商业数据统计
     */
    public Map<String, Object> getUserBusinessStats(Long userId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
        Map<String, Object> stats = new HashMap<>();
        
        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            stats.put("productViews", profile.getProductViews());
            stats.put("salesAmount", profile.getSalesAmount());
        }
        
        return stats;
    }
}