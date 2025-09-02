# 搜索服务 (service-search)

  服务概述

搜索服务是海牙平台的核心服务之一，负责处理平台内容搜索功能，包括视频搜索、用户搜索、话题搜索等核心功能。

  功能特性

1. 全文搜索引擎
2. 视频搜索
3. 用户搜索
4. 话题搜索
5. 搜索结果排序与过滤
6. 搜索建议与自动补全
7. 搜索热词统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Elasticsearch 搜索引擎
- Redis 缓存

  API 接口

 # 搜索接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/search` | GET | 综合搜索 |
| `/api/search/videos` | GET | 视频搜索 |
| `/api/search/users` | GET | 用户搜索 |
| `/api/search/topics` | GET | 话题搜索 |
| `/api/search/suggestions` | GET | 搜索建议 |

 # 搜索统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/search/hotwords` | GET | 获取搜索热词 |
| `/api/search/history/{userId}` | GET | 获取用户搜索历史 |
| `/api/search/history/{userId}` | DELETE | 清空用户搜索历史 |

  数据库设计

 # search_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| keyword | VARCHAR(255) | 搜索关键词 |
| search_type | VARCHAR(50) | 搜索类型（ALL, VIDEO, USER, TOPIC） |
| result_count | INT | 结果数量 |
| ip_address | VARCHAR(50) | IP地址 |
| user_agent | TEXT | 用户代理 |
| created_at | BIGINT | 创建时间 |

 # hot_words 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| word | VARCHAR(255) | 热词 |
| search_count | INT | 搜索次数 |
| date | DATE | 日期 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # search_history 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| keyword | VARCHAR(255) | 搜索关键词 |
| created_at | BIGINT | 创建时间 |

  Elasticsearch 索引设计

 # videos 索引

```
{
  "title": "视频标题",
  "description": "视频描述",
  "tags": ["标签1", "标签2"],
  "user_id": 12345,
  "username": "用户名",
  "created_at": 1612345678901,
  "view_count": 1000,
  "like_count": 100,
  "comment_count": 50
}
```

 # users 索引

```
{
  "user_id": 12345,
  "username": "用户名",
  "nickname": "昵称",
  "bio": "个人简介",
  "follower_count": 1000,
  "video_count": 50
}
```

 # topics 索引

```
{
  "topic_id": 12345,
  "name": "话题名称",
  "description": "话题描述",
  "video_count": 1000,
  "follower_count": 500,
  "created_at": 1612345678901
}
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Elasticsearch 7.x
- Redis 缓存服务

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8089

spring:
  application:
    name: service-search
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_search
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
```

 # 启动服务

```bash
cd service-search
mvn spring-boot:run
```

或

```bash
cd service-search
mvn clean package
java -jar target/service-search-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-topic (话题服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/search
├── SearchServiceApplication.java  # 应用入口
├── controller/                    # 控制器层
│   └── SearchController.java      # 搜索控制器
├── entity/                        # 实体类
│   ├── SearchLog.java             # 搜索日志实体
│   ├── HotWord.java               # 热词实体
│   └── SearchHistory.java         # 搜索历史实体
├── repository/                    # 数据访问层
│   ├── SearchLogRepository.java   # 搜索日志数据访问
│   └── HotWordRepository.java     # 热词数据访问
└── service/                       # 业务逻辑层
    ├── SearchService.java         # 搜索服务
    └── ElasticsearchService.java  # Elasticsearch服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8089/actuator/health