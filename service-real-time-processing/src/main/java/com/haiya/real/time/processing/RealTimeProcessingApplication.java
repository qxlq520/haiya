package com.haiya.real.time.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class RealTimeProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RealTimeProcessingApplication.class, args);
    }
}