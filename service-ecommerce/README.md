# 电商服务 (service-ecommerce)

  服务概述

电商服务是海牙平台的核心服务之一，负责处理平台电商功能，包括商品管理、订单处理、购物车功能等核心功能。

  功能特性

1. 商品信息管理
2. 商品分类管理
3. 购物车功能
4. 订单管理
5. 库存管理
6. 价格策略管理
7. 商品评价管理

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- RabbitMQ 消息队列

  API 接口

 # 商品接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/products` | GET | 获取商品列表 |
| `/api/products` | POST | 创建商品 |
| `/api/products/{id}` | GET | 获取商品详情 |
| `/api/products/{id}` | PUT | 更新商品信息 |
| `/api/products/{id}` | DELETE | 删除商品 |
| `/api/products/categories` | GET | 获取商品分类 |

 # 购物车接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/carts` | GET | 获取购物车列表 |
| `/api/carts` | POST | 添加商品到购物车 |
| `/api/carts/{id}` | PUT | 更新购物车商品数量 |
| `/api/carts/{id}` | DELETE | 从购物车删除商品 |
| `/api/carts/clear` | DELETE | 清空购物车 |

 # 订单接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/orders` | GET | 获取订单列表 |
| `/api/orders` | POST | 创建订单 |
| `/api/orders/{id}` | GET | 获取订单详情 |
| `/api/orders/{id}/status` | PUT | 更新订单状态 |
| `/api/orders/{id}/cancel` | POST | 取消订单 |

  数据库设计

 # products 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 商品名称 |
| description | TEXT | 商品描述 |
| category_id | BIGINT | 分类ID |
| price | DECIMAL(10,2) | 商品价格 |
| stock | INT | 库存数量 |
| image_urls | TEXT | 商品图片URL（JSON数组） |
| status | VARCHAR(20) | 商品状态（ON_SALE, OFF_SALE, DELETED） |
| seller_id | BIGINT | 卖家ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # product_categories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分类名称 |
| parent_id | BIGINT | 父分类ID |
| level | INT | 分类层级 |
| sort_order | INT | 排序 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # carts 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| product_id | BIGINT | 商品ID |
| quantity | INT | 商品数量 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # orders 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_no | VARCHAR(100) | 订单号 |
| user_id | BIGINT | 用户ID |
| total_amount | DECIMAL(10,2) | 订单总金额 |
| status | VARCHAR(20) | 订单状态（PENDING, PAID, SHIPPED, DELIVERED, CANCELLED） |
| receiver_name | VARCHAR(100) | 收货人姓名 |
| receiver_phone | VARCHAR(20) | 收货人电话 |
| receiver_address | VARCHAR(255) | 收货地址 |
| payment_id | BIGINT | 支付ID |
| shipped_at | BIGINT | 发货时间 |
| delivered_at | BIGINT | 确认收货时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # order_items 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_id | BIGINT | 订单ID |
| product_id | BIGINT | 商品ID |
| product_name | VARCHAR(255) | 商品名称 |
| product_price | DECIMAL(10,2) | 商品价格 |
| quantity | INT | 商品数量 |
| subtotal | DECIMAL(10,2) | 小计金额 |

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
  port: 8091

spring:
  application:
    name: service-ecommerce
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_ecommerce
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
cd service-ecommerce
mvn spring-boot:run
```

或

```bash
cd service-ecommerce
mvn clean package
java -jar target/service-ecommerce-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-payment (支付服务)
- service-product (产品服务)
- service-order (订单服务)
- service-cart (购物车服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/ecommerce
├── EcommerceServiceApplication.java  # 应用入口
├── controller/                       # 控制器层
│   └── ProductController.java        # 商品控制器
├── entity/                           # 实体类
│   ├── Product.java                  # 商品实体
│   └── ProductCategory.java          # 商品分类实体
├── repository/                       # 数据访问层
│   ├── ProductRepository.java        # 商品数据访问
│   └── ProductCategoryRepository.java # 商品分类数据访问
└── service/                          # 业务逻辑层
    └── ProductService.java           # 商品服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8091/actuator/health