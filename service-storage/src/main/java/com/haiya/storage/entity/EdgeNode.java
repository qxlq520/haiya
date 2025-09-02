package com.haiya.storage.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 边缘计算节点实体类
 */
public class EdgeNode {
    /**
     * 节点ID
     */
    private Long id;
    
    /**
     * 节点名称
     */
    private String name;
    
    /**
     * 节点区域
     */
    private String region;
    
    /**
     * 节点IP地址
     */
    private String ipAddress;
    
    /**
     * 节点状态
     */
    private NodeStatus status;
    
    /**
     * 节点能力列表
     */
    private List<NodeCapability> capabilities;
    
    /**
     * CPU使用率
     */
    private Double cpuUsage;
    
    /**
     * 内存使用率
     */
    private Double memoryUsage;
    
    /**
     * 网络带宽使用率
     */
    private Double bandwidthUsage;
    
    /**
     * 最大并发处理数
     */
    private Integer maxConcurrency;
    
    /**
     * 当前处理请求数
     */
    private Integer currentRequests;
    
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public NodeStatus getStatus() {
        return status;
    }
    
    public void setStatus(NodeStatus status) {
        this.status = status;
    }
    
    public List<NodeCapability> getCapabilities() {
        return capabilities;
    }
    
    public void setCapabilities(List<NodeCapability> capabilities) {
        this.capabilities = capabilities;
    }
    
    public Double getCpuUsage() {
        return cpuUsage;
    }
    
    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }
    
    public Double getMemoryUsage() {
        return memoryUsage;
    }
    
    public void setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }
    
    public Double getBandwidthUsage() {
        return bandwidthUsage;
    }
    
    public void setBandwidthUsage(Double bandwidthUsage) {
        this.bandwidthUsage = bandwidthUsage;
    }
    
    public Integer getMaxConcurrency() {
        return maxConcurrency;
    }
    
    public void setMaxConcurrency(Integer maxConcurrency) {
        this.maxConcurrency = maxConcurrency;
    }
    
    public Integer getCurrentRequests() {
        return currentRequests;
    }
    
    public void setCurrentRequests(Integer currentRequests) {
        this.currentRequests = currentRequests;
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
     * 节点状态枚举
     */
    public enum NodeStatus {
        ONLINE,    // 在线
        OFFLINE,   // 离线
        MAINTENANCE // 维护中
    }
    
    /**
     * 节点能力枚举
     */
    public enum NodeCapability {
        VIDEO_TRANSCODING,   // 视频转码
        IMAGE_PROCESSING,    // 图像处理
        LIVE_STREAMING,      // 直播流处理
        AI_INFERENCE,        // AI推理
        CONTENT_CACHING      // 内容缓存
    }
}