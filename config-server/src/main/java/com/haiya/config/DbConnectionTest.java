package com.haiya.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DbConnectionTest {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DbConnectionTest.class, args);
        
        // 检查数据库连接
        try {
            javax.sql.DataSource dataSource = context.getBean(javax.sql.DataSource.class);
            java.sql.Connection connection = dataSource.getConnection();
            System.out.println("数据库连接成功: " + connection.getMetaData().getURL());
            connection.close();
        } catch (Exception e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        SpringApplication.exit(context);
    }
}