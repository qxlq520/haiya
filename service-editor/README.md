# 编辑器服务 (service-editor)

  服务概述

编辑器服务是海牙平台的内容创作工具服务，为用户提供富文本编辑、视频编辑、图片处理等多媒体内容编辑功能。

  功能特性

1. 富文本编辑器
2. 视频编辑功能
3. 图片处理功能
4. 音频编辑功能
5. 模板管理
6. 素材库管理
7. 编辑历史记录
8. 协作编辑支持

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- FFmpeg 多媒体处理工具

  API 接口

 # 文本编辑接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/text-editor/documents` | POST | 创建文档 |
| `/api/text-editor/documents/{id}` | GET | 获取文档内容 |
| `/api/text-editor/documents/{id}` | PUT | 更新文档内容 |
| `/api/text-editor/documents/{id}/publish` | POST | 发布文档 |
| `/api/text-editor/documents/{id}/history` | GET | 获取文档编辑历史 |

 # 视频编辑接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/video-editor/projects` | POST | 创建视频项目 |
| `/api/video-editor/projects/{id}` | GET | 获取视频项目详情 |
| `/api/video-editor/projects/{id}/clips` | GET | 获取项目剪辑列表 |
| `/api/video-editor/projects/{id}/render` | POST | 渲染视频项目 |
| `/api/video-editor/projects/{id}/export` | POST | 导出视频项目 |

 # 图片处理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/image-editor/projects` | POST | 创建图片项目 |
| `/api/image-editor/projects/{id}` | GET | 获取图片项目详情 |
| `/api/image-editor/projects/{id}/layers` | GET | 获取项目图层列表 |
| `/api/image-editor/projects/{id}/process` | POST | 处理图片项目 |
| `/api/image-editor/projects/{id}/export` | POST | 导出图片项目 |

 # 音频编辑接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/audio-editor/projects` | POST | 创建音频项目 |
| `/api/audio-editor/projects/{id}` | GET | 获取音频项目详情 |
| `/api/audio-editor/projects/{id}/tracks` | GET | 获取项目音轨列表 |
| `/api/audio-editor/projects/{id}/mix` | POST | 混音音频项目 |
| `/api/audio-editor/projects/{id}/export` | POST | 导出音频项目 |

 # 模板管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/editor-templates` | GET | 获取模板列表 |
| `/api/editor-templates` | POST | 创建模板 |
| `/api/editor-templates/{id}` | GET | 获取模板详情 |
| `/api/editor-templates/{id}` | PUT | 更新模板 |
| `/api/editor-templates/{id}` | DELETE | 删除模板 |

 # 素材库接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/editor-assets` | GET | 获取素材列表 |
| `/api/editor-assets` | POST | 上传素材 |
| `/api/editor-assets/{id}` | GET | 获取素材详情 |
| `/api/editor-assets/{id}` | DELETE | 删除素材 |
| `/api/editor-assets/categories` | GET | 获取素材分类 |

  数据库设计

 # editor_documents 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| title | VARCHAR(255) | 文档标题 |
| content | TEXT | 文档内容 |
| author_id | BIGINT | 作者ID |
| status | VARCHAR(20) | 状态（DRAFT, PUBLISHED, ARCHIVED） |
| version | INT | 版本号 |
| word_count | INT | 字数统计 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |
| published_at | BIGINT | 发布时间 |

 # editor_projects 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 项目名称 |
| type | VARCHAR(20) | 项目类型（VIDEO, IMAGE, AUDIO） |
| description | TEXT | 项目描述 |
| owner_id | BIGINT | 所有者ID |
| status | VARCHAR(20) | 状态（CREATED, EDITING, RENDERING, COMPLETED, FAILED） |
| duration | INT | 时长（秒） |
| resolution | VARCHAR(50) | 分辨率 |
| file_size | BIGINT | 文件大小 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # editor_project_clips 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| project_id | BIGINT | 项目ID |
| clip_type | VARCHAR(20) | 剪辑类型（VIDEO, IMAGE, AUDIO, TEXT） |
| source_url | VARCHAR(500) | 源文件URL |
| start_time | INT | 开始时间（秒） |
| end_time | INT | 结束时间（秒） |
| position | INT | 位置 |
| duration | INT | 时长（秒） |
| properties | TEXT | 属性配置（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # editor_templates 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模板名称 |
| type | VARCHAR(20) | 模板类型（VIDEO, IMAGE, AUDIO, DOCUMENT） |
| description | TEXT | 模板描述 |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| preview_url | VARCHAR(500) | 预览URL |
| config_data | TEXT | 配置数据（JSON格式） |
| creator_id | BIGINT | 创建者ID |
| is_public | BOOLEAN | 是否公开 |
| download_count | INT | 下载次数 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # editor_assets 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 素材名称 |
| type | VARCHAR(20) | 素材类型（VIDEO, IMAGE, AUDIO, FONT） |
| category_id | BIGINT | 分类ID |
| file_url | VARCHAR(500) | 文件URL |
| thumbnail_url | VARCHAR(500) | 缩略图URL |
| file_size | BIGINT | 文件大小 |
| duration | INT | 时长（秒） |
| resolution | VARCHAR(50) | 分辨率 |
| uploader_id | BIGINT | 上传者ID |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DELETED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # editor_asset_categories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分类名称 |
| type | VARCHAR(20) | 分类类型 |
| parent_id | BIGINT | 父分类ID |
| sort_order | INT | 排序 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # editor_history 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| project_id | BIGINT | 项目ID |
| editor_id | BIGINT | 编辑者ID |
| action | VARCHAR(50) | 操作类型（CREATE, UPDATE, DELETE, RENDER等） |
| details | TEXT | 操作详情（JSON格式） |
| created_at | BIGINT | 创建时间 |

  FFmpeg 集成示例

 # 视频转码命令
```bash
ffmpeg -i input.mp4 -c:v libx264 -c:a aac output.mp4
```

 # 视频剪辑命令
```bash
ffmpeg -i input.mp4 -ss 00:00:10 -to 00:00:30 -c copy output.mp4
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

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8110

spring:
  application:
    name: service-editor
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_editor
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
cd service-editor
mvn spring-boot:run
```

或

```bash
cd service-editor
mvn clean package
java -jar target/service-editor-1.0.0.jar
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
src/main/java/com/haiya/editor
├── EditorServiceApplication.java  # 应用入口
├── controller/                   # 控制器层
│   ├── TextEditorController.java # 文本编辑控制器
│   ├── VideoEditorController.java # 视频编辑控制器
│   ├── ImageEditorController.java # 图片编辑控制器
│   ├── AudioEditorController.java # 音频编辑控制器
│   ├── TemplateController.java   # 模板控制器
│   └── AssetController.java      # 素材控制器
├── entity/                       # 实体类
│   ├── EditorDocument.java       # 编辑文档实体
│   ├── EditorProject.java        # 编辑项目实体
│   ├── ProjectClip.java          # 项目剪辑实体
│   ├── EditorTemplate.java       # 编辑模板实体
│   ├── EditorAsset.java          # 编辑素材实体
│   ├── AssetCategory.java        # 素材分类实体
│   └── EditorHistory.java        # 编辑历史实体
├── repository/                   # 数据访问层
│   ├── EditorDocumentRepository.java # 编辑文档数据访问
│   ├── EditorProjectRepository.java # 编辑项目数据访问
│   ├── ProjectClipRepository.java # 项目剪辑数据访问
│   ├── EditorTemplateRepository.java # 编辑模板数据访问
│   ├── EditorAssetRepository.java # 编辑素材数据访问
│   ├── AssetCategoryRepository.java # 素材分类数据访问
│   └── EditorHistoryRepository.java # 编辑历史数据访问
└── service/                      # 业务逻辑层
    ├── TextEditorService.java    # 文本编辑服务
    ├── VideoEditorService.java   # 视频编辑服务
    ├── ImageEditorService.java   # 图片编辑服务
    ├── AudioEditorService.java   # 音频编辑服务
    ├── TemplateService.java      # 模板服务
    └── AssetService.java         # 素材服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8110/actuator/health