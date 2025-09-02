package com.haiya.database.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DatabaseShardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatabaseShardingApplication.class, args);
    }
}