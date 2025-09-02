package com.haiya.mesh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiya.mesh.dto.ServiceCallDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final MeshService meshService;
    private final ObjectMapper objectMapper;
    private final MultiLevelCacheService cacheService;

    @Value("${mesh.messaging.rabbitmq.enabled:true}")
    private boolean rabbitmqEnabled;

    @Value("${mesh.messaging.kafka.enabled:true}")
    private boolean kafkaEnabled;

    public EventService(MeshService meshService, ObjectMapper objectMapper, MultiLevelCacheService cacheService) {
        this.meshService = meshService;
        this.objectMapper = objectMapper;
        this.cacheService = cacheService;
    }

    /**
     * 处理来自RabbitMQ的消息
     *
     * @param message 消息内容
     */
    @RabbitListener(queues = "mesh.event.queue")
    public void handleRabbitMQEvent(String message) {
        if (!rabbitmqEnabled) {
            logger.debug("RabbitMQ事件处理已禁用");
            return;
        }

        try {
            logger.info("接收到RabbitMQ事件消息: {}", message);
            ServiceCallDTO serviceCallDTO = objectMapper.readValue(message, ServiceCallDTO.class);
            
            // 根据serviceId进行缓存处理
            String cacheKey = "service_call:" + serviceCallDTO.getServiceId() + ":" + serviceCallDTO.getUri();
            
            // 异步处理事件，提高响应速度
            CompletableFuture.supplyAsync(() -> {
                // 使用多级缓存处理服务调用
                return cacheService.get("service_calls", cacheKey, () -> meshService.executeServiceCall(serviceCallDTO));
            })
                .thenAccept(response -> logger.info("RabbitMQ事件处理完成: {}", response))
                .exceptionally(throwable -> {
                    logger.error("处理RabbitMQ事件失败: {}", throwable.getMessage(), throwable);
                    return null;
                });
        } catch (Exception e) {
            logger.error("处理RabbitMQ事件失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 处理来自Kafka的消息
     *
     * @param message 消息内容
     */
    @KafkaListener(topics = "${mesh.messaging.kafka.topic:mesh.events}")
    public void handleKafkaEvent(String message) {
        if (!kafkaEnabled) {
            logger.debug("Kafka事件处理已禁用");
            return;
        }

        try {
            logger.info("接收到Kafka事件消息: {}", message);
            ServiceCallDTO serviceCallDTO = objectMapper.readValue(message, ServiceCallDTO.class);
            
            // 根据serviceId进行缓存处理
            String cacheKey = "service_call:" + serviceCallDTO.getServiceId() + ":" + serviceCallDTO.getUri();
            
            // 异步处理事件，提高响应速度
            CompletableFuture.supplyAsync(() -> {
                // 使用多级缓存处理服务调用
                return cacheService.get("service_calls", cacheKey, () -> meshService.executeServiceCall(serviceCallDTO));
            })
                .thenAccept(response -> logger.info("Kafka事件处理完成: {}", response))
                .exceptionally(throwable -> {
                    logger.error("处理Kafka事件失败: {}", throwable.getMessage(), throwable);
                    return null;
                });
        } catch (Exception e) {
            logger.error("处理Kafka事件失败: {}", e.getMessage(), e);
        }
    }
}