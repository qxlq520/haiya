package com.haiya.registry.controller;

import com.haiya.registry.entity.ServiceGovernance;
import com.haiya.registry.entity.ContainerDeployment;
import com.haiya.registry.service.ArchitectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务中心注册控制器
 * 提供服务注册、发现和管理功能
 */
@RestController
@RequestMapping("/api/registry")
public class ServiceRegistryController {

    @Autowired
    private ArchitectureService architectureService;

    /**
     * 注册微服务治理配置
     * @param config 服务治理配置
     * @return 配置ID
     */
    @PostMapping("/governance")
    public ResponseEntity<Long> registerServiceGovernance(@RequestBody ServiceGovernance config) {
        try {
            Long id = architectureService.configureServiceGovernance(config);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取服务治理配置
     * @param serviceName 服务名称
     * @return 治理配置
     */
    @GetMapping("/governance/{serviceName}")
    public ResponseEntity<ServiceGovernance> getServiceGovernance(@PathVariable String serviceName) {
        try {
            ServiceGovernance config = architectureService.getServiceGovernance(serviceName);
            if (config != null) {
                return ResponseEntity.ok(config);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新服务治理配置
     * @param id 配置ID
     * @param config 治理配置
     * @return 是否成功
     */
    @PutMapping("/governance/{id}")
    public ResponseEntity<Boolean> updateServiceGovernance(@PathVariable Long id, @RequestBody ServiceGovernance config) {
        try {
            boolean result = architectureService.updateServiceGovernance(id, config);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 部署容器化服务
     * @param deployment 部署配置
     * @return 部署ID
     */
    @PostMapping("/deployment")
    public ResponseEntity<Long> deployContainer(@RequestBody ContainerDeployment deployment) {
        try {
            Long id = architectureService.deployContainer(deployment);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取容器部署列表
     * @param environment 部署环境
     * @return 部署列表
     */
    @GetMapping("/deployment")
    public ResponseEntity<List<ContainerDeployment>> listContainerDeployments(
            @RequestParam(required = false) ContainerDeployment.DeploymentEnvironment environment) {
        try {
            List<ContainerDeployment> deployments = architectureService.listContainerDeployments(environment);
            return ResponseEntity.ok(deployments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新容器部署状态
     * @param deploymentId 部署ID
     * @param status 部署状态
     * @return 是否成功
     */
    @PutMapping("/deployment/{deploymentId}/status")
    public ResponseEntity<Boolean> updateDeploymentStatus(
            @PathVariable Long deploymentId, 
            @RequestParam ContainerDeployment.DeploymentStatus status) {
        try {
            boolean result = architectureService.updateDeploymentStatus(deploymentId, status);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 扩缩容服务
     * @param deploymentId 部署ID
     * @param replicas 副本数
     * @return 是否成功
     */
    @PutMapping("/deployment/{deploymentId}/scale")
    public ResponseEntity<Boolean> scaleDeployment(
            @PathVariable Long deploymentId, 
            @RequestParam Integer replicas) {
        try {
            boolean result = architectureService.scaleDeployment(deploymentId, replicas);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取服务健康状态
     * @param serviceName 服务名称
     * @return 健康状态
     */
    @GetMapping("/health/{serviceName}")
    public ResponseEntity<String> getServiceHealthStatus(@PathVariable String serviceName) {
        try {
            String status = architectureService.getServiceHealthStatus(serviceName);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取系统资源使用情况
     * @return 资源使用情况
     */
    @GetMapping("/system/resources")
    public ResponseEntity<Object> getSystemResourceUsage() {
        try {
            Object usage = architectureService.getSystemResourceUsage();
            return ResponseEntity.ok(usage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取服务调用统计
     * @param serviceName 服务名称
     * @param duration 时间范围(分钟)
     * @return 调用统计
     */
    @GetMapping("/statistics/{serviceName}")
    public ResponseEntity<Object> getServiceCallStatistics(
            @PathVariable String serviceName, 
            @RequestParam(defaultValue = "60") Integer duration) {
        try {
            Object statistics = architectureService.getServiceCallStatistics(serviceName, duration);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}