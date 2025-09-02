# 关注服务 (service-follow)

  服务概述

关注服务是海牙平台的核心社交服务之一，负责处理用户之间的关注关系，包括关注、取消关注、关注列表查询等核心功能。

  功能特性

1. 用户关注功能
2. 用户取消关注功能
3. 关注列表查询
4. 粉丝列表查询
5. 关注状态查询
6. 关注数量统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现

  API 接口

 # 关注接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/follows` | POST | 关注用户 |
| `/api/follows` | DELETE | 取消关注 |
| `/api/follows/following` | GET | 获取关注列表 |
| `/api/follows/followers` | GET | 获取粉丝列表 |
| `/api/follows/following/count` | GET | 获取关注数 |
| `/api/follows/followers/count` | GET | 获取粉丝数 |
| `/api/follows/status` | GET | 获取关注状态 |

  数据库设计

 # follows 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| follower_id | BIGINT | 关注者ID |
| followed_id | BIGINT | 被关注者ID |
| created_at | BIGINT | 创建时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8085

spring:
  application:
    name: service-follow
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_follow
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
```

 # 启动服务

```bash
cd service-follow
mvn spring-boot:run
```

或

```bash
cd service-follow
mvn clean package
java -jar target/service-follow-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/follow
├── FollowServiceApplication.java  # 应用入口
├── controller/                    # 控制器层
│   └── FollowController.java      # 关注控制器
├── entity/                        # 实体类
│   └── Follow.java                # 关注实体
├── repository/                    # 数据访问层
│   └── FollowRepository.java      # 关注数据访问
├── service/                       # 业务逻辑层
│   └── FollowService.java         # 关注服务
└── dto/                           # 数据传输对象
    └── FollowDto.java             # 关注DTO
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8085/actuator/health