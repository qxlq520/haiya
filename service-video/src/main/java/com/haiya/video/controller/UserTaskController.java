package com.haiya.video.controller;

import com.haiya.video.entity.UserTask;
import com.haiya.video.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-tasks")
public class UserTaskController {

    @Autowired
    private UserTaskService userTaskService;

    /**
     * 获取用户的所有任务
     * @param userId 用户ID
     * @return 任务列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTask>> getUserTasks(@PathVariable Long userId) {
        List<UserTask> tasks = userTaskService.getUserTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 获取用户特定类型的任务
     * @param userId 用户ID
     * @param taskType 任务类型
     * @return 任务列表
     */
    @GetMapping("/user/{userId}/type/{taskType}")
    public ResponseEntity<List<UserTask>> getUserTasksByType(@PathVariable Long userId, 
                                                            @PathVariable String taskType) {
        List<UserTask> tasks = userTaskService.getUserTasksByType(userId, taskType);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 获取用户特定状态的任务
     * @param userId 用户ID
     * @param status 任务状态
     * @return 任务列表
     */
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<UserTask>> getUserTasksByStatus(@PathVariable Long userId, 
                                                              @PathVariable String status) {
        List<UserTask> tasks = userTaskService.getUserTasksByStatus(userId, status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 创建用户任务
     * @param userTask 任务信息
     * @return 创建的任务
     */
    @PostMapping
    public ResponseEntity<UserTask> createUserTask(@RequestBody UserTask userTask) {
        UserTask createdTask = userTaskService.createUserTask(userTask);
        return ResponseEntity.ok(createdTask);
    }

    /**
     * 更新任务进度
     * @param taskId 任务ID
     * @param increment 增加的进度数
     * @return 更新后的任务
     */
    @PutMapping("/{taskId}/progress")
    public ResponseEntity<UserTask> updateTaskProgress(@PathVariable Long taskId, 
                                                      @RequestParam Integer increment) {
        UserTask updatedTask = userTaskService.updateTaskProgress(taskId, increment);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * 领取任务奖励
     * @param taskId 任务ID
     * @return 更新后的任务
     */
    @PostMapping("/{taskId}/claim")
    public ResponseEntity<UserTask> claimTaskReward(@PathVariable Long taskId) {
        UserTask updatedTask = userTaskService.claimTaskReward(taskId);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * 删除任务
     * @param taskId 任务ID
     * @return 操作结果
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long taskId) {
        userTaskService.deleteTask(taskId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task deleted successfully");
        return ResponseEntity.ok(response);
    }
}