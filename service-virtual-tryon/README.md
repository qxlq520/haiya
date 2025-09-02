# 虚拟试穿服务 (service-virtual-tryon)

  服务概述

虚拟试穿服务是海牙平台的AR增强现实功能服务，为用户提供虚拟试穿服装、配饰、化妆品等商品的能力，提升用户购物体验和转化率。

  功能特性

1. 虚拟服装试穿
2. 虚拟配饰试戴
3. 虚拟化妆品试用
4. 3D模型渲染
5. 实时姿态识别
6. 人脸识别与贴合
7. 试穿效果分享
8. 试穿数据统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- OpenCV 计算机视觉库
- Three.js 3D渲染引擎
- TensorFlow.js 机器学习框架

  API 接口

 # 虚拟试穿接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/virtual-tryon/session` | POST | 创建试穿会话 |
| `/api/virtual-tryon/session/{id}` | GET | 获取试穿会话详情 |
| `/api/virtual-tryon/session/{id}/end` | POST | 结束试穿会话 |
| `/api/virtual-tryon/session/{id}/products` | POST | 添加试穿商品 |
| `/api/virtual-tryon/session/{id}/try-on` | POST | 执行虚拟试穿 |

 # 试穿商品接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/virtual-tryon/products` | POST | 添加虚拟试穿商品 |
| `/api/virtual-tryon/products` | GET | 获取虚拟试穿商品列表 |
| `/api/virtual-tryon/products/{id}` | GET | 获取虚拟试穿商品详情 |
| `/api/virtual-tryon/products/{id}/model` | GET | 获取商品3D模型 |
| `/api/virtual-tryon/products/{id}/textures` | GET | 获取商品纹理贴图 |

 # 人体检测接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/virtual-tryon/detection/body` | POST | 人体检测 |
| `/api/virtual-tryon/detection/face` | POST | 人脸检测 |
| `/api/virtual-tryon/detection/pose` | POST | 姿态估计 |
| `/api/virtual-tryon/detection/landmarks` | POST | 关键点检测 |
| `/api/virtual-tryon/detection/segmentation` | POST | 人体分割 |

 # 渲染接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/virtual-tryon/render/preview` | POST | 生成预览图 |
| `/api/virtual-tryon/render/video` | POST | 生成试穿视频 |
| `/api/virtual-tryon/render/3d` | POST | 生成3D场景 |
| `/api/virtual-tryon/render/ar` | POST | 生成AR效果 |

 # 试穿分享接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/virtual-tryon/share` | POST | 分立试穿效果 |
| `/api/virtual-tryon/share/{id}` | GET | 获取分享内容 |
| `/api/virtual-tryon/share/{id}/like` | POST | 点赞分享内容 |
| `/api/virtual-tryon/share/{id}/comment` | POST | 评论分享内容 |

  数据库设计

 # tryon_sessions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| session_type | VARCHAR(20) | 会话类型（CLOTHING, ACCESSORY, COSMETICS） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| duration | INT | 持续时间（秒） |
| status | VARCHAR(20) | 状态（ACTIVE, COMPLETED, EXPIRED） |
| device_info | TEXT | 设备信息（JSON格式） |
| created_at | BIGINT | 创建时间 |

 # tryon_products 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| product_id | BIGINT | 商品ID |
| product_sku_id | BIGINT | 商品SKU ID |
| name | VARCHAR(255) | 商品名称 |
| category | VARCHAR(50) | 商品分类 |
| model_url | VARCHAR(500) | 3D模型URL |
| texture_urls | TEXT | 纹理贴图URL列表（JSON格式） |
| metadata | TEXT | 元数据（JSON格式） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # tryon_results 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| session_id | BIGINT | 试穿会话ID |
| product_id | BIGINT | 商品ID |
| input_image_url | VARCHAR(500) | 输入图片URL |
| result_image_url | VARCHAR(500) | 结果图片URL |
| result_video_url | VARCHAR(500) | 结果视频URL |
| processing_time | INT | 处理时间（毫秒） |
| confidence_score | DECIMAL(3,2) | 置信度评分 |
| created_at | BIGINT | 创建时间 |

 # tryon_shares 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| result_id | BIGINT | 试穿结果ID |
| share_image_url | VARCHAR(500) | 分享图片URL |
| share_video_url | VARCHAR(500) | 分享视频URL |
| caption | TEXT | 描述文字 |
| like_count | INT | 点赞数 |
| comment_count | INT | 评论数 |
| view_count | INT | 浏览数 |
| status | VARCHAR(20) | 状态（PUBLIC, PRIVATE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # tryon_detections 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| session_id | BIGINT | 试穿会话ID |
| detection_type | VARCHAR(20) | 检测类型（BODY, FACE, POSE） |
| input_image_url | VARCHAR(500) | 输入图片URL |
| detection_result | TEXT | 检测结果（JSON格式） |
| processing_time | INT | 处理时间（毫秒） |
| confidence_score | DECIMAL(3,2) | 置信度评分 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 试穿会话缓存
```
virtual-tryon:session:{sessionId} -> Hash结构存储试穿会话信息
virtual-tryon:user:{userId}:sessions -> List结构存储用户试穿会话
```

 # 商品模型缓存
```
virtual-tryon:product:{productId}:model -> String结构存储商品3D模型信息
virtual-tryon:product:{productId}:textures -> List结构存储商品纹理贴图
```

 # 检测结果缓存
```
virtual-tryon:detection:{imageHash} -> String结构存储检测结果缓存
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- OpenCV 4.x 计算机视觉库
- Node.js 14+ (用于前端渲染组件)

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8137

spring:
  application:
    name: service-virtual-tryon
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_virtual_tryon
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379

virtual-tryon:
  opencv:
    library-path: /usr/local/lib/opencv
  model:
    base-path: /data/virtual-tryon/models
  storage:
    base-url: https://cdn.haiya.com/virtual-tryon/
```

 # 启动服务

```bash
cd service-virtual-tryon
mvn spring-boot:run
```

或

```bash
cd service-virtual-tryon
mvn clean package
java -jar target/service-virtual-tryon-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-product (产品服务)
- service-storage (存储服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/virtual/tryon
├── VirtualTryonApplication.java  # 应用入口
├── config/                       # 配置类
│   └── OpenCVConfig.java         # OpenCV配置
├── controller/                   # 控制器层
│   ├── SessionController.java    # 会话控制器
│   ├── ProductController.java    # 商品控制器
│   ├── DetectionController.java  # 检测控制器
│   ├── RenderController.java     # 渲染控制器
│   └── ShareController.java      # 分享控制器
├── entity/                       # 实体类
│   ├── TryonSession.java         # 试穿会话实体
│   ├── TryonProduct.java         # 试穿商品实体
│   ├── TryonResult.java          # 试穿结果实体
│   ├── TryonShare.java           # 试穿分享实体
│   └── TryonDetection.java       # 检测结果实体
├── repository/                   # 数据访问层
│   ├── TryonSessionRepository.java # 试穿会话数据访问
│   ├── TryonProductRepository.java # 试穿商品数据访问
│   ├── TryonResultRepository.java # 试穿结果数据访问
│   ├── TryonShareRepository.java # 试穿分享数据访问
│   └── TryonDetectionRepository.java # 检测结果数据访问
└── service/                      # 业务逻辑层
    ├── SessionService.java       # 会话服务
    ├── ProductService.java       # 商品服务
    ├── DetectionService.java     # 检测服务
    ├── RenderService.java        # 渲染服务
    └── ShareService.java         # 分享服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8137/actuator/health