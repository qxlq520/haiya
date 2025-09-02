# 分析服务 (service-analytics)

  服务概述

分析服务是海牙平台的核心分析服务之一，负责处理平台数据收集、用户行为分析、内容质量评估、趋势发现、竞品分析等核心功能。

  功能特性

1. 用户行为数据收集与分析
2. 内容质量评估
3. 热点趋势发现
4. 竭品分析
5. 数据报告生成
6. 用户分群分析
7. 运营活动效果分析

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎

  API 接口

 # 用户分群接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/user-segments` | POST | 创建用户分群 |
| `/api/user-segments` | GET | 获取用户分群列表 |
| `/api/user-segments/{id}/users` | GET | 获取分群用户列表 |

 # 运营活动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/operation-campaigns` | POST | 创建运营活动 |
| `/api/operation-campaigns` | GET | 获取运营活动列表 |
| `/api/operation-campaigns/{id}` | GET | 获取运营活动详情 |

 # 内容质量接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/content-quality` | POST | 评估内容质量 |
| `/api/content-quality/batch` | POST | 批量评估内容质量 |
| `/api/content-quality/{videoId}` | GET | 获取内容质量报告 |

 # 热点趋势接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/trends` | GET | 发现热点 |
| `/api/trends/{id}` | GET | 获取热点详情 |

 # 竞品分析接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/competitor-analysis` | POST | 进行竞品分析 |
| `/api/competitor-analysis/{competitorName}` | GET | 获取竞品分析报告 |

 # 数据报告接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/reports` | POST | 生成数据报告 |
| `/api/reports` | GET | 获取报告列表 |
| `/api/reports/{id}` | GET | 获取报告详情 |
| `/api/reports/{id}/download` | GET | 下载报告 |

  数据库设计

 # user_segments 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分群名称 |
| conditions | TEXT | 分群条件（JSON格式） |
| user_count | INT | 用户数量 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # segment_users 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| segment_id | BIGINT | 分群ID |
| user_id | BIGINT | 用户ID |
| created_at | BIGINT | 创建时间 |

 # operation_campaigns 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 活动名称 |
| description | TEXT | 活动描述 |
| segment_id | BIGINT | 目标用户群ID |
| strategy | TEXT | 活动策略 |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| status | VARCHAR(20) | 活动状态 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # content_quality 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| video_id | BIGINT | 视频ID |
| quality_score | DECIMAL(5,2) | 质量评分 |
| engagement_score | DECIMAL(5,2) | 互动评分 |
| completion_rate | DECIMAL(5,2) | 完播率 |
| like_rate | DECIMAL(5,2) | 点赞率 |
| comment_rate | DECIMAL(5,2) | 评论率 |
| share_rate | DECIMAL(5,2) | 分享率 |
| analyzed_at | BIGINT | 分析时间 |
| created_at | BIGINT | 创建时间 |

 # trends 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| keyword | VARCHAR(255) | 关键词 |
| type | VARCHAR(50) | 热点类型 |
|热度值| INT | 热度值 |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| status | VARCHAR(20) | 状态 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # competitor_analysis 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| competitor_name | VARCHAR(100) | 竞品名称 |
| metric_type | VARCHAR(50) | 指标类型 |
| metric_value | DECIMAL(10,2) | 指标值 |
| analysis_date | DATE | 分析日期 |
| created_at | BIGINT | 创建时间 |

 # reports 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 报告名称 |
| type | VARCHAR(50) | 报告类型 |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| file_url | VARCHAR(500) | 文件URL |
| status | VARCHAR(20) | 状态 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

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
  port: 8094

spring:
  application:
    name: service-analytics
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_analytics
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
cd service-analytics
mvn spring-boot:run
```

或

```bash
cd service-analytics
mvn clean package
java -jar target/service-analytics-1.0.0.jar
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
src/main/java/com/haiya/analytics
├── AnalyticsServiceApplication.java  # 应用入口
├── controller/                       # 控制器层
│   └── AnalyticsController.java      # 分析控制器
├── entity/                           # 实体类
│   ├── UserSegment.java              # 用户分群实体
│   ├── OperationCampaign.java        # 运营活动实体
│   ├── ContentQuality.java           # 内容质量实体
│   ├── Trend.java                    # 热点趋势实体
│   └── CompetitorAnalysis.java       # 竞品分析实体
├── repository/                       # 数据访问层
│   └── AnalyticsRepository.java      # 分析数据访问
└── service/                          # 业务逻辑层
    ├── AnalyticsService.java         # 分析服务接口
    ├── AnalyticsServiceImpl.java     # 分析服务实现
    ├── UserSegmentService.java       # 用户分群服务
    ├── CampaignService.java          # 运营活动服务
    └── ReportService.java            # 报告服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8094/actuator/health