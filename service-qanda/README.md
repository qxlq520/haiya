# 问答服务 (service-qanda)

  服务概述

问答服务是海牙平台的知识社区服务，为用户提供提问、回答、讨论和知识分享功能，构建一个高质量的知识交流平台。

  功能特性

1. 问题发布与管理
2. 回答发布与管理
3. 问题分类与标签
4. 评论与讨论
5. 点赞与收藏
6. 搜索与筛选
7. 问答推荐
8. 专家认证与邀请

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎

  API 接口

 # 问题管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/questions` | POST | 提问 |
| `/api/questions` | GET | 获取问题列表 |
| `/api/questions/{id}` | GET | 获取问题详情 |
| `/api/questions/{id}` | PUT | 更新问题 |
| `/api/questions/{id}/delete` | DELETE | 删除问题 |
| `/api/questions/{id}/close` | POST | 关闭问题 |

 # 回答管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/answers` | POST | 回答问题 |
| `/api/answers` | GET | 获取回答列表 |
| `/api/answers/{id}` | GET | 获取回答详情 |
| `/api/answers/{id}` | PUT | 更新回答 |
| `/api/answers/{id}/delete` | DELETE | 删除回答 |
| `/api/answers/question/{questionId}` | GET | 获取问题的回答列表 |

 # 评论管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/comments` | POST | 发表评论 |
| `/api/comments` | GET | 获取评论列表 |
| `/api/comments/{id}` | GET | 获取评论详情 |
| `/api/comments/{id}/delete` | DELETE | 删除评论 |
| `/api/comments/target/{targetId}` | GET | 获取目标的评论列表 |

 # 点赞收藏接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/qanda/interactions/like` | POST | 点赞 |
| `/api/qanda/interactions/unlike` | POST | 取消点赞 |
| `/api/qanda/interactions/favorite` | POST | 收藏 |
| `/api/qanda/interactions/unfavorite` | POST | 取消收藏 |
| `/api/qanda/interactions/status` | GET | 获取互动状态 |

 # 分类标签接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/qanda/categories` | GET | 获取分类列表 |
| `/api/qanda/categories` | POST | 创建分类 |
| `/api/qanda/tags` | GET | 获取标签列表 |
| `/api/qanda/tags` | POST | 创建标签 |
| `/api/qanda/tags/popular` | GET | 获取热门标签 |

 # 搜索接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/qanda/search/questions` | GET | 搜索问题 |
| `/api/qanda/search/answers` | GET | 搜索回答 |
| `/api/qanda/search/users` | GET | 搜索用户 |
| `/api/qanda/search/suggestions` | GET | 获取搜索建议 |

  数据库设计

 # questions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| title | VARCHAR(255) | 问题标题 |
| content | TEXT | 问题内容 |
| author_id | BIGINT | 提问者ID |
| category_id | BIGINT | 分类ID |
| tags | TEXT | 标签（JSON格式） |
| view_count | INT | 浏览数 |
| answer_count | INT | 回答数 |
| like_count | INT | 点赞数 |
| favorite_count | INT | 收藏数 |
| status | VARCHAR(20) | 状态（OPEN, CLOSED, DELETED） |
| is_anonymous | BOOLEAN | 是否匿名 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # answers 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| question_id | BIGINT | 问题ID |
| content | TEXT | 回答内容 |
| author_id | BIGINT | 回答者ID |
| like_count | INT | 点赞数 |
| comment_count | INT | 评论数 |
| is_accepted | BOOLEAN | 是否被采纳 |
| accepted_at | BIGINT | 采纳时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # qanda_comments 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| target_id | BIGINT | 目标ID（问题或回答ID） |
| target_type | VARCHAR(20) | 目标类型（QUESTION, ANSWER） |
| content | TEXT | 评论内容 |
| author_id | BIGINT | 评论者ID |
| reply_to_id | BIGINT | 回复评论ID |
| like_count | INT | 点赞数 |
| status | VARCHAR(20) | 状态（NORMAL, DELETED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # qanda_categories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 分类名称 |
| description | TEXT | 分类描述 |
| parent_id | BIGINT | 父分类ID |
| icon_url | VARCHAR(500) | 图标URL |
| sort_order | INT | 排序 |
| is_active | BOOLEAN | 是否激活 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # qanda_tags 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(50) | 标签名称 |
| description | TEXT | 标签描述 |
| question_count | INT | 问题数 |
| follow_count | INT | 关注数 |
| is_official | BOOLEAN | 是否官方标签 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # qanda_interactions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| target_id | BIGINT | 目标ID |
| target_type | VARCHAR(20) | 目标类型（QUESTION, ANSWER, COMMENT） |
| interaction_type | VARCHAR(20) | 互动类型（LIKE, FAVORITE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # qanda_experts 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| category_id | BIGINT | 分类ID |
| title | VARCHAR(100) | 专家头衔 |
| introduction | TEXT | 专家介绍 |
| answer_count | INT | 回答数 |
| accepted_count | INT | 被采纳回答数 |
| score | DECIMAL(5,2) | 专家评分 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| certified_at | BIGINT | 认证时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  Elasticsearch 索引设计

 # 问题索引
```
Index: qanda-questions
  - question_id: 问题ID
  - title: 问题标题
  - content: 问题内容
  - author_id: 提问者ID
  - category_id: 分类ID
  - tags: 标签
  - created_at: 创建时间
  - answer_count: 回答数
  - view_count: 浏览数
```

 # 回答索引
```
Index: qanda-answers
  - answer_id: 回答ID
  - question_id: 问题ID
  - content: 回答内容
  - author_id: 回答者ID
  - created_at: 创建时间
  - like_count: 点赞数
```

  Redis 缓存设计

 # 问答缓存
```
qanda:question:{questionId} -> Hash结构存储问题详情
qanda:answer:{answerId} -> Hash结构存储回答详情
qanda:question:{questionId}:answers -> List结构存储问题的回答列表
```

 # 互动缓存
```
qanda:interaction:user:{userId}:liked -> Set结构存储用户点赞内容
qanda:interaction:user:{userId}:favorited -> Set结构存储用户收藏内容
```

 # 热门内容缓存
```
qanda:popular:questions -> ZSet结构存储热门问题
qanda:popular:tags -> ZSet结构存储热门标签
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
  port: 8129

spring:
  application:
    name: service-qanda
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_qanda
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

qanda:
  search:
    enabled: true
  recommendation:
    enabled: true
```

 # 启动服务

```bash
cd service-qanda
mvn spring-boot:run
```

或

```bash
cd service-qanda
mvn clean package
java -jar target/service-qanda-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-search (搜索服务)
- service-recommend (推荐服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/qanda
├── QandaApplication.java        # 应用入口
├── controller/                  # 控制器层
│   ├── QuestionController.java  # 问题控制器
│   ├── AnswerController.java    # 回答控制器
│   ├── CommentController.java   # 评论控制器
│   ├── CategoryController.java  # 分类控制器
│   ├── TagController.java       # 标签控制器
│   └── InteractionController.java # 互动控制器
├── entity/                      # 实体类
│   ├── Question.java            # 问题实体
│   ├── Answer.java              # 回答实体
│   ├── Comment.java             # 评论实体
│   ├── Category.java            # 分类实体
│   ├── Tag.java                 # 标签实体
│   ├── Interaction.java         # 互动实体
│   └── Expert.java              # 专家实体
├── repository/                  # 数据访问层
│   ├── QuestionRepository.java  # 问题数据访问
│   ├── AnswerRepository.java    # 回答数据访问
│   ├── CommentRepository.java   # 评论数据访问
│   ├── CategoryRepository.java  # 分类数据访问
│   ├── TagRepository.java       # 标签数据访问
│   ├── InteractionRepository.java # 互动数据访问
│   └── ExpertRepository.java    # 专家数据访问
└── service/                     # 业务逻辑层
    ├── QuestionService.java     # 问题服务
    ├── AnswerService.java       # 回答服务
    ├── CommentService.java      # 评论服务
    ├── CategoryService.java     # 分类服务
    ├── TagService.java          # 标签服务
    └── InteractionService.java  # 互动服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8129/actuator/health