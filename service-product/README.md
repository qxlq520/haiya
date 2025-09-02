# 产品服务 (service-product)

  服务概述

产品服务是海牙平台电商系统的基础服务，负责商品信息管理、商品分类、商品SKU、库存管理、价格管理等功能，为订单系统和购物车系统提供核心数据支持。

  功能特性

1. 商品信息管理
2. 商品分类管理
3. 商品SKU管理
4. 库存管理
5. 价格管理
6. 商品属性管理
7. 商品评价管理
8. 商品搜索与筛选

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎

  API 接口

 # 商品管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/products` | POST | 创建商品 |
| `/api/products` | GET | 获取商品列表 |
| `/api/products/{id}` | GET | 获取商品详情 |
| `/api/products/{id}` | PUT | 更新商品信息 |
| `/api/products/{id}/status` | PUT | 更新商品状态 |
| `/api/products/{id}/delete` | DELETE | 删除商品 |

 # 商品分类接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/product-categories` | POST | 创建商品分类 |
| `/api/product-categories` | GET | 获取商品分类列表 |
| `/api/product-categories/{id}` | GET | 获取商品分类详情 |
| `/api/product-categories/{id}` | PUT | 更新商品分类 |
| `/api/product-categories/{id}/delete` | DELETE | 删除商品分类 |
| `/api/product-categories/tree` | GET | 获取分类树结构 |

 # 商品SKU接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/product-skus` | POST | 创建商品SKU |
| `/api/product-skus` | GET | 获取商品SKU列表 |
| `/api/product-skus/{id}` | GET | 获取商品SKU详情 |
| `/api/product-skus/{id}` | PUT | 更新商品SKU |
| `/api/product-skus/batch-update` | POST | 批量更新商品SKU |

 # 库存管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/inventory` | GET | 获取库存信息 |
| `/api/inventory/{skuId}` | GET | 获取SKU库存详情 |
| `/api/inventory/update` | POST | 更新库存 |
| `/api/inventory/batch-update` | POST | 批量更新库存 |
| `/api/inventory/logs` | GET | 获取库存变更日志 |

 # 商品评价接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/product-reviews` | POST | 创建商品评价 |
| `/api/product-reviews` | GET | 获取商品评价列表 |
| `/api/product-reviews/{id}` | GET | 获取商品评价详情 |
| `/api/product-reviews/product/{productId}` | GET | 获取商品评价 |
| `/api/product-reviews/user/{userId}` | GET | 获取用户评价 |

 # 商品搜索接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/products/search` | GET | 搜索商品 |
| `/api/products/filter` | GET | 筛选商品 |
| `/api/products/advanced-search` | GET | 高级搜索商品 |
| `/api/products/suggestions` | GET | 获取搜索建议 |

  数据库设计

 # products 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 商品名称 |
| description | TEXT | 商品描述 |
| category_id | BIGINT | 分类ID |
| brand_id | BIGINT | 品牌ID |
| merchant_id | BIGINT | 商户ID |
| status | VARCHAR(20) | 状态（DRAFT, ACTIVE, INACTIVE, DELETED） |
| main_image | VARCHAR(500) | 主图URL |
| images | TEXT | 图片列表（JSON格式） |
| attributes | TEXT | 商品属性（JSON格式） |
| tags | TEXT | 标签（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |
| published_at | BIGINT | 发布时间 |

 # product_categories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分类名称 |
| description | TEXT | 分类描述 |
| parent_id | BIGINT | 父分类ID |
| level | INT | 分类层级 |
| sort_order | INT | 排序 |
| icon_url | VARCHAR(500) | 图标URL |
| is_active | BOOLEAN | 是否激活 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # product_skus 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| product_id | BIGINT | 商品ID |
| sku_code | VARCHAR(100) | SKU编码 |
| name | VARCHAR(255) | SKU名称 |
| description | TEXT | SKU描述 |
| price | DECIMAL(10,2) | 价格 |
| original_price | DECIMAL(10,2) | 原价 |
| cost_price | DECIMAL(10,2) | 成本价 |
| stock | INT | 库存数量 |
| reserved_stock | INT | 预占库存 |
| available_stock | INT | 可用库存 |
| attributes | TEXT | SKU属性（JSON格式） |
| barcode | VARCHAR(100) | 条形码 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DELETED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # inventory 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| sku_id | BIGINT | SKU ID |
| warehouse_id | BIGINT | 仓库ID |
| quantity | INT | 库存数量 |
| reserved_quantity | INT | 预占数量 |
| available_quantity | INT | 可用数量 |
| last_updated_at | BIGINT | 最后更新时间 |
| created_at | BIGINT | 创建时间 |

 # product_reviews 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| product_id | BIGINT | 商品ID |
| user_id | BIGINT | 用户ID |
| order_id | BIGINT | 订单ID |
| rating | INT | 评分（1-5） |
| title | VARCHAR(255) | 评价标题 |
| content | TEXT | 评价内容 |
| images | TEXT | 评价图片（JSON格式） |
| is_verified | BOOLEAN | 是否已验证购买 |
| status | VARCHAR(20) | 状态（PENDING, APPROVED, REJECTED） |
| helpful_count | INT | 有用数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # product_attributes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| category_id | BIGINT | 分类ID |
| name | VARCHAR(100) | 属性名称 |
| type | VARCHAR(20) | 属性类型（TEXT, NUMBER, SELECT, MULTI_SELECT） |
| values | TEXT | 属性值选项（JSON格式） |
| is_required | BOOLEAN | 是否必填 |
| is_searchable | BOOLEAN | 是否可搜索 |
| sort_order | INT | 排序 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # product_brands 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 品牌名称 |
| logo_url | VARCHAR(500) | 品牌Logo URL |
| description | TEXT | 品牌描述 |
| website | VARCHAR(255) | 品牌官网 |
| is_active | BOOLEAN | 是否激活 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  Elasticsearch 索引设计

 # 商品索引
```
Index: products
  - product_id: 商品ID
  - name: 商品名称
  - description: 商品描述
  - category_id: 分类ID
  - brand_id: 品牌ID
  - price: 价格
  - status: 状态
  - tags: 标签
  - attributes: 属性
```

 # 商品分类索引
```
Index: product-categories
  - category_id: 分类ID
  - name: 分类名称
  - parent_id: 父分类ID
  - level: 分类层级
```

  Redis 缓存设计

 # 商品缓存
```
product:{productId} -> Hash结构存储商品基本信息
product:{productId}:skus -> List结构存储商品SKU列表
product:category:{categoryId}:products -> Set结构存储分类下商品ID
```

 # 库存缓存
```
inventory:sku:{skuId} -> String结构存储SKU库存
inventory:sku:{skuId}:available -> String结构存储SKU可用库存
```

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
  port: 8128

spring:
  application:
    name: service-product
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_product
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

product:
  image:
    base-url: https://cdn.haiya.com/images/
  search:
    enabled: true
```

 # 启动服务

```bash
cd service-product
mvn spring-boot:run
```

或

```bash
cd service-product
mvn clean package
java -jar target/service-product-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-storage (存储服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/product
├── ProductServiceApplication.java  # 应用入口
├── controller/                     # 控制器层
│   ├── ProductController.java      # 商品控制器
│   ├── CategoryController.java     # 分类控制器
│   ├── SkuController.java          # SKU控制器
│   ├── InventoryController.java    # 库存控制器
│   └── ReviewController.java       # 评价控制器
├── entity/                         # 实体类
│   ├── Product.java                # 商品实体
│   ├── ProductCategory.java        # 商品分类实体
│   ├── ProductSku.java             # 商品SKU实体
│   ├── Inventory.java              # 库存实体
│   ├── ProductReview.java          # 商品评价实体
│   ├── ProductAttribute.java       # 商品属性实体
│   └── ProductBrand.java           # 商品品牌实体
├── repository/                     # 数据访问层
│   ├── ProductRepository.java      # 商品数据访问
│   ├── CategoryRepository.java     # 分类数据访问
│   ├── SkuRepository.java          # SKU数据访问
│   ├── InventoryRepository.java    # 库存数据访问
│   ├── ReviewRepository.java       # 评价数据访问
│   ├── AttributeRepository.java    # 属性数据访问
│   └── BrandRepository.java        # 品牌数据访问
└── service/                        # 业务逻辑层
    ├── ProductService.java         # 商品服务
    ├── CategoryService.java        # 分类服务
    ├── SkuService.java             # SKU服务
    ├── InventoryService.java       # 库存服务
    └── ReviewService.java          # 评价服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8128/actuator/health