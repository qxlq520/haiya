package com.haiya.infrastructure.monitoring.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 自动扩容服务
 * 负责根据监控指标自动调整应用实例数量
 */
@Service
public class AutoScalingService {
    
    private final SystemMetricsService systemMetricsService;
    
    // 当前实例数
    private int currentInstances = 2;
    
    // 配置参数
    private final double cpuThreshold = 75.0;
    private final double memoryThreshold = 80.0;
    private final int minInstances = 2;
    private final int maxInstances = 10;
    private final int cooldown = 300; // 5分钟冷却时间
    
    // 上次扩容时间
    private long lastScalingTime = 0;
    
    public AutoScalingService(SystemMetricsService systemMetricsService) {
        this.systemMetricsService = systemMetricsService;
    }
    
    /**
     * 定时检查指标并决定是否需要扩容或缩容
     */
    @Scheduled(fixedRate = 60000) // 每分钟检查一次
    public void checkAndScale() {
        long currentTime = System.currentTimeMillis();
        
        // 检查是否在冷却时间内
        if (currentTime - lastScalingTime < cooldown * 1000) {
            System.out.println("自动扩容: 处于冷却时间内，跳过检查");
            return;
        }
        
        // 获取当前资源使用情况
        double cpuUsage = systemMetricsService.getCpuUsage();
        double memoryUsage = systemMetricsService.getMemoryUsage();
        
        System.out.printf("自动扩容检查: CPU使用率=%.2f%%, 内存使用率=%.2f%%%n", cpuUsage, memoryUsage);
        
        // 判断是否需要扩容
        if (shouldScaleOut(cpuUsage, memoryUsage)) {
            scaleOut();
        } 
        // 判断是否需要缩容
        else if (shouldScaleIn(cpuUsage, memoryUsage)) {
            scaleIn();
        } 
        else {
            System.out.println("自动扩容: 当前资源使用正常，无需调整实例数");
        }
    }
    
    /**
     * 判断是否需要扩容
     * @param cpuUsage CPU使用率
     * @param memoryUsage 内存使用率
     * @return 是否需要扩容
     */
    private boolean shouldScaleOut(double cpuUsage, double memoryUsage) {
        return (cpuUsage > cpuThreshold || memoryUsage > memoryThreshold) 
                && currentInstances < maxInstances;
    }
    
    /**
     * 判断是否需要缩容
     * @param cpuUsage CPU使用率
     * @param memoryUsage 内存使用率
     * @return 是否需要缩容
     */
    private boolean shouldScaleIn(double cpuUsage, double memoryUsage) {
        return cpuUsage < cpuThreshold * 0.5 && memoryUsage < memoryThreshold * 0.5
                && currentInstances > minInstances;
    }
    
    /**
     * 扩容操作
     */
    private void scaleOut() {
        if (currentInstances < maxInstances) {
            currentInstances++;
            lastScalingTime = System.currentTimeMillis();
            System.out.println("自动扩容: 增加实例，当前实例数=" + currentInstances);
            // 实际实现中应该调用容器编排系统API（如Kubernetes）来增加实例
            executeScalingAction("scale-out", currentInstances);
        }
    }
    
    /**
     * 缩容操作
     */
    private void scaleIn() {
        if (currentInstances > minInstances) {
            currentInstances--;
            lastScalingTime = System.currentTimeMillis();
            System.out.println("自动扩容: 减少实例，当前实例数=" + currentInstances);
            // 实际实现中应该调用容器编排系统API（如Kubernetes）来减少实例
            executeScalingAction("scale-in", currentInstances);
        }
    }
    
    /**
     * 执行扩容/缩容操作
     * @param action 操作类型
     * @param targetInstances 目标实例数
     */
    private void executeScalingAction(String action, int targetInstances) {
        // 这里应该集成实际的容器编排系统（如Kubernetes）
        System.out.printf("执行自动%s操作，目标实例数=%d%n", action, targetInstances);
        
        // 模拟调用Kubernetes API
        // 在实际实现中，应该使用Fabric8 Kubernetes Client或其他客户端库
        /*
        try {
            // Kubernetes API调用示例
            // KubernetesClient client = new DefaultKubernetesClient();
            // client.apps().deployments().inNamespace("haiya")
            //     .withName("haiya-service")
            //     .scale(targetInstances);
        } catch (Exception e) {
            System.err.println("自动扩容操作失败: " + e.getMessage());
        }
        */
    }
    
    /**
     * 获取当前实例数
     * @return 当前实例数
     */
    public int getCurrentInstances() {
        return currentInstances;
    }
    
    /**
     * 手动设置实例数（用于测试）
     * @param instances 实例数
     */
    public void setCurrentInstances(int instances) {
        this.currentInstances = Math.max(minInstances, Math.min(maxInstances, instances));
    }
}