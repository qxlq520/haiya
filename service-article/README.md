# 文章服务 (service-article)

  服务概述

文章服务是海牙平台的内容服务之一，负责处理图文内容的发布、管理、展示等核心功能。

  功能特性

1. 文章发布与管理
2. 文章分类管理
3. 文章标签管理
4. 文章评论功能
5. 文章点赞功能
6. 文章收藏功能
7. 文章搜索功能
8. 文章推荐功能

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎

  API 接口

 # 文章接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/articles` | GET | 获取文章列表 |
| `/api/articles` | POST | 创建文章 |
| `/api/articles/{id}` | GET | 获取文章详情 |
| `/api/articles/{id}` | PUT | 更新文章信息 |
| `/api/articles/{id}` | DELETE | 删除文章 |
| `/api/articles/user/{userId}` | GET | 获取用户文章列表 |
| `/api/articles/category/{categoryId}` | GET | 获取分类文章列表 |

 # 文章分类接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/article-categories` | GET | 获取文章分类列表 |
| `/api/article-categories` | POST | 创建文章分类 |
| `/api/article-categories/{id}` | GET | 获取文章分类详情 |
| `/api/article-categories/{id}` | PUT | 更新文章分类信息 |
| `/api/article-categories/{id}` | DELETE | 删除文章分类 |

 # 文章标签接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/article-tags` | GET | 获取文章标签列表 |
| `/api/article-tags` | POST | 创建文章标签 |
| `/api/article-tags/{id}` | GET | 获取文章标签详情 |
| `/api/article-tags/{id}` | PUT | 更新文章标签信息 |
| `/api/article-tags/{id}` | DELETE | 删除文章标签 |

 # 文章互动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/article-likes` | POST | 点赞文章 |
| `/api/article-likes` | DELETE | 取消点赞文章 |
| `/api/article-likes/status` | GET | 获取文章点赞状态 |
| `/api/article-favorites` | POST | 收藏文章 |
| `/api/article-favorites` | DELETE | 取消收藏文章 |
| `/api/article-favorites/status` | GET | 获取文章收藏状态 |

  数据库设计

 # articles 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| title | VARCHAR(255) | 文章标题 |
| summary | TEXT | 文章摘要 |
| content | TEXT | 文章内容 |
| cover_image | VARCHAR(500) | 封面图片URL |
| author_id | BIGINT | 作者ID |
| category_id | BIGINT | 分类ID |
| status | VARCHAR(20) | 状态（DRAFT, PUBLISHED, DELETED） |
| view_count | INT | 浏览次数 |
| like_count | INT | 点赞数 |
| comment_count | INT | 评论数 |
| favorite_count | INT | 收藏数 |
| published_at | BIGINT | 发布时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # article_categories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分类名称 |
| description | TEXT | 分类描述 |
| parent_id | BIGINT | 父分类ID |
| sort_order | INT | 排序 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # article_tags 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(50) | 标签名称 |
| article_count | INT | 文章数量 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # article_tag_relations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| article_id | BIGINT | 文章ID |
| tag_id | BIGINT | 标签ID |
| created_at | BIGINT | 创建时间 |

 # article_likes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| article_id | BIGINT | 文章ID |
| user_id | BIGINT | 用户ID |
| created_at | BIGINT | 创建时间 |

 # article_favorites 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| article_id | BIGINT | 文章ID |
| user_id | BIGINT | 用户ID |
| created_at | BIGINT | 创建时间 |

  Elasticsearch 索引设计

 # articles 索引

```
{
  "article_id": 12345,
  "title": "文章标题",
  "summary": "文章摘要",
  "content": "文章内容",
  "author_id": 67890,
  "author_name": "作者名称",
  "category_id": 54321,
  "category_name": "分类名称",
  "tags": ["标签1", "标签2"],
  "published_at": 1612345678901,
  "view_count": 1000,
  "like_count": 100,
  "comment_count": 50
}
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
  port: 8096

spring:
  application:
    name: service-article
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_article
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
```

 # 启动服务

```bash
cd service-article
mvn spring-boot:run
```

或

```bash
cd service-article
mvn clean package
java -jar target/service-article-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-comment (评论服务)
- service-like (点赞服务)
- service-search (搜索服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/article
├── ArticleApplication.java       # 应用入口
├── controller/                   # 控制器层
│   ├── ArticleController.java    # 文章控制器
│   ├── CategoryController.java   # 分类控制器
│   └── TagController.java        # 标签控制器
├── entity/                       # 实体类
│   ├── Article.java              # 文章实体
│   ├── ArticleCategory.java      # 文章分类实体
│   ├── ArticleTag.java           # 文章标签实体
│   ├── ArticleTagRelation.java   # 文章标签关系实体
│   ├── ArticleLike.java          # 文章点赞实体
│   └── ArticleFavorite.java      # 文章收藏实体
├── repository/                   # 数据访问层
│   ├── ArticleRepository.java    # 文章数据访问
│   ├── CategoryRepository.java   # 分类数据访问
│   ├── TagRepository.java        # 标签数据访问
│   ├── ArticleLikeRepository.java # 文章点赞数据访问
│   └── ArticleFavoriteRepository.java # 文章收藏数据访问
└── service/                      # 业务逻辑层
    ├── ArticleService.java       # 文章服务
    ├── CategoryService.java      # 分类服务
    └── TagService.java           # 标签服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8096/actuator/health