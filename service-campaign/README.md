# 活动服务 (service-campaign)

  服务概述

活动服务是海牙平台的运营服务之一，负责处理平台营销活动的创建、管理、执行和效果分析等核心功能。

  功能特性

1. 营销活动创建与管理
2. 活动参与条件配置
3. 活动奖励发放
4. 活动数据统计与分析
5. 活动效果评估
6. 活动模板管理
7. 活动定时任务
8. 活动用户参与记录

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Quartz 定时任务框架

  API 接口

 # 活动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/campaigns` | GET | 获取活动列表 |
| `/api/campaigns` | POST | 创建活动 |
| `/api/campaigns/{id}` | GET | 获取活动详情 |
| `/api/campaigns/{id}` | PUT | 更新活动信息 |
| `/api/campaigns/{id}` | DELETE | 删除活动 |
| `/api/campaigns/{id}/status` | PUT | 更新活动状态 |

 # 活动参与接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/campaign-participations` | POST | 参与活动 |
| `/api/campaign-participations/batch` | POST | 批量参与活动 |
| `/api/campaign-participations/user/{userId}` | GET | 获取用户参与记录 |
| `/api/campaign-participations/campaign/{campaignId}` | GET | 获取活动参与记录 |

 # 活动奖励接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/campaign-rewards` | POST | 发放活动奖励 |
| `/api/campaign-rewards/batch` | POST | 批量发放活动奖励 |
| `/api/campaign-rewards/user/{userId}` | GET | 获取用户奖励记录 |
| `/api/campaign-rewards/campaign/{campaignId}` | GET | 获取活动奖励记录 |

 # 活动统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/campaign-stats/{campaignId}` | GET | 获取活动统计数据 |
| `/api/campaign-stats/{campaignId}/participants` | GET | 获取活动参与用户列表 |
| `/api/campaign-stats/{campaignId}/performance` | GET | 获取活动表现数据 |

  数据库设计

 # campaigns 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 活动名称 |
| description | TEXT | 活动描述 |
| type | VARCHAR(50) | 活动类型（DISCOUNT, LOTTERY, CHALLENGE等） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| status | VARCHAR(20) | 状态（DRAFT, ACTIVE, PAUSED, FINISHED, CANCELLED） |
| participation_conditions | TEXT | 参与条件（JSON格式） |
| reward_rules | TEXT | 奖励规则（JSON格式） |
| creator_id | BIGINT | 创建者ID |
| max_participants | INT | 最大参与人数 |
| current_participants | INT | 当前参与人数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # campaign_participations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| campaign_id | BIGINT | 活动ID |
| user_id | BIGINT | 用户ID |
| participation_time | BIGINT | 参与时间 |
| status | VARCHAR(20) | 状态（PARTICIPATED, COMPLETED, CANCELLED） |
| completion_time | BIGINT | 完成时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # campaign_rewards 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| campaign_id | BIGINT | 活动ID |
| user_id | BIGINT | 用户ID |
| reward_type | VARCHAR(50) | 奖励类型（COUPON, POINTS,实物等） |
| reward_value | TEXT | 奖励值 |
| status | VARCHAR(20) | 状态（PENDING, ISSUED, CLAIMED, EXPIRED） |
| issued_time | BIGINT | 发放时间 |
| claimed_time | BIGINT | 领取时间 |
| expiry_time | BIGINT | 过期时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # campaign_templates 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模板名称 |
| description | TEXT | 模板描述 |
| type | VARCHAR(50) | 模板类型 |
| template_data | TEXT | 模板数据（JSON格式） |
| creator_id | BIGINT | 创建者ID |
| is_public | BOOLEAN | 是否公开 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # campaign_stats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| campaign_id | BIGINT | 活动ID |
| stat_type | VARCHAR(50) | 统计类型 |
| stat_value | DECIMAL(15,2) | 统计值 |
| record_date | DATE | 记录日期 |
| created_at | BIGINT | 创建时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Quartz 定时任务框架

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8100

spring:
  application:
    name: service-campaign
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_campaign
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
cd service-campaign
mvn spring-boot:run
```

或

```bash
cd service-campaign
mvn clean package
java -jar target/service-campaign-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-product (产品服务)
- service-order (订单服务)
- service-notification (通知服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/campaign
├── CampaignServiceApplication.java  # 应用入口
├── controller/                      # 控制器层
│   ├── CampaignController.java      # 活动控制器
│   ├── ParticipationController.java # 参与控制器
│   ├── RewardController.java        # 奖励控制器
│   └── StatController.java          # 统计控制器
├── entity/                          # 实体类
│   ├── Campaign.java                # 活动实体
│   ├── Participation.java           # 参与实体
│   ├── Reward.java                  # 奖励实体
│   ├── CampaignTemplate.java        # 活动模板实体
│   └── CampaignStat.java            # 活动统计实体
├── repository/                      # 数据访问层
│   ├── CampaignRepository.java      # 活动数据访问
│   ├── ParticipationRepository.java # 参与数据访问
│   ├── RewardRepository.java        # 奖励数据访问
│   ├── CampaignTemplateRepository.java # 活动模板数据访问
│   └── CampaignStatRepository.java  # 活动统计数据访问
└── service/                         # 业务逻辑层
    ├── CampaignService.java         # 活动服务
    ├── ParticipationService.java    # 参与服务
    ├── RewardService.java           # 奖励服务
    └── StatService.java             # 统计服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8100/actuator/health