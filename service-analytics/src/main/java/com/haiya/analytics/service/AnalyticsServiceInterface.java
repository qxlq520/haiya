package com.haiya.analytics.service;

import com.haiya.analytics.entity.UserSegment;
import com.haiya.analytics.entity.OperationCampaign;
import com.haiya.analytics.entity.ContentQuality;
import com.haiya.analytics.entity.Trend;
import com.haiya.analytics.entity.CompetitorAnalysis;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * 数据分析服务接口
 */
public interface AnalyticsServiceInterface {
    
    /**
     * 创建用户分群
     * @param name 分群名称
     * @param conditions 分群条件
     * @return 分群ID
     */
    Long createUserSegment(String name, Map<String, Object> conditions);
    
    /**
     * 获取用户分群列表
     * @return 用户分群列表
     */
    List<UserSegment> getUserSegments();
    
    /**
     * 获取分群用户列表
     * @param segmentId 分群ID
     * @param page 页码
     * @param size 每页大小
     * @return 用户ID列表
     */
    List<Long> getSegmentUsers(Long segmentId, int page, int size);
    
    /**
     * 创建运营活动
     * @param name 活动名称
     * @param description 活动描述
     * @param segmentId 目标用户群ID
     * @param strategy 活动策略
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 活动ID
     */
    Long createOperationCampaign(String name, String description, Long segmentId, 
                                String strategy, LocalDateTime startTime, 
                                LocalDateTime endTime);
    
    /**
     * 获取运营活动列表
     * @param status 活动状态
     * @return 运营活动列表
     */
    List<OperationCampaign> getOperationCampaigns(OperationCampaign.CampaignStatus status);
    
    /**
     * 评估内容质量
     * @param videoId 视频ID
     * @return 质量评估ID
     */
    Long assessContentQuality(Long videoId);
    
    /**
     * 批量评估内容质量
     * @param videoIds 视频ID列表
     */
    void batchAssessContentQuality(List<Long> videoIds);
    
    /**
     * 获取内容质量报告
     * @param videoId 视频ID
     * @return 内容质量评估
     */
    ContentQuality getContentQuality(Long videoId);
    
    /**
     * 发现热点
     * @return 热点列表
     */
    List<Trend> discoverTrends();
    
    /**
     * 获取热点详情
     * @param trendId 热点ID
     * @return 热点详情
     */
    Trend getTrendDetails(Long trendId);
    
    /**
     * 进行竞品分析
     * @param competitorName 竞品名称
     * @param metricType 指标类型
     * @return 分析结果ID
     */
    Long analyzeCompetitor(String competitorName, CompetitorAnalysis.MetricType metricType);
    
    /**
     * 获取竞品分析报告
     * @param competitorName 竞品名称
     * @param days 天数范围
     * @return 竞品分析列表
     */
    List<CompetitorAnalysis> getCompetitorAnalysis(String competitorName, int days);
    
    /**
     * 生成数据报告
     * @param reportType 报告类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 报告数据
     */
    Map<String, Object> generateReport(String reportType, LocalDateTime startTime, 
                                      LocalDateTime endTime);
}