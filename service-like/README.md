# 点赞服务 (service-like)

  服务概述

点赞服务是海牙平台的核心服务之一，负责处理用户对各种内容的点赞功能，包括视频点赞、评论点赞等核心功能。

  功能特性

1. 通用点赞功能（支持多种内容类型）
2. 点赞状态查询
3. 点赞数量统计
4. 点赞记录管理

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现

  API 接口

 # 点赞接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/likes` | POST | 点赞目标内容 |
| `/api/likes` | DELETE | 取消点赞 |
| `/api/likes/count` | GET | 获取目标内容的点赞数 |
| `/api/likes/status` | GET | 获取用户对目标内容的点赞状态 |

  数据库设计

 # likes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| target_id | BIGINT | 目标内容ID |
| target_type | VARCHAR(50) | 目标内容类型（VIDEO、COMMENT等） |
| user_id | BIGINT | 用户ID |
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
  port: 8084

spring:
  application:
    name: service-like
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_like
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
```

 # 启动服务

```bash
cd service-like
mvn spring-boot:run
```

或

```bash
cd service-like
mvn clean package
java -jar target/service-like-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-comment (评论服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/like
├── LikeServiceApplication.java  # 应用入口
├── controller/                  # 控制器层
│   └── LikeController.java      # 点赞控制器
├── entity/                      # 实体类
│   └── Like.java                # 点赞实体
├── repository/                  # 数据访问层
│   └── LikeRepository.java      # 点赞数据访问
├── service/                     # 业务逻辑层
│   └── LikeService.java         # 点赞服务
└── dto/                         # 数据传输对象
    └── LikeDto.java             # 点赞DTO
```

  支持的点赞类型

| 类型 | 说明 |
|------|------|
| VIDEO | 视频点赞 |
| COMMENT | 评论点赞 |
| USER | 用户主页点赞 |

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8084/actuator/health