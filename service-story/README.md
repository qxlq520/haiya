# 故事服务 (service-story)

  服务概述

故事服务是海牙平台的短视频社交功能服务，提供24小时限时浏览的故事发布、浏览、互动和管理功能，增强用户社交体验和平台粘性。

  功能特性

1. 故事发布与管理
2. 故事浏览与播放
3. 故事互动（点赞、评论）
4. 故事特效与滤镜
5. 故事隐私设置
6. 故事推荐与发现
7. 故事数据统计
8. 故事过期自动删除

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- RabbitMQ 消息队列

  API 接口

 # 故事管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/stories` | POST | 发布故事 |
| `/api/stories` | GET | 获取故事列表 |
| `/api/stories/{id}` | GET | 获取故事详情 |
| `/api/stories/{id}/delete` | DELETE | 删除故事 |
| `/api/stories/batch-delete` | POST | 批量删除故事 |
| `/api/stories/me` | GET | 获取我的故事列表 |

 # 故事浏览接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/stories/feed` | GET | 获取故事Feed流 |
| `/api/stories/user/{userId}` | GET | 获取用户故事 |
| `/api/stories/friends` | GET | 获取好友故事 |
| `/api/stories/viewed` | POST | 标记故事已浏览 |
| `/api/stories/next` | GET | 获取下一个故事 |

 # 故事互动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/stories/{id}/like` | POST | 点赞故事 |
| `/api/stories/{id}/unlike` | POST | 取消点赞故事 |
| `/api/stories/{id}/comments` | POST | 评论故事 |
| `/api/stories/{id}/comments` | GET | 获取故事评论 |
| `/api/stories/{id}/share` | POST | 分享故事 |

 # 故事设置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/stories/settings` | GET | 获取故事设置 |
| `/api/stories/settings` | PUT | 更新故事设置 |
| `/api/stories/privacy/{storyId}` | PUT | 设置故事隐私 |
| `/api/stories/allow-list` | POST | 设置允许查看用户列表 |

 # 故事统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/stories/stats/overview` | GET | 获取故事概览统计 |
| `/api/stories/stats/user/{userId}` | GET | 获取用户故事统计 |
| `/api/stories/stats/interactions` | GET | 获取互动统计 |
| `/api/stories/stats/trends` | GET | 获取趋势统计 |

  数据库设计

 # stories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| content_type | VARCHAR(20) | 内容类型（IMAGE, VIDEO） |
| content_url | VARCHAR(500) | 内容URL |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| duration | INT | 时长（秒） |
| description | TEXT | 描述 |
| location | VARCHAR(255) | 位置信息 |
| view_count | INT | 浏览数 |
| like_count | INT | 点赞数 |
| comment_count | INT | 评论数 |
| share_count | INT | 分享数 |
| privacy_setting | VARCHAR(20) | 隐私设置（PUBLIC, FRIENDS, PRIVATE） |
| expires_at | BIGINT | 过期时间 |
| status | VARCHAR(20) | 状态（ACTIVE, DELETED, EXPIRED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # story_views 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| story_id | BIGINT | 故事ID |
| viewer_id | BIGINT | 浏览者ID |
| view_time | INT | 浏览时长（秒） |
| viewed_at | BIGINT | 浏览时间 |
| created_at | BIGINT | 创建时间 |

 # story_likes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| story_id | BIGINT | 故事ID |
| user_id | BIGINT | 用户ID |
| liked_at | BIGINT | 点赞时间 |
| created_at | BIGINT | 创建时间 |

 # story_comments 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| story_id | BIGINT | 故事ID |
| user_id | BIGINT | 用户ID |
| content | TEXT | 评论内容 |
| reply_to_id | BIGINT | 回复评论ID |
| like_count | INT | 点赞数 |
| status | VARCHAR(20) | 状态（NORMAL, DELETED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # story_settings 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| allow_messages | BOOLEAN | 是否允许消息 |
| show_story_views | BOOLEAN | 是否显示浏览者 |
| save_to_gallery | BOOLEAN | 是否保存到相册 |
| auto_delete_expired | BOOLEAN | 是否自动删除过期故事 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # story_stats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| stat_date | DATE | 统计日期 |
| story_count | INT | 故事数 |
| view_count | INT | 浏览数 |
| like_count | INT | 点赞数 |
| comment_count | INT | 评论数 |
| share_count | INT | 分享数 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 故事缓存
```
story:{storyId} -> Hash结构存储故事详情
story:user:{userId}:stories -> List结构存储用户故事列表
story:feed:{userId} -> List结构存储用户故事Feed
```

 # 互动缓存
```
story:{storyId}:views -> Set结构存储故事浏览者
story:{storyId}:likes -> Set结构存储故事点赞者
story:user:{userId}:viewed -> Set结构存储用户已浏览故事
```

 # 统计缓存
```
story:stats:user:{userId}:daily -> Hash结构存储用户每日统计
story:stats:popular -> ZSet结构存储热门故事
```

  RabbitMQ 消息队列设计

 # 故事事件队列
```
Queue: story.events
  - Exchange: story.exchange
  - Routing Key: story.{eventType}
```

 # 故事过期队列
```
Queue: story.expiration
  - Exchange: story.exchange
  - Routing Key: story.expiration.check
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
  port: 8132

spring:
  application:
    name: service-story
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_story
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

story:
  expiration:
    hours: 24
  storage:
    base-url: https://cdn.haiya.com/stories/
```

 # 启动服务

```bash
cd service-story
mvn spring-boot:run
```

或

```bash
cd service-story
mvn clean package
java -jar target/service-story-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-storage (存储服务)
- service-like (点赞服务)
- service-comment (评论服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/story
├── StoryApplication.java        # 应用入口
├── config/                      # 配置类
│   └── StoryConfig.java         # 故事配置
├── controller/                  # 控制器层
│   ├── StoryController.java     # 故事控制器
│   ├── ViewController.java      # 浏览控制器
│   ├── InteractionController.java # 互动控制器
│   ├── SettingController.java   # 设置控制器
│   └── StatController.java      # 统计控制器
├── entity/                      # 实体类
│   ├── Story.java               # 故事实体
│   ├── StoryView.java           # 故事浏览实体
│   ├── StoryLike.java           # 故事点赞实体
│   ├── StoryComment.java        # 故事评论实体
│   ├── StorySetting.java        # 故事设置实体
│   └── StoryStat.java           # 故事统计实体
├── repository/                  # 数据访问层
│   ├── StoryRepository.java     # 故事数据访问
│   ├── StoryViewRepository.java # 故事浏览数据访问
│   ├── StoryLikeRepository.java # 故事点赞数据访问
│   ├── StoryCommentRepository.java # 故事评论数据访问
│   ├── StorySettingRepository.java # 故事设置数据访问
│   └── StoryStatRepository.java # 故事统计数据访问
└── service/                     # 业务逻辑层
    ├── StoryService.java        # 故事服务
    ├── ViewService.java         # 浏览服务
    ├── InteractionService.java  # 互动服务
    ├── SettingService.java      # 设置服务
    └── StatService.java         # 统计服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8132/actuator/health