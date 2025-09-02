package com.haiya.video.controller;

import com.haiya.video.entity.UserAchievement;
import com.haiya.video.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-achievements")
public class UserAchievementController {

    @Autowired
    private UserAchievementService userAchievementService;

    /**
     * 获取用户的所有成就
     * @param userId 用户ID
     * @return 成就列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAchievement>> getUserAchievements(@PathVariable Long userId) {
        List<UserAchievement> achievements = userAchievementService.getUserAchievements(userId);
        return ResponseEntity.ok(achievements);
    }

    /**
     * 获取用户已达成的成就
     * @param userId 用户ID
     * @return 已达成的成就列表
     */
    @GetMapping("/user/{userId}/achieved")
    public ResponseEntity<List<UserAchievement>> getUserAchievedAchievements(@PathVariable Long userId) {
        List<UserAchievement> achievements = userAchievementService.getUserAchievedAchievements(userId);
        return ResponseEntity.ok(achievements);
    }

    /**
     * 获取用户未领取奖励的已达成成就
     * @param userId 用户ID
     * @return 未领取奖励的成就列表
     */
    @GetMapping("/user/{userId}/unclaimed")
    public ResponseEntity<List<UserAchievement>> getUserUnclaimedAchievements(@PathVariable Long userId) {
        List<UserAchievement> achievements = userAchievementService.getUserUnclaimedAchievements(userId);
        return ResponseEntity.ok(achievements);
    }

    /**
     * 创建用户成就
     * @param userAchievement 成就信息
     * @return 创建的成就
     */
    @PostMapping
    public ResponseEntity<UserAchievement> createUserAchievement(@RequestBody UserAchievement userAchievement) {
        UserAchievement createdAchievement = userAchievementService.createUserAchievement(userAchievement);
        return ResponseEntity.ok(createdAchievement);
    }

    /**
     * 更新成就进度
     * @param achievementId 成就ID
     * @param value 新的数值
     * @return 更新后的成就
     */
    @PutMapping("/{achievementId}/progress")
    public ResponseEntity<UserAchievement> updateAchievementProgress(@PathVariable Long achievementId, 
                                                                    @RequestParam Integer value) {
        UserAchievement updatedAchievement = userAchievementService.updateAchievementProgress(achievementId, value);
        return ResponseEntity.ok(updatedAchievement);
    }

    /**
     * 领取成就奖励
     * @param achievementId 成就ID
     * @return 更新后的成就
     */
    @PostMapping("/{achievementId}/claim")
    public ResponseEntity<UserAchievement> claimAchievementReward(@PathVariable Long achievementId) {
        UserAchievement updatedAchievement = userAchievementService.claimAchievementReward(achievementId);
        return ResponseEntity.ok(updatedAchievement);
    }

    /**
     * 删除成就
     * @param achievementId 成就ID
     * @return 操作结果
     */
    @DeleteMapping("/{achievementId}")
    public ResponseEntity<Map<String, String>> deleteAchievement(@PathVariable Long achievementId) {
        userAchievementService.deleteAchievement(achievementId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Achievement deleted successfully");
        return ResponseEntity.ok(response);
    }
}