# 直播服务 (service-live)

  服务概述

直播服务是海牙平台的核心服务之一，负责处理直播相关功能，包括直播间管理、直播流处理、直播互动等核心功能。

  功能特性

1. 直播间创建与管理
2. 直播流处理与分发
3. 直播互动功能（聊天、礼物等）
4. 直播间状态管理
5. 直播数据统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- WebSocket 实时通信
- FFmpeg 流媒体处理

  API 接口

 # 直播间接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/lives` | POST | 创建直播间 |
| `/api/lives` | GET | 获取直播间列表 |
| `/api/lives/{id}` | GET | 获取指定直播间详情 |
| `/api/lives/{id}` | PUT | 更新直播间信息 |
| `/api/lives/{id}/start` | POST | 开始直播 |
| `/api/lives/{id}/stop` | POST | 结束直播 |
| `/api/lives/user/{userId}` | GET | 获取用户直播间列表 |

 # 直播流接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/streams/{liveId}/push` | GET | 获取推流地址 |
| `/api/streams/{liveId}/pull` | GET | 获取拉流地址 |
| `/api/streams/{liveId}/status` | GET | 获取流状态 |

 # 直播互动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/live-chats` | POST | 发送聊天消息 |
| `/api/live-chats/{liveId}` | GET | 获取聊天记录 |
| `/api/live-gifts` | POST | 发送礼物 |
| `/api/live-gifts/{liveId}` | GET | 获取礼物记录 |

  数据库设计

 # lives 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 主播ID |
| title | VARCHAR(255) | 直播间标题 |
| cover_url | VARCHAR(500) | 封面图URL |
| stream_key | VARCHAR(100) | 推流密钥 |
| status | VARCHAR(20) | 直播间状态（PENDING, LIVE, FINISHED, CLOSED） |
| viewer_count | INT | 观看人数 |
| like_count | INT | 点赞数 |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # live_chats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| live_id | BIGINT | 直播间ID |
| user_id | BIGINT | 用户ID |
| content | TEXT | 聊天内容 |
| created_at | BIGINT | 创建时间 |

 # live_gifts 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| live_id | BIGINT | 直播间ID |
| user_id | BIGINT | 用户ID |
| gift_id | BIGINT | 礼物ID |
| gift_name | VARCHAR(100) | 礼物名称 |
| gift_image | VARCHAR(500) | 礼物图片 |
| gift_price | DECIMAL(10,2) | 礼物价格 |
| count | INT | 礼物数量 |
| created_at | BIGINT | 创建时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- FFmpeg 流媒体处理工具

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8087

spring:
  application:
    name: service-live
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_live
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
```

 # 启动服务

```bash
cd service-live
mvn spring-boot:run
```

或

```bash
cd service-live
mvn clean package
java -jar target/service-live-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-gift (礼物服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/live
├── LiveServiceApplication.java  # 应用入口
├── controller/                  # 控制器层
│   └── LiveController.java      # 直播控制器
├── entity/                      # 实体类
│   ├── Live.java                # 直播间实体
│   ├── LiveChat.java            # 直播聊天实体
│   └── LiveGift.java            # 直播礼物实体
├── repository/                  # 数据访问层
│   ├── LiveRepository.java      # 直播间数据访问
│   ├── LiveChatRepository.java  # 直播聊天数据访问
│   └── LiveGiftRepository.java  # 直播礼物数据访问
└── service/                     # 业务逻辑层
    └── LiveService.java         # 直播服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8087/actuator/health