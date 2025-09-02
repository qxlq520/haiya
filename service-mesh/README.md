# 服务网格服务 (service-mesh)

## 服务概述

服务网格服务是海牙平台微服务架构的核心基础设施，负责服务间通信管理、流量控制、安全控制、可观察性和服务治理等功能。

## 功能特性

1. 服务发现与注册
2. 负载均衡
3. 流量控制与路由
4. 服务间安全通信
5. 熔断与降级
6. 服务监控与追踪
7. 配置管理
8. 服务治理策略
9. 消息队列集成（RabbitMQ/Kafka）
10. 异步处理支持
11. 事件驱动架构

## 技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Cloud Gateway
- Spring Cloud LoadBalancer
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Resilience4j 熔断器
- Micrometer 监控指标
- RabbitMQ 消息队列
- Apache Kafka 消息流平台

## API 接口

### 服务管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/mesh/services` | GET | 获取服务列表 |
| `/api/mesh/services` | POST | 注册服务 |
| `/api/mesh/services/{id}` | GET | 获取服务详情 |
| `/api/mesh/services/{id}` | PUT | 更新服务信息 |
| `/api/mesh/services/{id}` | DELETE | 注销服务 |

### 路由配置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/mesh/routes` | GET | 获取路由列表 |
| `/api/mesh/routes` | POST | 创建路由规则 |
| `/api/mesh/routes/{id}` | GET | 获取路由详情 |
| `/api/mesh/routes/{id}` | PUT | 更新路由规则 |
| `/api/mesh/routes/{id}` | DELETE | 删除路由规则 |

### 熔断配置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/mesh/circuit-breakers` | GET | 获取熔断器列表 |
| `/api/mesh/circuit-breakers` | POST | 创建熔断器配置 |
| `/api/mesh/circuit-breakers/{id}` | GET | 获取熔断器详情 |
| `/api/mesh/circuit-breakers/{id}` | PUT | 更新熔断器配置 |
| `/api/mesh/circuit-breakers/{id}` | DELETE | 删除熔断器配置 |

### 流量控制接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/mesh/rate-limiters` | GET | 获取限流器列表 |
| `/api/mesh/rate-limiters` | POST | 创建限流器配置 |
| `/api/mesh/rate-limiters/{id}` | GET | 获取限流器详情 |
| `/api/mesh/rate-limiters/{id}` | PUT | 更新限流器配置 |
| `/api/mesh/rate-limiters/{id}` | DELETE | 删除限流器配置 |

### 安全配置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/mesh/security-policies` | GET | 获取安全策略列表 |
| `/api/mesh/security-policies` | POST | 创建安全策略 |
| `/api/mesh/security-policies/{id}` | GET | 获取安全策略详情 |
| `/api/mesh/security-policies/{id}` | PUT | 更新安全策略 |
| `/api/mesh/security-policies/{id}` | DELETE | 删除安全策略 |

### 服务调用接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/mesh/call` | POST | 同步服务调用 |
| `/api/mesh/call/async` | POST | 异步服务调用 |
| `/api/mesh/health` | GET | 健康检查 |

## 数据库设计

### mesh_services 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_id | VARCHAR(100) | 服务ID |
| service_name | VARCHAR(100) | 服务名称 |
| version | VARCHAR(20) | 版本 |
| instances | TEXT | 实例列表（JSON格式） |
| status | VARCHAR(20) | 状态（UP, DOWN, OUT_OF_SERVICE） |
| health_url | VARCHAR(500) | 健康检查URL |
| last_heartbeat | BIGINT | 最后心跳时间 |
| metadata | TEXT | 元数据（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

### mesh_routes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| route_id | VARCHAR(100) | 路由ID |
| path | VARCHAR(255) | 路径模式 |
| service_id | VARCHAR(100) | 目标服务ID |
| predicates | TEXT | 断言条件（JSON格式） |
| filters | TEXT | 过滤器（JSON格式） |
| order | INT | 顺序 |
| enabled | BOOLEAN | 是否启用 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

### mesh_circuit_breakers 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_id | VARCHAR(100) | 服务ID |
| failure_rate_threshold | DECIMAL(5,2) | 失败率阈值 |
| slow_call_rate_threshold | DECIMAL(5,2) | 慢调用率阈值 |
| wait_duration_in_open_state | INT | 打开状态等待时间（毫秒） |
| permitted_number_of_calls_in_half_open_state | INT | 半开状态允许调用数 |
| sliding_window_size | INT | 滑动窗口大小 |
| minimum_number_of_calls | INT | 最小调用数 |
| enabled | BOOLEAN | 是否启用 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

### mesh_rate_limiters 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_id | VARCHAR(100) | 服务ID |
| limit_type | VARCHAR(20) | 限流类型（REQUEST, CONCURRENT） |
| limit_for_period | INT | 周期内限制数 |
| limit_refresh_period | INT | 限制刷新周期（毫秒） |
| timeout_duration | INT | 超时时间（毫秒） |
| enabled | BOOLEAN | 是否启用 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

### mesh_security_policies 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| policy_name | VARCHAR(100) | 策略名称 |
| service_id | VARCHAR(100) | 服务ID |
| policy_type | VARCHAR(20) | 策略类型（AUTHENTICATION, AUTHORIZATION, ENCRYPTION） |
| config | TEXT | 配置（JSON格式） |
| enabled | BOOLEAN | 是否启用 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

## Redis 缓存设计

### 服务信息缓存
```
mesh:services -> Set结构存储所有服务ID
mesh:service:{serviceId} -> Hash结构存储服务详细信息
```

### 路由配置缓存
```
mesh:routes -> List结构存储所有路由规则
mesh:route:{routeId} -> String结构存储路由规则详情
```

### 熔断器状态缓存
```
mesh:circuit-breaker:{serviceId}:state -> String结构存储熔断器状态
```

## RabbitMQ 消息队列设计

### 服务网格事件交换机
```
Exchange: mesh.exchange
  - Type: Direct
  - Routing Key: mesh.event
```

### 服务网格事件队列
```
Queue: mesh.event.queue
```

## Kafka 消息流设计

### 服务网格事件主题
```
Topic: mesh.events
  - 分区: 6
  - 副本: 3
```

## 部署说明

### 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- RabbitMQ 消息队列
- Apache Kafka 消息流平台
- Resilience4j 1.7.x

### 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8122

spring:
  application:
    name: service-mesh
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_mesh
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379
    
  rabbitmq:
    host: localhost
    port: 5672
    username: your_username
    password: your_password
    
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: mesh-group
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

mesh:
  gateway:
    routes:
      - id: service-user
        uri: lb://service-user
        predicates:
          - Path=/api/users/**
      - id: service-video
        uri: lb://service-video
        predicates:
          - Path=/api/videos/**
```

### 启动服务

```bash
cd service-mesh
mvn spring-boot:run
```

或

```bash
cd service-mesh
mvn clean package
java -jar target/service-mesh-1.0.0.jar
```

## 服务依赖

- service-registry (服务注册中心)
- service-gateway (网关服务)
- service-user (用户服务)
- service-video (视频服务)
- 其他所有业务服务

## 开发指南

### 项目结构

```
src/main/java/com/haiya/mesh
├── ServiceMeshApplication.java     # 应用入口
├── config/                         # 配置类
│   ├── MeshConfig.java             # 网格配置
│   ├── AsyncConfig.java            # 异步配置
│   └── SecurityConfig.java         # 安全配置
├── controller/                     # 控制器层
│   ├── MeshController.java         # 网格控制器
│   ├── ServiceController.java      # 服务控制器
│   ├── RouteController.java        # 路由控制器
│   ├── CircuitBreakerController.java # 熔断器控制器
│   ├── RateLimiterController.java  # 限流器控制器
│   └── SecurityPolicyController.java # 安全策略控制器
├── dto/                            # 数据传输对象
│   ├── ServiceCallDTO.java         # 服务调用DTO
│   └── ServiceResponseDTO.java     # 服务响应DTO
├── entity/                         # 实体类
│   ├── MeshService.java            # 网格服务实体
│   ├── Route.java                  # 路由实体
│   ├── CircuitBreaker.java         # 熔断器实体
│   ├── RateLimiter.java            # 限流器实体
│   └── SecurityPolicy.java         # 安全策略实体
├── repository/                     # 数据访问层
│   ├── MeshServiceRepository.java  # 网格服务数据访问
│   ├── RouteRepository.java        # 路由数据访问
│   ├── CircuitBreakerRepository.java # 熔断器数据访问
│   ├── RateLimiterRepository.java  # 限流器数据访问
│   └── SecurityPolicyRepository.java # 安全策略数据访问
├── service/                        # 业务逻辑层
│   ├── MeshService.java            # 网格核心服务
│   ├── EventService.java           # 事件处理服务
│   ├── ServiceManagementService.java # 服务管理服务
│   ├── RouteService.java           # 路由服务
│   ├── CircuitBreakerService.java  # 熔断器服务
│   ├── RateLimiterService.java     # 限流器服务
│   └── SecurityPolicyService.java  # 安全策略服务
└── aspect/                         # 切面
    └── MeshAspect.java             # 网格切面
```

## 测试

运行单元测试：

```bash
mvn test
```

## 监控与健康检查

服务健康检查端点：

```
GET http://localhost:8122/actuator/health
```

## 核心功能说明

### 流量控制和熔断机制

服务网格通过Resilience4j实现流量控制和熔断机制，包括：

1. 熔断器：当服务失败率达到阈值时自动熔断，防止故障扩散
2. 限流器：控制服务调用频率，防止服务过载
3. 重试机制：对失败的请求进行重试，提高成功率
4. 超时控制：设置请求超时时间，防止长时间阻塞

### 服务降级和限流策略

1. 服务降级：当服务不可用时，返回预设的降级数据
2. 限流策略：基于令牌桶算法实现请求限流
3. 自适应限流：根据系统负载动态调整限流阈值

### 异步处理和事件驱动架构

1. 异步调用：通过线程池实现服务调用异步化，提高接口响应速度
2. 消息队列：集成RabbitMQ和Kafka，实现服务间解耦
3. 事件驱动：通过事件机制实现服务间通信，降低耦合度

### 服务间通信优化

1. 连接池：复用HTTP连接，减少连接建立开销
2. 缓存机制：缓存服务注册信息，减少网络调用
3. 负载均衡：智能负载均衡算法，优化服务调用路径