package com.haiya.infrastructure.monitoring.service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 系统指标监控服务
 * 负责收集基础设施监控指标（CPU、内存、磁盘、网络等）
 */
@Service
public class SystemMetricsService {
    
    private final SystemInfo systemInfo = new SystemInfo();
    
    /**
     * 获取CPU使用率
     * @return CPU使用率百分比
     */
    public double getCpuUsage() {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        
        // 等待1秒以获取准确的使用率
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
    }
    
    /**
     * 获取内存使用情况
     * @return 内存使用率百分比
     */
    public double getMemoryUsage() {
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        return (double) usedMemory / totalMemory * 100;
    }
    /**
     * 获取磁盘使用情况
     * @return 磁盘使用率百分比
     */
    public double getDiskUsage() {
        // 获取文件系统信息来计算磁盘使用情况
        List<OSFileStore> fileStores = systemInfo.getOperatingSystem().getFileSystem().getFileStores();
        long totalSpace = 0;
        long freeSpace = 0;
        
        for (OSFileStore store : fileStores) {
            totalSpace += store.getTotalSpace();
            freeSpace += store.getUsableSpace();
        }
        
        long usedSpace = totalSpace - freeSpace;
        if (totalSpace > 0) {
            return (double) usedSpace / totalSpace * 100;
        }
        return 0.0;
    }
    
    /**
     * 获取网络流量信息
     * @return 网络流量信息
     */
    public NetworkMetrics getNetworkMetrics() {
        List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();
        long totalBytesSent = 0;
        long totalBytesReceived = 0;
        
        for (NetworkIF networkIF : networkIFs) {
            totalBytesSent += networkIF.getBytesSent();
            totalBytesReceived += networkIF.getBytesRecv();
        }
        
        return new NetworkMetrics(totalBytesSent, totalBytesReceived);
    }
    
    /**
     * 获取JVM内存使用情况
     * @return JVM内存使用率百分比
     */
    public double getJvmMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        return (double) usedMemory / maxMemory * 100;
    }
    
    /**
     * 获取应用运行时间
     * @return 应用运行时间（毫秒）
     */
    public long getUptime() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getUptime();
    }
    
    /**
     * 网络指标数据类
     */
    public static class NetworkMetrics {
        private final long bytesSent;
        private final long bytesReceived;
        
        public NetworkMetrics(long bytesSent, long bytesReceived) {
            this.bytesSent = bytesSent;
            this.bytesReceived = bytesReceived;
        }
        
        public long getBytesSent() {
            return bytesSent;
        }
        
        public long getBytesReceived() {
            return bytesReceived;
        }
        
        public long getTotalBytes() {
            return bytesSent + bytesReceived;
        }
    }
}