package com.haiya.mesh.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class MultiLevelCacheService {

    private static final Logger logger = LoggerFactory.getLogger(MultiLevelCacheService.class);

    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager caffeineCacheManager;

    @Autowired
    @Qualifier("redisCacheManager")
    private CacheManager redisCacheManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 本地Caffeine缓存实例
    private Cache<String, Object> localCache;

    // 缓存穿透布隆过滤器(简化实现)
    private final Object lock = new Object();

    @PostConstruct
    public void init() {
        // 初始化本地缓存
        localCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    /**
     * 多级缓存获取数据 - Cache-Aside模式
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param loader    数据加载器
     * @param <T>       返回值类型
     * @return 缓存数据
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String cacheName, String key, Callable<T> loader) {
        // 1. 先从本地缓存获取
        T value = (T) localCache.getIfPresent(key);
        if (value != null) {
            logger.debug("从本地缓存获取数据: cacheName={}, key={}", cacheName, key);
            return value;
        }

        // 2. 本地缓存未命中，从Redis缓存获取
        org.springframework.cache.Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            org.springframework.cache.Cache.ValueWrapper redisValueWrapper = redisCache.get(key);
            if (redisValueWrapper != null) {
                value = (T) redisValueWrapper.get();
                if (value != null) {
                    // 回种到本地缓存
                    localCache.put(key, value);
                    logger.debug("从Redis缓存获取数据并回种到本地缓存: cacheName={}, key={}", cacheName, key);
                    return value;
                }
            }
        }

        // 3. 两级缓存都未命中，从数据源加载
        synchronized (lock) {
            // 双重检查锁定，防止缓存击穿
            value = (T) localCache.getIfPresent(key);
            if (value != null) {
                return value;
            }

            org.springframework.cache.Cache redisCacheAgain = redisCacheManager.getCache(cacheName);
            if (redisCacheAgain != null) {
                org.springframework.cache.Cache.ValueWrapper redisValueWrapper = redisCacheAgain.get(key);
                if (redisValueWrapper != null) {
                    value = (T) redisValueWrapper.get();
                    if (value != null) {
                        // 回种到本地缓存
                        localCache.put(key, value);
                        logger.debug("从Redis缓存获取数据并回种到本地缓存: cacheName={}, key={}", cacheName, key);
                        return value;
                    }
                }
            }

            try {
                // 防止缓存穿透，空值也缓存
                value = loader.call();
                put(cacheName, key, value);
                return value;
            } catch (Exception e) {
                logger.error("加载数据失败: cacheName={}, key={}", cacheName, key, e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 多级缓存获取数据 - 使用函数式接口
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param loader    数据加载函数
     * @param <T>       返回值类型
     * @return 缓存数据
     */
    public <T> T get(String cacheName, String key, Function<String, T> loader) {
        return get(cacheName, key, () -> loader.apply(key));
    }

    /**
     * 存储数据到多级缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param value     缓存值
     * @param <T>       值类型
     */
    public <T> void put(String cacheName, String key, T value) {
        // 存储到本地缓存
        localCache.put(key, value);

        // 存储到Redis缓存
        org.springframework.cache.Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            redisCache.put(key, value);
        }

        logger.debug("数据已存储到多级缓存: cacheName={}, key={}", cacheName, key);
    }

    /**
     * 从多级缓存中移除数据
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     */
    public void evict(String cacheName, String key) {
        // 从本地缓存移除
        localCache.invalidate(key);

        // 从Redis缓存移除
        org.springframework.cache.Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            redisCache.evict(key);
        }

        // 发布缓存移除事件
        publishCacheEvent("evict:" + cacheName + ":" + key);

        logger.debug("数据已从多级缓存移除: cacheName={}, key={}", cacheName, key);
    }

    /**
     * 清空缓存名称对应的所有缓存
     *
     * @param cacheName 缓存名称
     */
    public void evictAll(String cacheName) {
        // 清空本地缓存
        localCache.invalidateAll();

        // 清空Redis缓存
        org.springframework.cache.Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            redisCache.clear();
        }

        // 发布清空缓存事件
        publishCacheEvent("evictAll:" + cacheName);

        logger.debug("已清空所有缓存: cacheName={}", cacheName);
    }

    /**
     * 缓存预热
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param loader    数据加载器
     * @param <T>       值类型
     */
    public <T> void warmUp(String cacheName, String key, Callable<T> loader) {
        try {
            T value = loader.call();
            put(cacheName, key, value);
            logger.info("缓存预热完成: cacheName={}, key={}", cacheName, key);
        } catch (Exception e) {
            logger.error("缓存预热失败: cacheName={}, key={}", cacheName, key, e);
        }
    }

    /**
     * 获取缓存统计信息
     *
     * @return 统计信息
     */
    public String getCacheStats() {
        return localCache.stats().toString();
    }

    /**
     * 发布缓存事件
     *
     * @param message 消息内容
     */
    private void publishCacheEvent(String message) {
        try {
            redisTemplate.convertAndSend("cache:events", message);
        } catch (Exception e) {
            logger.warn("发布缓存事件失败: {}", e.getMessage());
        }
    }
}