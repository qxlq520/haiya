# 礼物服务 (service-gift)

  服务概述

礼物服务是海牙平台直播和社交互动的重要组成部分，负责管理平台内的虚拟礼物，处理礼物发送、接收和相关统计功能。

  功能特性

1. 虚拟礼物管理
2. 礼物发送与接收
3. 礼物效果展示
4. 礼物排行榜
5. 礼物商城展示
6. 礼物收益统计
7. 礼物活动管理
8. 礼物数据报表

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2.6.3
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存

  API 接口

 # 礼物管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/gifts` | GET | 获取礼物列表 |
| `/api/gifts` | POST | 创建礼物 |
| `/api/gifts/{id}` | GET | 获取礼物详情 |
| `/api/gifts/{id}` | PUT | 更新礼物信息 |
| `/api/gifts/{id}` | DELETE | 删除礼物 |
| `/api/gifts/categories` | GET | 获取礼物分类 |

 # 礼物发送接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/gift-sends` | POST | 发送礼物 |
| `/api/gift-sends/batch` | POST | 批量发送礼物 |
| `/api/gift-sends/history` | GET | 获取礼物发送历史 |
| `/api/gift-sends/user/{userId}` | GET | 获取用户发送记录 |
| `/api/gift-sends/live/{liveId}` | GET | 获取直播间礼物记录 |

 # 礼物接收接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/gift-receives/history` | GET | 获取礼物接收历史 |
| `/api/gift-receives/user/{userId}` | GET | 获取用户接收记录 |
| `/api/gift-receives/live/{liveId}` | GET | 获取直播间收到的礼物记录 |

 # 礼物统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/gift-stats/user-rankings` | GET | 获取用户礼物排行榜 |
| `/api/gift-stats/gift-rankings` | GET | 获取礼物排行榜 |
| `/api/gift-stats/daily-summary` | GET | 获取每日礼物汇总 |
| `/api/gift-stats/live-summary/{liveId}` | GET | 获取直播间礼物汇总 |

  数据库设计

 # gifts 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 礼物名称 |
| description | TEXT | 礼物描述 |
| price | DECIMAL(10,2) | 礼物价格 |
| image_url | VARCHAR(500) | 礼物图片URL |
| animation_url | VARCHAR(500) | 动画URL |
| category_id | BIGINT | 分类ID |
| effects | TEXT | 特效配置（JSON格式） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DEPRECATED） |
| sort_order | INT | 排序 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # gift_categories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分类名称 |
| description | TEXT | 分类描述 |
| icon_url | VARCHAR(500) | 图标URL |
| sort_order | INT | 排序 |
| is_active | BOOLEAN | 是否激活 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # gift_sends 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| gift_id | BIGINT | 礼物ID |
| sender_id | BIGINT | 发送者ID |
| receiver_id | BIGINT | 接收者ID |
| live_id | BIGINT | 直播间ID |
| quantity | INT | 数量 |
| total_price | DECIMAL(10,2) | 总价 |
| message | VARCHAR(500) | 留言 |
| status | VARCHAR(20) | 状态（PENDING, COMPLETED, FAILED） |
| sent_at | BIGINT | 发送时间 |
| created_at | BIGINT | 创建时间 |

 # gift_receives 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| gift_send_id | BIGINT | 礼物发送ID |
| gift_id | BIGINT | 礼物ID |
| sender_id | BIGINT | 发送者ID |
| receiver_id | BIGINT | 接收者ID |
| live_id | BIGINT | 直播间ID |
| quantity | INT | 数量 |
| total_price | DECIMAL(10,2) | 总价 |
| received_at | BIGINT | 接收时间 |
| created_at | BIGINT | 创建时间 |

 # gift_wallets 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| balance | DECIMAL(15,2) | 余额 |
| total_charged | DECIMAL(15,2) | 累计充值 |
| total_spent | DECIMAL(15,2) | 累计消费 |
| updated_at | BIGINT | 更新时间 |
| created_at | BIGINT | 创建时间 |

 # gift_transactions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| transaction_type | VARCHAR(20) | 交易类型（CHARGE, SPEND, REFUND） |
| amount | DECIMAL(10,2) | 金额 |
| balance_after | DECIMAL(15,2) | 交易后余额 |
| related_id | BIGINT | 关联ID |
| description | TEXT | 描述 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 礼物排行榜缓存
```
gift:rankings:daily:{date} -> ZSet结构存储每日排行榜
gift:rankings:weekly:{week} -> ZSet结构存储每周排行榜
gift:rankings:monthly:{month} -> ZSet结构存储每月排行榜
```

 # 用户礼物统计缓存
```
gift:stats:user:{userId} -> Hash结构存储用户礼物统计
  - sent_count: 发送数量
  - received_count: 接收数量
  - sent_amount: 发送金额
  - received_amount: 接收金额
```

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
  port: 8113

spring:
  application:
    name: service-gift
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_gift
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
cd service-gift
mvn spring-boot:run
```

或

```bash
cd service-gift
mvn clean package
java -jar target/service-gift-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-live (直播服务)
- service-payment (支付服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/gift
├── GiftServiceApplication.java  # 应用入口
├── controller/                  # 控制器层
│   ├── GiftController.java      # 礼物控制器
│   ├── SendController.java      # 发送控制器
│   ├── ReceiveController.java   # 接收控制器
│   └── StatController.java      # 统计控制器
├── entity/                      # 实体类
│   ├── Gift.java                # 礼物实体
│   ├── GiftCategory.java        # 礼物分类实体
│   ├── GiftSend.java            # 礼物发送实体
│   ├── GiftReceive.java         # 礼物接收实体
│   ├── GiftWallet.java          # 礼物钱包实体
│   └── GiftTransaction.java     # 礼物交易实体
├── repository/                  # 数据访问层
│   ├── GiftRepository.java      # 礼物数据访问
│   ├── GiftCategoryRepository.java # 礼物分类数据访问
│   ├── GiftSendRepository.java  # 礼物发送数据访问
│   ├── GiftReceiveRepository.java # 礼物接收数据访问
│   ├── GiftWalletRepository.java # 礼物钱包数据访问
│   └── GiftTransactionRepository.java # 礼物交易数据访问
└── service/                     # 业务逻辑层
    ├── GiftService.java         # 礼物服务
    ├── SendService.java         # 发送服务
    ├── ReceiveService.java      # 接收服务
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
GET http://localhost:8113/actuator/health