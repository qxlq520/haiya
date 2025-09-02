package com.haiya.content.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class ContentSecurityServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentSecurityServiceApplication.class, args);
    }
}