package com.haiya.user.entity;
import javax.persistence.*;
/**
 * 用户体验配置实体类
 */
@Entity
@Table(name = "user_experiences")
public class UserExperience {
    /**
     * 配置ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 无障碍功能设置
     */
    @Column(name = "accessibility_setting")
    @Enumerated(EnumType.STRING)
    private AccessibilitySetting accessibilitySetting;
    
    /**
     * 语言设置
     */
    private String language;
    
    /**
     * 主题设置
     */
    @Enumerated(EnumType.STRING)
    private ThemeSetting theme;
    
    /**
     * 手势操作设置
     */
    @Column(name = "gesture_setting")
    @Enumerated(EnumType.STRING)
    private GestureSetting gestureSetting;
    
    /**
     * 字体大小
     */
    @Column(name = "font_size")
    private Integer fontSize;
    
    /**
     * 对比度设置
     */
    @Enumerated(EnumType.STRING)
    private ContrastSetting contrast;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Long createdAt;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Long updatedAt;
    
    // Constructors
    public UserExperience() {}
    
    public UserExperience(Long userId) {
        this.userId = userId;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }
    
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
    
    public AccessibilitySetting getAccessibilitySetting() {
        return accessibilitySetting;
    }
    
    public void setAccessibilitySetting(AccessibilitySetting accessibilitySetting) {
        this.accessibilitySetting = accessibilitySetting;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public ThemeSetting getTheme() {
        return theme;
    }
    
    public void setTheme(ThemeSetting theme) {
        this.theme = theme;
    }
    
    public GestureSetting getGestureSetting() {
        return gestureSetting;
    }
    
    public void setGestureSetting(GestureSetting gestureSetting) {
        this.gestureSetting = gestureSetting;
    }
    
    public Integer getFontSize() {
        return fontSize;
    }
    
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }
    
    public ContrastSetting getContrast() {
        return contrast;
    }
    
    public void setContrast(ContrastSetting contrast) {
        this.contrast = contrast;
    }
    
    public Long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    
    public Long getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
    
    /**
     * 无障碍功能设置枚举
     */
    public enum AccessibilitySetting {
        NONE,                // 无特殊设置
        VISUAL_IMPAIRED,     // 视障模式
        HEARING_IMPAIRED,    // 听障模式
        MOTOR_IMPAIRED       // 肢体障碍模式
    }
    
    /**
     * 主题设置枚举
     */
    public enum ThemeSetting {
        LIGHT,   // 浅色主题
        DARK,    // 深色主题
        AUTO     // 自动切换
    }
    
    /**
     * 手势操作设置枚举
     */
    public enum GestureSetting {
        ENABLED,   // 启用手势
        DISABLED   // 禁用手势
    }
    
    /**
     * 对比度设置枚举
     */
    public enum ContrastSetting {
        NORMAL,   // 正常对比度
        HIGH      // 高对比度
    }
}