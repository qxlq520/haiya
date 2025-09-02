# 群组服务 (service-group)

  服务概述

群组服务是海牙平台社交功能的重要组成部分，负责管理用户群组、群组成员、群组消息和群组活动等功能，为用户提供群组交流的平台。

  功能特性

1. 群组创建与管理
2. 群组成员管理
3. 群组消息发送与接收
4. 群组权限控制
5. 群组搜索与发现
6. 群组公告与活动
7. 群组文件共享
8. 群组数据统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- RabbitMQ 消息队列

  API 接口

 # 群组管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/groups` | GET | 获取群组列表 |
| `/api/groups` | POST | 创建群组 |
| `/api/groups/{id}` | GET | 获取群组详情 |
| `/api/groups/{id}` | PUT | 更新群组信息 |
| `/api/groups/{id}` | DELETE | 解散群组 |
| `/api/groups/{id}/settings` | PUT | 更新群组设置 |

 # 群组成员接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/group-members` | POST | 添加成员 |
| `/api/group-members/batch` | POST | 批量添加成员 |
| `/api/group-members/{id}` | DELETE | 移除成员 |
| `/api/group-members/{groupId}/members` | GET | 获取群组成员列表 |
| `/api/group-members/{groupId}/admins` | GET | 获取群组管理员列表 |
| `/api/group-members/{groupId}/join` | POST | 申请加入群组 |
| `/api/group-members/{groupId}/leave` | POST | 退出群组 |

 # 群组消息接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/group-messages` | POST | 发送群组消息 |
| `/api/group-messages/{groupId}/messages` | GET | 获取群组消息列表 |
| `/api/group-messages/{id}` | DELETE | 删除群组消息 |
| `/api/group-messages/{groupId}/unread` | GET | 获取未读消息数 |

 # 群组公告接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/group-announcements` | POST | 发布群组公告 |
| `/api/group-announcements/{groupId}/announcements` | GET | 获取群组公告列表 |
| `/api/group-announcements/{id}` | PUT | 更新群组公告 |
| `/api/group-announcements/{id}` | DELETE | 删除群组公告 |

  数据库设计

 # groups 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 群组名称 |
| description | TEXT | 群组描述 |
| avatar_url | VARCHAR(500) | 群组头像URL |
| owner_id | BIGINT | 群主ID |
| member_count | INT | 成员数量 |
| max_members | INT | 最大成员数 |
| group_type | VARCHAR(20) | 群组类型（PUBLIC, PRIVATE, PROTECTED） |
| join_permission | VARCHAR(20) | 加入权限（ANYONE, APPROVAL, INVITE） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DISBANDED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # group_members 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| group_id | BIGINT | 群组ID |
| user_id | BIGINT | 用户ID |
| role | VARCHAR(20) | 角色（OWNER, ADMIN, MEMBER） |
| nickname | VARCHAR(100) | 群内昵称 |
| joined_at | BIGINT | 加入时间 |
| status | VARCHAR(20) | 状态（ACTIVE, PENDING, BLOCKED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # group_messages 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| group_id | BIGINT | 群组ID |
| sender_id | BIGINT | 发送者ID |
| message_type | VARCHAR(20) | 消息类型（TEXT, IMAGE, VIDEO, FILE等） |
| content | TEXT | 消息内容 |
| media_url | VARCHAR(500) | 媒体URL |
| reply_to_id | BIGINT | 回复消息ID |
| at_users | TEXT | @用户列表（JSON格式） |
| status | VARCHAR(20) | 状态（NORMAL, DELETED） |
| sent_at | BIGINT | 发送时间 |
| created_at | BIGINT | 创建时间 |

 # group_announcements 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| group_id | BIGINT | 群组ID |
| title | VARCHAR(255) | 公告标题 |
| content | TEXT | 公告内容 |
| author_id | BIGINT | 作者ID |
| is_pinned | BOOLEAN | 是否置顶 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # group_files 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| group_id | BIGINT | 群组ID |
| file_name | VARCHAR(255) | 文件名 |
| file_url | VARCHAR(500) | 文件URL |
| file_size | BIGINT | 文件大小 |
| uploader_id | BIGINT | 上传者ID |
| download_count | INT | 下载次数 |
| uploaded_at | BIGINT | 上传时间 |
| created_at | BIGINT | 创建时间 |

 # group_invitations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| group_id | BIGINT | 群组ID |
| inviter_id | BIGINT | 邀请者ID |
| invitee_id | BIGINT | 被邀请者ID |
| invite_message | TEXT | 邀请消息 |
| status | VARCHAR(20) | 状态（PENDING, ACCEPTED, REJECTED, EXPIRED） |
| expired_at | BIGINT | 过期时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  RabbitMQ 消息队列设计

 # 群组消息交换机
```
Exchange: group.messages
  - Type: Topic
  - Routing Key: group.{groupId}
```

 # 群组通知交换机
```
Exchange: group.notifications
  - Type: Topic
  - Routing Key: group.{groupId}.notification
```

  Redis 缓存设计

 # 群组成员缓存
```
group:{groupId}:members -> Set结构存储群组成员ID
group:{groupId}:admins -> Set结构存储群组管理员ID
```

 # 群组未读消息缓存
```
group:{groupId}:unread:{userId} -> String结构存储未读消息数
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- RabbitMQ 消息队列

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8114

spring:
  application:
    name: service-group
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_group
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
```

 # 启动服务

```bash
cd service-group
mvn spring-boot:run
```

或

```bash
cd service-group
mvn clean package
java -jar target/service-group-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-message (消息服务)
- service-storage (存储服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/group
├── GroupServiceApplication.java  # 应用入口
├── controller/                   # 控制器层
│   ├── GroupController.java      # 群组控制器
│   ├── MemberController.java     # 成员控制器
│   ├── MessageController.java    # 消息控制器
│   └── AnnouncementController.java # 公告控制器
├── entity/                       # 实体类
│   ├── Group.java                # 群组实体
│   ├── GroupMember.java          # 群组成员实体
│   ├── GroupMessage.java         # 群组消息实体
│   ├── GroupAnnouncement.java    # 群组公告实体
│   ├── GroupFile.java            # 群组文件实体
│   └── GroupInvitation.java      # 群组邀请实体
├── repository/                   # 数据访问层
│   ├── GroupRepository.java      # 群组数据访问
│   ├── GroupMemberRepository.java # 群组成员数据访问
│   ├── GroupMessageRepository.java # 群组消息数据访问
│   ├── GroupAnnouncementRepository.java # 群组公告数据访问
│   ├── GroupFileRepository.java  # 群组文件数据访问
│   └── GroupInvitationRepository.java # 群组邀请数据访问
└── service/                      # 业务逻辑层
    ├── GroupService.java         # 群组服务
    ├── MemberService.java        # 成员服务
    ├── MessageService.java       # 消息服务
    └── AnnouncementService.java  # 公告服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8114/actuator/health