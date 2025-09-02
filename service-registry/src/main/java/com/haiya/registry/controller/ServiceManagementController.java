package com.haiya.registry.controller;

import com.haiya.registry.entity.ServiceConfig;
import com.haiya.registry.entity.ServiceStatus;
import com.haiya.registry.entity.ServiceStatus;
import com.haiya.registry.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceManagementController {
    
    @Autowired
    private ServiceManagementService serviceManagementService;
    
    // 获取所有服务
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllServices() {
        List<com.haiya.registry.entity.Service> services = serviceManagementService.getAllServices();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "成功");
        response.put("data", services);
        return ResponseEntity.ok(response);
    }
    
    // 根据ID获取服务
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getServiceById(@PathVariable Long id) {
        Optional<com.haiya.registry.entity.Service> service = serviceManagementService.getServiceById(id);
        Map<String, Object> response = new HashMap<>();
        
        if (service.isPresent()) {
            response.put("code", 200);
            response.put("message", "成功");
            response.put("data", service.get());
        } else {
            response.put("code", 404);
            response.put("message", "服务不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 根据名称获取服务
    @GetMapping("/name/{name}")
    public ResponseEntity<Map<String, Object>> getServiceByName(@PathVariable String name) {
        Optional<com.haiya.registry.entity.Service> service = serviceManagementService.getServiceByName(name);
        Map<String, Object> response = new HashMap<>();
        
        if (service.isPresent()) {
            response.put("code", 200);
            response.put("message", "成功");
            response.put("data", service.get());
        } else {
            response.put("code", 404);
            response.put("message", "服务不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 创建新服务
    @PostMapping
    public ResponseEntity<Map<String, Object>> createService(@RequestBody com.haiya.registry.entity.Service service) {
        // 检查服务是否已存在
        Optional<com.haiya.registry.entity.Service> existingService = serviceManagementService.getServiceByName(service.getServiceName());
        if (existingService.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "服务已存在");
            return ResponseEntity.badRequest().body(response);
        }
        
        com.haiya.registry.entity.Service createdService = serviceManagementService.createService(service);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "创建成功");
        response.put("data", createdService);
        return ResponseEntity.ok(response);
    }
    
    // 更新服务
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateService(@PathVariable Long id, @RequestBody com.haiya.registry.entity.Service serviceDetails) {
        Optional<com.haiya.registry.entity.Service> updatedService = serviceManagementService.updateService(id, serviceDetails);
        Map<String, Object> response = new HashMap<>();
        
        if (updatedService.isPresent()) {
            response.put("code", 200);
            response.put("message", "更新成功");
            response.put("data", updatedService.get());
        } else {
            response.put("code", 404);
            response.put("message", "服务不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 删除服务
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteService(@PathVariable Long id) {
        Optional<com.haiya.registry.entity.Service> service = serviceManagementService.getServiceById(id);
        Map<String, Object> response = new HashMap<>();
        
        if (service.isPresent()) {
            serviceManagementService.deleteService(id);
            response.put("code", 200);
            response.put("message", "删除成功");
        } else {
            response.put("code", 404);
            response.put("message", "服务不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 更新服务状态
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateServiceStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String statusStr = payload.get("status");
        com.haiya.registry.entity.ServiceStatus status;
        try {
            status = com.haiya.registry.entity.ServiceStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "无效的状态值");
            return ResponseEntity.badRequest().body(response);
        }
        
        Optional<com.haiya.registry.entity.Service> updatedService = serviceManagementService.updateServiceStatus(id, status);
        Map<String, Object> response = new HashMap<>();
        
        if (updatedService.isPresent()) {
            response.put("code", 200);
            response.put("message", "状态更新成功");
            response.put("data", updatedService.get());
        } else {
            response.put("code", 404);
            response.put("message", "服务不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取服务配置
    @GetMapping("/{serviceId}/configs")
    public ResponseEntity<Map<String, Object>> getServiceConfigs(@PathVariable Long serviceId) {
        List<com.haiya.registry.entity.ServiceConfig> configs = serviceManagementService.getServiceConfigs(serviceId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "成功");
        response.put("data", configs);
        return ResponseEntity.ok(response);
    }
    
    // 创建服务配置
    @PostMapping("/{serviceId}/configs")
    public ResponseEntity<Map<String, Object>> createServiceConfig(@PathVariable Long serviceId, @RequestBody com.haiya.registry.entity.ServiceConfig config) {
        // 设置服务ID关联
        Optional<com.haiya.registry.entity.Service> service = serviceManagementService.getServiceById(serviceId);
        if (!service.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 404);
            response.put("message", "服务不存在");
            return ResponseEntity.badRequest().body(response);
        }
        
        config.setService(service.get());
        com.haiya.registry.entity.ServiceConfig createdConfig = serviceManagementService.createServiceConfig(config);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "创建成功");
        response.put("data", createdConfig);
        return ResponseEntity.ok(response);
    }
    
    // 更新服务配置
    @PutMapping("/configs/{id}")
    public ResponseEntity<Map<String, Object>> updateServiceConfig(@PathVariable Long id, @RequestBody ServiceConfig configDetails) {
        Optional<com.haiya.registry.entity.ServiceConfig> updatedConfig = serviceManagementService.updateServiceConfig(id, configDetails);
        Map<String, Object> response = new HashMap<>();
        
        if (updatedConfig.isPresent()) {
            response.put("code", 200);
            response.put("message", "更新成功");
            response.put("data", updatedConfig.get());
        } else {
            response.put("code", 404);
            response.put("message", "配置不存在");
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 删除服务配置
    @DeleteMapping("/configs/{id}")
    public ResponseEntity<Map<String, Object>> deleteServiceConfig(@PathVariable Long id) {
        serviceManagementService.deleteServiceConfig(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "删除成功");
        return ResponseEntity.ok(response);
    }
}