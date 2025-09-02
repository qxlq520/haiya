# 视频服务 (service-video)

  服务概述

视频服务是海牙平台的核心内容服务，负责视频上传、处理、存储、播放、推荐等功能，为用户提供高质量的视频内容体验。

  功能特性

1. 视频上传与处理
2. 视频存储与管理
3. 视频播放与流媒体传输
4. 视频推荐与发现
5. 视频互动（点赞、评论、分享）
6. 视频搜索与筛选
7. 视频数据分析
8. 视频版权保护

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Redis 缓存
- FFmpeg 视频处理
- HLS 流媒体协议
- Eureka 服务注册与发现

  API 接口

 # 视频上传接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/videos/upload` | POST | 上传视频 |
| `/api/videos/upload/chunk` | POST | 分片上传视频 |
| `/api/videos/upload/complete` | POST | 完成分片上传 |
| `/api/videos/upload/cancel` | POST | 取消上传 |
| `/api/videos/upload/status/{id}` | GET | 获取上传状态 |

 # 视频管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/videos` | POST | 发布视频 |
| `/api/videos` | GET | 获取视频列表 |
| `/api/videos/{id}` | GET | 获取视频详情 |
| `/api/videos/{id}` | PUT | 更新视频信息 |
| `/api/videos/{id}/delete` | DELETE | 删除视频 |
| `/api/videos/{id}/privacy` | PUT | 设置视频隐私 |

 # 视频播放接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/videos/{id}/play` | GET | 播放视频 |
| `/api/videos/{id}/stream/{quality}` | GET | 流媒体播放 |
| `/api/videos/{id}/download` | GET | 下载视频 |
| `/api/videos/{id}/thumbnail` | GET | 获取视频缩略图 |
| `/api/videos/{id}/subtitles` | GET | 获取视频字幕 |

 # 视频互动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/videos/{id}/like` | POST | 点赞视频 |
| `/api/videos/{id}/unlike` | POST | 取消点赞 |
| `/api/videos/{id}/favorite` | POST | 收藏视频 |
| `/api/videos/{id}/unfavorite` | POST | 取消收藏 |
| `/api/videos/{id}/share` | POST | 分享视频 |
| `/api/videos/{id}/report` | POST | 举报视频 |

 # 视频推荐接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/videos/recommend` | GET | 获取推荐视频 |
| `/api/videos/trending` | GET | 获取热门视频 |
| `/api/videos/following` | GET | 获取关注用户的视频 |
| `/api/videos/foryou` | GET | 获取为你推荐的视频 |
| `/api/videos/similar/{id}` | GET | 获取相似视频 |

  数据库设计

 # videos 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| title | VARCHAR(255) | 视频标题 |
| description | TEXT | 视频描述 |
| cover_url | VARCHAR(500) | 封面URL |
| video_url | VARCHAR(500) | 视频URL |
| duration | INT | 视频时长（秒） |
| width | INT | 视频宽度 |
| height | INT | 视频高度 |
| size | BIGINT | 视频大小（字节） |
| format | VARCHAR(20) | 视频格式 |
| quality | VARCHAR(20) | 视频质量 |
| view_count | BIGINT | 播放次数 |
| like_count | INT | 点赞数 |
| comment_count | INT | 评论数 |
| share_count | INT | 分享数 |
| favorite_count | INT | 收藏数 |
| privacy_setting | VARCHAR(20) | 隐私设置（PUBLIC, PRIVATE, FRIENDS） |
| status | VARCHAR(20) | 状态（UPLOADING, PROCESSING, PUBLISHED, DELETED） |
| tags | TEXT | 标签（JSON格式） |
| location | VARCHAR(255) | 地理位置 |
| published_at | BIGINT | 发布时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # video_streams 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| video_id | BIGINT | 视频ID |
| quality | VARCHAR(20) | 视频质量（360p, 480p, 720p, 1080p等） |
| url | VARCHAR(500) | 流媒体URL |
| size | BIGINT | 文件大小 |
| bitrate | INT | 码率 |
| codec | VARCHAR(50) | 编码格式 |
| created_at | BIGINT | 创建时间 |

 # video_interactions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| video_id | BIGINT | 视频ID |
| interaction_type | VARCHAR(20) | 互动类型（LIKE, FAVORITE, SHARE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # video_play_history 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| video_id | BIGINT | 视频ID |
| play_duration | INT | 播放时长（秒） |
| total_duration | INT | 总时长（秒） |
| completion_rate | DECIMAL(5,2) | 完成率 |
| played_at | BIGINT | 播放时间 |
| created_at | BIGINT | 创建时间 |

 # video_reports 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| reporter_id | BIGINT | 举报人ID |
| video_id | BIGINT | 视频ID |
| reason | VARCHAR(100) | 举报原因 |
| description | TEXT | 详细描述 |
| status | VARCHAR(20) | 状态（PENDING, RESOLVED, DISMISSED） |
| resolved_by | BIGINT | 处理人ID |
| resolved_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 视频信息缓存
```
video:info:{videoId} -> Hash结构存储视频基本信息
video:streams:{videoId} -> List结构存储视频流信息
```

 # 互动数据缓存
```
video:interactions:{videoId} -> Hash结构存储视频互动数据
video:user:{userId}:interacted -> Set结构存储用户互动过的视频
```

 # 推荐数据缓存
```
video:recommend:{userId} -> List结构存储推荐视频列表
video:trending -> ZSet结构存储热门视频排行榜
```

 # 播放历史缓存
```
video:history:{userId} -> List结构存储用户播放历史
video:history:{userId}:{videoId} -> String结构存储单个视频播放记录
```

  FFmpeg 处理配置

 # 视频转码命令
```bash
ffmpeg -i input.mp4 -c:v libx264 -crf 23 -c:a aac -b:a 128k output.mp4
```

 # 多分辨率处理
```bash
ffmpeg -i input.mp4 -vf scale=1920:1080 -c:v libx264 -crf 23 output_1080p.mp4
ffmpeg -i input.mp4 -vf scale=1280:720 -c:v libx264 -crf 25 output_720p.mp4
ffmpeg -i input.mp4 -vf scale=854:480 -c:v libx264 -crf 27 output_480p.mp4
```

 # HLS流媒体生成
```bash
ffmpeg -i input.mp4 -profile:v baseline -level 3.0 -start_number 0 \
  -hls_time 10 -hls_list_size 0 -f hls output.m3u8
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- FFmpeg 视频处理工具

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8081

spring:
  application:
    name: service-video
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_video
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379

video:
  storage:
    base-path: /data/videos
  ffmpeg:
    path: /usr/bin/ffmpeg
  transcoding:
    enabled: true
    qualities: [360p, 480p, 720p, 1080p]
```

 # 启动服务

```bash
cd service-video
mvn spring-boot:run
```

或

```bash
cd service-video
mvn clean package
java -jar target/service-video-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-storage (存储服务)
- service-like (点赞服务)
- service-comment (评论服务)
- service-recommend (推荐服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/video
├── VideoServiceApplication.java  # 应用入口
├── config/                       # 配置类
│   └── FFmpegConfig.java         # FFmpeg配置
├── controller/                   # 控制器层
│   ├── VideoController.java      # 视频控制器
│   ├── UploadController.java     # 上传控制器
│   ├── PlayController.java       # 播放控制器
│   └── InteractionController.java # 互动控制器
├── entity/                       # 实体类
│   ├── Video.java                # 视频实体
│   ├── VideoStream.java          # 视频流实体
│   ├── VideoInteraction.java     # 视频互动实体
│   ├── PlayHistory.java          # 播放历史实体
│   └── VideoReport.java          # 视频举报实体
├── repository/                   # 数据访问层
│   ├── VideoRepository.java      # 视频数据访问
│   ├── VideoStreamRepository.java # 视频流数据访问
│   ├── VideoInteractionRepository.java # 视频互动数据访问
│   ├── PlayHistoryRepository.java # 播放历史数据访问
│   └── VideoReportRepository.java # 视频举报数据访问
└── service/                      # 业务逻辑层
    ├── VideoService.java         # 视频服务
    ├── UploadService.java        # 上传服务
    ├── PlayService.java          # 播放服务
    └── InteractionService.java   # 互动服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8081/actuator/health