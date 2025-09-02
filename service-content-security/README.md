# 内容安全服务 (service-content-security)

  服务概述

内容安全服务是海牙平台的安全服务之一，负责处理平台内容的安全检查、敏感内容识别、违规内容过滤等核心功能。

  功能特性

1. 文本内容安全检查
2. 图片内容安全检查
3. 视频内容安全检查
4. 敏感词过滤
5. 违规内容识别与处理
6. 内容安全等级评估
7. 实时内容安全监控
8. 内容安全日志记录

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- TensorFlow/PyTorch 机器学习框架

  API 接口

 # 内容安全检查接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/content-security/check/text` | POST | 文本内容安全检查 |
| `/api/content-security/check/image` | POST | 图片内容安全检查 |
| `/api/content-security/check/video` | POST | 视频内容安全检查 |
| `/api/content-security/check/batch` | POST | 批量内容安全检查 |

 # 敏感词管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/sensitive-words` | GET | 获取敏感词列表 |
| `/api/sensitive-words` | POST | 添加敏感词 |
| `/api/sensitive-words/{id}` | PUT | 更新敏感词 |
| `/api/sensitive-words/{id}` | DELETE | 删除敏感词 |
| `/api/sensitive-words/batch` | POST | 批量操作敏感词 |
| `/api/sensitive-words/import` | POST | 导入敏感词 |

 # 违规内容管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/violations` | GET | 获取违规内容列表 |
| `/api/violations/{id}` | GET | 获取违规内容详情 |
| `/api/violations/{id}/handle` | POST | 处理违规内容 |
| `/api/violations/batch-handle` | POST | 批量处理违规内容 |

 # 内容安全统计接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/content-security/stats` | GET | 获取内容安全统计数据 |
| `/api/content-security/stats/daily` | GET | 获取每日内容安全统计 |
| `/api/content-security/risk-trends` | GET | 获取风险趋势数据 |

  数据库设计

 # content_security_checks 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| content_type | VARCHAR(50) | 内容类型（TEXT, IMAGE, VIDEO） |
| content_url | VARCHAR(500) | 内容URL |
| check_result | TEXT | 检查结果（JSON格式） |
| risk_level | VARCHAR(20) | 风险等级（SAFE, LOW_RISK, HIGH_RISK, VIOLATION） |
| check_status | VARCHAR(20) | 检查状态（PENDING, PROCESSING, COMPLETED, FAILED） |
| checked_at | BIGINT | 检查时间 |
| created_at | BIGINT | 创建时间 |

 # sensitive_words 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| word | VARCHAR(255) | 敏感词 |
| category | VARCHAR(50) | 分类（POLITICAL, VIOLENT, PORN, ADVERTISING等） |
| level | VARCHAR(20) | 等级（LOW, MEDIUM, HIGH） |
| replacement | VARCHAR(255) | 替换词 |
| is_active | BOOLEAN | 是否启用 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # content_violations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| content_type | VARCHAR(50) | 内容类型 |
| content_url | VARCHAR(500) | 内容URL |
| violation_type | VARCHAR(50) | 违规类型 |
| description | TEXT | 描述 |
| evidence | TEXT | 证据（JSON格式） |
| status | VARCHAR(20) | 状态（DETECTED, HANDLED, APPEALED, DISMISSED） |
| handler_id | BIGINT | 处理者ID |
| handled_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # content_security_stats 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| stat_date | DATE | 统计日期 |
| total_checks | INT | 总检查次数 |
| safe_content | INT | 安全内容数 |
| low_risk_content | INT | 低风险内容数 |
| high_risk_content | INT | 高风险内容数 |
| violations | INT | 违规内容数 |
| false_positives | INT | 误报数 |
| created_at | BIGINT | 创建时间 |

 # content_security_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| content_type | VARCHAR(50) | 内容类型 |
| action | VARCHAR(50) | 操作（CHECK, BLOCK, ALLOW, REPORT等） |
| details | TEXT | 详情（JSON格式） |
| operator_id | BIGINT | 操作者ID |
| ip_address | VARCHAR(50) | IP地址 |
| user_agent | TEXT | 用户代理 |
| created_at | BIGINT | 创建时间 |

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- TensorFlow/PyTorch 机器学习框架

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8102

spring:
  application:
    name: service-content-security
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_content_security
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
cd service-content-security
mvn spring-boot:run
```

或

```bash
cd service-content-security
mvn clean package
java -jar target/service-content-security-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-comment (评论服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/content/security
├── ContentSecurityServiceApplication.java  # 应用入口
├── controller/                             # 控制器层
│   ├── ContentSecurityController.java      # 内容安全控制器
│   ├── SensitiveWordController.java        # 敏感词控制器
│   ├── ViolationController.java            # 违规内容控制器
│   └── StatController.java                 # 统计控制器
├── entity/                                 # 实体类
│   ├── ContentSecurityCheck.java           # 内容安全检查实体
│   ├── SensitiveWord.java                  # 敏感词实体
│   ├── ContentViolation.java               # 内容违规实体
│   ├── ContentSecurityStat.java            # 内容安全统计实体
│   └── ContentSecurityLog.java             # 内容安全日志实体
├── repository/                             # 数据访问层
│   ├── ContentSecurityCheckRepository.java # 内容安全检查数据访问
│   ├── SensitiveWordRepository.java        # 敏感词数据访问
│   ├── ContentViolationRepository.java     # 内容违规数据访问
│   ├── ContentSecurityStatRepository.java  # 内容安全统计数据访问
│   └── ContentSecurityLogRepository.java   # 内容安全日志数据访问
└── service/                                # 业务逻辑层
    ├── ContentSecurityService.java         # 内容安全服务
    ├── SensitiveWordService.java           # 敏感词服务
    ├── ViolationService.java               # 违规内容服务
    └── StatService.java                    # 统计服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8102/actuator/health