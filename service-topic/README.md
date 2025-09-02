# 话题服务 (service-topic)

  服务概述

话题服务是海牙平台的内容聚合与社区互动服务，负责话题创建、话题内容聚合、话题推荐、话题排行榜等功能，帮助用户发现和参与热门话题讨论。

  功能特性

1. 话题创建与管理
2. 话题内容聚合
3. 话题推荐算法
4. 话题排行榜
5. 话题搜索与筛选
6. 话题关注与订阅
7. 话题数据分析
8. 话题活动管理

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎

  API 接口

 # 话题管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/topics` | POST | 创建话题 |
| `/api/topics` | GET | 获取话题列表 |
| `/api/topics/{id}` | GET | 获取话题详情 |
| `/api/topics/{id}` | PUT | 更新话题信息 |
| `/api/topics/{id}/delete` | DELETE | 删除话题 |
| `/api/topics/{id}/merge` | POST | 合并话题 |

 # 话题内容接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/topics/{id}/contents` | GET | 获取话题相关内容 |
| `/api/topics/{id}/videos` | GET | 获取话题相关视频 |
| `/api/topics/{id}/articles` | GET | 获取话题相关文章 |
| `/api/topics/{id}/posts` | GET | 获取话题相关帖子 |
| `/api/topics/{id}/attach-content` | POST | 关联内容到话题 |

 # 话题互动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/topics/{id}/follow` | POST | 关注话题 |
| `/api/topics/{id}/unfollow` | POST | 取消关注话题 |
| `/api/topics/{id}/like` | POST | 点赞话题 |
| `/api/topics/{id}/unlike` | POST | 取消点赞话题 |
| `/api/topics/{id}/share` | POST | 分享话题 |

 # 话题推荐接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/topics/recommendations` | GET | 获取推荐话题 |
| `/api/topics/trending` | GET | 获取热门话题 |
| `/api/topics/followed` | GET | 获取关注的话题 |
| `/api/topics/user-interests` | GET | 获取用户感兴趣的话题 |
| `/api/topics/similar/{id}` | GET | 获取相似话题 |

 # 话题统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/topics/{id}/stats` | GET | 获取话题统计数据 |
| `/api/topics/{id}/trends` | GET | 获取话题趋势数据 |
| `/api/topics/stats/overview` | GET | 获取话题概览统计 |
| `/api/topics/stats/rankings` | GET | 获取话题排行榜 |

  数据库设计

 # topics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 话题名称 |
| description | TEXT | 话题描述 |
| cover_image | VARCHAR(500) | 封面图片URL |
| view_count | BIGINT | 浏览数 |
| follow_count | INT | 关注数 |
| content_count | INT | 内容数 |
| like_count | INT | 点赞数 |
| share_count | INT | 分享数 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DELETED） |
| is_official | BOOLEAN | 是否官方话题 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # topic_contents 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| topic_id | BIGINT | 话题ID |
| content_id | BIGINT | 内容ID |
| content_type | VARCHAR(20) | 内容类型（VIDEO, ARTICLE, POST等） |
| relevance_score | DECIMAL(5,2) | 相关性得分 |
| attached_at | BIGINT | 关联时间 |
| created_at | BIGINT | 创建时间 |

 # topic_followers 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| topic_id | BIGINT | 话题ID |
| user_id | BIGINT | 用户ID |
| followed_at | BIGINT | 关注时间 |
| created_at | BIGINT | 创建时间 |

 # topic_interactions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| topic_id | BIGINT | 话题ID |
| user_id | BIGINT | 用户ID |
| interaction_type | VARCHAR(20) | 互动类型（LIKE, SHARE等） |
| interacted_at | BIGINT | 互动时间 |
| created_at | BIGINT | 创建时间 |

 # topic_stats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| topic_id | BIGINT | 话题ID |
| stat_date | DATE | 统计日期 |
| view_count | INT | 浏览数 |
| follow_count | INT | 关注数 |
| content_count | INT | 内容数 |
| like_count | INT | 点赞数 |
| share_count | INT | 分享数 |
| new_content_count | INT | 新增内容数 |
| created_at | BIGINT | 创建时间 |

 # topic_aliases 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| topic_id | BIGINT | 话题ID |
| alias_name | VARCHAR(100) | 别名 |
| created_at | BIGINT | 创建时间 |

  Elasticsearch 索引设计

 # 话题索引
```
Index: topics
  - topic_id: 话题ID
  - name: 话题名称
  - description: 话题描述
  - content_count: 内容数
  - follow_count: 关注数
  - view_count: 浏览数
  - is_official: 是否官方话题
  - created_at: 创建时间
```

 # 话题内容索引
```
Index: topic-contents
  - topic_id: 话题ID
  - content_id: 内容ID
  - content_type: 内容类型
  - relevance_score: 相关性得分
  - attached_at: 关联时间
```

  Redis 缓存设计

 # 话题缓存
```
topic:{topicId} -> Hash结构存储话题详情
topic:name:{topicName} -> String结构存储话题ID（用于名称查找）
```

 # 话题排行榜缓存
```
topic:rankings:hot -> ZSet结构存储热门话题排行榜
topic:rankings:trending -> ZSet结构存储趋势话题排行榜
```

 # 用户话题缓存
```
topic:user:{userId}:followed -> Set结构存储用户关注的话题
topic:user:{userId}:interacted -> Set结构存储用户互动过的话题
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Elasticsearch 搜索引擎

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8134

spring:
  application:
    name: service-topic
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_topic
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379
    
elasticsearch:
  host: localhost
  port: 9200

topic:
  recommendation:
    enabled: true
  search:
    enabled: true
```

 # 启动服务

```bash
cd service-topic
mvn spring-boot:run
```

或

```bash
cd service-topic
mvn clean package
java -jar target/service-topic-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-search (搜索服务)
- service-recommend (推荐服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/topic
├── TopicServiceApplication.java  # 应用入口
├── controller/                   # 控制器层
│   ├── TopicController.java      # 话题控制器
│   ├── ContentController.java    # 内容控制器
│   ├── InteractionController.java # 互动控制器
│   ├── RecommendationController.java # 推荐控制器
│   └── StatController.java       # 统计控制器
├── entity/                       # 实体类
│   ├── Topic.java                # 话题实体
│   ├── TopicContent.java         # 话题内容实体
│   ├── TopicFollower.java        # 话题关注者实体
│   ├── TopicInteraction.java     # 话题互动实体
│   ├── TopicStat.java            # 话题统计实体
│   └── TopicAlias.java           # 话题别名实体
├── repository/                   # 数据访问层
│   ├── TopicRepository.java      # 话题数据访问
│   ├── TopicContentRepository.java # 话题内容数据访问
│   ├── TopicFollowerRepository.java # 话题关注者数据访问
│   ├── TopicInteractionRepository.java # 话题互动数据访问
│   ├── TopicStatRepository.java  # 话题统计数据访问
│   └── TopicAliasRepository.java # 话题别名数据访问
└── service/                      # 业务逻辑层
    ├── TopicService.java         # 话题服务
    ├── ContentService.java       # 内容服务
    ├── InteractionService.java   # 互动服务
    ├── RecommendationService.java # 推荐服务
    └── StatService.java          # 统计服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8134/actuator/health