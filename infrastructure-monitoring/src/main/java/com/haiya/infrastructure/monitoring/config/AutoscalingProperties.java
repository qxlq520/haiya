package com.haiya.infrastructure.monitoring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自动扩容配置属性类
 * 用于绑定autoscaling前缀的配置属性
 */
@Component
@ConfigurationProperties(prefix = "autoscaling")
public class AutoscalingProperties {
    
    private boolean enabled = true;
    private int cpuThreshold = 75;
    private int memoryThreshold = 80;
    private int minInstances = 2;
    private int maxInstances = 10;
    private int cooldown = 300;
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public int getCpuThreshold() {
        return cpuThreshold;
    }
    
    public void setCpuThreshold(int cpuThreshold) {
        this.cpuThreshold = cpuThreshold;
    }
    
    public int getMemoryThreshold() {
        return memoryThreshold;
    }
    
    public void setMemoryThreshold(int memoryThreshold) {
        this.memoryThreshold = memoryThreshold;
    }
    
    public int getMinInstances() {
        return minInstances;
    }
    
    public void setMinInstances(int minInstances) {
        this.minInstances = minInstances;
    }
    
    public int getMaxInstances() {
        return maxInstances;
    }
    
    public void setMaxInstances(int maxInstances) {
        this.maxInstances = maxInstances;
    }
    
    public int getCooldown() {
        return cooldown;
    }
    
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}