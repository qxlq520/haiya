package com.haiya.payment.service;
import com.haiya.payment.entity.Currency;
import com.haiya.payment.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;

/**
 * 虚拟货币服务接口
 */
public interface VirtualCurrencyService {
    
    /**
     * 获取所有支持的虚拟货币类型
     * @return 货币列表
     */
    List<Currency> getAllCurrencies();
    
    /**
     * 根据代码获取货币信息
     * @param code 货币代码
     * @return 货币信息
     */
    Currency getCurrencyByCode(String code);
    
    /**
     * 获取用户账户余额
     * @param userId 用户ID
     * @param currencyId 货币ID
     * @return 账户余额
     */
    BigDecimal getUserBalance(Long userId, Long currencyId);
    
    /**
     * 创建用户账户
     * @param userId 用户ID
     * @param currencyId 货币ID
     * @return 是否成功
     */
    boolean createUserAccount(Long userId, Long currencyId);
    
    /**
     * 充值虚拟货币
     * @param userId 用户ID
     * @param currencyId 货币ID
     * @param amount 充值金额
     * @param realCurrencyAmount 对应的真实货币金额
     * @return 交易ID
     */
    Long rechargeCurrency(Long userId, Long currencyId, BigDecimal amount, BigDecimal realCurrencyAmount);
    
    /**
     * 消费虚拟货币
     * @param userId 用户ID
     * @param currencyId 货币ID
     * @param amount 消费金额
     * @param description 消费描述
     * @return 交易ID
     */
    Long consumeCurrency(Long userId, Long currencyId, BigDecimal amount, String description);
    
    /**
     * 冻结虚拟货币
     * @param userId 用户ID
     * @param currencyId 货币ID
     * @param amount 冻结金额
     * @param description 冻结描述
     * @return 是否成功
     */
    boolean freezeCurrency(Long userId, Long currencyId, BigDecimal amount, String description);
    
    /**
     * 解冻虚拟货币
     * @param userId 用户ID
     * @param currencyId 货币ID
     * @param amount 解冻金额
     * @param description 解冻描述
     * @return 是否成功
     */
    boolean unfreezeCurrency(Long userId, Long currencyId, BigDecimal amount, String description);
    
    /**
     * 获取用户交易记录
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 交易记录列表
     */
    List<Transaction> getUserTransactions(Long userId, int page, int size);
}