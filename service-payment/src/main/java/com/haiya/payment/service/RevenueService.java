package com.haiya.payment.service;

import com.haiya.payment.entity.AdRevenue;
import com.haiya.payment.entity.BrandPartnership;
import java.math.BigDecimal;
import java.util.List;

/**
 * 收入服务接口（广告分成和品牌合作）
 */
public interface RevenueService {
    
    /**
     * 记录广告收入
     * @param creatorId 创作者ID
     * @param videoId 视频ID
     * @param adId 广告ID
     * @param totalRevenue 总收入
     * @param creatorShareRatio 创作者分成比例
     * @return 收入记录ID
     */
    Long recordAdRevenue(Long creatorId, Long videoId, Long adId, BigDecimal totalRevenue, BigDecimal creatorShareRatio);
    
    /**
     * 获取创作者广告收入记录
     * @param creatorId 创作者ID
     * @param page 页码
     * @param size 每页大小
     * @return 广告收入列表
     */
    List<AdRevenue> getCreatorAdRevenues(Long creatorId, int page, int size);
    
    /**
     * 结算广告收入到创作者账户
     * @param adRevenueId 广告收入ID
     * @return 是否成功
     */
    boolean settleAdRevenue(Long adRevenueId);
    
    /**
     * 发起品牌合作
     * @param brandId 品牌ID
     * @param creatorId 创作者ID
     * @param type 合作类型
     * @param content 合作内容
     * @param fee 合作费用
     * @return 合作ID
     */
    Long initiateBrandPartnership(Long brandId, Long creatorId, BrandPartnership.PartnershipType type, 
                                 String content, BigDecimal fee);
    
    /**
     * 接受品牌合作
     * @param partnershipId 合作ID
     * @return 是否成功
     */
    boolean acceptBrandPartnership(Long partnershipId);
    
    /**
     * 完成品牌合作
     * @param partnershipId 合作ID
     * @return 是否成功
     */
    boolean completeBrandPartnership(Long partnershipId);
    
    /**
     * 获取创作者品牌合作记录
     * @param creatorId 创作者ID
     * @param page 页码
     * @param size 每页大小
     * @return 品牌合作列表
     */
    List<BrandPartnership> getCreatorBrandPartnerships(Long creatorId, int page, int size);
    
    /**
     * 获取品牌方合作记录
     * @param brandId 品牌ID
     * @param page 页码
     * @param size 每页大小
     * @return 品牌合作列表
     */
    List<BrandPartnership> getBrandPartnerships(Long brandId, int page, int size);
    
    /**
     * 支付品牌合作费用
     * @param partnershipId 合作ID
     * @return 是否成功
     */
    boolean payBrandPartnershipFee(Long partnershipId);
}