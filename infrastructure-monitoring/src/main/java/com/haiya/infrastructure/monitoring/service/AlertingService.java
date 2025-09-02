package com.haiya.infrastructure.monitoring.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * 告警服务
 * 负责处理监控指标告警和通知
 */
@Service
public class AlertingService {
    
    // 存储告警状态，防止重复告警
    private final Map<String, AlertState> alertStates = new ConcurrentHashMap<>();
    
    private final SystemMetricsService systemMetricsService;
    private final ApplicationMetricsService applicationMetricsService;
    
    public AlertingService(SystemMetricsService systemMetricsService, 
                          ApplicationMetricsService applicationMetricsService) {
        this.systemMetricsService = systemMetricsService;
        this.applicationMetricsService = applicationMetricsService;
    }
    
    /**
     * 定时检查基础设施指标并触发告警
     */
    @Scheduled(fixedRate = 30000) // 每30秒检查一次
    public void checkInfrastructureMetrics() {
        // 检查CPU使用率
        double cpuUsage = systemMetricsService.getCpuUsage();
        checkMetricAndAlert("infrastructure.cpu.usage", cpuUsage, 80.0, 90.0);
        
        // 检查内存使用率
        double memoryUsage = systemMetricsService.getMemoryUsage();
        checkMetricAndAlert("infrastructure.memory.usage", memoryUsage, 80.0, 90.0);
        
        // 检查磁盘使用率
        double diskUsage = systemMetricsService.getDiskUsage();
        checkMetricAndAlert("infrastructure.disk.usage", diskUsage, 85.0, 95.0);
    }
    
    /**
     * 定时检查应用性能指标并触发告警
     */
    @Scheduled(fixedRate = 60000) // 每60秒检查一次
    public void checkApplicationMetrics() {
        // 检查QPS
        double qps = applicationMetricsService.getQps(null);
        checkMetricAndAlert("application.qps", qps, 1000.0, 5000.0);
        
        // 检查响应时间
        double responseTime = applicationMetricsService.getAverageResponseTime(null);
        checkMetricAndAlert("application.response.time", responseTime, 500.0, 2000.0);
        
        // 检查错误率
        double errorRate = applicationMetricsService.getErrorRate(null);
        checkMetricAndAlert("application.error.rate", errorRate, 1.0, 5.0);
    }
    
    /**
     * 检查指标并触发告警
     * @param metricName 指标名称
     * @param currentValue 当前值
     * @param warningThreshold 警告阈值
     * @param criticalThreshold 严重阈值
     */
    private void checkMetricAndAlert(String metricName, double currentValue, 
                                   double warningThreshold, double criticalThreshold) {
        AlertLevel level = AlertLevel.NORMAL;
        
        if (currentValue >= criticalThreshold) {
            level = AlertLevel.CRITICAL;
        } else if (currentValue >= warningThreshold) {
            level = AlertLevel.WARNING;
        }
        
        // 获取或创建告警状态
        AlertState alertState = alertStates.computeIfAbsent(metricName, k -> new AlertState());
        
        // 检查是否需要发送告警
        if (shouldSendAlert(alertState, level)) {
            sendAlert(metricName, currentValue, level);
            alertState.update(level, LocalDateTime.now());
        }
    }
    
    /**
     * 判断是否需要发送告警
     * @param alertState 告警状态
     * @param currentLevel 当前告警级别
     * @return 是否需要发送告警
     */
    private boolean shouldSendAlert(AlertState alertState, AlertLevel currentLevel) {
        // 如果当前没有告警且级别为正常，则不发送告警
        if (currentLevel == AlertLevel.NORMAL && alertState.getCurrentLevel() == AlertLevel.NORMAL) {
            return false;
        }
        
        // 如果告警级别没有变化，则根据重复告警间隔判断是否需要重复发送
        if (currentLevel == alertState.getCurrentLevel()) {
            LocalDateTime now = LocalDateTime.now();
            long secondsSinceLastAlert = java.time.Duration.between(alertState.getLastAlertTime(), now).getSeconds();
            long repeatInterval = getRepeatInterval(currentLevel);
            return secondsSinceLastAlert >= repeatInterval;
        }
        
        // 如果告警级别发生变化，则需要发送告警
        return true;
    }
    
    /**
     * 获取重复告警间隔
     * @param level 告警级别
     * @return 重复告警间隔（秒）
     */
    private long getRepeatInterval(AlertLevel level) {
        switch (level) {
            case WARNING:
                return 300; // 5分钟
            case CRITICAL:
                return 60;  // 1分钟
            case EMERGENCY:
                return 30;  // 30秒
            default:
                return 3600; // 1小时
        }
    }
    
    /**
     * 发送告警
     * @param metricName 指标名称
     * @param currentValue 当前值
     * @param level 告警级别
     */
    private void sendAlert(String metricName, double currentValue, AlertLevel level) {
        String message = String.format("告警: %s 当前值: %.2f 级别: %s 时间: %s", 
                metricName, currentValue, level.getDescription(), LocalDateTime.now());
        
        switch (level) {
            case WARNING:
                sendEmailAlert(message);
                break;
            case CRITICAL:
                sendEmailAlert(message);
                sendSmsAlert(message);
                break;
            case EMERGENCY:
                sendEmailAlert(message);
                sendSmsAlert(message);
                makePhoneCall(message);
                break;
            default:
                // 正常级别不发送告警
                break;
        }
    }
    
    /**
     * 发送邮件告警
     * @param message 告警信息
     */
    private void sendEmailAlert(String message) {
        System.out.println("发送邮件告警: " + message);
        // 实际实现中应该集成邮件服务
    }
    
    /**
     * 发送短信告警
     * @param message 告警信息
     */
    private void sendSmsAlert(String message) {
        System.out.println("发送短信告警: " + message);
        // 实际实现中应该集成短信服务
    }
    
    /**
     * 发起电话告警
     * @param message 告警信息
     */
    private void makePhoneCall(String message) {
        System.out.println("发起电话告警: " + message);
        // 实际实现中应该集成电话服务
    }
    
    /**
     * 告警级别枚举
     */
    public enum AlertLevel {
        NORMAL("正常"),
        WARNING("警告"),
        CRITICAL("严重"),
        EMERGENCY("紧急");
        
        private final String description;
        
        AlertLevel(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * 告警状态类
     */
    private static class AlertState {
        private AlertLevel currentLevel = AlertLevel.NORMAL;
        private LocalDateTime lastAlertTime = LocalDateTime.now().minusHours(1);
        
        public AlertLevel getCurrentLevel() {
            return currentLevel;
        }
        
        public LocalDateTime getLastAlertTime() {
            return lastAlertTime;
        }
        
        public void update(AlertLevel level, LocalDateTime alertTime) {
            this.currentLevel = level;
            this.lastAlertTime = alertTime;
        }
    }
}