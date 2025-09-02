# 供应链服务 (service-supply-chain)

  服务概述

供应链服务是海牙平台电商生态的重要组成部分，负责管理供应商、采购、库存、配送等供应链全流程，确保商品从生产到消费者手中的高效流转。

  功能特性

1. 供应商管理
2. 采购管理
3. 库存管理
4. 配送管理
5. 供应链协同
6. 质量管理
7. 成本控制
8. 供应链数据分析

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- RabbitMQ 消息队列

  API 接口

 # 供应商管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/supply-chain/suppliers` | POST | 创建供应商 |
| `/api/supply-chain/suppliers` | GET | 获取供应商列表 |
| `/api/supply-chain/suppliers/{id}` | GET | 获取供应商详情 |
| `/api/supply-chain/suppliers/{id}` | PUT | 更新供应商信息 |
| `/api/supply-chain/suppliers/{id}/activate` | POST | 激活供应商 |
| `/api/supply-chain/suppliers/{id}/deactivate` | POST | 停用供应商 |

 # 采购管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/supply-chain/purchase-orders` | POST | 创建采购订单 |
| `/api/supply-chain/purchase-orders` | GET | 获取采购订单列表 |
| `/api/supply-chain/purchase-orders/{id}` | GET | 获取采购订单详情 |
| `/api/supply-chain/purchase-orders/{id}/approve` | POST | 审批采购订单 |
| `/api/supply-chain/purchase-orders/{id}/receive` | POST | 确认收货 |
| `/api/supply-chain/purchase-orders/{id}/cancel` | POST | 取消采购订单 |

 # 库存管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/supply-chain/inventory` | GET | 获取库存信息 |
| `/api/supply-chain/inventory/adjust` | POST | 调整库存 |
| `/api/supply-chain/inventory/transfer` | POST | 库存调拨 |
| `/api/supply-chain/inventory/alerts` | GET | 获取库存预警 |
| `/api/supply-chain/inventory/reports` | GET | 获取库存报告 |

 # 配送管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/supply-chain/delivery-plans` | POST | 创建配送计划 |
| `/api/supply-chain/delivery-plans` | GET | 获取配送计划列表 |
| `/api/supply-chain/delivery-plans/{id}` | GET | 获取配送计划详情 |
| `/api/supply-chain/delivery-plans/{id}/execute` | POST | 执行配送计划 |
| `/api/supply-chain/delivery-plans/{id}/track` | GET | 跟踪配送状态 |

 # 质量管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/supply-chain/quality-inspections` | POST | 创建质检记录 |
| `/api/supply-chain/quality-inspections` | GET | 获取质检记录列表 |
| `/api/supply-chain/quality-inspections/{id}` | GET | 获取质检记录详情 |
| `/api/supply-chain/quality-inspections/{id}/approve` | POST | 批准质检 |
| `/api/supply-chain/quality-inspections/{id}/reject` | POST | 拒绝质检 |

  数据库设计

 # suppliers 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 供应商名称 |
| code | VARCHAR(50) | 供应商编码 |
| contact_info | TEXT | 联系信息（JSON格式） |
| address | TEXT | 地址信息 |
| business_license | VARCHAR(500) | 营业执照URL |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, SUSPENDED） |
| rating | DECIMAL(3,2) | 评级 |
| cooperation_start_date | BIGINT | 合作开始日期 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # purchase_orders 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_no | VARCHAR(50) | 采购订单号 |
| supplier_id | BIGINT | 供应商ID |
| warehouse_id | BIGINT | 仓库ID |
| total_amount | DECIMAL(10,2) | 总金额 |
| status | VARCHAR(20) | 状态（DRAFT, APPROVED, CONFIRMED, RECEIVED, CANCELLED） |
| expected_delivery_date | BIGINT | 预计交货日期 |
| actual_delivery_date | BIGINT | 实际交货日期 |
| approver_id | BIGINT | 审批人ID |
| approved_at | BIGINT | 审批时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # purchase_order_items 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| purchase_order_id | BIGINT | 采购订单ID |
| product_id | BIGINT | 商品ID |
| product_sku_id | BIGINT | 商品SKU ID |
| quantity | INT | 数量 |
| unit_price | DECIMAL(10,2) | 单价 |
| total_price | DECIMAL(10,2) | 总价 |
| received_quantity | INT | 已收数量 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # supply_inventory 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| warehouse_id | BIGINT | 仓库ID |
| product_sku_id | BIGINT | 商品SKU ID |
| quantity | INT | 库存数量 |
| reserved_quantity | INT | 预占数量 |
| available_quantity | INT | 可用数量 |
| min_stock_level | INT | 最低库存水平 |
| max_stock_level | INT | 最高库存水平 |
| last_updated_at | BIGINT | 最后更新时间 |
| created_at | BIGINT | 创建时间 |

 # delivery_plans 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| plan_no | VARCHAR(50) | 配送计划号 |
| source_warehouse_id | BIGINT | 源仓库ID |
| target_warehouse_id | BIGINT | 目标仓库ID |
| transport_company | VARCHAR(100) | 运输公司 |
| estimated_departure | BIGINT | 预计出发时间 |
| estimated_arrival | BIGINT | 预计到达时间 |
| actual_departure | BIGINT | 实际出发时间 |
| actual_arrival | BIGINT | 实际到达时间 |
| status | VARCHAR(20) | 状态（PLANNED, IN_TRANSIT, DELIVERED, CANCELLED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # quality_inspections 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| purchase_order_id | BIGINT | 采购订单ID |
| inspector_id | BIGINT | 检验员ID |
| inspection_date | BIGINT | 检验日期 |
| result | VARCHAR(20) | 检验结果（PASS, FAIL, PARTIAL） |
| remarks | TEXT | 备注 |
| documents | TEXT | 检验文档（JSON格式） |
| approved_at | BIGINT | 批准时间 |
| approver_id | BIGINT | 批准人ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  Redis 缓存设计

 # 供应商缓存
```
supply-chain:supplier:{supplierId} -> Hash结构存储供应商信息
supply-chain:suppliers:active -> Set结构存储活跃供应商
```

 # 库存缓存
```
supply-chain:inventory:warehouse:{warehouseId} -> Hash结构存储仓库库存
supply-chain:inventory:sku:{skuId} -> String结构存储SKU库存信息
```

 # 采购订单缓存
```
supply-chain:purchase-order:{orderNo} -> Hash结构存储采购订单信息
supply-chain:purchase-orders:supplier:{supplierId} -> List结构存储供应商订单
```

  RabbitMQ 消息队列设计

 # 供应链事件队列
```
Queue: supply-chain.events
  - Exchange: supply-chain.exchange
  - Routing Key: supply-chain.{eventType}
```

 # 库存变更队列
```
Queue: supply-chain.inventory-changes
  - Exchange: supply-chain.exchange
  - Routing Key: supply-chain.inventory.changed
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
  port: 8133

spring:
  application:
    name: service-supply-chain
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_supply_chain
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

supply-chain:
  inventory:
    alert-threshold: 0.2  # 库存预警阈值
  quality:
    inspection-required: true  # 是否需要质检
```

 # 启动服务

```bash
cd service-supply-chain
mvn spring-boot:run
```

或

```bash
cd service-supply-chain
mvn clean package
java -jar target/service-supply-chain-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-product (产品服务)
- service-warehouse (仓储服务)
- service-logistics (物流服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/supply/chain
├── SupplyChainApplication.java  # 应用入口
├── controller/                  # 控制器层
│   ├── SupplierController.java  # 供应商控制器
│   ├── PurchaseController.java  # 采购控制器
│   ├── InventoryController.java # 库存控制器
│   ├── DeliveryController.java  # 配送控制器
│   └── QualityController.java   # 质量控制器
├── entity/                      # 实体类
│   ├── Supplier.java            # 供应商实体
│   ├── PurchaseOrder.java       # 采购订单实体
│   ├── PurchaseOrderItem.java   # 采购订单项实体
│   ├── SupplyInventory.java     # 供应库存实体
│   ├── DeliveryPlan.java        # 配送计划实体
│   └── QualityInspection.java   # 质量检验实体
├── repository/                  # 数据访问层
│   ├── SupplierRepository.java  # 供应商数据访问
│   ├── PurchaseOrderRepository.java # 采购订单数据访问
│   ├── PurchaseOrderItemRepository.java # 采购订单项数据访问
│   ├── SupplyInventoryRepository.java # 供应库存数据访问
│   ├── DeliveryPlanRepository.java # 配送计划数据访问
│   └── QualityInspectionRepository.java # 质量检验数据访问
└── service/                     # 业务逻辑层
    ├── SupplierService.java     # 供应商服务
    ├── PurchaseService.java     # 采购服务
    ├── InventoryService.java    # 库存服务
    ├── DeliveryService.java     # 配送服务
    └── QualityService.java      # 质量服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8133/actuator/health