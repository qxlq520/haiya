package com.haiya.registry.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 容器化部署配置实体类
 */
public class ContainerDeployment {
    /**
     * 部署ID
     */
    private Long id;
    
    /**
     * 服务名称
     */
    private String serviceName;
    
    /**
     * 镜像名称
     */
    private String imageName;
    
    /**
     * 镜像标签
     */
    private String imageTag;
    
    /**
     * 部署环境
     */
    private DeploymentEnvironment environment;
    
    /**
     * 容器配置
     */
    private ContainerConfig containerConfig;
    
    /**
     * 部署策略
     */
    private DeploymentStrategy strategy;
    
    /**
     * 资源限制
     */
    private ResourceLimit resourceLimit;
    
    /**
     * 环境变量
     */
    private Map<String, String> environmentVariables;
    
    /**
     * 挂载卷配置
     */
    private List<VolumeMount> volumeMounts;
    
    /**
     * 健康检查配置
     */
    private HealthCheckConfig healthCheck;
    
    /**
     * 网络配置
     */
    private NetworkConfig network;
    
    /**
     * 部署状态
     */
    private DeploymentStatus status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getImageName() {
        return imageName;
    }
    
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    public String getImageTag() {
        return imageTag;
    }
    
    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }
    
    public DeploymentEnvironment getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(DeploymentEnvironment environment) {
        this.environment = environment;
    }
    
    public ContainerConfig getContainerConfig() {
        return containerConfig;
    }
    
    public void setContainerConfig(ContainerConfig containerConfig) {
        this.containerConfig = containerConfig;
    }
    
    public DeploymentStrategy getStrategy() {
        return strategy;
    }
    
    public void setStrategy(DeploymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    public ResourceLimit getResourceLimit() {
        return resourceLimit;
    }
    
    public void setResourceLimit(ResourceLimit resourceLimit) {
        this.resourceLimit = resourceLimit;
    }
    
    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }
    
    public void setEnvironmentVariables(Map<String, String> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }
    
    public List<VolumeMount> getVolumeMounts() {
        return volumeMounts;
    }
    
    public void setVolumeMounts(List<VolumeMount> volumeMounts) {
        this.volumeMounts = volumeMounts;
    }
    
    public HealthCheckConfig getHealthCheck() {
        return healthCheck;
    }
    
    public void setHealthCheck(HealthCheckConfig healthCheck) {
        this.healthCheck = healthCheck;
    }
    
    public NetworkConfig getNetwork() {
        return network;
    }
    
    public void setNetwork(NetworkConfig network) {
        this.network = network;
    }
    
    public DeploymentStatus getStatus() {
        return status;
    }
    
    public void setStatus(DeploymentStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * 部署环境枚举
     */
    public enum DeploymentEnvironment {
        DEVELOPMENT,  // 开发环境
        TESTING,      // 测试环境
        STAGING,      // 预发布环境
        PRODUCTION    // 生产环境
    }
    
    /**
     * 部署策略枚举
     */
    public enum DeploymentStrategy {
        RECREATE,     // 重新创建
        ROLLING,      // 滚动更新
        BLUE_GREEN    // 蓝绿部署
    }
    
    /**
     * 部署状态枚举
     */
    public enum DeploymentStatus {
        PENDING,      // 待部署
        DEPLOYING,    // 部署中
        RUNNING,      // 运行中
        STOPPED,      // 已停止
        FAILED        // 部署失败
    }
    
    /**
     * 容器配置类
     */
    public static class ContainerConfig {
        private List<String> command; // 启动命令
        private List<String> args; // 启动参数
        private Integer replicas; // 副本数
        
        public List<String> getCommand() {
            return command;
        }
        
        public void setCommand(List<String> command) {
            this.command = command;
        }
        
        public List<String> getArgs() {
            return args;
        }
        
        public void setArgs(List<String> args) {
            this.args = args;
        }
        
        public Integer getReplicas() {
            return replicas;
        }
        
        public void setReplicas(Integer replicas) {
            this.replicas = replicas;
        }
    }
    
    /**
     * 资源限制类
     */
    public static class ResourceLimit {
        private String cpuRequest; // CPU请求
        private String cpuLimit; // CPU限制
        private String memoryRequest; // 内存请求
        private String memoryLimit; // 内存限制
        
        public String getCpuRequest() {
            return cpuRequest;
        }
        
        public void setCpuRequest(String cpuRequest) {
            this.cpuRequest = cpuRequest;
        }
        
        public String getCpuLimit() {
            return cpuLimit;
        }
        
        public void setCpuLimit(String cpuLimit) {
            this.cpuLimit = cpuLimit;
        }
        
        public String getMemoryRequest() {
            return memoryRequest;
        }
        
        public void setMemoryRequest(String memoryRequest) {
            this.memoryRequest = memoryRequest;
        }
        
        public String getMemoryLimit() {
            return memoryLimit;
        }
        
        public void setMemoryLimit(String memoryLimit) {
            this.memoryLimit = memoryLimit;
        }
    }
    
    /**
     * 挂载卷配置类
     */
    public static class VolumeMount {
        private String name; // 卷名称
        private String mountPath; // 挂载路径
        private VolumeType type; // 卷类型
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getMountPath() {
            return mountPath;
        }
        
        public void setMountPath(String mountPath) {
            this.mountPath = mountPath;
        }
        
        public VolumeType getType() {
            return type;
        }
        
        public void setType(VolumeType type) {
            this.type = type;
        }
        
        /**
         * 卷类型枚举
         */
        public enum VolumeType {
            HOST_PATH,    // 主机路径
            PERSISTENT,   // 持久卷
            CONFIG_MAP,   // 配置映射
            SECRET        // 密钥
        }
    }
    
    /**
     * 健康检查配置类
     */
    public static class HealthCheckConfig {
        private Boolean livenessProbeEnabled; // 存活探针启用
        private Boolean readinessProbeEnabled; // 就绪探针启用
        private String probeType; // 探针类型 (HTTP/TCP/COMMAND)
        private String probePath; // 探针路径
        private Integer initialDelaySeconds; // 初始延迟(秒)
        private Integer periodSeconds; // 检查周期(秒)
        
        public Boolean getLivenessProbeEnabled() {
            return livenessProbeEnabled;
        }
        
        public void setLivenessProbeEnabled(Boolean livenessProbeEnabled) {
            this.livenessProbeEnabled = livenessProbeEnabled;
        }
        
        public Boolean getReadinessProbeEnabled() {
            return readinessProbeEnabled;
        }
        
        public void setReadinessProbeEnabled(Boolean readinessProbeEnabled) {
            this.readinessProbeEnabled = readinessProbeEnabled;
        }
        
        public String getProbeType() {
            return probeType;
        }
        
        public void setProbeType(String probeType) {
            this.probeType = probeType;
        }
        
        public String getProbePath() {
            return probePath;
        }
        
        public void setProbePath(String probePath) {
            this.probePath = probePath;
        }
        
        public Integer getInitialDelaySeconds() {
            return initialDelaySeconds;
        }
        
        public void setInitialDelaySeconds(Integer initialDelaySeconds) {
            this.initialDelaySeconds = initialDelaySeconds;
        }
        
        public Integer getPeriodSeconds() {
            return periodSeconds;
        }
        
        public void setPeriodSeconds(Integer periodSeconds) {
            this.periodSeconds = periodSeconds;
        }
    }
    
    /**
     * 网络配置类
     */
    public static class NetworkConfig {
        private String serviceType; // 服务类型 (ClusterIP/NodePort/LoadBalancer)
        private List<PortMapping> ports; // 端口映射
        private List<String> exposedPorts; // 暴露端口
        
        public String getServiceType() {
            return serviceType;
        }
        
        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }
        
        public List<PortMapping> getPorts() {
            return ports;
        }
        
        public void setPorts(List<PortMapping> ports) {
            this.ports = ports;
        }
        
        public List<String> getExposedPorts() {
            return exposedPorts;
        }
        
        public void setExposedPorts(List<String> exposedPorts) {
            this.exposedPorts = exposedPorts;
        }
        
        /**
         * 端口映射类
         */
        public static class PortMapping {
            private Integer port; // 服务端口
            private Integer targetPort; // 容器端口
            private String protocol; // 协议 (TCP/UDP)
            
            public Integer getPort() {
                return port;
            }
            
            public void setPort(Integer port) {
                this.port = port;
            }
            
            public Integer getTargetPort() {
                return targetPort;
            }
            
            public void setTargetPort(Integer targetPort) {
                this.targetPort = targetPort;
            }
            
            public String getProtocol() {
                return protocol;
            }
            
            public void setProtocol(String protocol) {
                this.protocol = protocol;
            }
        }
    }
}