# 广告服务 (service-ad)

  服务概述

广告服务是海牙平台的商业服务之一，负责处理平台广告投放、广告展示、广告点击统计等核心功能。

  功能特性

1. 广告位管理
2. 广告投放管理
3. 广告展示策略
4. 广告点击统计
5. 广告效果分析
6. 广告主管理

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存

  API 接口

 # 广告位接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ad-positions` | GET | 获取广告位列表 |
| `/api/ad-positions` | POST | 创建广告位 |
| `/api/ad-positions/{id}` | GET | 获取广告位详情 |
| `/api/ad-positions/{id}` | PUT | 更新广告位信息 |
| `/api/ad-positions/{id}` | DELETE | 删除广告位 |

 # 广告接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ads` | GET | 获取广告列表 |
| `/api/ads` | POST | 创建广告 |
| `/api/ads/{id}` | GET | 获取广告详情 |
| `/api/ads/{id}` | PUT | 更新广告信息 |
| `/api/ads/{id}` | DELETE | 删除广告 |
| `/api/ads/position/{positionId}` | GET | 获取指定广告位的广告 |

 # 广告统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ad-stats/impressions` | POST | 上报广告展示 |
| `/api/ad-stats/clicks` | POST | 上报广告点击 |
| `/api/ad-stats/report` | GET | 获取广告效果报告 |

  数据库设计

 # ad_positions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 广告位名称 |
| description | TEXT | 广告位描述 |
| width | INT | 广告位宽度 |
| height | INT | 广告位高度 |
| position_key | VARCHAR(100) | 广告位标识 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ads 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| position_id | BIGINT | 广告位ID |
| advertiser_id | BIGINT | 广告主ID |
| title | VARCHAR(255) | 广告标题 |
| description | TEXT | 广告描述 |
| image_url | VARCHAR(500) | 广告图片URL |
| target_url | VARCHAR(500) | 跳转URL |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| budget | DECIMAL(10,2) | 预算 |
| cpc | DECIMAL(10,2) | 点击单价 |
| cpm | DECIMAL(10,2) | 千次展示单价 |
| status | VARCHAR(20) | 状态（PENDING, ACTIVE, PAUSED, EXPIRED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ad_impressions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| ad_id | BIGINT | 广告ID |
| user_id | BIGINT | 用户ID |
| ip_address | VARCHAR(50) | IP地址 |
| user_agent | TEXT | 用户代理 |
| created_at | BIGINT | 创建时间 |

 # ad_clicks 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| ad_id | BIGINT | 广告ID |
| user_id | BIGINT | 用户ID |
| ip_address | VARCHAR(50) | IP地址 |
| user_agent | TEXT | 用户代理 |
| created_at | BIGINT | 创建时间 |

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
  port: 8092

spring:
  application:
    name: service-ad
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_ad
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
cd service-ad
mvn spring-boot:run
```

或

```bash
cd service-ad
mvn clean package
java -jar target/service-ad-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/ad
├── AdServiceApplication.java     # 应用入口
├── controller/                   # 控制器层
│   ├── AdPositionController.java # 广告位控制器
│   ├── AdController.java         # 广告控制器
│   └── AdStatController.java     # 广告统计控制器
├── entity/                       # 实体类
│   ├── AdPosition.java           # 广告位实体
│   ├── Ad.java                   # 广告实体
│   ├── AdImpression.java         # 广告展示实体
│   └── AdClick.java              # 广告点击实体
├── repository/                   # 数据访问层
│   ├── AdPositionRepository.java # 广告位数据访问
│   ├── AdRepository.java         # 广告数据访问
│   ├── AdImpressionRepository.java # 广告展示数据访问
│   └── AdClickRepository.java    # 广告点击数据访问
└── service/                      # 业务逻辑层
    ├── AdPositionService.java    # 广告位服务
    ├── AdService.java            # 广告服务
    └── AdStatService.java        # 广告统计服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8092/actuator/health