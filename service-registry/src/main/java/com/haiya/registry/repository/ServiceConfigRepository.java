package com.haiya.registry.repository;

import com.haiya.registry.entity.ServiceConfig;
import com.haiya.registry.entity.ConfigType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceConfigRepository extends JpaRepository<ServiceConfig, Long> {
    List<ServiceConfig> findByServiceId(Long serviceId);
    
    List<ServiceConfig> findByServiceIdAndConfigType(Long serviceId, ConfigType configType);
    
    List<ServiceConfig> findByConfigKey(String configKey);
}