package com.haiya.video.controller;

import com.haiya.video.entity.UserPoints;
import com.haiya.video.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user-points")
public class UserPointsController {

    @Autowired
    private UserPointsService userPointsService;

    /**
     * 获取用户积分信息
     * @param userId 用户ID
     * @return 用户积分信息
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserPoints> getUserPoints(@PathVariable Long userId) {
        Optional<UserPoints> userPoints = userPointsService.getUserPoints(userId);
        if (userPoints.isPresent()) {
            return ResponseEntity.ok(userPoints.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新用户积分记录
     * @param userId 用户ID
     * @param points 积分变化数量
     * @return 更新后的用户积分信息
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<UserPoints> updateUserPoints(
            @PathVariable Long userId,
            @RequestParam Integer points) {
        UserPoints updatedUserPoints = userPointsService.updateUserPoints(userId, points);
        return ResponseEntity.ok(updatedUserPoints);
    }

    /**
     * 消费用户积分
     * @param userId 用户ID
     * @param points 消费的积分数
     * @return 操作结果
     */
    @PostMapping("/user/{userId}/consume")
    public ResponseEntity<Boolean> consumeUserPoints(
            @PathVariable Long userId,
            @RequestParam Integer points) {
        boolean consumed = userPointsService.consumeUserPoints(userId, points);
        return ResponseEntity.ok(consumed);
    }

    /**
     * 检查用户积分是否足够
     * @param userId 用户ID
     * @param points 需要的积分数
     * @return true表示积分足够
     */
    @GetMapping("/user/{userId}/sufficient")
    public ResponseEntity<Boolean> hasEnoughPoints(
            @PathVariable Long userId,
            @RequestParam Integer points) {
        boolean hasEnough = userPointsService.hasEnoughPoints(userId, points);
        return ResponseEntity.ok(hasEnough);
    }

    /**
     * 获取用户的可用积分
     * @param userId 用户ID
     * @return 可用积分数
     */
    @GetMapping("/user/{userId}/available")
    public ResponseEntity<Integer> getAvailablePoints(@PathVariable Long userId) {
        Integer availablePoints = userPointsService.getAvailablePoints(userId);
        return ResponseEntity.ok(availablePoints);
    }
}