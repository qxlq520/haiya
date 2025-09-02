package com.haiya.search.service;

import com.haiya.search.entity.SearchRecord;
import com.haiya.search.entity.DiscoveryContent;
import java.util.List;

/**
 * 搜索与发现服务接口
 */
public interface SearchDiscoveryService {
    
    /**
     * 文本搜索
     * @param keyword 搜索关键词
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    List<Object> textSearch(String keyword, Long userId, int page, int size);
    
    /**
     * 语义搜索
     * @param query 查询语句
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    List<Object> semanticSearch(String query, Long userId, int page, int size);
    
    /**
     * 模糊搜索
     * @param keyword 搜索关键词
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    List<Object> fuzzySearch(String keyword, Long userId, int page, int size);
    
    /**
     * 语音搜索
     * @param voiceData 语音数据
     * @param userId 用户ID
     * @return 搜索结果
     */
    List<Object> voiceSearch(byte[] voiceData, Long userId);
    
    /**
     * 图像搜索
     * @param imageData 图像数据
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    List<Object> imageSearch(byte[] imageData, Long userId, int page, int size);
    
    /**
     * 以图搜图
     * @param imageId 图像ID
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 相似图像列表
     */
    List<Object> searchByImage(Long imageId, Long userId, int page, int size);
    
    /**
     * 记录搜索行为
     * @param userId 用户ID
     * @param keyword 搜索关键词
     * @param searchType 搜索类型
     * @param resultCount 结果数量
     * @param duration 搜索耗时
     * @param hasClick 是否有点击
     */
    void recordSearchBehavior(Long userId, String keyword, SearchRecord.SearchType searchType, 
                             Integer resultCount, Long duration, Boolean hasClick);
    
    /**
     * 获取个性化发现内容
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 发现内容列表
     */
    List<DiscoveryContent> getPersonalizedDiscovery(Long userId, int page, int size);
    
    /**
     * 获取热门发现内容
     * @param page 页码
     * @param size 每页大小
     * @return 发现内容列表
     */
    List<DiscoveryContent> getTrendingDiscovery(int page, int size);
    
    /**
     * 根据标签获取发现内容
     * @param tag 标签
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 发现内容列表
     */
    List<DiscoveryContent> getDiscoveryByTag(String tag, Long userId, int page, int size);
    
    /**
     * 添加发现内容
     * @param content 发现内容
     * @return 内容ID
     */
    Long addDiscoveryContent(DiscoveryContent content);
    
    /**
     * 更新发现内容权重
     * @param contentId 内容ID
     * @param weight 权重
     * @return 是否成功
     */
    boolean updateDiscoveryWeight(Long contentId, Double weight);
    
    /**
     * 获取搜索建议
     * @param partialKeyword 部分关键词
     * @param userId 用户ID
     * @return 建议列表
     */
    List<String> getSearchSuggestions(String partialKeyword, Long userId);
    
    /**
     * 获取热门搜索词
     * @param size 数量
     * @return 热门搜索词列表
     */
    List<String> getTrendingSearches(int size);
}