package com.haiya.infrastructure.monitoring.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * 应用性能监控服务
 * 负责收集应用性能指标（QPS、响应时间、错误率等）
 */
@Service
public class ApplicationMetricsService {
    
    private final MeterRegistry meterRegistry;
    
    public ApplicationMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    
    /**
     * 记录HTTP请求
     * @param uri 请求URI
     * @param method HTTP方法
     * @param status 状态码
     * @param duration 请求处理时间（纳秒）
     */
    public void recordHttpRequest(String uri, String method, int status, long duration) {
        // 记录请求计数
        meterRegistry.counter("http.requests", 
                "uri", uri, 
                "method", method, 
                "status", String.valueOf(status))
                .increment();
        
        // 记录请求处理时间
        Timer.Sample sample = Timer.start(meterRegistry);
        sample.stop(Timer.builder("http.request.duration")
                .tag("uri", uri)
                .tag("method", method)
                .tag("status", String.valueOf(status))
                .register(meterRegistry));
        
        // 记录处理时间直方图
        meterRegistry.timer("http.request.duration.histogram", 
                "uri", uri, 
                "method", method, 
                "status", String.valueOf(status))
                .record(duration, TimeUnit.NANOSECONDS);
    }
    
    /**
     * 记录业务操作
     * @param operation 操作名称
     * @param success 是否成功
     * @param duration 操作时间（纳秒）
     */
    public void recordBusinessOperation(String operation, boolean success, long duration) {
        // 记录操作计数
        meterRegistry.counter("business.operations", 
                "operation", operation, 
                "success", String.valueOf(success))
                .increment();
        
        // 记录操作处理时间
        meterRegistry.timer("business.operation.duration", 
                "operation", operation, 
                "success", String.valueOf(success))
                .record(duration, TimeUnit.NANOSECONDS);
    }
    
    /**
     * 获取QPS（每秒查询数）
     * @param uri 请求URI（可选）
     * @return QPS值
     */
    public double getQps(String uri) {
        // 这里应该从Micrometer获取实际的指标数据
        // 简化实现，返回模拟数据
        return 100.0;
    }
    
    /**
     * 获取平均响应时间
     * @param uri 请求URI（可选）
     * @return 平均响应时间（毫秒）
     */
    public double getAverageResponseTime(String uri) {
        // 这里应该从Micrometer获取实际的指标数据
        // 简化实现，返回模拟数据
        return 50.0;
    }
    
    /**
     * 获取错误率
     * @param uri 请求URI（可选）
     * @return 错误率（百分比）
     */
    public double getErrorRate(String uri) {
        // 这里应该从Micrometer获取实际的指标数据
        // 简化实现，返回模拟数据
        return 0.5;
    }
}