package com.haiya.minor.protection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class MinorProtectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinorProtectionApplication.class, args);
    }
}