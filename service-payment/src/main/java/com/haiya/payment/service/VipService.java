package com.haiya.payment.service;

import com.haiya.payment.entity.VipLevel;
import com.haiya.payment.entity.UserVip;
import java.util.List;

/**
 * VIP会员服务接口
 */
public interface VipService {
    
    /**
     * 获取所有VIP等级
     * @return VIP等级列表
     */
    List<VipLevel> getAllVipLevels();
    
    /**
     * 根据ID获取VIP等级信息
     * @param id 等级ID
     * @return VIP等级信息
     */
    VipLevel getVipLevelById(Long id);
    
    /**
     * 获取用户当前VIP信息
     * @param userId 用户ID
     * @return 用户VIP信息
     */
    UserVip getUserVipInfo(Long userId);
    
    /**
     * 开通VIP会员
     * @param userId 用户ID
     * @param vipLevelId VIP等级ID
     * @param period 购买时长（月）
     * @param autoRenew 是否自动续费
     * @return 是否成功
     */
    boolean activateVip(Long userId, Long vipLevelId, Integer period, Boolean autoRenew);
    
    /**
     * 续费VIP会员
     * @param userId 用户ID
     * @param period 续费时长（月）
     * @return 是否成功
     */
    boolean renewVip(Long userId, Integer period);
    
    /**
     * 升级VIP等级
     * @param userId 用户ID
     * @param newVipLevelId 新的VIP等级ID
     * @return 是否成功
     */
    boolean upgradeVip(Long userId, Long newVipLevelId);
    
    /**
     * 检查用户是否拥有特定特权
     * @param userId 用户ID
     * @param privilege 特权标识
     * @return 是否拥有该特权
     */
    boolean hasPrivilege(Long userId, String privilege);
    
    /**
     * 获取用户特权列表
     * @param userId 用户ID
     * @return 特权列表
     */
    List<String> getUserPrivileges(Long userId);
}