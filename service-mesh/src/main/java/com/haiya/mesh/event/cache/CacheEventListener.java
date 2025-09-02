package com.haiya.mesh.event.cache;

import com.haiya.mesh.service.MultiLevelCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class CacheEventListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(CacheEventListener.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MultiLevelCacheService cacheService;

    /**
     * 监听Redis消息，实现缓存更新通知
     *
     * @param message 消息内容
     * @param pattern 匹配的模式
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            logger.info("收到缓存更新通知: {}", messageBody);

            // 解析消息内容
            if (messageBody.startsWith("evict:")) {
                // 缓存移除通知
                String cacheKey = messageBody.substring(6);
                String[] parts = cacheKey.split(":", 2);
                if (parts.length == 2) {
                    String cacheName = parts[0];
                    String key = parts[1];
                    // 清除本地缓存
                    cacheService.evict(cacheName, key);
                    logger.info("已清除本地缓存: cacheName={}, key={}", cacheName, key);
                }
            } else if (messageBody.startsWith("update:")) {
                // 缓存更新通知
                String cacheKey = messageBody.substring(7);
                // 可以根据需要实现缓存更新逻辑
                logger.info("收到缓存更新通知: {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("处理缓存事件通知失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 初始化缓存事件监听
     */
    @PostConstruct
    public void init() {
        // 订阅缓存更新频道
        redisTemplate.getConnectionFactory().getConnection().subscribe(this, "cache:events".getBytes(StandardCharsets.UTF_8));
        logger.info("已订阅缓存事件频道: cache:events");
    }
}