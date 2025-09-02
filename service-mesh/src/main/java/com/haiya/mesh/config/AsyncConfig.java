package com.haiya.mesh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${mesh.async.core-pool-size:10}")
    private int corePoolSize;

    @Value("${mesh.async.max-pool-size:50}")
    private int maxPoolSize;

    @Value("${mesh.async.queue-capacity:1000}")
    private int queueCapacity;

    @Bean("meshTaskExecutor")
    public Executor meshTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("mesh-async-");
        executor.initialize();
        return executor;
    }
}