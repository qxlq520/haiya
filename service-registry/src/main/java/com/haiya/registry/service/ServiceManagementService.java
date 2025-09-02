package com.haiya.registry.service;

import com.haiya.registry.entity.ServiceConfig;
import com.haiya.registry.entity.ServiceStatus;
import com.haiya.registry.entity.ConfigType;
import com.haiya.registry.repository.ServiceRepository;
import com.haiya.registry.repository.ServiceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceManagementService {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private ServiceConfigRepository configRepository;
    
    // 获取所有服务
    public List<com.haiya.registry.entity.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // 根据ID获取服务
    public Optional<com.haiya.registry.entity.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    // 根据名称获取服务
    public Optional<com.haiya.registry.entity.Service> getServiceByName(String name) {
        return serviceRepository.findByServiceName(name);
    }

    // 创建新服务
    public com.haiya.registry.entity.Service createService(com.haiya.registry.entity.Service service) {
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());
        return serviceRepository.save(service);
    }

    // 更新服务
    public Optional<com.haiya.registry.entity.Service> updateService(Long id, com.haiya.registry.entity.Service serviceDetails) {
        return serviceRepository.findById(id).map(service -> {
            service.setServiceName(serviceDetails.getServiceName());
            service.setServiceDescription(serviceDetails.getServiceDescription());
            service.setStatus(serviceDetails.getStatus());
            service.setServiceVersion(serviceDetails.getServiceVersion());
            service.setUpdatedAt(LocalDateTime.now());
            return serviceRepository.save(service);
        });
    }
    
    // 删除服务
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
    
    // 更新服务状态
    public Optional<com.haiya.registry.entity.Service> updateServiceStatus(Long id, com.haiya.registry.entity.ServiceStatus status) {
        return serviceRepository.findById(id).map(service -> {
            service.setStatus(status);
            service.setUpdatedAt(LocalDateTime.now());
            return serviceRepository.save(service);
        });
    }

    // 获取服务配置
    public List<com.haiya.registry.entity.ServiceConfig> getServiceConfigs(Long serviceId) {
        return configRepository.findByServiceId(serviceId);
    }

    // 创建服务配置
    public com.haiya.registry.entity.ServiceConfig createServiceConfig(com.haiya.registry.entity.ServiceConfig config) {
        config.setCreatedAt(LocalDateTime.now());
        config.setUpdatedAt(LocalDateTime.now());
        return configRepository.save(config);
    }

    // 更新服务配置
    public Optional<com.haiya.registry.entity.ServiceConfig> updateServiceConfig(Long id, com.haiya.registry.entity.ServiceConfig configDetails) {
        return configRepository.findById(id).map(config -> {
            config.setConfigKey(configDetails.getConfigKey());
            config.setConfigValue(configDetails.getConfigValue());
            config.setConfigType(configDetails.getConfigType());
            config.setDescription(configDetails.getDescription());
            config.setUpdatedAt(LocalDateTime.now());
            return configRepository.save(config);
        });
    }
    
    // 删除服务配置
    public void deleteServiceConfig(Long id) {
        configRepository.deleteById(id);
    }
}