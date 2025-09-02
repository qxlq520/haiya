# 推荐服务 (service-recommend)

  服务概述

推荐服务是海牙平台的核心服务之一，负责处理个性化内容推荐功能，包括用户行为分析、推荐算法实现、个性化推荐内容生成等核心功能。

  功能特性

1. 用户行为数据收集与分析
2. 多种推荐算法实现（协同过滤、内容推荐等）
3. 个性化推荐内容生成
4. 推荐效果评估与优化
5. 热门内容推荐

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Mahout/Spark MLlib 机器学习库

  API 接口

 # 推荐接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/recommendations` | GET | 获取推荐内容列表 |
| `/api/recommendations/user/{userId}` | GET | 获取用户个性化推荐 |
| `/api/recommendations/hot` | GET | 获取热门推荐 |
| `/api/recommendations/similar/{videoId}` | GET | 获取相似内容推荐 |

 # 用户行为接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/user-behaviors` | POST | 上报用户行为 |
| `/api/user-behaviors/{userId}` | GET | 获取用户行为数据 |

 # 算法管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/algorithms` | GET | 获取算法列表 |
| `/api/algorithms/{id}` | GET | 获取算法详情 |
| `/api/algorithms/{id}/enable` | POST | 启用算法 |
| `/api/algorithms/{id}/disable` | POST | 禁用算法 |

  数据库设计

 # recommendations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| target_id | BIGINT | 推荐目标ID |
| target_type | VARCHAR(50) | 推荐目标类型（VIDEO, USER等） |
| score | DECIMAL(5,2) | 推荐分数 |
| algorithm | VARCHAR(100) | 使用的算法 |
| reason | VARCHAR(255) | 推荐理由 |
| created_at | BIGINT | 创建时间 |

 # user_behaviors 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| target_id | BIGINT | 目标ID |
| target_type | VARCHAR(50) | 目标类型（VIDEO, USER等） |
| behavior_type | VARCHAR(50) | 行为类型（VIEW, LIKE, COMMENT, SHARE等） |
| duration | INT | 观看时长（秒） |
| created_at | BIGINT | 创建时间 |

 # algorithms 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 算法名称 |
| description | TEXT | 算法描述 |
| type | VARCHAR(50) | 算法类型（COLLABORATIVE_FILTERING, CONTENT_BASED等） |
| params | TEXT | 算法参数（JSON格式） |
| is_enabled | BOOLEAN | 是否启用 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8088

spring:
  application:
    name: service-recommend
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_recommend
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379
```

 # 启动服务

```bash
cd service-recommend
mvn spring-boot:run
```

或

```bash
cd service-recommend
mvn clean package
java -jar target/service-recommend-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-like (点赞服务)
- service-comment (评论服务)
- service-follow (关注服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/recommend
├── RecommendServiceApplication.java  # 应用入口
├── config/                           # 配置类
├── controller/                       # 控制器层
│   └── RecommendController.java      # 推荐控制器
├── entity/                           # 实体类
│   ├── Recommendation.java           # 推荐实体
│   ├── UserBehavior.java             # 用户行为实体
│   └── Algorithm.java                # 算法实体
├── service/                          # 业务逻辑层
│   ├── RecommendService.java         # 推荐服务
│   └── AlgorithmService.java         # 算法服务
└── util/                             # 工具类
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8088/actuator/health