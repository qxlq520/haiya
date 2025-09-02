# 音频服务 (service-audio)

  服务概述

音频服务是海牙平台的多媒体服务之一，负责处理音频内容的上传、处理、存储、播放等核心功能。

  功能特性

1. 音频上传与管理
2. 音频转码与处理
3. 音频存储与分发
4. 音频播放统计
5. 音频标签管理
6. 音频推荐功能
7. 音频搜索功能
8. 音频互动功能（评论、点赞等）

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- FFmpeg 音频处理工具

  API 接口

 # 音频接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/audios` | GET | 获取音频列表 |
| `/api/audios` | POST | 上传音频 |
| `/api/audios/{id}` | GET | 获取音频详情 |
| `/api/audios/{id}` | PUT | 更新音频信息 |
| `/api/audios/{id}` | DELETE | 删除音频 |
| `/api/audios/user/{userId}` | GET | 获取用户音频列表 |

 # 音频流接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/audio-streams/{audioId}` | GET | 获取音频流地址 |
| `/api/audio-streams/{audioId}/play` | POST | 上报音频播放 |

 # 音频互动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/audio-likes` | POST | 点赞音频 |
| `/api/audio-likes` | DELETE | 取消点赞音频 |
| `/api/audio-likes/status` | GET | 获取音频点赞状态 |
| `/api/audio-favorites` | POST | 收藏音频 |
| `/api/audio-favorites` | DELETE | 取消收藏音频 |
| `/api/audio-favorites/status` | GET | 获取音频收藏状态 |

  数据库设计

 # audios 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| title | VARCHAR(255) | 音频标题 |
| description | TEXT | 音频描述 |
| cover_image | VARCHAR(500) | 封面图片URL |
| audio_url | VARCHAR(500) | 音频URL |
| duration | INT | 音频时长（秒） |
| format | VARCHAR(20) | 音频格式（MP3, WAV, FLAC等） |
| size | BIGINT | 音频大小（字节） |
| uploader_id | BIGINT | 上传者ID |
| status | VARCHAR(20) | 状态（PROCESSING, AVAILABLE, DELETED） |
| play_count | INT | 播放次数 |
| like_count | INT | 点赞数 |
| comment_count | INT | 评论数 |
| favorite_count | INT | 收藏数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |
| published_at | BIGINT | 发布时间 |

 # audio_tags 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(50) | 标签名称 |
| audio_count | INT | 音频数量 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # audio_tag_relations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| audio_id | BIGINT | 音频ID |
| tag_id | BIGINT | 标签ID |
| created_at | BIGINT | 创建时间 |

 # audio_plays 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| audio_id | BIGINT | 音频ID |
| user_id | BIGINT | 用户ID |
| play_duration | INT | 播放时长（秒） |
| ip_address | VARCHAR(50) | IP地址 |
| user_agent | TEXT | 用户代理 |
| created_at | BIGINT | 创建时间 |

 # audio_likes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| audio_id | BIGINT | 音频ID |
| user_id | BIGINT | 用户ID |
| created_at | BIGINT | 创建时间 |

 # audio_favorites 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| audio_id | BIGINT | 音频ID |
| user_id | BIGINT | 用户ID |
| created_at | BIGINT | 创建时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- FFmpeg 音频处理工具

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8097

spring:
  application:
    name: service-audio
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_audio
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
cd service-audio
mvn spring-boot:run
```

或

```bash
cd service-audio
mvn clean package
java -jar target/service-audio-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-storage (存储服务)
- service-like (点赞服务)
- service-comment (评论服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/audio
├── AudioServiceApplication.java  # 应用入口
├── controller/                   # 控制器层
│   ├── AudioController.java      # 音频控制器
│   └── AudioStreamController.java # 音频流控制器
├── entity/                       # 实体类
│   ├── Audio.java                # 音频实体
│   ├── AudioTag.java             # 音频标签实体
│   ├── AudioTagRelation.java     # 音频标签关系实体
│   ├── AudioPlay.java            # 音频播放实体
│   ├── AudioLike.java            # 音频点赞实体
│   └── AudioFavorite.java        # 音频收藏实体
├── repository/                   # 数据访问层
│   ├── AudioRepository.java      # 音频数据访问
│   ├── AudioTagRepository.java   # 音频标签数据访问
│   ├── AudioPlayRepository.java  # 音频播放数据访问
│   ├── AudioLikeRepository.java  # 音频点赞数据访问
│   └── AudioFavoriteRepository.java # 音频收藏数据访问
└── service/                      # 业务逻辑层
    ├── AudioService.java         # 音频服务
    ├── AudioStreamService.java   # 音频流服务
    └── AudioProcessService.java  # 音频处理服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8097/actuator/health