# 海牙平台服务注册中心

## 简介

服务注册中心是海牙平台的核心组件之一，基于Netflix Eureka构建，提供了服务注册与发现功能。此外，它还扩展了微服务治理、容器部署管理、健康检查和监控统计等功能。

## 功能特性

1. **服务注册与发现**
   - 微服务启动时自动注册到注册中心
   - 支持服务发现和调用

2. **微服务治理**
   - 负载均衡配置
   - 熔断器配置
   - 限流配置
   - 服务发现配置
   - 监控配置

3. **容器部署管理**
   - 容器化服务部署
   - 部署状态管理
   - 服务扩缩容

4. **健康检查与监控**
   - 服务健康状态检查
   - 系统资源使用情况监控
   - 服务调用统计

## API接口

详细API文档请参考: [服务注册API文档](src/main/resources/docs/service-registration-api.md)

## 核心组件

### 1. ServiceRegistryApplication
服务注册中心主应用类，启动Eureka服务器。

### 2. ServiceRegistryController
提供RESTful API接口，处理服务注册、部署和监控请求。

### 3. ArchitectureService
技术架构服务接口，定义了服务治理和容器部署的核心方法。

### 4. ServiceRegistryServiceImpl
技术架构服务实现类，提供具体的服务治理和容器部署功能。

## 配置说明

### application.yml
```yaml
server:
  port: 8761

spring:
  application:
    name: service-registry

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  server:
    wait-time-in-ms-when-sync-empty: 0
    enable-self-preservation: false
```

## 启动方式

### Maven方式启动
```bash
cd haiya-backend/service-registry
mvn spring-boot:run
```

### Jar包方式启动
```bash
cd haiya-backend/service-registry
mvn clean package
java -jar target/service-registry-1.0.0.jar
```

## 访问地址

- **Eureka控制台**: http://localhost:8761
- **服务注册API**: http://localhost:8761/api/registry

## 服务注册示例

### 微服务注册配置示例
在其他微服务的`application.yml`中添加以下配置：

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
    ip-address: localhost
```

## 开发指南

1. **添加新的服务治理功能**
   - 在`ServiceGovernance`实体类中添加新的配置项
   - 更新`ArchitectureService`接口和实现类

2. **扩展容器部署功能**
   - 在`ContainerDeployment`实体类中添加新的配置项
   - 更新相关服务方法

3. **添加新的监控指标**
   - 在`getSystemResourceUsage`方法中添加新的指标
   - 在`getServiceCallStatistics`方法中添加新的统计数据

## 注意事项

1. 服务注册中心需要首先启动，其他微服务才能正常注册
2. 确保网络连接正常，各服务能够访问注册中心
3. 在生产环境中，建议部署多个注册中心实例以确保高可用性
# 注册中心 (service-registry)

  服务概述

注册中心是海牙平台的服务注册与发现中心，基于Netflix Eureka实现，负责管理所有微服务的注册、发现和健康监控，是微服务架构的核心基础设施组件。

  功能特性

1. 服务注册与发现
2. 服务健康监控
3. 服务负载均衡支持
4. 服务元数据管理
5. 高可用集群部署
6. REST API管理接口
7. 可视化管理界面
8. 安全访问控制

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Netflix Eureka Server
- Spring Security

  核心功能

 # 服务注册
微服务启动时自动向注册中心注册自己的信息，包括：
- 服务名称
- IP地址和端口
- 健康检查URL
- 元数据信息

 # 服务发现
客户端可以通过注册中心发现可用的服务实例，并进行负载均衡调用。

 # 健康监控
注册中心持续监控已注册服务的健康状态，及时剔除不健康的服务实例。

  管理界面

注册中心提供Web管理界面，可以查看：
- 已注册的服务列表
- 每个服务的实例信息
- 服务的健康状态
- 系统状态信息

访问地址：`http://localhost:8761`

  API 接口

 # 服务管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/eureka/apps` | GET | 获取所有应用信息 |
| `/eureka/apps/{appName}` | GET | 获取指定应用信息 |
| `/eureka/apps/{appName}/{instanceId}` | GET | 获取指定实例信息 |
| `/eureka/apps/{appName}` | POST | 注册新应用 |
| `/eureka/apps/{appName}/{instanceId}` | PUT | 更新实例状态 |
| `/eureka/apps/{appName}/{instanceId}` | DELETE | 注销应用实例 |

 # 系统状态接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/actuator/health` | GET | 系统健康检查 |
| `/actuator/info` | GET | 系统信息 |
| `/actuator/metrics` | GET | 系统指标 |

  集群部署

注册中心支持集群部署以提高可用性：

 # 集群配置示例
```yaml
server:
  port: 8761

spring:
  application:
    name: service-registry

eureka:
  instance:
    hostname: registry1
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://registry2:8762/eureka/,http://registry3:8763/eureka/
  server:
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 5000
```

```yaml
server:
  port: 8762

spring:
  application:
    name: service-registry

eureka:
  instance:
    hostname: registry2
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://registry1:8761/eureka/,http://registry3:8763/eureka/
```

```yaml
server:
  port: 8763

spring:
  application:
    name: service-registry

eureka:
  instance:
    hostname: registry3
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://registry1:8761/eureka/,http://registry2:8762/eureka/
```

  安全配置

 # HTTP Basic认证配置
```yaml
spring:
  security:
    user:
      name: ${REGISTRY_USER:registry_admin}
      password: ${REGISTRY_PASSWORD:registry_password}

eureka:
  client:
    service-url:
      defaultZone: http://${REGISTRY_USER:registry_admin}:${REGISTRY_PASSWORD:registry_password}@localhost:8761/eureka/
```

 # 安全配置类
```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/eureka/**").authenticated()
            .and()
            .httpBasic();
    }
}
```

  客户端配置

 # 服务提供者配置
```yaml
spring:
  application:
    name: service-user

server:
  port: 8080

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    registry-fetch-interval-seconds: 30
  instance:
    lease-renewal-interval-in-seconds: 30
    lease-expiration-duration-in-seconds: 90
    instance-id: ${spring.application.name}:${server.port}
    prefer-ip-address: true
```

 # 服务消费者配置
```yaml
spring:
  application:
    name: service-video

server:
  port: 8081

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    registry-fetch-interval-seconds: 30
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8761

spring:
  application:
    name: service-registry

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  server:
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 5000

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

 # 启动服务

```bash
cd service-registry
mvn spring-boot:run
```

或

```bash
cd service-registry
mvn clean package
java -jar target/service-registry-1.0.0.jar
```

  高级配置

 # 心跳检测配置
```yaml
eureka:
  instance:
    lease-renewal-interval-in-seconds: 30        # 心跳间隔
    lease-expiration-duration-in-seconds: 90     # 失效时间
  server:
    enable-self-preservation: false              # 关闭自我保护
    eviction-interval-timer-in-ms: 5000          # 清理间隔
```

 # 网络超时配置
```yaml
eureka:
  server:
    response-cache-update-interval-ms: 3000     # 响应缓存更新间隔
    response-cache-auto-expiration-in-seconds: 180 # 响应缓存过期时间
  client:
    registry-fetch-interval-seconds: 30          # 获取注册信息间隔
    instance-info-replication-interval-seconds: 30 # 实例信息复制间隔
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8761/actuator/health
```

Eureka状态检查：

```
GET http://localhost:8761/eureka/status
```

  最佳实践

 # 1. 高可用部署
- 部署至少3个注册中心实例形成集群
- 使用负载均衡器对外提供统一入口
- 配置合适的健康检查参数

 # 2. 安全防护
- 启用HTTP Basic认证
- 使用HTTPS加密通信
- 限制管理接口访问

 # 3. 性能优化
- 合理配置心跳间隔和超时时间
- 启用响应缓存
- 监控和调优JVM参数