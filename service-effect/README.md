# 特效服务 (service-effect)

  服务概述

特效服务是海牙平台的多媒体处理服务，负责提供各种视频、图片、音频特效处理功能，包括滤镜、转场、动画、AR特效等。

  功能特性

1. 视频滤镜特效
2. 图片滤镜特效
3. 音频特效处理
4. 视频转场特效
5. AR增强现实特效
6. 动画特效
7. 特效模板管理
8. 特效参数配置

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- OpenCV 计算机视觉库
- FFmpeg 多媒体处理工具

  API 接口

 # 视频特效接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/video-effects` | GET | 获取视频特效列表 |
| `/api/video-effects` | POST | 创建视频特效 |
| `/api/video-effects/{id}` | GET | 获取视频特效详情 |
| `/api/video-effects/{id}/apply` | POST | 应用视频特效 |
| `/api/video-effects/{id}/preview` | GET | 预览视频特效 |

 # 图片特效接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/image-effects` | GET | 获取图片特效列表 |
| `/api/image-effects` | POST | 创建图片特效 |
| `/api/image-effects/{id}` | GET | 获取图片特效详情 |
| `/api/image-effects/{id}/apply` | POST | 应用图片特效 |
| `/api/image-effects/{id}/preview` | GET | 预览图片特效 |

 # 音频特效接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/audio-effects` | GET | 获取音频特效列表 |
| `/api/audio-effects` | POST | 创建音频特效 |
| `/api/audio-effects/{id}` | GET | 获取音频特效详情 |
| `/api/audio-effects/{id}/apply` | POST | 应用音频特效 |
| `/api/audio-effects/{id}/preview` | GET | 预览音频特效 |

 # 特效模板接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/effect-templates` | GET | 获取特效模板列表 |
| `/api/effect-templates` | POST | 创建特效模板 |
| `/api/effect-templates/{id}` | GET | 获取特效模板详情 |
| `/api/effect-templates/{id}` | PUT | 更新特效模板 |
| `/api/effect-templates/{id}` | DELETE | 删除特效模板 |

 # 特效分类接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/effect-categories` | GET | 获取特效分类列表 |
| `/api/effect-categories` | POST | 创建特效分类 |
| `/api/effect-categories/{id}` | GET | 获取特效分类详情 |
| `/api/effect-categories/{id}` | PUT | 更新特效分类 |
| `/api/effect-categories/{id}` | DELETE | 删除特效分类 |

  数据库设计

 # video_effects 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 特效名称 |
| description | TEXT | 特效描述 |
| type | VARCHAR(50) | 特效类型（FILTER, TRANSITION, OVERLAY等） |
| category_id | BIGINT | 分类ID |
| parameters | TEXT | 参数配置（JSON格式） |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| preview_url | VARCHAR(500) | 预览URL |
| file_url | VARCHAR(500) | 特效文件URL |
| is_public | BOOLEAN | 是否公开 |
| creator_id | BIGINT | 创建者ID |
| download_count | INT | 下载次数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # image_effects 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 特效名称 |
| description | TEXT | 特效描述 |
| type | VARCHAR(50) | 特效类型（FILTER, STICKER, FRAME等） |
| category_id | BIGINT | 分类ID |
| parameters | TEXT | 参数配置（JSON格式） |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| preview_url | VARCHAR(500) | 预览URL |
| file_url | VARCHAR(500) | 特效文件URL |
| is_public | BOOLEAN | 是否公开 |
| creator_id | BIGINT | 创建者ID |
| download_count | INT | 下载次数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # audio_effects 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 特效名称 |
| description | TEXT | 特效描述 |
| type | VARCHAR(50) | 特效类型（ECHO, REVERB, CHORUS等） |
| category_id | BIGINT | 分类ID |
| parameters | TEXT | 参数配置（JSON格式） |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| preview_url | VARCHAR(500) | 预览URL |
| file_url | VARCHAR(500) | 特效文件URL |
| is_public | BOOLEAN | 是否公开 |
| creator_id | BIGINT | 创建者ID |
| download_count | INT | 下载次数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # effect_templates 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模板名称 |
| description | TEXT | 模板描述 |
| type | VARCHAR(20) | 模板类型（VIDEO, IMAGE, AUDIO） |
| category_id | BIGINT | 分类ID |
| config_data | TEXT | 配置数据（JSON格式） |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| preview_url | VARCHAR(500) | 预览URL |
| is_public | BOOLEAN | 是否公开 |
| creator_id | BIGINT | 创建者ID |
| use_count | INT | 使用次数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # effect_categories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分类名称 |
| type | VARCHAR(20) | 分类类型（VIDEO, IMAGE, AUDIO） |
| parent_id | BIGINT | 父分类ID |
| icon_url | VARCHAR(500) | 图标URL |
| sort_order | INT | 排序 |
| is_active | BOOLEAN | 是否激活 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # effect_usage_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| effect_id | BIGINT | 特效ID |
| effect_type | VARCHAR(20) | 特效类型 |
| user_id | BIGINT | 用户ID |
| content_id | BIGINT | 内容ID |
| usage_time | BIGINT | 使用时间 |
| parameters | TEXT | 使用参数（JSON格式） |
| created_at | BIGINT | 创建时间 |

  OpenCV 集成示例

 # 图像滤镜处理
```java
Mat src = Imgcodecs.imread("input.jpg");
Mat dst = new Mat();
Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
Imgcodecs.imwrite("output.jpg", dst);
```

 # 视频特效处理
```java
VideoCapture capture = new VideoCapture("input.mp4");
Mat frame = new Mat();
while (capture.read(frame)) {
    // 应用特效处理
    // ...
}
```

  FFmpeg 集成示例

 # 视频滤镜命令
```bash
ffmpeg -i input.mp4 -vf "eq=contrast=1.2:brightness=0.1" output.mp4
```

 # 音频特效命令
```bash
ffmpeg -i input.mp3 -af "aecho=0.8:0.9:1000:0.3" output.mp3
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- OpenCV 4.x 计算机视觉库
- FFmpeg 多媒体处理工具

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8111

spring:
  application:
    name: service-effect
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_effect
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
cd service-effect
mvn spring-boot:run
```

或

```bash
cd service-effect
mvn clean package
java -jar target/service-effect-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-storage (存储服务)
- service-media-processing (媒体处理服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/effect
├── EffectServiceApplication.java  # 应用入口
├── controller/                   # 控制器层
│   ├── VideoEffectController.java # 视频特效控制器
│   ├── ImageEffectController.java # 图片特效控制器
│   ├── AudioEffectController.java # 音频特效控制器
│   ├── TemplateController.java   # 模板控制器
│   └── CategoryController.java   # 分类控制器
├── entity/                       # 实体类
│   ├── VideoEffect.java          # 视频特效实体
│   ├── ImageEffect.java          # 图片特效实体
│   ├── AudioEffect.java          # 音频特效实体
│   ├── EffectTemplate.java       # 特效模板实体
│   ├── EffectCategory.java       # 特效分类实体
│   └── EffectUsageLog.java       # 特效使用日志实体
├── repository/                   # 数据访问层
│   ├── VideoEffectRepository.java # 视频特效数据访问
│   ├── ImageEffectRepository.java # 图片特效数据访问
│   ├── AudioEffectRepository.java # 音频特效数据访问
│   ├── EffectTemplateRepository.java # 特效模板数据访问
│   ├── EffectCategoryRepository.java # 特效分类数据访问
│   └── EffectUsageLogRepository.java # 特效使用日志数据访问
└── service/                      # 业务逻辑层
    ├── VideoEffectService.java   # 视频特效服务
    ├── ImageEffectService.java   # 图片特效服务
    ├── AudioEffectService.java   # 音频特效服务
    ├── TemplateService.java      # 模板服务
    └── CategoryService.java      # 分类服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8111/actuator/health