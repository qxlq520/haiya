package com.haiya.campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class CampaignServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampaignServiceApplication.class, args);
    }
}