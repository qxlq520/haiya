# 相机服务 (service-camera)

  服务概述

相机服务是海牙平台的多媒体服务之一，负责处理相机功能的控制、图像拍摄、图像处理等核心功能。

  功能特性

1. 相机设备管理
2. 图像拍摄控制
3. 图像处理与优化
4. 滤镜与特效应用
5. 美颜功能
6. 图像存储与管理
7. 相机参数配置
8. 多摄像头支持

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- OpenCV 图像处理库

  API 接口

 # 相机设备接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/cameras` | GET | 获取相机设备列表 |
| `/api/cameras` | POST | 添加相机设备 |
| `/api/cameras/{id}` | GET | 获取相机设备详情 |
| `/api/cameras/{id}` | PUT | 更新相机设备信息 |
| `/api/cameras/{id}` | DELETE | 删除相机设备 |
| `/api/cameras/{id}/connect` | POST | 连接相机设备 |
| `/api/cameras/{id}/disconnect` | POST | 断开相机设备 |

 # 图像拍摄接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/camera-shots` | POST | 拍摄图像 |
| `/api/camera-shots/{id}` | GET | 获取图像详情 |
| `/api/camera-shots/{id}` | DELETE | 删除图像 |
| `/api/camera-shots/batch` | POST | 批量拍摄图像 |

 # 图像处理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/image-processing/filter` | POST | 应用滤镜 |
| `/api/image-processing/beauty` | POST | 应用美颜 |
| `/api/image-processing/crop` | POST | 裁剪图像 |
| `/api/image-processing/resize` | POST | 调整图像大小 |
| `/api/image-processing/rotate` | POST | 旋转图像 |

 # 相机配置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/camera-configs` | GET | 获取相机配置 |
| `/api/camera-configs` | PUT | 更新相机配置 |
| `/api/camera-configs/presets` | GET | 获取预设配置列表 |
| `/api/camera-configs/presets` | POST | 创建预设配置 |

  数据库设计

 # cameras 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 相机名称 |
| model | VARCHAR(100) | 相机型号 |
| brand | VARCHAR(50) | 相机品牌 |
| resolution | VARCHAR(50) | 分辨率 |
| status | VARCHAR(20) | 状态（AVAILABLE, IN_USE, OFFLINE） |
| owner_id | BIGINT | 所有者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # camera_shots 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| camera_id | BIGINT | 相机ID |
| image_url | VARCHAR(500) | 图像URL |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| width | INT | 图像宽度 |
| height | INT | 图像高度 |
| file_size | BIGINT | 文件大小 |
| shot_time | BIGINT | 拍摄时间 |
| location | VARCHAR(100) | 拍摄位置 |
| metadata | TEXT | 元数据（JSON格式） |
| created_at | BIGINT | 创建时间 |

 # image_processing_tasks 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| image_id | BIGINT | 图像ID |
| task_type | VARCHAR(50) | 任务类型（FILTER, BEAUTY, CROP等） |
| parameters | TEXT | 处理参数（JSON格式） |
| status | VARCHAR(20) | 状态（PENDING, PROCESSING, COMPLETED, FAILED） |
| result_url | VARCHAR(500) | 结果图像URL |
| started_at | BIGINT | 开始时间 |
| completed_at | BIGINT | 完成时间 |
| created_at | BIGINT | 创建时间 |

 # camera_configs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| camera_id | BIGINT | 相机ID |
| config_key | VARCHAR(100) | 配置键 |
| config_value | TEXT | 配置值 |
| description | VARCHAR(255) | 描述 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # camera_config_presets 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 预设名称 |
| config_data | TEXT | 配置数据（JSON格式） |
| creator_id | BIGINT | 创建者ID |
| is_public | BOOLEAN | 是否公开 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- OpenCV 4.x 图像处理库

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8099

spring:
  application:
    name: service-camera
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_camera
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
cd service-camera
mvn spring-boot:run
```

或

```bash
cd service-camera
mvn clean package
java -jar target/service-camera-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-storage (存储服务)
- service-effect (特效服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/camera
├── CameraServiceApplication.java # 应用入口
├── controller/                   # 控制器层
│   ├── CameraController.java     # 相机控制器
│   ├── CameraShotController.java # 图像拍摄控制器
│   ├── ImageProcessingController.java # 图像处理控制器
│   └── CameraConfigController.java # 相机配置控制器
├── entity/                       # 实体类
│   ├── Camera.java               # 相机实体
│   ├── CameraShot.java           # 图像实体
│   ├── ImageProcessingTask.java  # 图像处理任务实体
│   ├── CameraConfig.java         # 相机配置实体
│   └── CameraConfigPreset.java   # 相机配置预设实体
├── repository/                   # 数据访问层
│   ├── CameraRepository.java     # 相机数据访问
│   ├── CameraShotRepository.java # 图像数据访问
│   ├── ImageProcessingTaskRepository.java # 图像处理任务数据访问
│   ├── CameraConfigRepository.java # 相机配置数据访问
│   └── CameraConfigPresetRepository.java # 相机配置预设数据访问
└── service/                      # 业务逻辑层
    ├── CameraService.java        # 相机服务
    ├── CameraShotService.java    # 图像拍摄服务
    ├── ImageProcessingService.java # 图像处理服务
    └── CameraConfigService.java  # 相机配置服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8099/actuator/health