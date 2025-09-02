package com.haiya.infrastructure.monitoring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 监控配置类
 * 启用定时任务和配置监控相关组件
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties({
    InfrastructureMonitoringProperties.class,
    ApplicationMonitoringProperties.class,
    AlertingProperties.class,
    AutoscalingProperties.class
})
public class MonitoringConfig {
    // 监控相关配置
}