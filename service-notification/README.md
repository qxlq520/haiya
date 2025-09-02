# 通知服务 (service-notification)

  服务概述

通知服务是海牙平台用户触达的核心服务，负责处理系统通知、消息推送、邮件通知、短信通知等多种通知方式，确保用户能够及时收到平台重要信息。

  功能特性

1. 多通道通知发送（推送、邮件、短信）
2. 通知模板管理
3. 通知策略配置
4. 用户通知偏好设置
5. 通知发送记录管理
6. 通知效果统计分析
7. 通知定时发送
8. 通知撤回功能

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- RabbitMQ 消息队列
- Firebase Cloud Messaging (FCM)
- 邮件服务集成
- 短信服务集成

  API 接口

 # 通知发送接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/notifications` | POST | 发送通知 |
| `/api/notifications/batch` | POST | 批量发送通知 |
| `/api/notifications/scheduled` | POST | 创建定时通知 |
| `/api/notifications/{id}/cancel` | POST | 取消通知发送 |
| `/api/notifications/{id}/retry` | POST | 重试通知发送 |

 # 通知模板接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/notification-templates` | GET | 获取通知模板列表 |
| `/api/notification-templates` | POST | 创建通知模板 |
| `/api/notification-templates/{id}` | GET | 获取通知模板详情 |
| `/api/notification-templates/{id}` | PUT | 更新通知模板 |
| `/api/notification-templates/{id}` | DELETE | 删除通知模板 |

 # 用户通知设置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/user-notifications/settings/{userId}` | GET | 获取用户通知设置 |
| `/api/user-notifications/settings/{userId}` | PUT | 更新用户通知设置 |
| `/api/user-notifications/preferences/{userId}` | GET | 获取用户通知偏好 |
| `/api/user-notifications/preferences/{userId}` | PUT | 更新用户通知偏好 |

 # 通知记录接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/notification-records` | GET | 获取通知记录列表 |
| `/api/notification-records/{id}` | GET | 获取通知记录详情 |
| `/api/notification-records/user/{userId}` | GET | 获取用户通知记录 |
| `/api/notification-records/status/{status}` | GET | 根据状态获取通知记录 |

 # 通知统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/notification-stats/overview` | GET | 获取通知概览统计 |
| `/api/notification-stats/channels` | GET | 获取各通道统计 |
| `/api/notification-stats/templates` | GET | 获取模板使用统计 |
| `/api/notification-stats/delivery-rates` | GET | 获取送达率统计 |

  数据库设计

 # notification_templates 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模板名称 |
| description | TEXT | 模板描述 |
| channel | VARCHAR(20) | 通知通道（PUSH, EMAIL, SMS） |
| title | VARCHAR(255) | 标题 |
| content | TEXT | 内容模板 |
| variables | TEXT | 变量定义（JSON格式） |
| is_active | BOOLEAN | 是否激活 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # notifications 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| template_id | BIGINT | 模板ID |
| recipient_ids | TEXT | 接收者ID列表（JSON格式） |
| channel | VARCHAR(20) | 通知通道（PUSH, EMAIL, SMS） |
| title | VARCHAR(255) | 标题 |
| content | TEXT | 内容 |
| variables | TEXT | 变量值（JSON格式） |
| send_time | BIGINT | 发送时间 |
| scheduled_time | BIGINT | 定时发送时间 |
| status | VARCHAR(20) | 状态（PENDING, SENDING, SENT, FAILED, CANCELLED） |
| priority | VARCHAR(20) | 优先级（LOW, MEDIUM, HIGH, URGENT） |
| retry_count | INT | 重试次数 |
| max_retries | INT | 最大重试次数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # notification_records 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| notification_id | BIGINT | 通知ID |
| recipient_id | BIGINT | 接收者ID |
| channel | VARCHAR(20) | 通知通道 |
| title | VARCHAR(255) | 标题 |
| content | TEXT | 内容 |
| status | VARCHAR(20) | 状态（PENDING, SENT, DELIVERED, READ, FAILED） |
| error_message | TEXT | 错误信息 |
| sent_at | BIGINT | 发送时间 |
| delivered_at | BIGINT | 送达时间 |
| read_at | BIGINT | 阅读时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # user_notification_settings 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| channel | VARCHAR(20) | 通知通道 |
| enabled | BOOLEAN | 是否启用 |
| do_not_disturb_start | VARCHAR(10) | 免打扰开始时间 |
| do_not_disturb_end | VARCHAR(10) | 免打扰结束时间 |
| max_daily_notifications | INT | 每日最大通知数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # notification_stats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| stat_date | DATE | 统计日期 |
| channel | VARCHAR(20) | 通知通道 |
| template_id | BIGINT | 模板ID |
| sent_count | INT | 发送数量 |
| delivered_count | INT | 送达数量 |
| read_count | INT | 阅读数量 |
| failed_count | INT | 失败数量 |
| avg_delivery_time | INT | 平均送达时间（秒） |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 通知缓存
```
notification:user:{userId}:unread -> String结构存储未读通知数
notification:user:{userId}:recent -> List结构存储最近通知
```

 # 模板缓存
```
notification:template:{templateId} -> String结构存储模板详情
notification:templates:channel:{channel} -> Set结构存储通道模板
```

  RabbitMQ 消息队列设计

 # 通知发送队列
```
Queue: notification.send
  - Exchange: notification.exchange
  - Routing Key: notification.send.{channel}
```

 # 通知状态更新队列
```
Queue: notification.status
  - Exchange: notification.exchange
  - Routing Key: notification.status.update
```

  FCM 集成配置

 # 推送通知配置
```json
{
  "notification": {
    "title": "通知标题",
    "body": "通知内容",
    "icon": "ic_notification",
    "sound": "default"
  },
  "data": {
    "click_action": "FLUTTER_NOTIFICATION_CLICK",
    "id": "1",
    "status": "done"
  }
}
```

  邮件服务集成

 # 邮件模板示例
```html
<!DOCTYPE html>
<html>
<head>
    <title>{{title}}</title>
</head>
<body>
    <h1>{{heading}}</h1>
    <p>{{content}}</p>
    <p>感谢您使用海牙平台！</p>
</body>
</html>
```

  短信服务集成

 # 短信模板示例
```
【海牙平台】{{content}} 回复TD退订
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- RabbitMQ 消息队列
- Firebase Cloud Messaging 账户
- 邮件服务账户
- 短信服务账户

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8126

spring:
  application:
    name: service-notification
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_notification
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

notification:
  fcm:
    credentials-path: /path/to/fcm-credentials.json
  email:
    host: smtp.gmail.com
    port: 587
    username: your_email@gmail.com
    password: your_password
  sms:
    provider: aliyun
    access-key-id: your_access_key_id
    access-key-secret: your_access_key_secret
```

 # 启动服务

```bash
cd service-notification
mvn spring-boot:run
```

或

```bash
cd service-notification
mvn clean package
java -jar target/service-notification-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-message (消息服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/notification
├── NotificationServiceApplication.java  # 应用入口
├── config/                             # 配置类
│   └── NotificationConfig.java         # 通知配置
├── controller/                         # 控制器层
│   ├── NotificationController.java     # 通知控制器
│   ├── TemplateController.java         # 模板控制器
│   ├── UserSettingController.java      # 用户设置控制器
│   ├── RecordController.java           # 记录控制器
│   └── StatController.java             # 统计控制器
├── entity/                             # 实体类
│   ├── NotificationTemplate.java       # 通知模板实体
│   ├── Notification.java               # 通知实体
│   ├── NotificationRecord.java         # 通知记录实体
│   ├── UserNotificationSetting.java    # 用户通知设置实体
│   └── NotificationStat.java           # 通知统计实体
├── repository/                         # 数据访问层
│   ├── NotificationTemplateRepository.java # 通知模板数据访问
│   ├── NotificationRepository.java     # 通知数据访问
│   ├── NotificationRecordRepository.java # 通知记录数据访问
│   ├── UserNotificationSettingRepository.java # 用户通知设置数据访问
│   └── NotificationStatRepository.java # 通知统计数据访问
└── service/                            # 业务逻辑层
    ├── NotificationService.java        # 通知服务
    ├── TemplateService.java            # 模板服务
    ├── UserSettingService.java         # 用户设置服务
    ├── RecordService.java              # 记录服务
    └── StatService.java                # 统计服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8126/actuator/health