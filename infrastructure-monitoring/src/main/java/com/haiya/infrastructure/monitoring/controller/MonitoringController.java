package com.haiya.infrastructure.monitoring.controller;

import com.haiya.infrastructure.monitoring.service.SystemMetricsService;
import com.haiya.infrastructure.monitoring.service.ApplicationMetricsService;
import com.haiya.infrastructure.monitoring.service.AutoScalingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * 监控控制器
 * 提供监控指标的REST API接口
 */
@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {
    
    private final SystemMetricsService systemMetricsService;
    private final ApplicationMetricsService applicationMetricsService;
    private final AutoScalingService autoScalingService;
    
    public MonitoringController(SystemMetricsService systemMetricsService,
                               ApplicationMetricsService applicationMetricsService,
                               AutoScalingService autoScalingService) {
        this.systemMetricsService = systemMetricsService;
        this.applicationMetricsService = applicationMetricsService;
        this.autoScalingService = autoScalingService;
    }
    
    /**
     * 获取基础设施指标
     * @return 基础设施指标
     */
    @GetMapping("/infrastructure")
    public Map<String, Object> getInfrastructureMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        metrics.put("cpuUsage", systemMetricsService.getCpuUsage());
        metrics.put("memoryUsage", systemMetricsService.getMemoryUsage());
        metrics.put("diskUsage", systemMetricsService.getDiskUsage());
        metrics.put("jvmMemoryUsage", systemMetricsService.getJvmMemoryUsage());
        metrics.put("uptime", systemMetricsService.getUptime());
        
        SystemMetricsService.NetworkMetrics networkMetrics = systemMetricsService.getNetworkMetrics();
        Map<String, Long> network = new HashMap<>();
        network.put("bytesSent", networkMetrics.getBytesSent());
        network.put("bytesReceived", networkMetrics.getBytesReceived());
        network.put("totalBytes", networkMetrics.getTotalBytes());
        metrics.put("network", network);
        
        return metrics;
    }
    
    /**
     * 获取应用性能指标
     * @return 应用性能指标
     */
    @GetMapping("/application")
    public Map<String, Object> getApplicationMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        metrics.put("qps", applicationMetricsService.getQps(null));
        metrics.put("averageResponseTime", applicationMetricsService.getAverageResponseTime(null));
        metrics.put("errorRate", applicationMetricsService.getErrorRate(null));
        
        return metrics;
    }
    
    /**
     * 获取自动扩容状态
     * @return 自动扩容状态
     */
    @GetMapping("/autoscaling")
    public Map<String, Object> getAutoScalingStatus() {
        Map<String, Object> status = new HashMap<>();
        
        status.put("currentInstances", autoScalingService.getCurrentInstances());
        status.put("minInstances", 2);
        status.put("maxInstances", 10);
        
        return status;
    }
    
    /**
     * 获取所有监控指标
     * @return 所有监控指标
     */
    @GetMapping("/all")
    public Map<String, Object> getAllMetrics() {
        Map<String, Object> allMetrics = new HashMap<>();
        
        allMetrics.put("infrastructure", getInfrastructureMetrics());
        allMetrics.put("application", getApplicationMetrics());
        allMetrics.put("autoscaling", getAutoScalingStatus());
        
        return allMetrics;
    }
}