# 内容审核服务 (service-moderation)

  服务概述

内容审核服务是海牙平台的内容质量保障服务，负责对用户生成的内容进行自动化审核和人工审核管理，确保平台内容符合法律法规和社区规范。

  功能特性

1. 自动化内容审核
2. 人工审核工作流
3. 违规内容识别
4. 审核规则管理
5. 审核任务分配
6. 审核结果处理
7. 审核数据统计
8. 申诉处理机制

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎
- Apache Kafka 消息队列

  API 接口

 # 内容审核接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/moderation/content` | POST | 提交内容审核 |
| `/api/moderation/content/batch` | POST | 批量提交内容审核 |
| `/api/moderation/content/{id}` | GET | 获取内容审核详情 |
| `/api/moderation/content/{id}/status` | GET | 获取内容审核状态 |
| `/api/moderation/content/{id}/approve` | POST | 通过内容审核 |
| `/api/moderation/content/{id}/reject` | POST | 拒绝内容审核 |

 # 审核任务接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/moderation/tasks` | GET | 获取审核任务列表 |
| `/api/moderation/tasks/assign` | POST | 分配审核任务 |
| `/api/moderation/tasks/{id}` | GET | 获取审核任务详情 |
| `/api/moderation/tasks/{id}/complete` | POST | 完成审核任务 |
| `/api/moderation/tasks/{id}/reassign` | POST | 重新分配审核任务 |

 # 审核规则接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/moderation/rules` | GET | 获取审核规则列表 |
| `/api/moderation/rules` | POST | 创建审核规则 |
| `/api/moderation/rules/{id}` | GET | 获取审核规则详情 |
| `/api/moderation/rules/{id}` | PUT | 更新审核规则 |
| `/api/moderation/rules/{id}` | DELETE | 删除审核规则 |

 # 申诉处理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/moderation/appeals` | GET | 获取申诉列表 |
| `/api/moderation/appeals` | POST | 提交申诉 |
| `/api/moderation/appeals/{id}` | GET | 获取申诉详情 |
| `/api/moderation/appeals/{id}/process` | POST | 处理申诉 |
| `/api/moderation/appeals/{id}/approve` | POST | 通过申诉 |
| `/api/moderation/appeals/{id}/reject` | POST | 拒绝申诉 |

 # 审核统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/moderation/stats/overview` | GET | 获取审核概览统计 |
| `/api/moderation/stats/user-performance` | GET | 获取审核员绩效统计 |
| `/api/moderation/stats/content-categories` | GET | 获取内容分类统计 |
| `/api/moderation/stats/violation-types` | GET | 获取违规类型统计 |

  数据库设计

 # moderation_content 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| content_type | VARCHAR(50) | 内容类型（TEXT, IMAGE, VIDEO, AUDIO） |
| content_url | VARCHAR(500) | 内容URL |
| submitter_id | BIGINT | 提交者ID |
| status | VARCHAR(20) | 状态（PENDING, AUTO_APPROVED, AUTO_REJECTED, MANUAL_REVIEW, APPROVED, REJECTED） |
| auto_review_result | TEXT | 自动审核结果（JSON格式） |
| manual_reviewer_id | BIGINT | 人工审核员ID |
| manual_review_result | TEXT | 人工审核结果（JSON格式） |
| submitted_at | BIGINT | 提交时间 |
| auto_reviewed_at | BIGINT | 自动审核时间 |
| manual_reviewed_at | BIGINT | 人工审核时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # moderation_tasks 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| assignee_id | BIGINT | 指派审核员ID |
| status | VARCHAR(20) | 状态（ASSIGNED, IN_PROGRESS, COMPLETED, REASSIGNED） |
| priority | VARCHAR(20) | 优先级（LOW, MEDIUM, HIGH, URGENT） |
| assigned_at | BIGINT | 指派时间 |
| started_at | BIGINT | 开始时间 |
| completed_at | BIGINT | 完成时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # moderation_rules 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 规则名称 |
| description | TEXT | 规则描述 |
| rule_type | VARCHAR(50) | 规则类型（KEYWORD, IMAGE_RECOGNITION, VIDEO_ANALYSIS, AUDIO_ANALYSIS） |
| rule_config | TEXT | 规则配置（JSON格式） |
| action | VARCHAR(20) | 动作（APPROVE, REJECT, MANUAL_REVIEW） |
| severity_level | VARCHAR(20) | 严重级别（LOW, MEDIUM, HIGH, CRITICAL） |
| is_active | BOOLEAN | 是否激活 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # moderation_appeals 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| appellant_id | BIGINT | 申诉人ID |
| reason | TEXT | 申诉理由 |
| status | VARCHAR(20) | 状态（SUBMITTED, PROCESSING, APPROVED, REJECTED） |
| processor_id | BIGINT | 处理人ID |
| processing_result | TEXT | 处理结果 |
| submitted_at | BIGINT | 提交时间 |
| processed_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # moderation_reviewers 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| role | VARCHAR(20) | 角色（REVIEWER, SUPERVISOR, ADMIN） |
| expertise_areas | TEXT | 专业领域（JSON格式） |
| performance_score | DECIMAL(5,2) | 绩效评分 |
| total_reviews | INT | 总审核数 |
| approved_reviews | INT | 通过审核数 |
| rejected_reviews | INT | 拒绝审核数 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, SUSPENDED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # moderation_stats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| stat_type | VARCHAR(50) | 统计类型（CONTENT_REVIEW, APPEAL_PROCESSING等） |
| stat_date | DATE | 统计日期 |
| stat_value | TEXT | 统计值（JSON格式） |
| created_at | BIGINT | 创建时间 |

  Elasticsearch 索引设计

 # 审核内容索引
```
Index: moderation-content
  - content_id: 内容ID
  - content_type: 内容类型
  - status: 审核状态
  - submitter_id: 提交者ID
  - submitted_at: 提交时间
  - violation_types: 违规类型列表
```

 # 审核日志索引
```
Index: moderation-logs
  - content_id: 内容ID
  - action: 操作类型
  - operator_id: 操作人ID
  - timestamp: 时间戳
  - details: 操作详情
```

  Redis 缓存设计

 # 审核任务缓存
```
moderation:task:queue -> List结构存储待审核任务队列
moderation:task:assignee:{assigneeId} -> Set结构存储指派给审核员的任务
```

 # 审核规则缓存
```
moderation:rules -> List结构存储所有审核规则
moderation:rule:{ruleId} -> String结构存储规则详情
```

  Kafka 消息队列设计

 # 内容审核主题
```
Topic: moderation.content-submitted
  - 分区: 6
  - 副本: 3
```

 # 审核结果主题
```
Topic: moderation.review-completed
  - 分区: 6
  - 副本: 3
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Elasticsearch 搜索引擎
- Apache Kafka 消息队列

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8125

spring:
  application:
    name: service-moderation
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_moderation
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
    
elasticsearch:
  host: localhost
  port: 9200

moderation:
  auto-review:
    enabled: true
    providers:
      - name: content-security
        weight: 80
      - name: ml-model
        weight: 20
```

 # 启动服务

```bash
cd service-moderation
mvn spring-boot:run
```

或

```bash
cd service-moderation
mvn clean package
java -jar target/service-moderation-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-content-security (内容安全服务)
- service-ml-platform (机器学习平台服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/moderation
├── ModerationServiceApplication.java  # 应用入口
├── config/                            # 配置类
│   └── ModerationConfig.java          # 内容审核配置
├── controller/                        # 控制器层
│   ├── ContentController.java         # 内容控制器
│   ├── TaskController.java            # 任务控制器
│   ├── RuleController.java            # 规则控制器
│   ├── AppealController.java          # 申诉控制器
│   └── StatController.java            # 统计控制器
├── entity/                            # 实体类
│   ├── ModerationContent.java         # 审核内容实体
│   ├── ModerationTask.java            # 审核任务实体
│   ├── ModerationRule.java            # 审核规则实体
│   ├── ModerationAppeal.java          # 申诉实体
│   ├── ModerationReviewer.java        # 审核员实体
│   └── ModerationStat.java            # 审核统计实体
├── repository/                        # 数据访问层
│   ├── ModerationContentRepository.java # 审核内容数据访问
│   ├── ModerationTaskRepository.java  # 审核任务数据访问
│   ├── ModerationRuleRepository.java  # 审核规则数据访问
│   ├── ModerationAppealRepository.java # 申诉数据访问
│   ├── ModerationReviewerRepository.java # 审核员数据访问
│   └── ModerationStatRepository.java  # 审核统计数据访问
└── service/                           # 业务逻辑层
    ├── ContentService.java            # 内容服务
    ├── TaskService.java               # 任务服务
    ├── RuleService.java               # 规则服务
    ├── AppealService.java             # 申诉服务
    └── StatService.java               # 统计服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8125/actuator/health