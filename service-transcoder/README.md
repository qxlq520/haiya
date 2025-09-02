# 转码服务 (service-transcoder)

  服务概述

转码服务是海牙平台的多媒体处理核心服务，专门负责视频、音频和图片的格式转换、压缩优化、分辨率适配等转码处理，确保内容在不同设备和网络环境下的最佳播放体验。

  功能特性

1. 视频转码处理
2. 音频转码处理
3. 图片格式转换
4. 多分辨率适配
5. 码率自适应处理
6. 转码任务调度
7. 转码进度监控
8. 转码质量控制

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

 # 转码任务接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transcoder/jobs` | POST | 创建转码任务 |
| `/api/transcoder/jobs` | GET | 获取转码任务列表 |
| `/api/transcoder/jobs/{id}` | GET | 获取转码任务详情 |
| `/api/transcoder/jobs/{id}/cancel` | POST | 取消转码任务 |
| `/api/transcoder/jobs/{id}/retry` | POST | 重试转码任务 |
| `/api/transcoder/jobs/{id}/progress` | GET | 获取转码进度 |

 # 视频转码接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transcoder/video/transcode` | POST | 视频转码 |
| `/api/transcoder/video/formats` | GET | 获取支持的视频格式 |
| `/api/transcoder/video/resolutions` | GET | 获取支持的分辨率 |
| `/api/transcoder/video/bitrate-profiles` | GET | 获取码率配置模板 |
| `/api/transcoder/video/thumbnails` | POST | 生成视频缩略图 |

 # 音频转码接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transcoder/audio/transcode` | POST | 音频转码 |
| `/api/transcoder/audio/formats` | GET | 获取支持的音频格式 |
| `/api/transcoder/audio/bitrate-profiles` | GET | 获取音频码率配置模板 |
| `/api/transcoder/audio/normalize` | POST | 音频标准化处理 |

 # 图片转码接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transcoder/image/convert` | POST | 图片格式转换 |
| `/api/transcoder/image/resize` | POST | 图片缩放 |
| `/api/transcoder/image/crop` | POST | 图片裁剪 |
| `/api/transcoder/image/compress` | POST | 图片压缩 |
| `/api/transcoder/image/formats` | GET | 获取支持的图片格式 |

 # 转码模板接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transcoder/templates` | POST | 创建转码模板 |
| `/api/transcoder/templates` | GET | 获取转码模板列表 |
| `/api/transcoder/templates/{id}` | GET | 获取转码模板详情 |
| `/api/transcoder/templates/{id}` | PUT | 更新转码模板 |
| `/api/transcoder/templates/{id}/delete` | DELETE | 删除转码模板 |

  数据库设计

 # transcode_jobs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| job_type | VARCHAR(20) | 任务类型（VIDEO, AUDIO, IMAGE） |
| source_url | VARCHAR(500) | 源文件URL |
| output_configs | TEXT | 输出配置（JSON格式） |
| status | VARCHAR(20) | 状态（PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED） |
| priority | INT | 优先级 |
| progress | DECIMAL(5,2) | 进度百分比 |
| error_message | TEXT | 错误信息 |
| started_at | BIGINT | 开始时间 |
| completed_at | BIGINT | 完成时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transcode_outputs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| job_id | BIGINT | 转码任务ID |
| output_type | VARCHAR(20) | 输出类型（VIDEO, AUDIO, IMAGE） |
| format | VARCHAR(20) | 格式 |
| resolution | VARCHAR(20) | 分辨率 |
| bitrate | INT | 码率 |
| file_size | BIGINT | 文件大小 |
| duration | INT | 时长（秒） |
| output_url | VARCHAR(500) | 输出文件URL |
| status | VARCHAR(20) | 状态（PROCESSING, COMPLETED, FAILED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transcode_templates 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模板名称 |
| description | TEXT | 模板描述 |
| media_type | VARCHAR(20) | 媒体类型（VIDEO, AUDIO, IMAGE） |
| config | TEXT | 配置参数（JSON格式） |
| is_default | BOOLEAN | 是否默认模板 |
| owner_id | BIGINT | 所有者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transcode_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| job_id | BIGINT | 转码任务ID |
| log_level | VARCHAR(20) | 日志级别（INFO, WARN, ERROR） |
| message | TEXT | 日志消息 |
| timestamp | BIGINT | 时间戳 |
| created_at | BIGINT | 创建时间 |

  Kafka 消息队列设计

 # 转码任务队列
```
Topic: transcoder.jobs
  - 分区: 8
  - 副本: 3
```

 # 转码结果队列
```
Topic: transcoder.results
  - 分区: 8
  - 副本: 3
```

  Redis 缓存设计

 # 转码任务缓存
```
transcoder:job:{jobId} -> Hash结构存储转码任务信息
transcoder:job:{jobId}:progress -> String结构存储转码进度
```

 # 转码模板缓存
```
transcoder:templates -> List结构存储所有转码模板
transcoder:template:{templateId} -> String结构存储模板详情
```

  FFmpeg 配置示例

 # 视频转码命令
```bash
ffmpeg -i input.mp4 -c:v libx264 -crf 23 -c:a aac -b:a 128k output.mp4
```

 # 多分辨率转码命令
```bash
ffmpeg -i input.mp4 -vf scale=1920:1080 -c:v libx264 -crf 23 -c:a aac output_1080p.mp4
ffmpeg -i input.mp4 -vf scale=1280:720 -c:v libx264 -crf 25 -c:a aac output_720p.mp4
```

 # 音频转码命令
```bash
ffmpeg -i input.mp3 -b:a 128k -ar 44100 output.mp3
```

 # 图片处理命令
```bash
ffmpeg -i input.jpg -vf scale=800:600 output.jpg
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
  port: 8135

spring:
  application:
    name: service-transcoder
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_transcoder
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

transcoder:
  ffmpeg:
    path: /usr/bin/ffmpeg
  storage:
    base-path: /data/transcoder
  concurrency:
    max-jobs: 4
```

 # 启动服务

```bash
cd service-transcoder
mvn spring-boot:run
```

或

```bash
cd service-transcoder
mvn clean package
java -jar target/service-transcoder-1.0.0.jar
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
src/main/java/com/haiya/transcoder
├── TranscoderServiceApplication.java  # 应用入口
├── config/                            # 配置类
│   └── FFmpegConfig.java              # FFmpeg配置
├── controller/                        # 控制器层
│   ├── JobController.java             # 任务控制器
│   ├── VideoController.java           # 视频控制器
│   ├── AudioController.java           # 音频控制器
│   ├── ImageController.java           # 图片控制器
│   └── TemplateController.java        # 模板控制器
├── entity/                            # 实体类
│   ├── TranscodeJob.java              # 转码任务实体
│   ├── TranscodeOutput.java           # 转码输出实体
│   ├── TranscodeTemplate.java         # 转码模板实体
│   └── TranscodeLog.java              # 转码日志实体
├── repository/                        # 数据访问层
│   ├── TranscodeJobRepository.java    # 转码任务数据访问
│   ├── TranscodeOutputRepository.java # 转码输出数据访问
│   ├── TranscodeTemplateRepository.java # 转码模板数据访问
│   └── TranscodeLogRepository.java    # 转码日志数据访问
└── service/                           # 业务逻辑层
    ├── JobService.java                # 任务服务
    ├── VideoTranscodeService.java     # 视频转码服务
    ├── AudioTranscodeService.java     # 音频转码服务
    ├── ImageTranscodeService.java     # 图片转码服务
    └── TemplateService.java           # 模板服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8135/actuator/health