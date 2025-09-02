package com.haiya.user.service;

import com.haiya.user.entity.UserExperience;
import java.util.List;
import java.util.Map;

/**
 * 用户体验服务接口
 */
public interface UserExperienceService {
    
    /**
     * 获取用户个性化设置
     * @param userId 用户ID
     * @return 用户体验配置
     */
    UserExperience getUserExperience(Long userId);
    
    /**
     * 设置无障碍功能
     * @param userId 用户ID
     * @param setting 无障碍设置
     * @return 是否成功
     */
    boolean setAccessibilitySetting(Long userId, UserExperience.AccessibilitySetting setting);
    
    /**
     * 设置语言
     * @param userId 用户ID
     * @param language 语言代码
     * @return 是否成功
     */
    boolean setLanguage(Long userId, String language);
    
    /**
     * 设置主题
     * @param userId 用户ID
     * @param theme 主题设置
     * @return 是否成功
     */
    boolean setTheme(Long userId, UserExperience.ThemeSetting theme);
    
    /**
     * 设置手势操作
     * @param userId 用户ID
     * @param setting 手势设置
     * @return 是否成功
     */
    boolean setGestureSetting(Long userId, UserExperience.GestureSetting setting);
    
    /**
     * 设置字体大小
     * @param userId 用户ID
     * @param fontSize 字体大小
     * @return 是否成功
     */
    boolean setFontSize(Long userId, Integer fontSize);
    
    /**
     * 设置对比度
     * @param userId 用户ID
     * @param contrast 对比度设置
     * @return 是否成功
     */
    boolean setContrast(Long userId, UserExperience.ContrastSetting contrast);
    
    /**
     * 获取支持的语言列表
     * @return 语言列表
     */
    List<String> getSupportedLanguages();
    
    /**
     * 获取默认用户体验配置
     * @return 默认配置
     */
    UserExperience getDefaultExperience();
    
    /**
     * 根据用户设备和位置推荐体验设置
     * @param userId 用户ID
     * @param deviceInfo 设备信息
     * @param location 位置信息
     * @return 推荐设置
     */
    Map<String, Object> recommendExperienceSettings(Long userId, Map<String, String> deviceInfo, String location);
}