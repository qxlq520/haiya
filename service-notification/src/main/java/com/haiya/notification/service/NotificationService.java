package com.haiya.notification.service;

import com.haiya.notification.entity.Notification;
import com.haiya.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender emailSender;

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, "UNREAD");
    }

    public List<Notification> getNotificationsByType(Long userId, String type) {
        return notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(userId, type);
    }

    public Notification createNotification(Notification notification) {
        // 发送邮件通知（如果配置了邮箱）
        if (notification.getType().equals("SYSTEM")) {
            sendEmailNotification(notification);
        }
        
        return notificationRepository.save(notification);
    }

    public Notification markAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId).map(notification -> {
            notification.setStatus("READ");
            notification.setReadAt(new Date());
            return notificationRepository.save(notification);
        }).orElse(null);
    }

    public void markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = getUnreadNotifications(userId);
        for (Notification notification : unreadNotifications) {
            notification.setStatus("READ");
            notification.setReadAt(new Date());
        }
        notificationRepository.saveAll(unreadNotifications);
    }

    public boolean deleteNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).map(notification -> {
            notificationRepository.delete(notification);
            return true;
        }).orElse(false);
    }

    public Long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndStatus(userId, "UNREAD");
    }

    private void sendEmailNotification(Notification notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("user@example.com"); // 实际项目中应该从用户信息中获取邮箱
            message.setSubject(notification.getTitle());
            message.setText(notification.getContent());
            emailSender.send(message);
        } catch (Exception e) {
            // 记录邮件发送失败日志，但不中断主要流程
            e.printStackTrace();
        }
    }
}