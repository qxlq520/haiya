# 服务中心注册服务API文档

## 概述

服务中心注册服务提供了一套完整的API接口，用于管理海牙平台中的微服务注册、配置和监控。通过这些API，可以实现服务治理、容器部署、健康检查等功能。

## API端点

所有API端点都以 `/api/registry` 为前缀。

### 服务治理配置

#### 1. 注册微服务治理配置
```
POST /api/registry/governance
```

**请求体:**
```json
{
  "serviceName": "user-service",
  "version": "1.0.0",
  "loadBalance": "ROUND_ROBIN",
  "circuitBreaker": {
    "enabled": true,
    "failureThreshold": 5,
    "timeoutSeconds": 30,
    "recoveryTimeoutSeconds": 60
  },
  "rateLimit": {
    "enabled": true,
    "requestsPerSecond": 100,
    "burstCapacity": 200
  },
  "discovery": {
    "enabled": true,
    "heartbeatIntervalSeconds": 30,
    "expirationSeconds": 90
  },
  "monitoring": {
    "enabled": true,
    "metrics": ["cpu", "memory", "response_time"],
    "alertEmail": "admin@example.com"
  },
  "enabled": true
}
```

**响应:**
```json
{
  "id": 1
}
```

#### 2. 获取服务治理配置
```
GET /api/registry/governance/{serviceName}
```

**响应:**
```json
{
  "id": 1,
  "serviceName": "user-service",
  "version": "1.0.0",
  "loadBalance": "ROUND_ROBIN",
  "circuitBreaker": {
    "enabled": true,
    "failureThreshold": 5,
    "timeoutSeconds": 30,
    "recoveryTimeoutSeconds": 60
  },
  "rateLimit": {
    "enabled": true,
    "requestsPerSecond": 100,
    "burstCapacity": 200
  },
  "discovery": {
    "enabled": true,
    "heartbeatIntervalSeconds": 30,
    "expirationSeconds": 90
  },
  "monitoring": {
    "enabled": true,
    "metrics": ["cpu", "memory", "response_time"],
    "alertEmail": "admin@example.com"
  },
  "enabled": true,
  "createdAt": "2023-01-01T10:00:00",
  "updatedAt": "2023-01-01T10:00:00"
}
```

#### 3. 更新服务治理配置
```
PUT /api/registry/governance/{id}
```

**请求体:**
```json
{
  "serviceName": "user-service",
  "version": "1.0.1",
  "loadBalance": "LEAST_CONNECTIONS",
  "circuitBreaker": {
    "enabled": true,
    "failureThreshold": 3,
    "timeoutSeconds": 20,
    "recoveryTimeoutSeconds": 30
  },
  "rateLimit": {
    "enabled": true,
    "requestsPerSecond": 200,
    "burstCapacity": 400
  },
  "discovery": {
    "enabled": true,
    "heartbeatIntervalSeconds": 15,
    "expirationSeconds": 45
  },
  "monitoring": {
    "enabled": true,
    "metrics": ["cpu", "memory", "response_time", "throughput"],
    "alertEmail": "ops@example.com"
  },
  "enabled": true
}
```

**响应:**
```json
true
```

### 容器部署管理

#### 1. 部署容器化服务
```
POST /api/registry/deployment
```

**请求体:**
```json
{
  "serviceName": "user-service",
  "imageName": "haiya/user-service",
  "imageTag": "v1.0.0",
  "environment": "PRODUCTION",
  "containerConfig": {
    "replicas": 3,
    "ports": [8081],
    "command": ["java", "-jar", "app.jar"]
  },
  "strategy": "ROLLING_UPDATE",
  "resourceLimit": {
    "cpu": "500m",
    "memory": "512Mi"
  },
  "environmentVariables": {
    "SPRING_PROFILES_ACTIVE": "prod",
    "JAVA_OPTS": "-Xmx512m"
  },
  "volumeMounts": [
    {
      "name": "logs",
      "mountPath": "/app/logs"
    }
  ],
  "healthCheck": {
    "enabled": true,
    "path": "/actuator/health",
    "port": 8081,
    "initialDelaySeconds": 30,
    "periodSeconds": 10
  },
  "network": {
    "exposePorts": [8081],
    "ingressEnabled": true
  }
}
```

**响应:**
```json
{
  "id": 1
}
```

#### 2. 获取容器部署列表
```
GET /api/registry/deployment?environment=PRODUCTION
```

**响应:**
```json
[
  {
    "id": 1,
    "serviceName": "user-service",
    "imageName": "haiya/user-service",
    "imageTag": "v1.0.0",
    "environment": "PRODUCTION",
    "containerConfig": {
      "replicas": 3,
      "ports": [8081],
      "command": ["java", "-jar", "app.jar"]
    },
    "strategy": "ROLLING_UPDATE",
    "resourceLimit": {
      "cpu": "500m",
      "memory": "512Mi"
    },
    "environmentVariables": {
      "SPRING_PROFILES_ACTIVE": "prod",
      "JAVA_OPTS": "-Xmx512m"
    },
    "volumeMounts": [
      {
        "name": "logs",
        "mountPath": "/app/logs"
      }
    ],
    "healthCheck": {
      "enabled": true,
      "path": "/actuator/health",
      "port": 8081,
      "initialDelaySeconds": 30,
      "periodSeconds": 10
    },
    "network": {
      "exposePorts": [8081],
      "ingressEnabled": true
    },
    "status": "RUNNING",
    "createdAt": "2023-01-01T10:00:00",
    "updatedAt": "2023-01-01T10:00:00"
  }
]
```

#### 3. 更新容器部署状态
```
PUT /api/registry/deployment/{deploymentId}/status?status=STOPPED
```

**响应:**
```json
true
```

#### 4. 扩缩容服务
```
PUT /api/registry/deployment/{deploymentId}/scale?replicas=5
```

**响应:**
```json
true
```

### 服务监控和健康检查

#### 1. 获取服务健康状态
```
GET /api/registry/health/{serviceName}
```

**响应:**
```json
"UP"
```

#### 2. 获取系统资源使用情况
```
GET /api/registry/system/resources
```

**响应:**
```json
{
  "cpuUsage": "45%",
  "memoryUsage": "60%",
  "diskUsage": "70%",
  "networkIn": "100MB/s",
  "networkOut": "150MB/s"
}
```

#### 3. 获取服务调用统计
```
GET /api/registry/statistics/{serviceName}?duration=60
```

**响应:**
```json
{
  "serviceName": "user-service",
  "duration": "60分钟",
  "totalCalls": 5421,
  "successRate": "99.8%",
  "averageResponseTime": "45ms"
}
```

## 错误处理

所有API在发生错误时都会返回相应的HTTP状态码和错误信息:

- `400 Bad Request`: 请求参数错误
- `404 Not Found`: 资源未找到
- `500 Internal Server Error`: 服务器内部错误

## 使用示例

### 注册和配置用户服务

1. 首先注册服务治理配置:
```bash
curl -X POST http://localhost:8761/api/registry/governance \
  -H "Content-Type: application/json" \
  -d '{
    "serviceName": "user-service",
    "version": "1.0.0",
    "loadBalance": "ROUND_ROBIN",
    "circuitBreaker": {
      "enabled": true,
      "failureThreshold": 5
    },
    "rateLimit": {
      "enabled": true,
      "requestsPerSecond": 100
    },
    "discovery": {
      "enabled": true
    },
    "monitoring": {
      "enabled": true
    },
    "enabled": true
  }'
```

2. 部署容器化服务:
```bash
curl -X POST http://localhost:8761/api/registry/deployment \
  -H "Content-Type: application/json" \
  -d '{
    "serviceName": "user-service",
    "imageName": "haiya/user-service",
    "imageTag": "v1.0.0",
    "environment": "PRODUCTION",
    "containerConfig": {
      "replicas": 3,
      "ports": [8081]
    },
    "strategy": "ROLLING_UPDATE",
    "resourceLimit": {
      "cpu": "500m",
      "memory": "512Mi"
    }
  }'
```

3. 检查服务健康状态:
```bash
curl http://localhost:8761/api/registry/health/user-service
```

## 注意事项

1. 所有时间字段使用ISO 8601格式
2. 服务名称必须唯一
3. 部署ID和配置ID由系统自动生成
4. 容器部署状态包括: PENDING, RUNNING, STOPPED, FAILED
5. 部署环境包括: DEVELOPMENT, TESTING, STAGING, PRODUCTION