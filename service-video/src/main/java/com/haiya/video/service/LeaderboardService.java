package com.haiya.video.service;

import com.haiya.video.entity.Leaderboard;
import com.haiya.video.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class LeaderboardService {

    private static final Logger logger = Logger.getLogger(LeaderboardService.class.getName());

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    /**
     * 更新用户在排行榜中的位置
     * @param userId 用户ID
     * @param username 用户名
     * @param leaderboardType 排行榜类型
     * @param periodType 周期类型
     * @param rankValue 排行值
     * @param region 地区（可选）
     * @return 更新后的排行榜记录
     */
    @Transactional
    public Leaderboard updateUserRank(Long userId, String username, String leaderboardType, 
                                     String periodType, Double rankValue, String region) {
        try {
            // 查找现有的排行榜记录
            Leaderboard leaderboard = leaderboardRepository
                .findByUserIdAndLeaderboardTypeAndPeriodTypeAndRegion(userId, leaderboardType, periodType, region)
                .orElse(new Leaderboard(userId, username, leaderboardType, periodType, rankValue));
            
            // 更新排行值
            leaderboard.setRankValue(rankValue);
            leaderboard.setUsername(username);
            leaderboard.setRegion(region);
            
            Leaderboard savedLeaderboard = leaderboardRepository.save(leaderboard);
            logger.info("Updated user rank for user: " + userId + " in " + leaderboardType + " leaderboard");
            return savedLeaderboard;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating user rank for user: " + userId, e);
            throw new RuntimeException("Failed to update user rank", e);
        }
    }

    /**
     * 获取排行榜
     * @param leaderboardType 排行榜类型
     * @param periodType 周期类型
     * @param region 地区（可选）
     * @param limit 限制数量
     * @return 排行榜列表
     */
    public List<Leaderboard> getLeaderboard(String leaderboardType, String periodType, 
                                           String region, int limit) {
        try {
            List<Leaderboard> leaderboard;
            
            if (region != null && !region.isEmpty()) {
                List<Leaderboard> fullList = leaderboardRepository
                    .findTop10ByLeaderboardTypeAndPeriodTypeAndRegionOrderByRankValueDesc(
                        leaderboardType, periodType, region);
                leaderboard = fullList.subList(0, Math.min(limit, fullList.size()));
            } else {
                List<Leaderboard> fullList = leaderboardRepository
                    .findTop10ByLeaderboardTypeAndPeriodTypeOrderByRankValueDesc(
                        leaderboardType, periodType);
                leaderboard = fullList.subList(0, Math.min(limit, fullList.size()));
            }
            
            // 更新排名位置
            for (int i = 0; i < leaderboard.size(); i++) {
                leaderboard.get(i).setRankPosition(i + 1);
            }
            
            logger.info("Retrieved " + leaderboard.size() + " entries from " + 
                       leaderboardType + " leaderboard");
            return leaderboard;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving leaderboard: " + leaderboardType, e);
            throw new RuntimeException("Failed to retrieve leaderboard", e);
        }
    }

    /**
     * 获取用户的排行榜位置
     * @param userId 用户ID
     * @param leaderboardType 排行榜类型
     * @param periodType 周期类型
     * @param region 地区（可选）
     * @return 用户的排行榜记录
     */
    public Leaderboard getUserRank(Long userId, String leaderboardType, 
                                  String periodType, String region) {
        try {
            Leaderboard leaderboard = leaderboardRepository
                .findByUserIdAndLeaderboardTypeAndPeriodTypeAndRegion(userId, leaderboardType, periodType, region)
                .orElse(null);
            
            if (leaderboard != null) {
                logger.info("Retrieved rank for user: " + userId + " in " + leaderboardType + " leaderboard");
            } else {
                logger.info("User: " + userId + " not found in " + leaderboardType + " leaderboard");
            }
            
            return leaderboard;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving user rank for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user rank", e);
        }
    }

    /**
     * 批量更新排行榜
     * @param leaderboardList 排行榜列表
     * @return 更新后的排行榜列表
     */
    @Transactional
    public List<Leaderboard> updateLeaderboards(List<Leaderboard> leaderboardList) {
        try {
            List<Leaderboard> savedLeaderboards = leaderboardRepository.saveAll(leaderboardList);
            logger.info("Updated " + savedLeaderboards.size() + " leaderboard entries");
            return savedLeaderboards;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating leaderboards", e);
            throw new RuntimeException("Failed to update leaderboards", e);
        }
    }

    /**
     * 清除过期的排行榜数据
     * @param days 天数
     * @return 删除的记录数
     */
    @Transactional
    public int clearExpiredLeaderboards(int days) {
        try {
            LocalDateTime expiryDate = LocalDateTime.now().minusDays(days);
            int deletedCount = leaderboardRepository.deleteByCalculatedAtBefore(expiryDate);
            logger.info("Cleared " + deletedCount + " expired leaderboard entries");
            return deletedCount;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error clearing expired leaderboards", e);
            throw new RuntimeException("Failed to clear expired leaderboards", e);
        }
    }
}