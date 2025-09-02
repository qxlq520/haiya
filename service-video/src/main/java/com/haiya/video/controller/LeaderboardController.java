package com.haiya.video.controller;

import com.haiya.video.entity.Leaderboard;
import com.haiya.video.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    /**
     * 获取排行榜
     * @param leaderboardType 排行榜类型
     * @param periodType 周期类型
     * @param region 地区（可选）
     * @param limit 限制数量
     * @return 排行榜列表
     */
    @GetMapping
    public ResponseEntity<List<Leaderboard>> getLeaderboard(
            @RequestParam String leaderboardType,
            @RequestParam String periodType,
            @RequestParam(required = false) String region,
            @RequestParam(defaultValue = "50") int limit) {
        List<Leaderboard> leaderboard = leaderboardService
            .getLeaderboard(leaderboardType, periodType, region, limit);
        return ResponseEntity.ok(leaderboard);
    }

    /**
     * 获取用户的排行榜位置
     * @param userId 用户ID
     * @param leaderboardType 排行榜类型
     * @param periodType 周期类型
     * @param region 地区（可选）
     * @return 用户的排行榜记录
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Leaderboard> getUserRank(
            @PathVariable Long userId,
            @RequestParam String leaderboardType,
            @RequestParam String periodType,
            @RequestParam(required = false) String region) {
        Leaderboard userRank = leaderboardService
            .getUserRank(userId, leaderboardType, periodType, region);
        if (userRank != null) {
            return ResponseEntity.ok(userRank);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
    @PostMapping("/user/{userId}")
    public ResponseEntity<Leaderboard> updateUserRank(
            @PathVariable Long userId,
            @RequestParam String username,
            @RequestParam String leaderboardType,
            @RequestParam String periodType,
            @RequestParam Double rankValue,
            @RequestParam(required = false) String region) {
        Leaderboard updatedRank = leaderboardService
            .updateUserRank(userId, username, leaderboardType, periodType, rankValue, region);
        return ResponseEntity.ok(updatedRank);
    }

    /**
     * 批量更新排行榜
     * @param leaderboardList 排行榜列表
     * @return 更新后的排行榜列表
     */
    @PostMapping("/batch")
    public ResponseEntity<List<Leaderboard>> updateLeaderboards(
            @RequestBody List<Leaderboard> leaderboardList) {
        List<Leaderboard> updatedLeaderboards = leaderboardService.updateLeaderboards(leaderboardList);
        return ResponseEntity.ok(updatedLeaderboards);
    }

    /**
     * 清除过期的排行榜数据
     * @param days 天数
     * @return 清除结果
     */
    @DeleteMapping("/expired")
    public ResponseEntity<Map<String, Object>> clearExpiredLeaderboards(@RequestParam int days) {
        int deletedCount = leaderboardService.clearExpiredLeaderboards(days);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expired leaderboards cleared successfully");
        response.put("deletedCount", deletedCount);
        return ResponseEntity.ok(response);
    }
}