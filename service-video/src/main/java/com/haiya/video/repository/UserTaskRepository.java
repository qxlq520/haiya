package com.haiya.video.repository;

import com.haiya.video.entity.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    List<UserTask> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<UserTask> findByUserIdAndTaskTypeOrderByCreatedAtDesc(Long userId, String taskType);
    List<UserTask> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, String status);
}