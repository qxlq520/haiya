# 购物车服务 (service-cart)

  服务概述

购物车服务是海牙平台电商服务的重要组成部分，负责处理用户购物车的管理、商品添加、数量修改、结算等核心功能。

  功能特性

1. 购物车商品管理
2. 商品数量修改
3. 商品删除与清空
4. 购物车商品选中状态管理
5. 购物车商品库存检查
6. 购物车商品价格计算
7. 购物车合并功能
8. 购物车数据持久化

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存

  API 接口

 # 购物车接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/carts` | GET | 获取用户购物车 |
| `/api/carts` | POST | 添加商品到购物车 |
| `/api/carts/{id}` | PUT | 更新购物车商品数量 |
| `/api/carts/{id}` | DELETE | 从购物车删除商品 |
| `/api/carts/batch` | POST | 批量操作购物车商品 |
| `/api/carts/clear` | DELETE | 清空购物车 |
| `/api/carts/select` | PUT | 选中/取消选中商品 |
| `/api/carts/select-all` | PUT | 全选/取消全选 |

 # 购物车统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/cart-stats` | GET | 获取购物车统计信息 |
| `/api/cart-stats/total` | GET | 获取购物车商品总价 |
| `/api/cart-stats/count` | GET | 获取购物车商品总数量 |

  数据库设计

 # carts 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| product_id | BIGINT | 商品ID |
| product_sku_id | BIGINT | 商品SKU ID |
| quantity | INT | 商品数量 |
| price | DECIMAL(10,2) | 商品单价 |
| selected | BOOLEAN | 是否选中 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # cart_items 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| cart_id | BIGINT | 购物车ID |
| product_id | BIGINT | 商品ID |
| product_sku_id | BIGINT | 商品SKU ID |
| product_name | VARCHAR(255) | 商品名称 |
| product_image | VARCHAR(500) | 商品图片URL |
| quantity | INT | 商品数量 |
| price | DECIMAL(10,2) | 商品单价 |
| selected | BOOLEAN | 是否选中 |
| attributes | TEXT | 商品属性（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # cart_summary 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| total_items | INT | 商品总数量 |
| total_selected_items | INT | 选中商品总数量 |
| total_amount | DECIMAL(10,2) | 商品总金额 |
| total_selected_amount | DECIMAL(10,2) | 选中商品总金额 |
| updated_at | BIGINT | 更新时间 |

  Redis 缓存设计

 # 购物车缓存结构

```
cart:user:{userId} -> Hash结构存储购物车商品
  - {productId}:{skuId} -> 商品信息（数量、选中状态等）

cart:summary:{userId} -> String结构存储购物车统计信息
  - {totalItems}:{totalSelectedItems}:{totalAmount}:{totalSelectedAmount}
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
  port: 8101

spring:
  application:
    name: service-cart
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_cart
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
cd service-cart
mvn spring-boot:run
```

或

```bash
cd service-cart
mvn clean package
java -jar target/service-cart-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-product (产品服务)
- service-ecommerce (电商服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/cart
├── CartServiceApplication.java   # 应用入口
├── controller/                   # 控制器层
│   └── CartController.java       # 购物车控制器
├── entity/                       # 实体类
│   ├── Cart.java                 # 购物车实体
│   ├── CartItem.java             # 购物车商品实体
│   └── CartSummary.java          # 购物车统计实体
├── repository/                   # 数据访问层
│   ├── CartRepository.java       # 购物车数据访问
│   ├── CartItemRepository.java   # 购物车商品数据访问
│   └── CartSummaryRepository.java # 购物车统计数据访问
└── service/                      # 业务逻辑层
    └── CartService.java          # 购物车服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8101/actuator/health