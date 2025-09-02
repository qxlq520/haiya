package com.haiya.database.sharding;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库连接池监控服务
 */
@Service
public class ConnectionPoolMonitoringService {

    @Autowired
    @Qualifier("masterDataSource")
    private HikariDataSource masterDataSource;

    @Autowired
    @Qualifier("slaveDataSource")
    private HikariDataSource slaveDataSource;

    /**
     * 获取主库连接池状态
     *
     * @return 连接池状态信息
     */
    public Map<String, Object> getMasterPoolStatus() {
        return getPoolStatus(masterDataSource, "master");
    }

    /**
     * 获取从库连接池状态
     *
     * @return 连接池状态信息
     */
    public Map<String, Object> getSlavePoolStatus() {
        return getPoolStatus(slaveDataSource, "slave");
    }

    /**
     * 获取连接池状态信息
     *
     * @param dataSource 数据源
     * @param name       数据源名称
     * @return 连接池状态信息
     */
    private Map<String, Object> getPoolStatus(HikariDataSource dataSource, String name) {
        Map<String, Object> status = new HashMap<>();
        status.put("name", name);
        status.put("totalConnections", dataSource.getHikariPoolMXBean().getTotalConnections());
        status.put("activeConnections", dataSource.getHikariPoolMXBean().getActiveConnections());
        status.put("idleConnections", dataSource.getHikariPoolMXBean().getIdleConnections());
        status.put("threadsAwaitingConnection", dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
        status.put("maxPoolSize", dataSource.getMaximumPoolSize());
        status.put("minIdle", dataSource.getMinimumIdle());
        status.put("connectionTimeout", dataSource.getConnectionTimeout());
        status.put("idleTimeout", dataSource.getIdleTimeout());
        status.put("maxLifetime", dataSource.getMaxLifetime());
        return status;
    }

    /**
     * 获取所有连接池状态
     *
     * @return 所有连接池状态信息
     */
    public Map<String, Map<String, Object>> getAllPoolStatus() {
        Map<String, Map<String, Object>> allStatus = new HashMap<>();
        allStatus.put("master", getMasterPoolStatus());
        allStatus.put("slave", getSlavePoolStatus());
        return allStatus;
    }

    /**
     * 检查是否存在连接池问题
     *
     * @return 是否存在问题
     */
    public boolean checkPoolIssues() {
        // 检查主库连接池
        HikariPoolMXBean masterPoolBean = masterDataSource.getHikariPoolMXBean();
        if (masterPoolBean.getActiveConnections() >= masterDataSource.getMaximumPoolSize() * 0.8) {
            return true; // 连接使用率过高
        }

        if (masterPoolBean.getThreadsAwaitingConnection() > 0) {
            return true; // 有线程在等待连接
        }

        // 检查从库连接池
        HikariPoolMXBean slavePoolBean = slaveDataSource.getHikariPoolMXBean();
        if (slavePoolBean.getActiveConnections() >= slaveDataSource.getMaximumPoolSize() * 0.8) {
            return true; // 连接使用率过高
        }

        if (slavePoolBean.getThreadsAwaitingConnection() > 0) {
            return true; // 有线程在等待连接
        }

        return false;
    }
}