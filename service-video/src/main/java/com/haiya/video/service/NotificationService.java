package com.haiya.video.service;

import com.haiya.video.entity.Notification;
import com.haiya.video.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserIdAndIsReadOrderByCreatedAtDesc(userId, false);
    }

    public Long getUnreadNotificationCount(Long userId) {
        return notificationRepository.countByUserIdAndIsRead(userId, false);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification markAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId).map(notification -> {
            notification.setIsRead(true);
            return notificationRepository.save(notification);
        }).orElse(null);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = getUnreadNotificationsByUserId(userId);
        for (Notification notification : unreadNotifications) {
            notification.setIsRead(true);
        }
        notificationRepository.saveAll(unreadNotifications);
    }
    
    public Notification createLikeNotification(Long userId, Long actorId, Long videoId) {
        Notification notification = new Notification(userId, "LIKE");
        notification.setActorId(actorId);
        notification.setEntityType("VIDEO");
        notification.setEntityId(videoId);
        notification.setMessage("用户点赞了你的视频");
        return notificationRepository.save(notification);
    }
    
    public Notification createCommentNotification(Long userId, Long actorId, Long videoId) {
        Notification notification = new Notification(userId, "COMMENT");
        notification.setActorId(actorId);
        notification.setEntityType("VIDEO");
        notification.setEntityId(videoId);
        notification.setMessage("用户评论了你的视频");
        return notificationRepository.save(notification);
    }
    
    public Notification createFollowNotification(Long userId, Long actorId) {
        Notification notification = new Notification(userId, "FOLLOW");
        notification.setActorId(actorId);
        notification.setEntityType("USER");
        notification.setEntityId(userId);
        notification.setMessage("用户关注了你");
        return notificationRepository.save(notification);
    }
}