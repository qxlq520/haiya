# 订单服务 (service-order)

  服务概述

订单服务是海牙平台电商系统的核心服务，负责处理用户订单的创建、支付、发货、退款等全生命周期管理，确保订单流程的顺畅和数据的准确性。

  功能特性

1. 订单创建与管理
2. 订单状态跟踪
3. 订单支付处理
4. 订单发货管理
5. 退款退货处理
6. 订单查询与统计
7. 订单超时处理
8. 订单数据同步

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- RabbitMQ 消息队列

  API 接口

 # 订单管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/orders` | POST | 创建订单 |
| `/api/orders` | GET | 获取订单列表 |
| `/api/orders/{id}` | GET | 获取订单详情 |
| `/api/orders/{orderNo}` | GET | 根据订单号获取订单 |
| `/api/orders/{id}/cancel` | POST | 取消订单 |
| `/api/orders/{id}/close` | POST | 关闭订单 |

 # 订单状态接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/orders/{id}/status` | PUT | 更新订单状态 |
| `/api/orders/{id}/pay` | POST | 订单支付 |
| `/api/orders/{id}/ship` | POST | 订单发货 |
| `/api/orders/{id}/receive` | POST | 确认收货 |
| `/api/orders/{id}/refund` | POST | 申请退款 |
| `/api/orders/{id}/return` | POST | 申请退货 |

 # 订单查询接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/orders/search` | GET | 搜索订单 |
| `/api/orders/user/{userId}` | GET | 获取用户订单 |
| `/api/orders/status/{status}` | GET | 根据状态获取订单 |
| `/api/orders/date-range` | GET | 获取日期范围内的订单 |

 # 订单统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/order-stats/overview` | GET | 获取订单概览统计 |
| `/api/order-stats/sales` | GET | 获取销售统计 |
| `/api/order-stats/user-behavior` | GET | 获取用户行为统计 |
| `/api/order-stats/product-performance` | GET | 获取商品表现统计 |

  数据库设计

 # orders 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_no | VARCHAR(50) | 订单号 |
| user_id | BIGINT | 用户ID |
| merchant_id | BIGINT | 商户ID |
| total_amount | DECIMAL(10,2) | 订单总金额 |
| discount_amount | DECIMAL(10,2) | 优惠金额 |
| shipping_amount | DECIMAL(10,2) | 运费 |
| payable_amount | DECIMAL(10,2) | 应付金额 |
| status | VARCHAR(20) | 订单状态（PENDING, PAID, SHIPPED, RECEIVED, CLOSED, CANCELLED） |
| payment_method | VARCHAR(20) | 支付方式（ALIPAY, WECHAT, CREDIT_CARD等） |
| shipping_address | TEXT | 收货地址（JSON格式） |
| remark | TEXT | 订单备注 |
| created_at | BIGINT | 创建时间 |
| paid_at | BIGINT | 支付时间 |
| shipped_at | BIGINT | 发货时间 |
| received_at | BIGINT | 收货时间 |
| closed_at | BIGINT | 关闭时间 |
| updated_at | BIGINT | 更新时间 |

 # order_items 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_id | BIGINT | 订单ID |
| product_id | BIGINT | 商品ID |
| product_sku_id | BIGINT | 商品SKU ID |
| product_name | VARCHAR(255) | 商品名称 |
| product_image | VARCHAR(500) | 商品图片URL |
| quantity | INT | 数量 |
| price | DECIMAL(10,2) | 单价 |
| total_price | DECIMAL(10,2) | 总价 |
| status | VARCHAR(20) | 状态（NORMAL, REFUND_APPLIED, REFUNDED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # order_payments 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_id | BIGINT | 订单ID |
| payment_no | VARCHAR(100) | 支付单号 |
| payment_method | VARCHAR(20) | 支付方式 |
| amount | DECIMAL(10,2) | 支付金额 |
| status | VARCHAR(20) | 支付状态（PENDING, SUCCESS, FAILED, REFUNDED） |
| transaction_id | VARCHAR(100) | 第三方交易ID |
| paid_at | BIGINT | 支付时间 |
| refund_amount | DECIMAL(10,2) | 退款金额 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # order_shippings 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_id | BIGINT | 订单ID |
| shipping_no | VARCHAR(100) | 运单号 |
| shipping_company | VARCHAR(100) | 快递公司 |
| shipping_address | TEXT | 发货地址 |
| shipped_at | BIGINT | 发货时间 |
| received_at | BIGINT | 收货时间 |
| status | VARCHAR(20) | 状态（SHIPPED, IN_TRANSIT, DELIVERED, RETURNED） |
| tracking_info | TEXT | 物流跟踪信息 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # order_refunds 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_id | BIGINT | 订单ID |
| order_item_id | BIGINT | 订单项ID |
| refund_no | VARCHAR(100) | 退款单号 |
| refund_type | VARCHAR(20) | 退款类型（REFUND, RETURN） |
| refund_reason | TEXT | 退款原因 |
| refund_amount | DECIMAL(10,2) | 退款金额 |
| status | VARCHAR(20) | 状态（APPLIED, APPROVED, REJECTED, PROCESSED） |
| applied_at | BIGINT | 申请时间 |
| processed_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # order_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_id | BIGINT | 订单ID |
| operation | VARCHAR(50) | 操作类型 |
| operator_id | BIGINT | 操作人ID |
| operator_type | VARCHAR(20) | 操作人类型（USER, SYSTEM, ADMIN） |
| description | TEXT | 操作描述 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 订单缓存
```
order:{orderNo} -> String结构存储订单基本信息
order:{orderId}:items -> List结构存储订单项
order:user:{userId}:recent -> List结构存储用户最近订单
```

 # 订单状态缓存
```
order:{orderId}:status -> String结构存储订单状态
```

  RabbitMQ 消息队列设计

 # 订单事件队列
```
Queue: order.events
  - Exchange: order.exchange
  - Routing Key: order.{eventType}
```

 # 订单超时队列
```
Queue: order.timeout
  - Exchange: order.exchange
  - Routing Key: order.timeout.check
```

  订单状态机

 # 状态转换图
```
PENDING -> PAID -> SHIPPED -> RECEIVED -> CLOSED
    |       |         |          |
    |       |         |          -> CANCELLED
    |       |         -> RETURNED
    |       -> REFUND_APPLIED -> REFUNDED -> CLOSED
    -> CANCELLED
```

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
  port: 8127

spring:
  application:
    name: service-order
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_order
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
cd service-order
mvn spring-boot:run
```

或

```bash
cd service-order
mvn clean package
java -jar target/service-order-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-product (产品服务)
- service-payment (支付服务)
- service-logistics (物流服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/order
├── OrderServiceApplication.java  # 应用入口
├── controller/                   # 控制器层
│   ├── OrderController.java      # 订单控制器
│   ├── StatusController.java     # 状态控制器
│   └── StatController.java       # 统计控制器
├── entity/                       # 实体类
│   ├── Order.java                # 订单实体
│   ├── OrderItem.java            # 订单项实体
│   ├── OrderPayment.java         # 订单支付实体
│   ├── OrderShipping.java        # 订单发货实体
│   ├── OrderRefund.java          # 订单退款实体
│   └── OrderLog.java             # 订单日志实体
├── repository/                   # 数据访问层
│   ├── OrderRepository.java      # 订单数据访问
│   ├── OrderItemRepository.java  # 订单项数据访问
│   ├── OrderPaymentRepository.java # 订单支付数据访问
│   ├── OrderShippingRepository.java # 订单发货数据访问
│   ├── OrderRefundRepository.java # 订单退款数据访问
│   └── OrderLogRepository.java   # 订单日志数据访问
└── service/                      # 业务逻辑层
    ├── OrderService.java         # 订单服务
    ├── PaymentService.java       # 支付服务
    ├── ShippingService.java      # 发货服务
    ├── RefundService.java        # 退款服务
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
GET http://localhost:8127/actuator/health