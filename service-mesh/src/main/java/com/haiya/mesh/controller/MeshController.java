package com.haiya.mesh.controller;

import com.haiya.mesh.dto.ServiceCallDTO;
import com.haiya.mesh.dto.ServiceResponseDTO;
import com.haiya.mesh.service.MeshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mesh")
public class MeshController {

    private static final Logger logger = LoggerFactory.getLogger(MeshController.class);

    private final MeshService meshService;

    public MeshController(MeshService meshService) {
        this.meshService = meshService;
    }

    /**
     * 同步服务调用接口
     *
     * @param serviceCallDTO 服务调用信息
     * @return 服务响应结果
     */
    @PostMapping("/call")
    public ResponseEntity<ServiceResponseDTO> callService(@RequestBody ServiceCallDTO serviceCallDTO) {
        try {
            logger.info("收到服务调用请求: {}", serviceCallDTO);
            ServiceResponseDTO response = meshService.executeServiceCall(serviceCallDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("服务调用处理失败: {}", e.getMessage(), e);
            ServiceResponseDTO errorResponse = new ServiceResponseDTO();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("服务调用处理失败: " + e.getMessage());
            errorResponse.setStatusCode(500);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 异步服务调用接口
     *
     * @param serviceCallDTO 服务调用信息
     * @return 异步处理结果
     */
    @PostMapping("/call/async")
    public ResponseEntity<String> callServiceAsync(@RequestBody ServiceCallDTO serviceCallDTO) {
        try {
            logger.info("收到异步服务调用请求: {}", serviceCallDTO);
            meshService.executeServiceCallAsync(serviceCallDTO);
            // 立即返回，不等待结果
            return ResponseEntity.ok("异步服务调用已提交");
        } catch (Exception e) {
            logger.error("异步服务调用提交失败: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("异步服务调用提交失败: " + e.getMessage());
        }
    }

    /**
     * 健康检查接口
     *
     * @return 健康状态
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service Mesh is running");
    }
}