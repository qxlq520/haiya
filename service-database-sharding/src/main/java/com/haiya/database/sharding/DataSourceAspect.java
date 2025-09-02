package com.haiya.database.sharding;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源切面
 * 根据方法名自动切换读写数据源
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {

    /**
     * 读操作切点
     * 匹配所有以select、get、query、find、count、list开头的方法
     */
    @Pointcut("execution(* com.haiya..*.service.*.select*(..)) || " +
              "execution(* com.haiya..*.service.*.get*(..)) || " +
              "execution(* com.haiya..*.service.*.query*(..)) || " +
              "execution(* com.haiya..*.service.*.find*(..)) || " +
              "execution(* com.haiya..*.service.*.count*(..)) || " +
              "execution(* com.haiya..*.service.*.list*(..))")
    public void readOperation() {}

    /**
     * 写操作切点
     * 匹配所有以insert、update、delete、add、create、save、remove、modify开头的方法
     */
    @Pointcut("execution(* com.haiya..*.service.*.insert*(..)) || " +
              "execution(* com.haiya..*.service.*.update*(..)) || " +
              "execution(* com.haiya..*.service.*.delete*(..)) || " +
              "execution(* com.haiya..*.service.*.add*(..)) || " +
              "execution(* com.haiya..*.service.*.create*(..)) || " +
              "execution(* com.haiya..*.service.*.save*(..)) || " +
              "execution(* com.haiya..*.service.*.remove*(..)) || " +
              "execution(* com.haiya..*.service.*.modify*(..))")
    public void writeOperation() {}

    /**
     * 读操作环绕通知
     */
    @Around("readOperation()")
    public Object aroundReadOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 检查方法上是否有MasterOnly注解
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            
            if (method.isAnnotationPresent(MasterOnly.class)) {
                // 如果有MasterOnly注解，强制使用主库
                DataSourceContextHolder.setDataSourceType("master");
            } else {
                // 否则使用从库
                DataSourceContextHolder.setDataSourceType("slave");
            }
            
            return joinPoint.proceed();
        } finally {
            // 清除数据源设置
            DataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 写操作环绕通知
     */
    @Around("writeOperation()")
    public Object aroundWriteOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 设置为主库数据源
            DataSourceContextHolder.setDataSourceType("master");
            return joinPoint.proceed();
        } finally {
            // 清除数据源设置
            DataSourceContextHolder.clearDataSourceType();
        }
    }
}