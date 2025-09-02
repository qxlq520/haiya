# 配置中心 (config-server)

  服务概述

配置中心是海牙平台的统一配置管理服务，基于Spring Cloud Config实现，为所有微服务提供集中化的外部配置管理，支持配置的版本控制、环境隔离和动态刷新。

  功能特性

1. 集中化配置管理
2. 多环境配置支持
3. 配置版本控制
4. 配置动态刷新
5. 配置加密解密
6. 高可用部署
7. 配置审计日志
8. 访问权限控制

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Cloud Config Server
- Git 版本控制系统
- Redis 缓存
- Eureka 服务注册与发现

  API 接口

 # 配置管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/{application}/{profile}` | GET | 获取应用配置 |
| `/{application}/{profile}/{label}` | GET | 获取指定版本的应用配置 |
| `/{application}-{profile}.properties` | GET | 获取属性格式的配置 |
| `/{application}-{profile}.json` | GET | 获取JSON格式的配置 |
| `/{application}-{profile}.yml` | GET | 获取YAML格式的配置 |

 # 配置刷新接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/actuator/refresh` | POST | 刷新配置 |
| `/actuator/bus-refresh` | POST | 总线刷新配置 |
| `/actuator/env` | GET | 查看环境变量 |
| `/actuator/configprops` | GET | 查看配置属性 |

  配置存储结构

 # Git仓库结构
```
config-repo/
├── application.yml                  # 默认配置
├── application-dev.yml             # 开发环境配置
├── application-test.yml            # 测试环境配置
├── application-prod.yml            # 生产环境配置
├── service-user.yml                # 用户服务配置
├── service-user-dev.yml            # 用户服务开发环境配置
├── service-video.yml               # 视频服务配置
└── service-video-prod.yml          # 视频服务生产环境配置
```

 # 配置文件示例
```yaml
# application.yml
spring:
  application:
    name: haiya-platform
    
server:
  port: 8080
  
logging:
  level:
    com.haiya: INFO
    
jwt:
  secret: default_secret_key
  expiration: 86400
```

```yaml
# application-prod.yml
spring:
  datasource:
    url: jdbc:mysql://prod-db:3306/haiya_platform
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    
logging:
  level:
    com.haiya: WARN
    
jwt:
  secret: ${JWT_SECRET}
  expiration: 86400
```

  安全配置

 # 加密解密配置
```yaml
encrypt:
  key: ${ENCRYPT_KEY:default_encrypt_key}
```

 # 访问控制配置
```yaml
spring:
  security:
    user:
      name: ${CONFIG_USER:config_user}
      password: ${CONFIG_PASSWORD:config_password}
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- Git 版本控制系统
- Redis 缓存服务（可选）

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8888

spring:
  application:
    name: config-server
    
  cloud:
    config:
      server:
        git:
          uri: https://github.com/haiya/config-repo.git
          username: ${GIT_USERNAME}
          password: ${GIT_PASSWORD}
          clone-on-start: true
          force-pull: true
          search-paths: '{application}'
          
  security:
    user:
      name: ${CONFIG_SERVER_USER:config_user}
      password: ${CONFIG_SERVER_PASSWORD:config_password}

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

 # 启动服务

```bash
cd config-server
mvn spring-boot:run
```

或

```bash
cd config-server
mvn clean package
java -jar target/config-server-1.0.0.jar
```

  客户端集成

 # 客户端配置
在微服务的 `bootstrap.yml` 中添加：

```yaml
spring:
  application:
    name: service-user
    
  cloud:
    config:
      uri: http://config-server:8888
      username: ${CONFIG_USER:config_user}
      password: ${CONFIG_PASSWORD:config_password}
      fail-fast: true
      retry:
        initial-interval: 2000
        max-attempts: 20
```

 # 配置刷新
在需要动态刷新的Bean上添加 `@RefreshScope` 注解：

```java
@Component
@RefreshScope
public class ConfigProperties {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    
    // getters and setters
}
```

  高可用部署

 # 集群部署配置
```yaml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/haiya/config-repo.git
          repos:
            production:
              pattern: "*/prod"
              uri: https://github.com/haiya/config-repo-prod.git
            development:
              pattern: "*/dev"
              uri: https://github.com/haiya/config-repo-dev.git
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8888/actuator/health
```

配置服务状态检查：

```
GET http://localhost:8888/actuator/configprops