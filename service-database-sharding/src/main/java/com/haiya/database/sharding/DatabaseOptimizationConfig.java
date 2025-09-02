package com.haiya.database.sharding;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseOptimizationConfig {

    /**
     * 主数据库配置（写操作）
     */
    @Bean
    @Primary
    public DataSource masterDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/haiya_master?useSSL=false&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("password");
        
        // 连接池优化配置
        config.setMinimumIdle(10);              // 最小空闲连接数
        config.setMaximumPoolSize(50);          // 最大连接数
        config.setConnectionTimeout(30000);     // 连接超时时间 30 秒
        config.setIdleTimeout(600000);          // 空闲连接超时时间 10 分钟
        config.setMaxLifetime(1800000);         // 连接最大生命周期 30 分钟
        config.setLeakDetectionThreshold(60000); // 连接泄漏检测阈值 1 分钟
        
        // 性能优化配置
        config.setConnectionTestQuery("SELECT 1");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        
        return new HikariDataSource(config);
    }

    /**
     * 从数据库配置（读操作）
     */
    @Bean
    public DataSource slaveDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/haiya_slave?useSSL=false&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("password");
        
        // 连接池优化配置（针对读操作）
        config.setMinimumIdle(5);               // 最小空闲连接数
        config.setMaximumPoolSize(100);         // 最大连接数（读操作可以更多）
        config.setConnectionTimeout(30000);     // 连接超时时间 30 秒
        config.setIdleTimeout(600000);          // 空闲连接超时时间 10 分钟
        config.setMaxLifetime(1800000);         // 连接最大生命周期 30 分钟
        config.setLeakDetectionThreshold(60000); // 连接泄漏检测阈值 1 分钟
        
        // 性能优化配置
        config.setConnectionTestQuery("SELECT 1");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        
        return new HikariDataSource(config);
    }

    /**
     * 数据源路由配置，实现读写分离
     */
    @Bean
    public DataSource routingDataSource() {
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataSourceContextHolder.getDataSourceType();
            }
        };

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", masterDataSource());
        dataSourceMap.put("slave", slaveDataSource());

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource());
        
        return routingDataSource;
    }

    /**
     * 最终的数据源Bean
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        return routingDataSource();
    }
}