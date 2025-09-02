package com.haiya.video.service;

import com.haiya.video.entity.UserTask;
import com.haiya.video.entity.UserPoints;
import com.haiya.video.repository.UserTaskRepository;
import com.haiya.video.repository.UserPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class UserTaskService {

    private static final Logger logger = Logger.getLogger(UserTaskService.class.getName());

    @Autowired
    private UserTaskRepository userTaskRepository;
    
    @Autowired
    private UserPointsRepository userPointsRepository;

    /**
     * 为用户创建任务
     * @param userTask 任务信息
     * @return 创建的任务
     */
    @Transactional
    public UserTask createUserTask(UserTask userTask) {
        try {
            UserTask savedTask = userTaskRepository.save(userTask);
            logger.info("Created user task with ID: " + savedTask.getId() + " for user: " + userTask.getUserId());
            return savedTask;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating user task for user: " + userTask.getUserId(), e);
            throw new RuntimeException("Failed to create user task", e);
        }
    }

    /**
     * 获取用户的所有任务
     * @param userId 用户ID
     * @return 任务列表
     */
    public List<UserTask> getUserTasks(Long userId) {
        try {
            List<UserTask> tasks = userTaskRepository.findByUserIdOrderByCreatedAtDesc(userId);
            logger.info("Retrieved " + tasks.size() + " tasks for user: " + userId);
            return tasks;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving tasks for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user tasks", e);
        }
    }

    /**
     * 获取用户特定类型的任务
     * @param userId 用户ID
     * @param taskType 任务类型
     * @return 任务列表
     */
    public List<UserTask> getUserTasksByType(Long userId, String taskType) {
        try {
            List<UserTask> tasks = userTaskRepository.findByUserIdAndTaskTypeOrderByCreatedAtDesc(userId, taskType);
            logger.info("Retrieved " + tasks.size() + " " + taskType + " tasks for user: " + userId);
            return tasks;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving " + taskType + " tasks for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user tasks by type", e);
        }
    }

    /**
     * 获取用户特定状态的任务
     * @param userId 用户ID
     * @param status 任务状态
     * @return 任务列表
     */
    public List<UserTask> getUserTasksByStatus(Long userId, String status) {
        try {
            List<UserTask> tasks = userTaskRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status);
            logger.info("Retrieved " + tasks.size() + " " + status + " tasks for user: " + userId);
            return tasks;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving " + status + " tasks for user: " + userId, e);
            throw new RuntimeException("Failed to retrieve user tasks by status", e);
        }
    }

    /**
     * 更新任务进度
     * @param taskId 任务ID
     * @param increment 增加的进度数
     * @return 更新后的任务
     */
    @Transactional
    public UserTask updateTaskProgress(Long taskId, Integer increment) {
        try {
            Optional<UserTask> taskOptional = userTaskRepository.findById(taskId);
            if (!taskOptional.isPresent()) {
                throw new RuntimeException("Task not found with ID: " + taskId);
            }
            
            UserTask task = taskOptional.get();
            task.incrementProgress(increment);
            UserTask updatedTask = userTaskRepository.save(task);
            
            // 如果任务已完成，自动发放奖励
            if (updatedTask.isCompletedButNotClaimed()) {
                claimTaskReward(updatedTask.getId());
            }
            
            logger.info("Updated task progress for task ID: " + taskId + " with increment: " + increment);
            return updatedTask;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating task progress for task: " + taskId, e);
            throw new RuntimeException("Failed to update task progress", e);
        }
    }

    /**
     * 领取任务奖励
     * @param taskId 任务ID
     * @return 更新后的任务
     */
    @Transactional
    public UserTask claimTaskReward(Long taskId) {
        try {
            Optional<UserTask> taskOptional = userTaskRepository.findById(taskId);
            if (!taskOptional.isPresent()) {
                throw new RuntimeException("Task not found with ID: " + taskId);
            }
            
            UserTask task = taskOptional.get();
            if (!task.isCompletedButNotClaimed()) {
                throw new RuntimeException("Task is not completed or reward already claimed");
            }
            
            task.claimReward();
            UserTask updatedTask = userTaskRepository.save(task);
            
            // 发放奖励积分和金币
            awardTaskRewards(task.getUserId(), task.getRewardPoints(), task.getRewardCoins());
            
            logger.info("Claimed reward for task ID: " + taskId + " for user: " + task.getUserId());
            return updatedTask;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error claiming task reward for task: " + taskId, e);
            throw new RuntimeException("Failed to claim task reward", e);
        }
    }

    /**
     * 发放任务奖励
     * @param userId 用户ID
     * @param points 积分奖励
     * @param coins 金币奖励
     */
    @Transactional
    private void awardTaskRewards(Long userId, Integer points, Integer coins) {
        try {
            // 获取或创建用户积分记录
            Optional<UserPoints> userPointsOptional = userPointsRepository.findByUserId(userId);
            UserPoints userPoints;
            
            if (userPointsOptional.isPresent()) {
                userPoints = userPointsOptional.get();
            } else {
                userPoints = new UserPoints(userId);
            }
            
            // 添加积分奖励
            if (points != null && points > 0) {
                userPoints.addPoints(points);
            }
            
            // 保存用户积分记录
            userPointsRepository.save(userPoints);
            
            logger.info("Awarded rewards to user " + userId + ": " + points + " points");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error awarding rewards to user: " + userId, e);
            throw new RuntimeException("Failed to award rewards", e);
        }
    }

    /**
     * 删除任务
     * @param taskId 任务ID
     */
    @Transactional
    public void deleteTask(Long taskId) {
        try {
            userTaskRepository.deleteById(taskId);
            logger.info("Deleted task with ID: " + taskId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting task: " + taskId, e);
            throw new RuntimeException("Failed to delete task", e);
        }
    }
}