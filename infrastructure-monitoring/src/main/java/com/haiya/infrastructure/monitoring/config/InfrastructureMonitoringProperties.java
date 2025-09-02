package com.haiya.infrastructure.monitoring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 基础设施监控配置属性类
 * 用于绑定infrastructure.monitoring前缀的配置属性
 */
@Component
@ConfigurationProperties(prefix = "infrastructure.monitoring")
public class InfrastructureMonitoringProperties {
    
    private final Cpu cpu = new Cpu();
    private final Memory memory = new Memory();
    private final Disk disk = new Disk();
    private final Network network = new Network();
    
    public static class Cpu {
        private boolean enabled = true;
        private final Threshold threshold = new Threshold();
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public Threshold getThreshold() {
            return threshold;
        }
    }
    
    public static class Memory {
        private boolean enabled = true;
        private final Threshold threshold = new Threshold();
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public Threshold getThreshold() {
            return threshold;
        }
    }
    
    public static class Disk {
        private boolean enabled = true;
        private final Threshold threshold = new Threshold();
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public Threshold getThreshold() {
            return threshold;
        }
    }
    
    public static class Network {
        private boolean enabled = true;
        private final NetworkThreshold threshold = new NetworkThreshold();
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public NetworkThreshold getThreshold() {
            return threshold;
        }
    }
    
    public static class Threshold {
        private int warning = 80;
        private int critical = 90;
        
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
    
    public static class NetworkThreshold {
        private final Latency latency = new Latency();
        private final PacketLoss packetLoss = new PacketLoss();
        
        public Latency getLatency() {
            return latency;
        }
        
        public PacketLoss getPacketLoss() {
            return packetLoss;
        }
    }
    
    public static class Latency {
        private int warning = 100;
        private int critical = 500;
        
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
    
    public static class PacketLoss {
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
    
    public Cpu getCpu() {
        return cpu;
    }
    
    public Memory getMemory() {
        return memory;
    }
    
    public Disk getDisk() {
        return disk;
    }
    
    public Network getNetwork() {
        return network;
    }
}