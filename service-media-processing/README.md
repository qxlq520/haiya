# 媒体处理服务 (service-media-processing)

  服务概述

媒体处理服务是海牙平台多媒体内容处理的核心服务，负责视频、音频、图片等媒体文件的转码、压缩、格式转换、水印添加、缩略图生成等处理功能。

  功能特性

1. 视频转码与压缩
2. 音频处理与转换
3. 图片处理与优化
4. 水印添加功能
5. 缩略图自动生成
6. 媒体文件格式转换
7. 媒体处理任务调度
8. 媒体处理进度监控

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- FFmpeg 多媒体处理工具
- Apache Kafka 消息队列

  API 接口

 # 媒体处理任务接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/media-processing/tasks` | POST | 创建媒体处理任务 |
| `/api/media-processing/tasks` | GET | 获取媒体处理任务列表 |
| `/api/media-processing/tasks/{id}` | GET | 获取媒体处理任务详情 |
| `/api/media-processing/tasks/{id}/cancel` | POST | 取消媒体处理任务 |
| `/api/media-processing/tasks/{id}/retry` | POST | 重试媒体处理任务 |

 # 视频处理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/media-processing/video/transcode` | POST | 视频转码 |
| `/api/media-processing/video/compress` | POST | 视频压缩 |
| `/api/media-processing/video/watermark` | POST | 添加视频水印 |
| `/api/media-processing/video/thumbnails` | POST | 生成视频缩略图 |
| `/api/media-processing/video/preview` | POST | 生成视频预览 |

 # 音频处理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/media-processing/audio/transcode` | POST | 音频转码 |
| `/api/media-processing/audio/compress` | POST | 音频压缩 |
| `/api/media-processing/audio/normalize` | POST | 音频标准化 |
| `/api/media-processing/audio/watermark` | POST | 添加音频水印 |

 # 图片处理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/media-processing/image/resize` | POST | 图片缩放 |
| `/api/media-processing/image/crop` | POST | 图片裁剪 |
| `/api/media-processing/image/watermark` | POST | 添加图片水印 |
| `/api/media-processing/image/compress` | POST | 图片压缩 |
| `/api/media-processing/image/format` | POST | 图片格式转换 |

  数据库设计

 # media_processing_tasks 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| task_type | VARCHAR(50) | 任务类型（VIDEO_TRANSCODE, AUDIO_COMPRESS, IMAGE_RESIZE等） |
| source_url | VARCHAR(500) | 源文件URL |
| output_url | VARCHAR(500) | 输出文件URL |
| parameters | TEXT | 处理参数（JSON格式） |
| status | VARCHAR(20) | 状态（PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED） |
| priority | INT | 优先级 |
| progress | DECIMAL(5,2) | 处理进度百分比 |
| error_message | TEXT | 错误信息 |
| started_at | BIGINT | 开始时间 |
| completed_at | BIGINT | 完成时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # media_processing_templates 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模板名称 |
| type | VARCHAR(50) | 模板类型（VIDEO, AUDIO, IMAGE） |
| config | TEXT | 配置参数（JSON格式） |
| description | TEXT | 描述 |
| is_default | BOOLEAN | 是否默认模板 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # media_files 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| file_name | VARCHAR(255) | 文件名 |
| file_type | VARCHAR(20) | 文件类型（VIDEO, AUDIO, IMAGE） |
| file_url | VARCHAR(500) | 文件URL |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| file_size | BIGINT | 文件大小（字节） |
| duration | INT | 时长（秒，仅适用于音频和视频） |
| width | INT | 宽度（像素，仅适用于图片和视频） |
| height | INT | 高度（像素，仅适用于图片和视频） |
| format | VARCHAR(20) | 格式（mp4, avi, jpg等） |
| codec | VARCHAR(50) | 编码格式 |
| bitrate | INT | 比特率 |
| status | VARCHAR(20) | 状态（AVAILABLE, DELETED） |
| uploaded_at | BIGINT | 上传时间 |
| processed_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # media_processing_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| task_id | BIGINT | 任务ID |
| log_level | VARCHAR(20) | 日志级别（INFO, WARN, ERROR） |
| message | TEXT | 日志消息 |
| timestamp | BIGINT | 时间戳 |
| created_at | BIGINT | 创建时间 |

  Kafka 消息队列设计

 # 媒体处理任务主题
```
Topic: media-processing.tasks
  - 分区: 6
  - 副本: 3
```

 # 媒体处理结果主题
```
Topic: media-processing.results
  - 分区: 6
  - 副本: 3
```

  Redis 缓存设计

 # 处理任务缓存
```
media:processing:task:{taskId} -> String结构存储任务信息
media:processing:progress:{taskId} -> String结构存储处理进度
```

 # 处理模板缓存
```
media:processing:templates -> Hash结构存储所有处理模板
media:processing:template:{templateId} -> String结构存储模板详情
```

  FFmpeg 集成示例

 # 视频转码命令
```bash
ffmpeg -i input.mp4 -c:v libx264 -c:a aac -strict experimental output.mp4
```

 # 视频压缩命令
```bash
ffmpeg -i input.mp4 -vcodec libx264 -crf 28 output.mp4
```

 # 图片处理命令
```bash
ffmpeg -i input.jpg -vf "scale=1920:1080" output.jpg
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- FFmpeg 多媒体处理工具
- Apache Kafka 消息队列

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8121

spring:
  application:
    name: service-media-processing
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_media_processing
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379
    
  kafka:
    bootstrap-servers: localhost:9092

media-processing:
  ffmpeg:
    path: /usr/bin/ffmpeg
  storage:
    base-path: /data/media
```

 # 启动服务

```bash
cd service-media-processing
mvn spring-boot:run
```

或

```bash
cd service-media-processing
mvn clean package
java -jar target/service-media-processing-1.0.0.jar
```

  服务依赖

- service-storage (存储服务)
- service-video (视频服务)
- service-audio (音频服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/media/processing
├── MediaProcessingServiceApplication.java  # 应用入口
├── config/                                 # 配置类
│   └── FFmpegConfig.java                   # FFmpeg配置
├── controller/                             # 控制器层
│   ├── TaskController.java                 # 任务控制器
│   ├── VideoController.java                # 视频控制器
│   ├── AudioController.java                # 音频控制器
│   └── ImageController.java                # 图片控制器
├── entity/                                 # 实体类
│   ├── MediaProcessingTask.java            # 媒体处理任务实体
│   ├── MediaProcessingTemplate.java        # 媒体处理模板实体
│   ├── MediaFile.java                      # 媒体文件实体
│   └── MediaProcessingLog.java             # 媒体处理日志实体
├── repository/                             # 数据访问层
│   ├── MediaProcessingTaskRepository.java  # 媒体处理任务数据访问
│   ├── MediaProcessingTemplateRepository.java # 媒体处理模板数据访问
│   ├── MediaFileRepository.java            # 媒体文件数据访问
│   └── MediaProcessingLogRepository.java   # 媒体处理日志数据访问
├── service/                                # 业务逻辑层
│   ├── TaskService.java                    # 任务服务
│   ├── VideoProcessingService.java         # 视频处理服务
│   ├── AudioProcessingService.java         # 音频处理服务
│   └── ImageProcessingService.java         # 图片处理服务
└── processor/                              # 处理器层
    ├── FFmpegProcessor.java                # FFmpeg处理器
    ├── VideoProcessor.java                 # 视频处理器
    ├── AudioProcessor.java                 # 音频处理器
    └── ImageProcessor.java                 # 图片处理器
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8121/actuator/health