package com.haiya.database.sharding;

/**
 * 数据源上下文持有者
 * 用于在读写分离时确定使用哪个数据源
 */
public class DataSourceContextHolder {
    
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    
    /**
     * 设置数据源类型
     * @param dataSourceType 数据源类型（master/slave）
     */
    public static void setDataSourceType(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }
    
    /**
     * 获取数据源类型
     * @return 数据源类型
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }
    
    /**
     * 清除数据源类型
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}