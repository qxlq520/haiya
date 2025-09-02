# 版权服务 (service-copyright)

  服务概述

版权服务是海牙平台的知识产权保护服务，负责处理平台内容的版权登记、版权验证、侵权检测、版权纠纷处理等核心功能。

  功能特性

1. 版权登记与管理
2. 版权验证与查询
3. 侵权内容检测
4. 版权纠纷处理
5. 版权使用授权管理
6. 版权收益分配
7. 版权链路追踪
8. 版权数据统计分析

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- 区块链技术（可选）

  API 接口

 # 版权登记接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/copyrights` | POST | 申请版权登记 |
| `/api/copyrights` | GET | 获取版权列表 |
| `/api/copyrights/{id}` | GET | 获取版权详情 |
| `/api/copyrights/{id}` | PUT | 更新版权信息 |
| `/api/copyrights/{id}/status` | PUT | 更新版权状态 |

 # 版权验证接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/copyright-verification` | POST | 版权验证 |
| `/api/copyright-verification/batch` | POST | 批量版权验证 |
| `/api/copyright-verification/result/{id}` | GET | 获取验证结果 |

 # 侵权检测接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/infringement-detection` | POST | 侵权检测 |
| `/api/infringement-detection/batch` | POST | 批量侵权检测 |
| `/api/infringement-reports` | GET | 获取侵权报告列表 |
| `/api/infringement-reports/{id}` | GET | 获取侵权报告详情 |

 # 授权管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/copyright-licenses` | POST | 创建授权许可 |
| `/api/copyright-licenses` | GET | 获取授权许可列表 |
| `/api/copyright-licenses/{id}` | GET | 获取授权许可详情 |
| `/api/copyright-licenses/{id}/revoke` | POST | 撤销授权许可 |

  数据库设计

 # copyrights 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| title | VARCHAR(255) | 作品标题 |
| description | TEXT | 作品描述 |
| content_type | VARCHAR(50) | 内容类型（VIDEO, IMAGE, AUDIO, ARTICLE等） |
| content_id | BIGINT | 内容ID |
| content_url | VARCHAR(500) | 内容URL |
| owner_id | BIGINT | 版权所有者ID |
| registration_number | VARCHAR(100) | 登记号 |
| registration_date | BIGINT | 登记日期 |
| expiry_date | BIGINT | 到期日期 |
| status | VARCHAR(20) | 状态（PENDING, APPROVED, REJECTED, EXPIRED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # copyright_verifications 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| content_id | BIGINT | 内容ID |
| content_url | VARCHAR(500) | 内容URL |
| copyright_id | BIGINT | 版权ID |
| verification_result | TEXT | 验证结果（JSON格式） |
| similarity_score | DECIMAL(5,2) | 相似度得分 |
| status | VARCHAR(20) | 状态（PENDING, PROCESSING, COMPLETED, FAILED） |
| verified_at | BIGINT | 验证时间 |
| created_at | BIGINT | 创建时间 |

 # infringement_reports 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| reported_content_id | BIGINT | 被举报内容ID |
| reported_content_url | VARCHAR(500) | 被举报内容URL |
| copyright_id | BIGINT | 版权ID |
| reporter_id | BIGINT | 举报者ID |
| evidence | TEXT | 证据（JSON格式） |
| description | TEXT | 描述 |
| status | VARCHAR(20) | 状态（REPORTED, INVESTIGATING, CONFIRMED, REJECTED, RESOLVED） |
| handler_id | BIGINT | 处理者ID |
| handled_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # copyright_licenses 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| copyright_id | BIGINT | 版权ID |
| licensee_id | BIGINT | 被许可方ID |
| license_type | VARCHAR(50) | 许可类型（PERSONAL, COMMERCIAL等） |
| scope | TEXT | 使用范围（JSON格式） |
| start_date | BIGINT | 开始日期 |
| end_date | BIGINT | 结束日期 |
| status | VARCHAR(20) | 状态（ACTIVE, EXPIRED, REVOKED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # copyright_earnings 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| copyright_id | BIGINT | 版权ID |
| earner_id | BIGINT | 收益者ID |
| amount | DECIMAL(10,2) | 金额 |
| currency | VARCHAR(10) | 货币 |
| source_type | VARCHAR(50) | 来源类型（LICENSE, USAGE, AD_REVENUE等） |
| source_id | BIGINT | 来源ID |
| settled_at | BIGINT | 结算时间 |
| created_at | BIGINT | 创建时间 |

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
  port: 8103

spring:
  application:
    name: service-copyright
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_copyright
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
cd service-copyright
mvn spring-boot:run
```

或

```bash
cd service-copyright
mvn clean package
java -jar target/service-copyright-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-audio (音频服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/copyright
├── CopyrightApplication.java     # 应用入口
├── controller/                   # 控制器层
│   ├── CopyrightController.java  # 版权控制器
│   ├── VerificationController.java # 验证控制器
│   ├── InfringementController.java # 侵权控制器
│   └── LicenseController.java    # 授权控制器
├── entity/                       # 实体类
│   ├── Copyright.java            # 版权实体
│   ├── Verification.java         # 验证实体
│   ├── InfringementReport.java   # 侵权报告实体
│   ├── License.java              # 授权实体
│   └── Earning.java              # 收益实体
├── repository/                   # 数据访问层
│   ├── CopyrightRepository.java  # 版权数据访问
│   ├── VerificationRepository.java # 验证数据访问
│   ├── InfringementReportRepository.java # 侵权报告数据访问
│   ├── LicenseRepository.java    # 授权数据访问
│   └── EarningRepository.java    # 收益数据访问
└── service/                      # 业务逻辑层
    ├── CopyrightService.java     # 版权服务
    ├── VerificationService.java  # 验证服务
    ├── InfringementService.java  # 侵权服务
    └── LicenseService.java       # 授权服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8103/actuator/health