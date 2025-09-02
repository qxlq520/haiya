package com.haiya.user.service;

import com.haiya.user.entity.CheatDetection;
import com.haiya.user.entity.ContentModeration;
import com.haiya.user.entity.AccountSecurity;
import com.haiya.user.entity.DataSecurity;
import java.util.List;

/**
 * 安全服务接口
 */
public interface SecurityService {
    
    /**
     * 检测用户作弊行为
     * @param userId 用户ID
     * @param cheatType 作弊类型
     * @param evidence 证据
     * @return 检测记录ID
     */
    Long detectCheatBehavior(Long userId, CheatDetection.CheatType cheatType, String evidence);
    
    /**
     * 获取用户作弊记录
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 作弊记录列表
     */
    List<CheatDetection> getUserCheatRecords(Long userId, int page, int size);
    
    /**
     * 处理作弊行为
     * @param detectionId 检测记录ID
     * @param action 处理动作
     * @return 是否成功
     */
    boolean handleCheatBehavior(Long detectionId, String action);
    
    /**
     * 提交内容审核
     * @param contentId 内容ID
     * @param contentType 内容类型
     * @param authorId 作者ID
     * @return 审核记录ID
     */
    Long submitContentForModeration(Long contentId, ContentModeration.ContentType contentType, Long authorId);
    
    /**
     * AI自动审核内容
     * @param moderationId 审核记录ID
     * @return 审核结果
     */
    ContentModeration.ModerationResult aiModerateContent(Long moderationId);
    
    /**
     * 人工审核内容
     * @param moderationId 审核记录ID
     * @param moderatorId 审核员ID
     * @param result 审核结果
     * @param remarks 备注
     * @return 是否成功
     */
    boolean manualModerateContent(Long moderationId, Long moderatorId, 
                                 ContentModeration.ModerationResult result, String remarks);
    
    /**
     * 获取内容审核记录
     * @param contentId 内容ID
     * @return 审核记录
     */
    ContentModeration getContentModerationRecord(Long contentId);
    
    /**
     * 启用登录保护
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean enableLoginProtection(Long userId);
    
    /**
     * 检测异常登录
     * @param userId 用户ID
     * @param ip 登录IP
     * @param device 登录设备
     * @return 是否异常
     */
    boolean detectAnomalousLogin(Long userId, String ip, String device);
    
    /**
     * 获取账户安全信息
     * @param userId 用户ID
     * @return 账户安全信息
     */
    AccountSecurity getAccountSecurityInfo(Long userId);
    
    /**
     * 启用双因素认证
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean enableTwoFactorAuth(Long userId);
    
    /**
     * 启用数据加密
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean enableDataEncryption(Long userId);
    
    /**
     * 设置隐私级别
     * @param userId 用户ID
     * @param privacySetting 隐私设置
     * @return 是否成功
     */
    boolean setPrivacySetting(Long userId, DataSecurity.PrivacySetting privacySetting);
    
    /**
     * 获取数据安全信息
     * @param userId 用户ID
     * @return 数据安全信息
     */
    DataSecurity getDataSecurityInfo(Long userId);
    
    /**
     * 记录数据访问日志
     * @param userId 用户ID
     * @param ip 访问IP
     * @param action 访问动作
     */
    void logDataAccess(Long userId, String ip, String action);
}