# 评论服务 (service-comment)

  服务概述

评论服务是海牙平台的核心服务之一，负责处理用户对视频内容的评论功能，包括评论发布、评论查询、评论点赞等核心功能。

  功能特性

1. 评论发布与管理
2. 评论回复功能
3. 评论点赞功能
4. 评论数据统计
5. 用户评论历史查询

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现

  API 接口

 # 评论接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/comments/video/{videoId}` | GET | 获取视频的所有评论 |
| `/api/comments` | POST | 发布新评论 |
| `/api/comments/{id}` | GET | 获取指定评论详情 |
| `/api/comments/{id}` | PUT | 更新评论内容 |
| `/api/comments/{id}` | DELETE | 删除评论 |
| `/api/comments/user/{userId}` | GET | 获取用户的所有评论 |
| `/api/comments/video/{videoId}/count` | GET | 获取视频的评论数量 |

 # 评论回复接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/comments/{id}/replies` | GET | 获取评论的所有回复 |

 # 评论点赞接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/comment-likes` | POST | 点赞评论 |
| `/api/comment-likes` | DELETE | 取消点赞评论 |
| `/api/comment-likes/count` | GET | 获取评论点赞数 |
| `/api/comment-likes/status` | GET | 获取用户对评论的点赞状态 |

  数据库设计

 # comments 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| video_id | BIGINT | 视频ID |
| parent_id | BIGINT | 父评论ID（用于回复） |
| content | VARCHAR(1000) | 评论内容 |
| like_count | INT | 点赞数 |
| reply_count | INT | 回复数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # comment_likes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| comment_id | BIGINT | 评论ID |
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
  port: 8083

spring:
  application:
    name: service-comment
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_comment
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
```

 # 启动服务

```bash
cd service-comment
mvn spring-boot:run
```

或

```bash
cd service-comment
mvn clean package
java -jar target/service-comment-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/comment
├── CommentServiceApplication.java  # 应用入口
├── controller/                     # 控制器层
│   ├── CommentController.java      # 评论控制器
│   └── CommentLikeController.java  # 评论点赞控制器
├── entity/                         # 实体类
│   ├── Comment.java                # 评论实体
│   └── CommentLike.java            # 评论点赞实体
├── repository/                     # 数据访问层
│   ├── CommentRepository.java      # 评论数据访问
│   └── CommentLikeRepository.java  # 评论点赞数据访问
├── service/                        # 业务逻辑层
│   ├── CommentService.java         # 评论服务
│   └── CommentLikeService.java     # 评论点赞服务
└── dto/                            # 数据传输对象
    └── CommentDto.java             # 评论DTO
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8083/actuator/health