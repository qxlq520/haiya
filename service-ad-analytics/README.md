# 广告分析服务 (service-ad-analytics)

  服务概述

广告分析服务是海牙平台的商业分析服务之一，负责处理广告效果分析、广告数据统计、广告报表生成等核心功能。

  功能特性

1. 广告效果数据分析
2. 广告投放效果统计
3. 广告收入分析
4. 广告主数据报告
5. 广告ROI计算
6. 实时广告数据监控

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎

  API 接口

 # 广告数据分析接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ad-analytics/overview` | GET | 获取广告数据概览 |
| `/api/ad-analytics/performance` | GET | 获取广告性能数据 |
| `/api/ad-analytics/revenue` | GET | 获取广告收入数据 |
| `/api/ad-analytics/roi` | GET | 获取广告ROI数据 |

 # 广告报表接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ad-reports` | GET | 获取广告报表列表 |
| `/api/ad-reports` | POST | 生成广告报表 |
| `/api/ad-reports/{id}` | GET | 获取广告报表详情 |
| `/api/ad-reports/{id}/download` | GET | 下载广告报表 |

 # 实时数据接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ad-realtime/impressions` | GET | 获取实时展示数据 |
| `/api/ad-realtime/clicks` | GET | 获取实时点击数据 |
| `/api/ad-realtime/conversions` | GET | 获取实时转化数据 |

  数据库设计

 # ad_analytics_daily 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| ad_id | BIGINT | 广告ID |
| date | DATE | 日期 |
| impressions | INT | 展示次数 |
| clicks | INT | 点击次数 |
| conversions | INT | 转化次数 |
| cost | DECIMAL(10,2) | 花费 |
| revenue | DECIMAL(10,2) | 收入 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ad_reports 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 报表名称 |
| type | VARCHAR(50) | 报表类型 |
| start_date | DATE | 开始日期 |
| end_date | DATE | 结束日期 |
| file_url | VARCHAR(500) | 文件URL |
| status | VARCHAR(20) | 状态（GENERATING, COMPLETED, FAILED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ad_realtime_stats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| ad_id | BIGINT | 广告ID |
| stat_type | VARCHAR(20) | 统计类型（IMPRESSION, CLICK, CONVERSION） |
| count | INT | 数量 |
| timestamp | BIGINT | 时间戳 |
| created_at | BIGINT | 创建时间 |

  Elasticsearch 索引设计

 # ad_analytics 索引

```
{
  "ad_id": 12345,
  "advertiser_id": 67890,
  "position_id": 54321,
  "date": "2023-01-01",
  "impressions": 1000,
  "clicks": 100,
  "conversions": 10,
  "cost": 50.00,
  "revenue": 100.00,
  "created_at": 1612345678901
}
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
  port: 8093

spring:
  application:
    name: service-ad-analytics
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_ad_analytics
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
cd service-ad-analytics
mvn spring-boot:run
```

或

```bash
cd service-ad-analytics
mvn clean package
java -jar target/service-ad-analytics-1.0.0.jar
```

  服务依赖

- service-ad (广告服务)
- service-user (用户服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/ad/analytics
├── AdAnalyticsServiceApplication.java  # 应用入口
├── controller/                         # 控制器层
│   ├── AdAnalyticsController.java      # 广告分析控制器
│   ├── AdReportController.java         # 广告报表控制器
│   └── AdRealtimeController.java       # 实时数据控制器
├── entity/                             # 实体类
│   ├── AdAnalyticsDaily.java           # 日常广告分析实体
│   ├── AdReport.java                   # 广告报表实体
│   └── AdRealtimeStats.java            # 实时统计数据实体
├── repository/                         # 数据访问层
│   ├── AdAnalyticsDailyRepository.java # 日常广告分析数据访问
│   ├── AdReportRepository.java         # 广告报表数据访问
│   └── AdRealtimeStatsRepository.java  # 实时统计数据访问
├── service/                            # 业务逻辑层
│   ├── AdAnalyticsService.java         # 广告分析服务
│   ├── AdReportService.java            # 广告报表服务
│   └── AdRealtimeService.java          # 实时数据服务
└── util/                               # 工具类
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8093/actuator/health