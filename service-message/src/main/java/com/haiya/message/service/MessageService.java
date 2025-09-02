package com.haiya.message.service;

import com.haiya.message.entity.Message;
import com.haiya.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesBetweenUsers(Long userId, Long targetUserId) {
        return messageRepository.findBySenderIdAndReceiverIdOrderByCreatedAtAsc(userId, targetUserId);
    }

    public Message sendMessage(Message message) {
        if (message.getCreatedAt() == null) {
            message.setCreatedAt(new Date(System.currentTimeMillis()));
        }
        return messageRepository.save(message);
    }

    public List<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiverIdAndStatusOrderByCreatedAtAsc(receiverId, "SENT");
    }

    public Message markAsRead(Long messageId) {
        return messageRepository.findById(messageId).map(message -> {
            message.setStatus("READ");
            return messageRepository.save(message);
        }).orElse(null);
    }

    public void markAllAsRead(Long receiverId) {
        List<Message> unreadMessages = getUnreadMessages(receiverId);
        for (Message message : unreadMessages) {
            message.setStatus("READ");
        }
        messageRepository.saveAll(unreadMessages);
    }
    
    public List<Message> getNotifications(Long userId) {
        return messageRepository.findByReceiverIdAndTypeOrderByCreatedAtDesc(userId, "NOTIFICATION");
    }
}