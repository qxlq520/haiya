package com.haiya.message.controller;

import com.haiya.message.entity.Message;
import com.haiya.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送私信
     * POST /api/messages
     */
    @PostMapping("/messages")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    /**
     * 获取私信记录
     * GET /api/messages?userId={userId}&targetUserId={targetUserId}
     */
    @GetMapping("/messages")
    public List<Message> getMessagesBetweenUsers(
            @RequestParam Long userId,
            @RequestParam Long targetUserId) {
        return messageService.getMessagesBetweenUsers(userId, targetUserId);
    }

    /**
     * 获取通知列表
     * GET /api/notifications
     */
    @GetMapping("/notifications")
    public List<Message> getNotifications(@RequestParam(required = false) Long userId) {
        return messageService.getNotifications(userId);
    }

    /**
     * 标记通知为已读
     * PUT /api/notifications/{id}/read
     */
    @PutMapping("/notifications/{id}/read")
    public Message markAsRead(@PathVariable Long id) {
        return messageService.markAsRead(id);
    }

    /**
     * 标记所有通知为已读
     */
    @PutMapping("/notifications/read-all")
    public void markAllAsRead(@RequestParam Long userId) {
        messageService.markAllAsRead(userId);
    }
}