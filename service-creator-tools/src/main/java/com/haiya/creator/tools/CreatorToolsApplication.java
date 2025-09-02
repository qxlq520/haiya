package com.haiya.creator.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class CreatorToolsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreatorToolsApplication.class, args);
    }
}