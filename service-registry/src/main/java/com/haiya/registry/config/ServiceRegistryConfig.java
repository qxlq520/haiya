package com.haiya.registry.config;

import com.haiya.registry.service.ArchitectureService;
import com.haiya.registry.service.impl.ServiceRegistryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务注册中心配置类
 */
@Configuration
public class ServiceRegistryConfig {
    
    /**
     * 配置架构服务Bean
     * @return ArchitectureService实例
     */
    @Bean
    public ArchitectureService architectureService() {
        return new ServiceRegistryServiceImpl();
    }
}