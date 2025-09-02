package com.haiya.infrastructure.monitoring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用性能监控配置属性类
 * 用于绑定application.monitoring前缀的配置属性
 */
@Component
@ConfigurationProperties(prefix = "application.monitoring")
public class ApplicationMonitoringProperties {
    
    private final QpsOverride qpsOverride = new QpsOverride();
    private final ResponseTimeOverride responseTimeOverride = new ResponseTimeOverride();
    private final ErrorRate errorRateOverride = new ErrorRate();
    
    // QPS的特殊阈值设置
    public static class QpsThreshold extends Threshold {
        private int warning = 1000;
        private int critical = 5000;
        
        @Override
        public int getWarning() {
            return warning;
        }
        
        @Override
        public void setWarning(int warning) {
            this.warning = warning;
        }
        
        @Override
        public int getCritical() {
            return critical;
        }
        
        @Override
        public void setCritical(int critical) {
            this.critical = critical;
        }
    }
    
    // ResponseTime的特殊阈值设置
    public static class ResponseTimeThreshold extends Threshold {
        private int warning = 500;
        private int critical = 2000;
        
        @Override
        public int getWarning() {
            return warning;
        }
        
        @Override
        public void setWarning(int warning) {
            this.warning = warning;
        }
        
        @Override
        public int getCritical() {
            return critical;
        }
        
        @Override
        public void setCritical(int critical) {
            this.critical = critical;
        }
    }
    
    // 重新定义Qps类使用特殊阈值
    public static class QpsOverride {
        private final QpsThreshold threshold = new QpsThreshold();
        
        public QpsThreshold getThreshold() {
            return threshold;
        }
    }
    
    // 重新定义ResponseTime类使用特殊阈值
    public static class ResponseTimeOverride {
        private final ResponseTimeThreshold threshold = new ResponseTimeThreshold();
        
        public ResponseTimeThreshold getThreshold() {
            return threshold;
        }
    }
    
    public static class ErrorRate {
        private final Threshold threshold = new Threshold();
        
        public Threshold getThreshold() {
            return threshold;
        }
    }
    
    public static class Threshold {
        private int warning = 1;
        private int critical = 5;
        
        public int getWarning() {
            return warning;
        }
        
        public void setWarning(int warning) {
            this.warning = warning;
        }
        
        public int getCritical() {
            return critical;
        }
        
        public void setCritical(int critical) {
            this.critical = critical;
        }
    }
    
    public QpsOverride getQps() {
        return qpsOverride;
    }
    
    public ResponseTimeOverride getResponseTime() {
        return responseTimeOverride;
    }
    
    public ErrorRate getErrorRate() {
        return errorRateOverride;
    }
}