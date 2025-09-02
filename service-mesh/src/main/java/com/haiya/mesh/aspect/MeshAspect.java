package com.haiya.mesh.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MeshAspect {

    private static final Logger logger = LoggerFactory.getLogger(MeshAspect.class);

    /**
     * 环绕通知，用于监控服务调用执行时间
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("execution(* com.haiya.mesh.service.MeshService.*(..))")
    public Object monitorServiceCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        
        try {
            logger.debug("开始执行方法: {}", methodName);
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.debug("方法 {} 执行完成，耗时: {} ms", methodName, (endTime - startTime));
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("方法 {} 执行异常，耗时: {} ms，异常信息: {}", methodName, (endTime - startTime), e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 监控控制器方法执行
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("execution(* com.haiya.mesh.controller.MeshController.*(..))")
    public Object monitorControllerCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        
        try {
            logger.info("开始处理请求: {}", methodName);
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("请求 {} 处理完成，耗时: {} ms", methodName, (endTime - startTime));
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("请求 {} 处理异常，耗时: {} ms，异常信息: {}", methodName, (endTime - startTime), e.getMessage(), e);
            throw e;
        }
    }
}