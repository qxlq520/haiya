# 支付服务 (service-payment)

  服务概述

支付服务是海牙平台的核心服务之一，负责处理平台支付功能，包括支付接口集成、订单支付处理、交易记录管理等核心功能。

  功能特性

1. 多种支付渠道集成（微信支付、支付宝等）
2. 订单支付处理
3. 交易记录管理
4. 退款处理
5. 支付状态回调处理
6. 财务对账功能

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- RabbitMQ 消息队列

  API 接口

 # 支付接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/payments` | POST | 创建支付订单 |
| `/api/payments/{id}` | GET | 获取支付订单详情 |
| `/api/payments/{id}/status` | GET | 获取支付状态 |
| `/api/payments/callback` | POST | 支付回调接口 |

 # 退款接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/refunds` | POST | 创建退款申请 |
| `/api/refunds/{id}` | GET | 获取退款详情 |
| `/api/refunds/{id}/status` | GET | 获取退款状态 |

 # 查询接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transactions` | GET | 获取交易记录列表 |
| `/api/transactions/{id}` | GET | 获取交易记录详情 |

  数据库设计

 # payments 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_id | BIGINT | 订单ID |
| user_id | BIGINT | 用户ID |
| amount | DECIMAL(10,2) | 支付金额 |
| currency | VARCHAR(10) | 货币类型 |
| payment_method | VARCHAR(50) | 支付方式（WECHAT, ALIPAY等） |
| payment_no | VARCHAR(100) | 支付流水号 |
| status | VARCHAR(20) | 支付状态（PENDING, SUCCESS, FAILED, CLOSED） |
| paid_at | BIGINT | 支付完成时间 |
| expired_at | BIGINT | 过期时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # refunds 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| payment_id | BIGINT | 支付ID |
| order_id | BIGINT | 订单ID |
| user_id | BIGINT | 用户ID |
| amount | DECIMAL(10,2) | 退款金额 |
| currency | VARCHAR(10) | 货币类型 |
| refund_no | VARCHAR(100) | 退款流水号 |
| reason | VARCHAR(255) | 退款原因 |
| status | VARCHAR(20) | 退款状态（PENDING, SUCCESS, FAILED, CLOSED） |
| refunded_at | BIGINT | 退款完成时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transactions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| order_id | BIGINT | 订单ID |
| payment_id | BIGINT | 支付ID |
| type | VARCHAR(20) | 交易类型（PAYMENT, REFUND） |
| amount | DECIMAL(10,2) | 交易金额 |
| currency | VARCHAR(10) | 货币类型 |
| status | VARCHAR(20) | 交易状态 |
| transaction_no | VARCHAR(100) | 交易流水号 |
| completed_at | BIGINT | 完成时间 |
| created_at | BIGINT | 创建时间 |

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
  port: 8090

spring:
  application:
    name: service-payment
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_payment
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
```

 # 启动服务

```bash
cd service-payment
mvn spring-boot:run
```

或

```bash
cd service-payment
mvn clean package
java -jar target/service-payment-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-order (订单服务)
- service-ecommerce (电商服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/payment
├── PaymentServiceApplication.java  # 应用入口
├── controller/                     # 控制器层
│   └── PaymentController.java      # 支付控制器
├── entity/                         # 实体类
│   ├── Payment.java                # 支付实体
│   ├── Refund.java                 # 退款实体
│   └── Transaction.java            # 交易实体
├── repository/                     # 数据访问层
│   ├── PaymentRepository.java      # 支付数据访问
│   ├── RefundRepository.java       # 退款数据访问
│   └── TransactionRepository.java  # 交易数据访问
└── service/                        # 业务逻辑层
    ├── PaymentService.java         # 支付服务
    ├── RefundService.java          # 退款服务
    ├── TransactionService.java     # 交易服务
    └── PaymentCallbackService.java # 支付回调服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8090/actuator/health