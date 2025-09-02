# 创作者工具服务 (service-creator-tools)

  服务概述

创作者工具服务是海牙平台为内容创作者提供的专业工具服务，包括数据分析、内容优化建议、粉丝互动工具、收益管理等核心功能。

  功能特性

1. 创作者数据仪表板
2. 内容表现分析
3. 粉丝画像分析
4. 内容优化建议
5. 粉丝互动工具
6. 收益管理与分析
7. 创作趋势分析
8. 竞品对比分析

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎

  API 接口

 # 数据仪表板接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/creator-dashboard/overview` | GET | 获取创作者数据概览 |
| `/api/creator-dashboard/trends` | GET | 获取创作趋势数据 |
| `/api/creator-dashboard/recent-performance` | GET | 获取近期表现数据 |

 # 内容分析接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/content-analytics` | GET | 获取内容分析数据 |
| `/api/content-analytics/{contentId}` | GET | 获取指定内容分析详情 |
| `/api/content-analytics/comparison` | GET | 获取内容对比分析 |

 # 粉丝分析接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/fan-analytics` | GET | 获取粉丝分析数据 |
| `/api/fan-analytics/demographics` | GET | 获取粉丝画像数据 |
| `/api/fan-analytics/growth` | GET | 获取粉丝增长数据 |

 # 优化建议接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/optimization-suggestions` | GET | 获取优化建议列表 |
| `/api/optimization-suggestions/{contentId}` | GET | 获取指定内容的优化建议 |
| `/api/optimization-suggestions/apply` | POST | 应用优化建议 |

 # 收益管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/creator-earnings` | GET | 获取创作者收益数据 |
| `/api/creator-earnings/details` | GET | 获取收益详情 |
| `/api/creator-earnings/withdraw` | POST | 提现申请 |

  数据库设计

 # creator_profiles 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| nickname | VARCHAR(100) | 创作者昵称 |
| bio | TEXT | 个人简介 |
| category | VARCHAR(50) | 创作领域 |
| level | VARCHAR(20) | 创作者等级 |
| follower_count | INT | 粉丝数 |
| content_count | INT | 内容数 |
| total_views | BIGINT | 总观看数 |
| total_likes | BIGINT | 总点赞数 |
| total_earnings | DECIMAL(15,2) | 总收益 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # content_performance 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| content_type | VARCHAR(50) | 内容类型 |
| creator_id | BIGINT | 创作者ID |
| views | INT | 观看数 |
| likes | INT | 点赞数 |
| comments | INT | 评论数 |
| shares | INT | 分享数 |
| completion_rate | DECIMAL(5,2) | 完播率 |
| avg_watch_time | INT | 平均观看时长 |
| engagement_rate | DECIMAL(5,2) | 互动率 |
| recorded_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

 # fan_analytics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| creator_id | BIGINT | 创作者ID |
| date | DATE | 日期 |
| new_followers | INT | 新增粉丝数 |
| unfollowers | INT | 取关数 |
| net_followers | INT | 净增粉丝数 |
| total_followers | INT | 总粉丝数 |
| active_followers | INT | 活跃粉丝数 |
| created_at | BIGINT | 创建时间 |

 # optimization_suggestions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| creator_id | BIGINT | 创作者ID |
| content_id | BIGINT | 内容ID |
| suggestion_type | VARCHAR(50) | 建议类型 |
| title | VARCHAR(255) | 建议标题 |
| description | TEXT | 建议描述 |
| priority | VARCHAR(20) | 优先级（LOW, MEDIUM, HIGH） |
| status | VARCHAR(20) | 状态（PENDING, APPLIED, DISMISSED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # creator_earnings 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| creator_id | BIGINT | 创作者ID |
| period | VARCHAR(20) | 结算周期（DAILY, WEEKLY, MONTHLY） |
| amount | DECIMAL(10,2) | 金额 |
| currency | VARCHAR(10) | 货币 |
| breakdown | TEXT | 收益明细（JSON格式） |
| settled_at | BIGINT | 结算时间 |
| created_at | BIGINT | 创建时间 |

 # creator_withdrawals 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| creator_id | BIGINT | 创作者ID |
| amount | DECIMAL(10,2) | 提现金额 |
| currency | VARCHAR(10) | 货币 |
| status | VARCHAR(20) | 状态（PENDING, APPROVED, REJECTED, COMPLETED） |
| withdrawal_method | VARCHAR(50) | 提现方式 |
| account_info | TEXT | 账户信息（JSON格式） |
| processed_at | BIGINT | 处理时间 |
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
  port: 8104

spring:
  application:
    name: service-creator-tools
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_creator_tools
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
cd service-creator-tools
mvn spring-boot:run
```

或

```bash
cd service-creator-tools
mvn clean package
java -jar target/service-creator-tools-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-audio (音频服务)
- service-analytics (分析服务)
- service-payment (支付服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/creator/tools
├── CreatorToolsApplication.java  # 应用入口
├── controller/                   # 控制器层
│   ├── DashboardController.java  # 仪表板控制器
│   ├── ContentAnalyticsController.java # 内容分析控制器
│   ├── FanAnalyticsController.java # 粉丝分析控制器
│   ├── OptimizationController.java # 优化建议控制器
│   └── EarningController.java    # 收益管理控制器
├── entity/                       # 实体类
│   ├── CreatorProfile.java       # 创作者档案实体
│   ├── ContentPerformance.java   # 内容表现实体
│   ├── FanAnalytics.java         # 粉丝分析实体
│   ├── OptimizationSuggestion.java # 优化建议实体
│   ├── CreatorEarning.java       # 创作者收益实体
│   └── CreatorWithdrawal.java    # 创作者提现实体
├── repository/                   # 数据访问层
│   ├── CreatorProfileRepository.java # 创作者档案数据访问
│   ├── ContentPerformanceRepository.java # 内容表现数据访问
│   ├── FanAnalyticsRepository.java # 粉丝分析数据访问
│   ├── OptimizationSuggestionRepository.java # 优化建议数据访问
│   ├── CreatorEarningRepository.java # 创作者收益数据访问
│   └── CreatorWithdrawalRepository.java # 创作者提现数据访问
└── service/                      # 业务逻辑层
    ├── DashboardService.java     # 仪表板服务
    ├── ContentAnalyticsService.java # 内容分析服务
    ├── FanAnalyticsService.java  # 粉丝分析服务
    ├── OptimizationService.java  # 优化建议服务
    └── EarningService.java       # 收益管理服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8104/actuator/health