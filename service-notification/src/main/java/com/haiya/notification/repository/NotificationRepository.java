package com.haiya.notification.repository;

import com.haiya.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Notification> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, String status);
    List<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, String type);
    List<Notification> findByUserIdAndStatusAndTypeOrderByCreatedAtDesc(Long userId, String status, String type);
    Long countByUserIdAndStatus(Long userId, String status);
}