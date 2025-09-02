package com.haiya.infrastructure.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableScheduling
public class InfrastructureMonitoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfrastructureMonitoringApplication.class, args);
    }
}