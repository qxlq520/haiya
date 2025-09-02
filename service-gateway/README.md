# 网关服务 (service-gateway)

  服务概述

网关服务是海牙平台的API网关，基于Spring Cloud Gateway实现，负责请求路由、负载均衡、认证授权、限流熔断、日志记录等核心功能，是平台对外提供服务的统一入口。

  功能特性

1. 请求路由与负载均衡
2. 认证与授权控制
3. API限流与熔断
4. 请求日志记录
5. 请求/响应修改
6. SSL/TLS终端
7. 跨域处理
8. 动态路由配置

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Cloud Gateway
- Eureka 服务注册与发现
- Redis 缓存
- Hystrix 熔断器

  核心功能

 # 路由功能
网关根据预定义的路由规则将请求转发到相应的后端服务：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://service-user
          predicates:
            - Path=/api/users/**
          filters:
            - StripPrefix=2
```

 # 过滤器功能
网关提供多种内置过滤器和自定义过滤器：

1. **全局过滤器** - 对所有请求生效
2. **路由过滤器** - 对特定路由生效
3. **自定义过滤器** - 根据业务需求自定义

 # 限流功能
基于Redis实现分布式限流：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://service-user
          predicates:
            - Path=/api/users/**
          filters:
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
```

  API 接口

 # 网关管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/actuator/gateway/routes` | GET | 获取路由列表 |
| `/actuator/gateway/routes/{id}` | GET | 获取指定路由详情 |
| `/actuator/gateway/routes/{id}` | POST | 创建路由 |
| `/actuator/gateway/routes/{id}` | DELETE | 删除路由 |
| `/actuator/gateway/refresh` | POST | 刷新路由配置 |

 # 健康检查接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/actuator/health` | GET | 网关健康检查 |
| `/actuator/info` | GET | 网关信息 |
| `/actuator/metrics` | GET | 网关指标 |

  路由配置

 # 基础路由配置
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://service-user
          predicates:
            - Path=/api/users/**
          filters:
            - StripPrefix=2
            
        - id: video-service
          uri: lb://service-video
          predicates:
            - Path=/api/videos/**
          filters:
            - StripPrefix=2
            
        - id: comment-service
          uri: lb://service-comment
          predicates:
            - Path=/api/comments/**
          filters:
            - StripPrefix=2
```

 # 认证路由配置
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: lb://service-user
          predicates:
            - Path=/api/auth/**
          filters:
            - StripPrefix=2
            
        - id: protected-service
          uri: lb://service-user
          predicates:
            - Path=/api/users/**
          filters:
            - name: AuthFilter
            - StripPrefix=2
```

  过滤器配置

 # 全局过滤器
```java
@Component
@Order(-1)
public class GlobalAuthFilter implements GlobalFilter {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // 验证JWT Token
        String token = request.getHeaders().getFirst("Authorization");
        if (token != null && validateToken(token)) {
            // 添加用户信息到请求头
            ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-User-ID", getUserIdFromToken(token))
                .build();
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }
        
        // 返回未授权错误
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
```

 # 自定义路由过滤器
```java
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {
    
    public AuthGatewayFilterFactory() {
        super(Config.class);
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            // 检查API密钥
            String apiKey = request.getHeaders().getFirst("X-API-Key");
            if (isValidApiKey(apiKey)) {
                return chain.filter(exchange);
            }
            
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        };
    }
    
    public static class Config {
        // 过滤器配置
    }
}
```

  限流配置

 # Redis限流配置
```yaml
spring:
  cloud:
    gateway:
      default-filters:
        - name: RequestRateLimiter
          args:
            redis-rate-limiter.replenishRate: 100
            redis-rate-limiter.burstCapacity: 200
            key-resolver: "#{@userKeyResolver}"
            
  redis:
    host: localhost
    port: 6379
```

 # 自定义限流键解析器
```java
@Component
public class UserKeyResolver implements KeyResolver {
    
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 根据用户ID进行限流
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        if (userId != null) {
            return Mono.just(userId);
        }
        
        // 根据IP地址进行限流
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
```

  熔断配置

 # Hystrix熔断配置
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://service-user
          predicates:
            - Path=/api/users/**
          filters:
            - name: Hystrix
              args:
                name: userServiceFallback
                fallbackUri: forward:/fallback/user-service
```

 # 熔断降级处理
```java
@RestController
public class FallbackController {
    
    @RequestMapping("/fallback/user-service")
    public ResponseEntity<String> userServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body("User service is temporarily unavailable. Please try again later.");
    }
}
```

  跨域配置

 # CORS配置
```yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods:
              - GET
              - POST
              - PUT
              - DELETE
              - OPTIONS
            allowedHeaders: "*"
            allowCredentials: false
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- Redis 缓存服务
- Eureka 服务注册中心

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 9999

spring:
  application:
    name: service-gateway
    
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: user-service
          uri: lb://service-user
          predicates:
            - Path=/api/users/**
          filters:
            - StripPrefix=2
            
        - id: video-service
          uri: lb://service-video
          predicates:
            - Path=/api/videos/**
          filters:
            - StripPrefix=2

  redis:
    host: localhost
    port: 6379

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

 # 启动服务

```bash
cd service-gateway
mvn spring-boot:run
```

或

```bash
cd service-gateway
mvn clean package
java -jar target/service-gateway-1.0.0.jar
```

  服务依赖

- service-registry (服务注册中心)
- service-user (用户服务)
- service-video (视频服务)
- 其他所有业务服务

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:9999/actuator/health
```

路由状态检查：

```
GET http://localhost:9999/actuator/gateway/routes