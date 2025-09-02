# 物流服务 (service-logistics)

  服务概述

物流服务是海牙平台电商功能的重要组成部分，负责处理商品配送、物流跟踪、仓储管理和配送区域管理等功能。

  功能特性

1. 物流公司管理
2. 配送区域管理
3. 运费模板配置
4. 订单物流跟踪
5. 仓储管理
6. 配送时效计算
7. 物流异常处理
8. 物流数据统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存

  API 接口

 # 物流公司管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/logistics/companies` | GET | 获取物流公司列表 |
| `/api/logistics/companies` | POST | 创建物流公司 |
| `/api/logistics/companies/{id}` | GET | 获取物流公司详情 |
| `/api/logistics/companies/{id}` | PUT | 更新物流公司信息 |
| `/api/logistics/companies/{id}` | DELETE | 删除物流公司 |

 # 配送区域管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/logistics/delivery-areas` | GET | 获取配送区域列表 |
| `/api/logistics/delivery-areas` | POST | 创建配送区域 |
| `/api/logistics/delivery-areas/{id}` | GET | 获取配送区域详情 |
| `/api/logistics/delivery-areas/{id}` | PUT | 更新配送区域信息 |
| `/api/logistics/delivery-areas/{id}` | DELETE | 删除配送区域 |

 # 运费模板接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/logistics/shipping-templates` | GET | 获取运费模板列表 |
| `/api/logistics/shipping-templates` | POST | 创建运费模板 |
| `/api/logistics/shipping-templates/{id}` | GET | 获取运费模板详情 |
| `/api/logistics/shipping-templates/{id}` | PUT | 更新运费模板 |
| `/api/logistics/shipping-templates/{id}/calculate` | POST | 计算运费 |

 # 物流订单接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/logistics/orders` | POST | 创建物流订单 |
| `/api/logistics/orders/{id}` | GET | 获取物流订单详情 |
| `/api/logistics/orders/{orderNo}` | GET | 根据订单号获取物流订单 |
| `/api/logistics/orders/{id}/track` | GET | 获取物流跟踪信息 |
| `/api/logistics/orders/{id}/update-status` | POST | 更新物流状态 |

 # 仓储管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/logistics/warehouses` | GET | 获取仓库列表 |
| `/api/logistics/warehouses` | POST | 创建仓库 |
| `/api/logistics/warehouses/{id}` | GET | 获取仓库详情 |
| `/api/logistics/warehouses/{id}` | PUT | 更新仓库信息 |
| `/api/logistics/warehouses/{id}/inventory` | GET | 获取仓库库存 |

  数据库设计

 # logistics_companies 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 公司名称 |
| code | VARCHAR(50) | 公司代码 |
| logo_url | VARCHAR(500) | Logo URL |
| contact_info | TEXT | 联系信息（JSON格式） |
| tracking_url | VARCHAR(500) | 物流跟踪URL模板 |
| supported_services | TEXT | 支持的服务（JSON格式） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| sort_order | INT | 排序 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # delivery_areas 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 区域名称 |
| parent_id | BIGINT | 父区域ID |
| area_code | VARCHAR(20) | 区域代码 |
| area_level | INT | 区域级别（国家:1, 省:2, 市:3, 区:4） |
| postcode | VARCHAR(20) | 邮政编码 |
| coordinates | TEXT | 坐标信息（JSON格式） |
| is_supported | BOOLEAN | 是否支持配送 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # shipping_templates 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模板名称 |
| template_type | VARCHAR(20) | 模板类型（WEIGHT, QUANTITY, VOLUME） |
| pricing_rules | TEXT | 计价规则（JSON格式） |
| default_flag | BOOLEAN | 是否默认模板 |
| creator_id | BIGINT | 创建者ID |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # logistics_orders 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_no | VARCHAR(50) | 订单号 |
| logistics_no | VARCHAR(100) | 物流单号 |
| company_id | BIGINT | 物流公司ID |
| sender_info | TEXT | 发货人信息（JSON格式） |
| receiver_info | TEXT | 收货人信息（JSON格式） |
| goods_info | TEXT | 货物信息（JSON格式） |
| weight | DECIMAL(10,2) | 重量（kg） |
| volume | DECIMAL(10,2) | 体积（m³） |
| shipping_fee | DECIMAL(10,2) | 运费 |
| status | VARCHAR(20) | 状态（PENDING, SHIPPED, IN_TRANSIT, DELIVERED, FAILED） |
| shipped_at | BIGINT | 发货时间 |
| delivered_at | BIGINT | 签收时间 |
| estimated_delivery_at | BIGINT | 预计送达时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # logistics_tracking_records 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| logistics_order_id | BIGINT | 物流订单ID |
| logistics_no | VARCHAR(100) | 物流单号 |
| tracking_time | BIGINT | 跟踪时间 |
| tracking_status | VARCHAR(50) | 跟踪状态 |
| location | VARCHAR(255) | 位置信息 |
| description | TEXT | 描述信息 |
| operator | VARCHAR(100) | 操作员 |
| created_at | BIGINT | 创建时间 |

 # warehouses 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 仓库名称 |
| code | VARCHAR(50) | 仓库代码 |
| address | TEXT | 仓库地址 |
| contact_person | VARCHAR(100) | 联系人 |
| contact_phone | VARCHAR(20) | 联系电话 |
| capacity | DECIMAL(15,2) | 仓储容量 |
| current_usage | DECIMAL(15,2) | 当前使用量 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # warehouse_inventory 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| warehouse_id | BIGINT | 仓库ID |
| product_id | BIGINT | 商品ID |
| sku_id | BIGINT | SKU ID |
| quantity | INT | 库存数量 |
| reserved_quantity | INT | 预占数量 |
| available_quantity | INT | 可用数量 |
| last_updated_at | BIGINT | 最后更新时间 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 物流信息缓存
```
logistics:order:{orderNo} -> String结构存储物流订单信息
logistics:tracking:{logisticsNo} -> List结构存储物流跟踪记录
```

 # 运费计算缓存
```
logistics:shipping-cost:{templateId}:{areaId}:{weight} -> String结构存储运费计算结果
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
  port: 8120

spring:
  application:
    name: service-logistics
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_logistics
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
cd service-logistics
mvn spring-boot:run
```

或

```bash
cd service-logistics
mvn clean package
java -jar target/service-logistics-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-order (订单服务)
- service-product (产品服务)
- service-ecommerce (电商服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/logistics
├── LogisticsApplication.java     # 应用入口
├── controller/                   # 控制器层
│   ├── CompanyController.java    # 物流公司控制器
│   ├── DeliveryAreaController.java # 配送区域控制器
│   ├── ShippingTemplateController.java # 运费模板控制器
│   ├── LogisticsOrderController.java # 物流订单控制器
│   └── WarehouseController.java  # 仓储控制器
├── entity/                       # 实体类
│   ├── LogisticsCompany.java     # 物流公司实体
│   ├── DeliveryArea.java         # 配送区域实体
│   ├── ShippingTemplate.java     # 运费模板实体
│   ├── LogisticsOrder.java       # 物流订单实体
│   ├── TrackingRecord.java       # 跟踪记录实体
│   ├── Warehouse.java            # 仓库实体
│   └── WarehouseInventory.java   # 仓库库存实体
├── repository/                   # 数据访问层
│   ├── LogisticsCompanyRepository.java # 物流公司数据访问
│   ├── DeliveryAreaRepository.java # 配送区域数据访问
│   ├── ShippingTemplateRepository.java # 运费模板数据访问
│   ├── LogisticsOrderRepository.java # 物流订单数据访问
│   ├── TrackingRecordRepository.java # 跟踪记录数据访问
│   ├── WarehouseRepository.java  # 仓库数据访问
│   └── WarehouseInventoryRepository.java # 仓库库存数据访问
└── service/                      # 业务逻辑层
    ├── CompanyService.java       # 物流公司服务
    ├── DeliveryAreaService.java  # 配送区域服务
    ├── ShippingTemplateService.java # 运费模板服务
    ├── LogisticsOrderService.java # 物流订单服务
    └── WarehouseService.java     # 仓储服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8120/actuator/health