package com.haiya.registry.service;

import com.haiya.registry.entity.ServiceGovernance;
import com.haiya.registry.entity.ContainerDeployment;
import java.util.List;

/**
 * 技术架构服务接口
 */
public interface ArchitectureService {
    
    /**
     * 配置微服务治理
     * @param config 治理配置
     * @return 配置ID
     */
    Long configureServiceGovernance(ServiceGovernance config);
    
    /**
     * 获取服务治理配置
     * @param serviceName 服务名称
     * @return 治理配置
     */
    ServiceGovernance getServiceGovernance(String serviceName);
    
    /**
     * 更新服务治理配置
     * @param id 配置ID
     * @param config 治理配置
     * @return 是否成功
     */
    boolean updateServiceGovernance(Long id, ServiceGovernance config);
    
    /**
     * 部署容器化服务
     * @param deployment 部署配置
     * @return 部署ID
     */
    Long deployContainer(ContainerDeployment deployment);
    
    /**
     * 获取容器部署列表
     * @param environment 部署环境
     * @return 部署列表
     */
    List<ContainerDeployment> listContainerDeployments(ContainerDeployment.DeploymentEnvironment environment);
    
    /**
     * 更新容器部署状态
     * @param deploymentId 部署ID
     * @param status 部署状态
     * @return 是否成功
     */
    boolean updateDeploymentStatus(Long deploymentId, ContainerDeployment.DeploymentStatus status);
    
    /**
     * 扩缩容服务
     * @param deploymentId 部署ID
     * @param replicas 副本数
     * @return 是否成功
     */
    boolean scaleDeployment(Long deploymentId, Integer replicas);
    
    /**
     * 获取服务健康状态
     * @param serviceName 服务名称
     * @return 健康状态
     */
    String getServiceHealthStatus(String serviceName);
    
    /**
     * 获取系统资源使用情况
     * @return 资源使用情况
     */
    Object getSystemResourceUsage();
    
    /**
     * 获取服务调用统计
     * @param serviceName 服务名称
     * @param duration 时间范围(分钟)
     * @return 调用统计
     */
    Object getServiceCallStatistics(String serviceName, Integer duration);
}