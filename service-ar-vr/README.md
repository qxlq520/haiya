# AR/VR服务 (service-ar-vr)

  服务概述

AR/VR服务是海牙平台的创新服务之一，负责处理增强现实和虚拟现实相关功能，包括AR特效、VR内容展示、虚拟场景交互等核心功能。

  功能特性

1. AR特效处理
2. VR内容展示
3. 虚拟场景交互
4. 3D模型处理
5. 空间定位与追踪
6. 虚拟物品放置
7. AR/VR内容创作工具

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- OpenCV 计算机视觉库
- ARCore/ARKit AR开发框架

  API 接口

 # AR特效接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ar-effects` | GET | 获取AR特效列表 |
| `/api/ar-effects` | POST | 创建AR特效 |
| `/api/ar-effects/{id}` | GET | 获取AR特效详情 |
| `/api/ar-effects/{id}` | PUT | 更新AR特效信息 |
| `/api/ar-effects/{id}` | DELETE | 删除AR特效 |

 # VR内容接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/vr-content` | GET | 获取VR内容列表 |
| `/api/vr-content` | POST | 创建VR内容 |
| `/api/vr-content/{id}` | GET | 获取VR内容详情 |
| `/api/vr-content/{id}` | PUT | 更新VR内容信息 |
| `/api/vr-content/{id}` | DELETE | 删除VR内容 |

 # 虚拟场景接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/virtual-scenes` | GET | 获取虚拟场景列表 |
| `/api/virtual-scenes` | POST | 创建虚拟场景 |
| `/api/virtual-scenes/{id}` | GET | 获取虚拟场景详情 |
| `/api/virtual-scenes/{id}` | PUT | 更新虚拟场景信息 |
| `/api/virtual-scenes/{id}` | DELETE | 删除虚拟场景 |

 # 空间定位接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/spatial-tracking` | POST | 上报空间定位数据 |
| `/api/spatial-tracking/{userId}` | GET | 获取用户空间定位数据 |

  数据库设计

 # ar_effects 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 特效名称 |
| description | TEXT | 特效描述 |
| type | VARCHAR(50) | 特效类型（FACE, OBJECT, ENVIRONMENT） |
| model_url | VARCHAR(500) | 模型URL |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| creator_id | BIGINT | 创建者ID |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # vr_content 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| title | VARCHAR(255) | 内容标题 |
| description | TEXT | 内容描述 |
| content_url | VARCHAR(500) | 内容URL |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| duration | INT | 时长（秒） |
| view_count | INT | 观看次数 |
| like_count | INT | 点赞数 |
| creator_id | BIGINT | 创建者ID |
| status | VARCHAR(20) | 状态（PUBLISHED, DRAFT, DELETED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # virtual_scenes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 场景名称 |
| description | TEXT | 场景描述 |
| scene_data | TEXT | 场景数据（JSON格式） |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| creator_id | BIGINT | 创建者ID |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # spatial_tracking 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| session_id | VARCHAR(100) | 会话ID |
| position_x | DECIMAL(10,2) | X坐标 |
| position_y | DECIMAL(10,2) | Y坐标 |
| position_z | DECIMAL(10,2) | Z坐标 |
| rotation_x | DECIMAL(10,2) | X轴旋转 |
| rotation_y | DECIMAL(10,2) | Y轴旋转 |
| rotation_z | DECIMAL(10,2) | Z轴旋转 |
| timestamp | BIGINT | 时间戳 |
| created_at | BIGINT | 创建时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- OpenCV 4.x

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8095

spring:
  application:
    name: service-ar-vr
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_ar_vr
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
cd service-ar-vr
mvn spring-boot:run
```

或

```bash
cd service-ar-vr
mvn clean package
java -jar target/service-ar-vr-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-effect (特效服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/ar/vr
├── ArVrApplication.java          # 应用入口
├── controller/                   # 控制器层
│   ├── ArEffectController.java   # AR特效控制器
│   ├── VrContentController.java  # VR内容控制器
│   ├── VirtualSceneController.java # 虚拟场景控制器
│   └── SpatialTrackingController.java # 空间定位控制器
├── entity/                       # 实体类
│   ├── ArEffect.java             # AR特效实体
│   ├── VrContent.java            # VR内容实体
│   ├── VirtualScene.java         # 虚拟场景实体
│   └── SpatialTracking.java      # 空间定位实体
├── repository/                   # 数据访问层
│   ├── ArEffectRepository.java   # AR特效数据访问
│   ├── VrContentRepository.java  # VR内容数据访问
│   ├── VirtualSceneRepository.java # 虚拟场景数据访问
│   └── SpatialTrackingRepository.java # 空间定位数据访问
└── service/                      # 业务逻辑层
    ├── ArEffectService.java      # AR特效服务
    ├── VrContentService.java     # VR内容服务
    ├── VirtualSceneService.java  # 虚拟场景服务
    └── SpatialTrackingService.java # 空间定位服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8095/actuator/health