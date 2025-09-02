# 未成年人保护服务 (service-minor-protection)

  服务概述

未成年人保护服务是海牙平台的重要合规服务，负责实现对未成年人用户的保护机制，包括实名认证、防沉迷系统、内容过滤、消费限制等功能。

  功能特性

1. 实名认证管理
2. 防沉迷系统
3. 内容访问控制
4. 消费限制管理
5. 使用时间监控
6. 家长监护功能
7. 举报处理机制
8. 合规数据报告

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存

  API 接口

 # 实名认证接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/minor-protection/real-name` | POST | 提交实名认证 |
| `/api/minor-protection/real-name/{userId}` | GET | 获取实名认证状态 |
| `/api/minor-protection/real-name/{userId}/verify` | POST | 验证实名认证 |
| `/api/minor-protection/real-name/batch-verify` | POST | 批量验证实名认证 |

 # 防沉迷接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/minor-protection/anti-addiction/status` | GET | 获取防沉迷状态 |
| `/api/minor-protection/anti-addiction/duration` | POST | 上报使用时长 |
| `/api/minor-protection/anti-addiction/reset` | POST | 重置使用时长 |
| `/api/minor-protection/anti-addiction/blocked` | GET | 检查是否被限制 |

 # 内容过滤接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/minor-protection/content-filter/check` | POST | 检查内容是否适合未成年人 |
| `/api/minor-protection/content-filter/batch-check` | POST | 批量检查内容 |
| `/api/minor-protection/content-filter/rules` | GET | 获取内容过滤规则 |
| `/api/minor-protection/content-filter/rules` | POST | 创建内容过滤规则 |

 # 消费限制接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/minor-protection/spending-limit` | GET | 获取消费限制 |
| `/api/minor-protection/spending-limit` | POST | 设置消费限制 |
| `/api/minor-protection/spending-limit/check` | POST | 检查消费是否超限 |
| `/api/minor-protection/spending-limit/reset` | POST | 重置消费额度 |

 # 家长监护接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/minor-protection/parental-control/bind` | POST | 绑定家长账户 |
| `/api/minor-protection/parental-control/unbind` | POST | 解绑家长账户 |
| `/api/minor-protection/parental-control/settings` | GET | 获取监护设置 |
| `/api/minor-protection/parental-control/settings` | PUT | 更新监护设置 |

  数据库设计

 # user_real_name_auth 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| real_name | VARCHAR(100) | 真实姓名 |
| id_card_number | VARCHAR(50) | 身份证号（加密存储） |
| age | INT | 年龄 |
| is_minor | BOOLEAN | 是否未成年人 |
| auth_status | VARCHAR(20) | 认证状态（PENDING, APPROVED, REJECTED） |
| auth_time | BIGINT | 认证时间 |
| expiry_time | BIGINT | 过期时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # anti_addiction_records 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| login_time | BIGINT | 登录时间 |
| logout_time | BIGINT | 登出时间 |
| duration_today | INT | 今日累计时长（分钟） |
| duration_weekly | INT | 本周累计时长（分钟） |
| status | VARCHAR(20) | 状态（NORMAL, WARNING, BLOCKED） |
| blocked_reason | VARCHAR(255) | 限制原因 |
| record_date | DATE | 记录日期 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # content_filter_rules 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| rule_name | VARCHAR(100) | 规则名称 |
| rule_type | VARCHAR(20) | 规则类型（KEYWORD, CATEGORY, RATING） |
| rule_content | TEXT | 规则内容 |
| severity_level | VARCHAR(20) | 严重级别（LOW, MEDIUM, HIGH） |
| action | VARCHAR(20) | 动作（BLOCK, WARN, ALLOW） |
| target_age_group | VARCHAR(20) | 目标年龄组（ALL, MINOR, ADULT） |
| is_active | BOOLEAN | 是否激活 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # spending_limits 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| daily_limit | DECIMAL(10,2) | 每日限额 |
| monthly_limit | DECIMAL(10,2) | 每月限额 |
| single_limit | DECIMAL(10,2) | 单次限额 |
| used_today | DECIMAL(10,2) | 今日已用 |
| used_this_month | DECIMAL(10,2) | 本月已用 |
| reset_time_daily | BIGINT | 每日重置时间 |
| reset_time_monthly | BIGINT | 每月重置时间 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # parental_controls 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| minor_user_id | BIGINT | 未成年人用户ID |
| parent_user_id | BIGINT | 家长用户ID |
| relationship | VARCHAR(20) | 关系（FATHER, MOTHER, GUARDIAN） |
| permissions | TEXT | 权限设置（JSON格式） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| bound_at | BIGINT | 绑定时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # protection_reports 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| reporter_id | BIGINT | 举报人ID |
| target_user_id | BIGINT | 被举报用户ID |
| content_id | BIGINT | 内容ID |
| report_type | VARCHAR(50) | 举报类型（INAPPROPRIATE_CONTENT, ADDICTION_RISK等） |
| description | TEXT | 描述 |
| evidence | TEXT | 证据（JSON格式） |
| status | VARCHAR(20) | 状态（REPORTED, INVESTIGATING, RESOLVED, DISMISSED） |
| handler_id | BIGINT | 处理人ID |
| handled_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  Redis 缓存设计

 # 防沉迷状态缓存
```
minor:anti-addiction:{userId}:status -> String结构存储防沉迷状态
minor:anti-addiction:{userId}:duration -> Hash结构存储使用时长
```

 # 内容过滤缓存
```
minor:content-filter:keywords -> Set结构存储过滤关键词
minor:content-filter:categories -> Set结构存储过滤分类
```

 # 消费限制缓存
```
minor:spending-limit:{userId} -> Hash结构存储消费限制信息
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8123

spring:
  application:
    name: service-minor-protection
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_minor_protection
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
cd service-minor-protection
mvn spring-boot:run
```

或

```bash
cd service-minor-protection
mvn clean package
java -jar target/service-minor-protection-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-content-security (内容安全服务)
- service-payment (支付服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/minor/protection
├── MinorProtectionApplication.java  # 应用入口
├── controller/                      # 控制器层
│   ├── RealNameController.java      # 实名认证控制器
│   ├── AntiAddictionController.java # 防沉迷控制器
│   ├── ContentFilterController.java # 内容过滤控制器
│   ├── SpendingLimitController.java # 消费限制控制器
│   └── ParentalControlController.java # 家长监护控制器
├── entity/                          # 实体类
│   ├── UserRealNameAuth.java        # 用户实名认证实体
│   ├── AntiAddictionRecord.java     # 防沉迷记录实体
│   ├── ContentFilterRule.java       # 内容过滤规则实体
│   ├── SpendingLimit.java           # 消费限制实体
│   ├── ParentalControl.java         # 家长监护实体
│   └── ProtectionReport.java        # 保护报告实体
├── repository/                      # 数据访问层
│   ├── UserRealNameAuthRepository.java # 用户实名认证数据访问
│   ├── AntiAddictionRecordRepository.java # 防沉迷记录数据访问
│   ├── ContentFilterRuleRepository.java # 内容过滤规则数据访问
│   ├── SpendingLimitRepository.java # 消费限制数据访问
│   ├── ParentalControlRepository.java # 家长监护数据访问
│   └── ProtectionReportRepository.java # 保护报告数据访问
└── service/                         # 业务逻辑层
    ├── RealNameService.java         # 实名认证服务
    ├── AntiAddictionService.java    # 防沉迷服务
    ├── ContentFilterService.java    # 内容过滤服务
    ├── SpendingLimitService.java    # 消费限制服务
    └── ParentalControlService.java  # 家长监护服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8123/actuator/health