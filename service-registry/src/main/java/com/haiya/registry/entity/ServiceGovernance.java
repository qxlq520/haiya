package com.haiya.registry.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 微服务治理配置实体类
 */
public class ServiceGovernance {
    /**
     * 配置ID
     */
    private Long id;
    
    /**
     * 服务名称
     */
    private String serviceName;
    
    /**
     * 服务版本
     */
    private String version;
    
    /**
     * 负载均衡策略
     */
    private LoadBalanceStrategy loadBalance;
    
    /**
     * 熔断器配置
     */
    private CircuitBreakerConfig circuitBreaker;
    
    /**
     * 限流配置
     */
    private RateLimitConfig rateLimit;
    
    /**
     * 服务发现配置
     */
    private DiscoveryConfig discovery;
    
    /**
     * 监控配置
     */
    private MonitoringConfig monitoring;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public LoadBalanceStrategy getLoadBalance() {
        return loadBalance;
    }
    
    public void setLoadBalance(LoadBalanceStrategy loadBalance) {
        this.loadBalance = loadBalance;
    }
    
    public CircuitBreakerConfig getCircuitBreaker() {
        return circuitBreaker;
    }
    
    public void setCircuitBreaker(CircuitBreakerConfig circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }
    
    public RateLimitConfig getRateLimit() {
        return rateLimit;
    }
    
    public void setRateLimit(RateLimitConfig rateLimit) {
        this.rateLimit = rateLimit;
    }
    
    public DiscoveryConfig getDiscovery() {
        return discovery;
    }
    
    public void setDiscovery(DiscoveryConfig discovery) {
        this.discovery = discovery;
    }
    
    public MonitoringConfig getMonitoring() {
        return monitoring;
    }
    
    public void setMonitoring(MonitoringConfig monitoring) {
        this.monitoring = monitoring;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * 负载均衡策略枚举
     */
    public enum LoadBalanceStrategy {
        ROUND_ROBIN,    // 轮询
        RANDOM,         // 随机
        WEIGHTED,       // 加权
        LEAST_CONNECTIONS // 最少连接
    }
    
    /**
     * 熔断器配置类
     */
    public static class CircuitBreakerConfig {
        private Boolean enabled; // 是否启用
        private Integer failureThreshold; // 失败阈值
        private Integer timeoutSeconds; // 超时时间(秒)
        private Integer recoveryTimeoutSeconds; // 恢复超时时间(秒)
        
        public Boolean getEnabled() {
            return enabled;
        }
        
        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
        
        public Integer getFailureThreshold() {
            return failureThreshold;
        }
        
        public void setFailureThreshold(Integer failureThreshold) {
            this.failureThreshold = failureThreshold;
        }
        
        public Integer getTimeoutSeconds() {
            return timeoutSeconds;
        }
        
        public void setTimeoutSeconds(Integer timeoutSeconds) {
            this.timeoutSeconds = timeoutSeconds;
        }
        
        public Integer getRecoveryTimeoutSeconds() {
            return recoveryTimeoutSeconds;
        }
        
        public void setRecoveryTimeoutSeconds(Integer recoveryTimeoutSeconds) {
            this.recoveryTimeoutSeconds = recoveryTimeoutSeconds;
        }
    }
    
    /**
     * 限流配置类
     */
    public static class RateLimitConfig {
        private Boolean enabled; // 是否启用
        private Integer requestsPerSecond; // 每秒请求数
        private Integer burstCapacity; // 突发容量
        private List<String> excludedEndpoints; // 排除的端点
        
        public Boolean getEnabled() {
            return enabled;
        }
        
        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
        
        public Integer getRequestsPerSecond() {
            return requestsPerSecond;
        }
        
        public void setRequestsPerSecond(Integer requestsPerSecond) {
            this.requestsPerSecond = requestsPerSecond;
        }
        
        public Integer getBurstCapacity() {
            return burstCapacity;
        }
        
        public void setBurstCapacity(Integer burstCapacity) {
            this.burstCapacity = burstCapacity;
        }
        
        public List<String> getExcludedEndpoints() {
            return excludedEndpoints;
        }
        
        public void setExcludedEndpoints(List<String> excludedEndpoints) {
            this.excludedEndpoints = excludedEndpoints;
        }
    }
    
    /**
     * 服务发现配置类
     */
    public static class DiscoveryConfig {
        private Boolean enabled; // 是否启用
        private Integer heartbeatIntervalSeconds; // 心跳间隔(秒)
        private Integer expirationSeconds; // 过期时间(秒)
        
        public Boolean getEnabled() {
            return enabled;
        }
        
        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
        
        public Integer getHeartbeatIntervalSeconds() {
            return heartbeatIntervalSeconds;
        }
        
        public void setHeartbeatIntervalSeconds(Integer heartbeatIntervalSeconds) {
            this.heartbeatIntervalSeconds = heartbeatIntervalSeconds;
        }
        
        public Integer getExpirationSeconds() {
            return expirationSeconds;
        }
        
        public void setExpirationSeconds(Integer expirationSeconds) {
            this.expirationSeconds = expirationSeconds;
        }
    }
    
    /**
     * 监控配置类
     */
    public static class MonitoringConfig {
        private Boolean enabled; // 是否启用
        private List<String> metrics; // 监控指标列表
        private String alertEmail; // 告警邮箱
        
        public Boolean getEnabled() {
            return enabled;
        }
        
        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
        
        public List<String> getMetrics() {
            return metrics;
        }
        
        public void setMetrics(List<String> metrics) {
            this.metrics = metrics;
        }
        
        public String getAlertEmail() {
            return alertEmail;
        }
        
        public void setAlertEmail(String alertEmail) {
            this.alertEmail = alertEmail;
        }
    }
}