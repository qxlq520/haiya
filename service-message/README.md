# 消息服务 (service-message)

  服务概述

消息服务是海牙平台的核心服务之一，负责处理用户之间的消息传递和系统通知功能，包括私信、通知推送等核心功能。

  功能特性

1. 私信发送与接收
2. 消息查询与管理
3. 系统通知推送
4. 消息状态管理
5. 实时消息推送

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- WebSocket 实时通信

  API 接口

 # 私信接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/messages` | POST | 发送私信 |
| `/api/messages` | GET | 获取私信列表 |
| `/api/messages/{id}` | GET | 获取指定私信详情 |
| `/api/messages/{id}` | DELETE | 删除私信 |
| `/api/messages/users/{userId}` | GET | 获取与指定用户的私信记录 |

 # 通知接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/notifications` | GET | 获取通知列表 |
| `/api/notifications/{id}` | GET | 获取指定通知详情 |
| `/api/notifications/{id}/read` | PUT | 标记通知为已读 |
| `/api/notifications/read` | PUT | 批量标记通知为已读 |

  数据库设计

 # messages 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| sender_id | BIGINT | 发送者ID |
| receiver_id | BIGINT | 接收者ID |
| content | TEXT | 消息内容 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |
| status | VARCHAR(20) | 消息状态（SENT, READ, DELETED） |

 # notifications 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| title | VARCHAR(255) | 通知标题 |
| content | TEXT | 通知内容 |
| type | VARCHAR(50) | 通知类型（LIKE, COMMENT, FOLLOW等） |
| target_id | BIGINT | 目标ID |
| target_type | VARCHAR(50) | 目标类型 |
| is_read | BOOLEAN | 是否已读 |
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
  port: 8086

spring:
  application:
    name: service-message
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_message
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
```

 # 启动服务

```bash
cd service-message
mvn spring-boot:run
```

或

```bash
cd service-message
mvn clean package
java -jar target/service-message-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/message
├── MessageServiceApplication.java  # 应用入口
├── config/                         # 配置类
│   └── WebSocketConfig.java        # WebSocket配置
├── controller/                     # 控制器层
│   ├── MessageController.java      # 私信控制器
│   ├── NotificationController.java # 通知控制器
│   └── ChatController.java         # 聊天控制器
├── entity/                         # 实体类
│   ├── Message.java                # 私信实体
│   └── Notification.java           # 通知实体
├── repository/                     # 数据访问层
│   ├── MessageRepository.java      # 私信数据访问
│   └── NotificationRepository.java # 通知数据访问
└── service/                        # 业务逻辑层
    ├── MessageService.java         # 私信服务
    └── NotificationService.java    # 通知服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8086/actuator/health