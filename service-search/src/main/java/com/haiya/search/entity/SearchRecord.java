package com.haiya.search.entity;

import java.time.LocalDateTime;

/**
 * 搜索记录实体类
 */
public class SearchRecord {
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 搜索关键词
     */
    private String keyword;
    
    /**
     * 搜索类型
     */
    private SearchType searchType;
    
    /**
     * 搜索结果数量
     */
    private Integer resultCount;
    
    /**
     * 搜索耗时（毫秒）
     */
    private Long duration;
    
    /**
     * 是否有结果点击
     */
    private Boolean hasClick;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public SearchType getSearchType() {
        return searchType;
    }
    
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
    
    public Integer getResultCount() {
        return resultCount;
    }
    
    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }
    
    public Long getDuration() {
        return duration;
    }
    
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public Boolean getHasClick() {
        return hasClick;
    }
    
    public void setHasClick(Boolean hasClick) {
        this.hasClick = hasClick;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 搜索类型枚举
     */
    public enum SearchType {
        TEXT,     // 文本搜索
        VOICE,    // 语音搜索
        IMAGE     // 图像搜索
    }
}