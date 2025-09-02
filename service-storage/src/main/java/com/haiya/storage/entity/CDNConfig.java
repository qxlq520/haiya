package com.haiya.storage.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CDN配置实体类
 */
public class CDNConfig {
    /**
     * 配置ID
     */
    private Long id;
    
    /**
     * 配置名称
     */
    private String name;
    
    /**
     * CDN提供商
     */
    private CDNProvider provider;
    
    /**
     * 区域列表
     */
    private List<String> regions;
    
    /**
     * 缓存策略
     */
    private CacheStrategy cacheStrategy;
    
    /**
     * 压缩设置
     */
    private CompressionSetting compression;
    
    /**
     * 安全设置
     */
    private SecuritySetting security;
    
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public CDNProvider getProvider() {
        return provider;
    }
    
    public void setProvider(CDNProvider provider) {
        this.provider = provider;
    }
    
    public List<String> getRegions() {
        return regions;
    }
    
    public void setRegions(List<String> regions) {
        this.regions = regions;
    }
    
    public CacheStrategy getCacheStrategy() {
        return cacheStrategy;
    }
    
    public void setCacheStrategy(CacheStrategy cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }
    
    public CompressionSetting getCompression() {
        return compression;
    }
    
    public void setCompression(CompressionSetting compression) {
        this.compression = compression;
    }
    
    public SecuritySetting getSecurity() {
        return security;
    }
    
    public void setSecurity(SecuritySetting security) {
        this.security = security;
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
     * CDN提供商枚举
     */
    public enum CDNProvider {
        CLOUDFLARE,    // Cloudflare
        AWS_CLOUDFRONT, // AWS CloudFront
        ALIYUN_CDN,    // 阿里云CDN
        TENCENT_CDN,   // 腾讯云CDN
        CUSTOM         // 自定义CDN
    }
    
    /**
     * 缓存策略类
     */
    public static class CacheStrategy {
        private Integer ttl; // 生存时间(秒)
        private List<String> cacheRules; // 缓存规则
        
        public Integer getTtl() {
            return ttl;
        }
        
        public void setTtl(Integer ttl) {
            this.ttl = ttl;
        }
        
        public List<String> getCacheRules() {
            return cacheRules;
        }
        
        public void setCacheRules(List<String> cacheRules) {
            this.cacheRules = cacheRules;
        }
    }
    
    /**
     * 压缩设置类
     */
    public static class CompressionSetting {
        private Boolean gzipEnabled; // Gzip压缩
        private Boolean brotliEnabled; // Brotli压缩
        
        public Boolean getGzipEnabled() {
            return gzipEnabled;
        }
        
        public void setGzipEnabled(Boolean gzipEnabled) {
            this.gzipEnabled = gzipEnabled;
        }
        
        public Boolean getBrotliEnabled() {
            return brotliEnabled;
        }
        
        public void setBrotliEnabled(Boolean brotliEnabled) {
            this.brotliEnabled = brotliEnabled;
        }
    }
    
    /**
     * 安全设置类
     */
    public static class SecuritySetting {
        private Boolean httpsEnabled; // HTTPS启用
        private List<String> allowedDomains; // 允许的域名
        private Boolean antiTheftChainEnabled; // 防盗链启用
        
        public Boolean getHttpsEnabled() {
            return httpsEnabled;
        }
        
        public void setHttpsEnabled(Boolean httpsEnabled) {
            this.httpsEnabled = httpsEnabled;
        }
        
        public List<String> getAllowedDomains() {
            return allowedDomains;
        }
        
        public void setAllowedDomains(List<String> allowedDomains) {
            this.allowedDomains = allowedDomains;
        }
        
        public Boolean getAntiTheftChainEnabled() {
            return antiTheftChainEnabled;
        }
        
        public void setAntiTheftChainEnabled(Boolean antiTheftChainEnabled) {
            this.antiTheftChainEnabled = antiTheftChainEnabled;
        }
    }
}