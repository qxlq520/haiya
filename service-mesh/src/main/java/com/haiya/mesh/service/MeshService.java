package com.haiya.mesh.service;

import com.haiya.mesh.dto.ServiceCallDTO;
import com.haiya.mesh.dto.ServiceResponseDTO;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Service
public class MeshService {

    private static final Logger logger = LoggerFactory.getLogger(MeshService.class);

    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RateLimiterRegistry rateLimiterRegistry;
    private final RetryRegistry retryRegistry;
    private final RestTemplate restTemplate;
    private final Executor meshTaskExecutor;
    private final MultiLevelCacheService cacheService;

    @Value("${mesh.fallback.enabled:true}")
    private boolean fallbackEnabled;

    @Value("${mesh.fallback.default-message:服务暂时不可用，请稍后再试}")
    private String defaultMessage;

    public MeshService(CircuitBreakerRegistry circuitBreakerRegistry,
                       RateLimiterRegistry rateLimiterRegistry,
                       RetryRegistry retryRegistry,
                       RestTemplate restTemplate,
                       Executor meshTaskExecutor,
                       MultiLevelCacheService cacheService) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.retryRegistry = retryRegistry;
        this.restTemplate = restTemplate;
        this.meshTaskExecutor = meshTaskExecutor;
        this.cacheService = cacheService;
    }

    /**
     * 执行服务调用，包含熔断、限流和重试机制
     *
     * @param serviceCallDTO 服务调用信息
     * @return 服务响应结果
     */
    public ServiceResponseDTO executeServiceCall(ServiceCallDTO serviceCallDTO) {
        String serviceId = serviceCallDTO.getServiceId();
        
        // 为热点服务生成缓存键
        String cacheKey = "mesh_service:" + serviceId + ":" + serviceCallDTO.getUri();
        
        // 对高频访问的服务启用缓存
        if (isHotService(serviceId)) {
            return cacheService.get("hot_services", cacheKey, () -> doExecuteServiceCall(serviceCallDTO));
        }
        
        return doExecuteServiceCall(serviceCallDTO);
    }

    /**
     * 实际执行服务调用的方法
     *
     * @param serviceCallDTO 服务调用信息
     * @return 服务响应结果
     */
    private ServiceResponseDTO doExecuteServiceCall(ServiceCallDTO serviceCallDTO) {
        String serviceId = serviceCallDTO.getServiceId();
        
        // 获取或创建针对该服务的熔断器
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(serviceId);
        
        // 获取或创建针对该服务的限流器
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(serviceId);
        
        // 获取或创建针对该服务的重试器
        Retry retry = retryRegistry.retry(serviceId);
        
        // 组合熔断器、限流器
        Supplier<ServiceResponseDTO> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> rateLimiter.executeSupplier(() -> {
                    try {
                        return callService(serviceCallDTO);
                    } catch (Exception e) {
                        logger.error("服务调用失败: {}", e.getMessage(), e);
                        throw e;
                    }
                }));

        // 添加重试机制
        decoratedSupplier = Retry.decorateSupplier(retry, decoratedSupplier);

        try {
            ServiceResponseDTO response = decoratedSupplier.get();
            return response;
        } catch (Exception e) {
            logger.error("服务调用最终失败: {}", e.getMessage(), e);
            // 如果启用了降级，则返回降级结果
            if (fallbackEnabled) {
                return createFallbackResponse(e);
            }
            throw e;
        }
    }

    /**
     * 异步执行服务调用
     *
     * @param serviceCallDTO 服务调用信息
     * @return CompletableFuture包装的响应结果
     */
    public CompletableFuture<ServiceResponseDTO> executeServiceCallAsync(ServiceCallDTO serviceCallDTO) {
        return CompletableFuture.supplyAsync(() -> executeServiceCall(serviceCallDTO), meshTaskExecutor)
                .exceptionally(throwable -> {
                    logger.error("异步服务调用失败: {}", throwable.getMessage(), throwable);
                    return createFallbackResponse((Exception) throwable);
                });
    }

    /**
     * 实际调用服务
     *
     * @param serviceCallDTO 服务调用信息
     * @return 服务响应结果
     */
    private ServiceResponseDTO callService(ServiceCallDTO serviceCallDTO) {
        long startTime = System.currentTimeMillis();
        
        try {
            logger.info("调用服务: {} {}", serviceCallDTO.getMethod(), serviceCallDTO.getUri());
            
            // 构建HTTP请求头
            HttpHeaders headers = new HttpHeaders();
            if (serviceCallDTO.getHeaders() != null) {
                serviceCallDTO.getHeaders().forEach(headers::add);
            }
            
            // 构建HTTP请求实体
            HttpEntity<?> requestEntity = new HttpEntity<>(serviceCallDTO.getBody(), headers);
            
            // 确定HTTP方法
            HttpMethod httpMethod = HttpMethod.valueOf(serviceCallDTO.getMethod().toUpperCase());
            
            // 使用restTemplate执行HTTP请求
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    serviceCallDTO.getUri(),
                    httpMethod,
                    requestEntity,
                    String.class
            );
            
            long endTime = System.currentTimeMillis();
            
            // 构建成功响应
            ServiceResponseDTO response = new ServiceResponseDTO();
            response.setSuccess(true);
            response.setMessage("服务调用成功");
            response.setStatusCode(responseEntity.getStatusCodeValue());
            response.setData(responseEntity.getBody());
            response.setResponseTime(endTime - startTime);
            
            return response;
        } catch (Exception e) {
            logger.error("服务调用异常: {}", e.getMessage(), e);
            throw new RuntimeException("服务调用失败: " + e.getMessage(), e);
        }
    }

    /**
     * 创建降级响应
     *
     * @param exception 异常信息
     * @return 降级响应
     */
    private ServiceResponseDTO createFallbackResponse(Exception exception) {
        ServiceResponseDTO response = new ServiceResponseDTO();
        response.setSuccess(false);
        response.setMessage(defaultMessage);
        response.setStatusCode(503); // Service Unavailable
        response.setFallbackData("这是降级数据");
        response.setResponseTime(0);
        logger.warn("服务调用失败，返回降级响应: {}", exception.getMessage());
        return response;
    }
    
    /**
     * 判断是否为热点服务（根据访问频率等指标）
     * 
     * @param serviceId 服务ID
     * @return 是否为热点服务
     */
    private boolean isHotService(String serviceId) {
        // 简化实现：根据服务ID判断是否为热点服务
        // 实际应用中可以根据访问频率、调用次数等指标动态判断
        return serviceId.contains("user") || 
               serviceId.contains("product") || 
               serviceId.contains("order") ||
               serviceId.startsWith("hot");
    }
}