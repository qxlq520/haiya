package com.haiya.mesh.config;

import com.haiya.mesh.service.MultiLevelCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class CacheMessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(CacheMessageReceiver.class);

    @Autowired
    private MultiLevelCacheService cacheService;

    /**
     * 接收缓存更新消息
     *
     * @param message 消息内容
     */
    public void receiveMessage(String message) {
        try {
            logger.info("收到缓存更新消息: {}", message);

            // 解析消息内容
            if (message.startsWith("evict:")) {
                // 缓存移除通知
                String cacheKey = message.substring(6);
                String[] parts = cacheKey.split(":", 2);
                if (parts.length == 2) {
                    String cacheName = parts[0];
                    String key = parts[1];
                    // 清除本地缓存
                    cacheService.evict(cacheName, key);
                    logger.info("已清除本地缓存: cacheName={}, key={}", cacheName, key);
                }
            } else if (message.startsWith("update:")) {
                // 缓存更新通知
                String cacheKey = message.substring(7);
                // 可以根据需要实现缓存更新逻辑
                logger.info("收到缓存更新通知: {}", cacheKey);
            } else if (message.startsWith("evictAll:")) {
                // 清空所有缓存通知
                String cacheName = message.substring(9);
                cacheService.evictAll(cacheName);
                logger.info("已清空缓存: cacheName={}", cacheName);
            }
        } catch (Exception e) {
            logger.error("处理缓存更新消息失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 接收二进制消息
     *
     * @param message 消息内容
     */
    public void receiveMessage(byte[] message) {
        String messageStr = new String(message, StandardCharsets.UTF_8);
        receiveMessage(messageStr);
    }
}