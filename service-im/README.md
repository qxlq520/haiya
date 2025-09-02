# 即时通讯服务 (service-im)

  服务概述

即时通讯服务是海牙平台实时通信的核心服务，负责处理用户之间的实时消息传递、在线状态管理、消息存储和推送通知等功能。

  功能特性

1. 实时消息传递
2. 在线状态管理
3. 消息存储与同步
4. 消息推送通知
5. 多设备消息同步
6. 消息已读回执
7. 消息撤回功能
8. 消息加密传输

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Netty 网络通信框架
- WebSocket 实时通信协议
- RabbitMQ 消息队列

  API 接口

 # 连接管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/im/connect` | POST | 建立IM连接 |
| `/api/im/disconnect` | POST | 断开IM连接 |
| `/api/im/heartbeat` | POST | 发送心跳包 |
| `/api/im/status` | GET | 获取连接状态 |

 # 消息发送接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/im/messages` | POST | 发送消息 |
| `/api/im/messages/batch` | POST | 批量发送消息 |
| `/api/im/messages/{id}/recall` | POST | 撤回消息 |
| `/api/im/messages/{id}/read` | POST | 标记消息已读 |

 # 会话管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/im/conversations` | GET | 获取会话列表 |
| `/api/im/conversations/{id}/messages` | GET | 获取会话消息列表 |
| `/api/im/conversations/{id}/unread` | GET | 获取会话未读数 |
| `/api/im/conversations/{id}/clear` | POST | 清空会话消息 |

 # 联系人管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/im/contacts` | GET | 获取联系人列表 |
| `/api/im/contacts/{id}/online-status` | GET | 获取联系人在线状态 |
| `/api/im/contacts/block` | POST | 拉黑联系人 |
| `/api/im/contacts/unblock` | POST | 解除拉黑联系人 |

  WebSocket 接口

 # 连接地址
```
ws://localhost:8115/im/ws?token={access_token}
```

 # 客户端发送消息格式
```json
{
  "type": "message",
  "data": {
    "to": "user_id",
    "content": "Hello World",
    "type": "text"
  }
}
```

 # 服务端推送消息格式
```json
{
  "type": "message",
  "data": {
    "from": "user_id",
    "content": "Hello World",
    "type": "text",
    "timestamp": 1612345678901
  }
}
```

  数据库设计

 # im_messages 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| from_user_id | BIGINT | 发送者ID |
| to_user_id | BIGINT | 接收者ID |
| message_type | VARCHAR(20) | 消息类型（TEXT, IMAGE, VIDEO, FILE, VOICE等） |
| content | TEXT | 消息内容 |
| media_url | VARCHAR(500) | 媒体URL |
| status | VARCHAR(20) | 消息状态（SENT, DELIVERED, READ, RECALLED） |
| is_recall | BOOLEAN | 是否已撤回 |
| sent_at | BIGINT | 发送时间 |
| delivered_at | BIGINT | 投递时间 |
| read_at | BIGINT | 阅读时间 |
| created_at | BIGINT | 创建时间 |

 # im_conversations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| contact_id | BIGINT | 联系人ID |
| unread_count | INT | 未读消息数 |
| last_message_id | BIGINT | 最后一条消息ID |
| last_message_preview | VARCHAR(255) | 最后消息预览 |
| updated_at | BIGINT | 更新时间 |
| created_at | BIGINT | 创建时间 |

 # im_contacts 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| contact_id | BIGINT | 联系人ID |
| nickname | VARCHAR(100) | 备注昵称 |
| is_blocked | BOOLEAN | 是否被拉黑 |
| added_at | BIGINT | 添加时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # im_user_sessions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| session_id | VARCHAR(100) | 会话ID |
| device_id | VARCHAR(100) | 设备ID |
| device_type | VARCHAR(20) | 设备类型（ANDROID, IOS, WEB等） |
| connect_time | BIGINT | 连接时间 |
| last_heartbeat_time | BIGINT | 最后心跳时间 |
| disconnect_time | BIGINT | 断开时间 |
| status | VARCHAR(20) | 状态（ONLINE, OFFLINE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # im_message_acknowledgements 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| message_id | BIGINT | 消息ID |
| user_id | BIGINT | 用户ID |
| ack_type | VARCHAR(20) | 确认类型（DELIVERED, READ） |
| acknowledged_at | BIGINT | 确认时间 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 用户在线状态缓存
```
im:user:online:{userId} -> String结构存储在线状态
im:user:sessions:{userId} -> Hash结构存储用户会话信息
```

 # 消息队列缓存
```
im:message:queue -> List结构存储待发送消息
im:message:unread:{userId} -> Hash结构存储未读消息数
```

  Netty 服务器配置

 # 服务器启动配置
```java
ServerBootstrap bootstrap = new ServerBootstrap();
bootstrap.group(bossGroup, workerGroup)
    .channel(NioServerSocketChannel.class)
    .childHandler(new ImChannelInitializer())
    .option(ChannelOption.SO_BACKLOG, 128)
    .childOption(ChannelOption.SO_KEEPALIVE, true);
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- RabbitMQ 消息队列
- Netty 4.x 网络通信框架

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8115

spring:
  application:
    name: service-im
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_im
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

im:
  netty:
    port: 8116
    boss-threads: 2
    worker-threads: 10
```

 # 启动服务

```bash
cd service-im
mvn spring-boot:run
```

或

```bash
cd service-im
mvn clean package
java -jar target/service-im-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-message (消息服务)
- service-notification (通知服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/im
├── ImServiceApplication.java     # 应用入口
├── config/                       # 配置类
│   └── NettyConfig.java          # Netty配置
├── controller/                   # 控制器层
│   ├── ConnectionController.java # 连接控制器
│   ├── MessageController.java    # 消息控制器
│   ├── ConversationController.java # 会话控制器
│   └── ContactController.java    # 联系人控制器
├── entity/                       # 实体类
│   ├── ImMessage.java            # IM消息实体
│   ├── Conversation.java          # 会话实体
│   ├── Contact.java               # 联系人实体
│   ├── UserSession.java           # 用户会话实体
│   └── MessageAcknowledgement.java # 消息确认实体
├── repository/                   # 数据访问层
│   ├── ImMessageRepository.java   # IM消息数据访问
│   ├── ConversationRepository.java # 会话数据访问
│   ├── ContactRepository.java     # 联系人数据访问
│   ├── UserSessionRepository.java # 用户会话数据访问
│   └── MessageAcknowledgementRepository.java # 消息确认数据访问
├── service/                      # 业务逻辑层
│   ├── ConnectionService.java     # 连接服务
│   ├── MessageService.java        # 消息服务
│   ├── ConversationService.java   # 会话服务
│   └── ContactService.java        # 联系人服务
└── netty/                        # Netty网络通信层
    ├── ImServer.java             # IM服务器
    ├── ImChannelInitializer.java # 通道初始化器
    ├── ImHandler.java            # 消息处理器
    └── MessageEncoderDecoder.java # 消息编解码器
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8115/actuator/health