# 数据收集服务 (service-data-collector)

  服务概述

数据收集服务是海牙平台的数据基础设施服务，负责从各个业务服务中收集、聚合和预处理数据，为后续的数据分析和商业智能提供统一的数据源。

  功能特性

1. 多源数据收集
2. 实时数据流处理
3. 批量数据处理
4. 数据清洗与验证
5. 数据格式标准化
6. 数据路由与分发
7. 数据质量监控
8. 数据收集日志记录

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Apache Kafka 消息队列
- Apache Spark 大数据处理框架

  API 接口

 # 数据收集接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/data-collection/events` | POST | 上报业务事件数据 |
| `/api/data-collection/batch` | POST | 批量上报数据 |
| `/api/data-collection/stream` | POST | 实时数据流接入 |

 # 数据源管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/data-sources` | GET | 获取数据源列表 |
| `/api/data-sources` | POST | 创建数据源 |
| `/api/data-sources/{id}` | GET | 获取数据源详情 |
| `/api/data-sources/{id}` | PUT | 更新数据源配置 |
| `/api/data-sources/{id}` | DELETE | 删除数据源 |

 # 数据收集任务接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/collection-tasks` | GET | 获取收集任务列表 |
| `/api/collection-tasks` | POST | 创建收集任务 |
| `/api/collection-tasks/{id}` | GET | 获取收集任务详情 |
| `/api/collection-tasks/{id}/status` | PUT | 更新收集任务状态 |
| `/api/collection-tasks/{id}/run` | POST | 执行收集任务 |

 # 数据质量监控接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/data-quality/metrics` | GET | 获取数据质量指标 |
| `/api/data-quality/reports` | GET | 获取数据质量报告 |
| `/api/data-quality/issues` | GET | 获取数据质量问题列表 |
| `/api/data-quality/issues/{id}` | GET | 获取数据质量问题详情 |

  数据库设计

 # data_sources 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 数据源名称 |
| description | TEXT | 数据源描述 |
| type | VARCHAR(50) | 数据源类型（DATABASE, API, FILE, STREAM等） |
| connection_config | TEXT | 连接配置（JSON格式） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, ERROR） |
| last_collected_at | BIGINT | 最后收集时间 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # collection_tasks 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 任务名称 |
| source_id | BIGINT | 数据源ID |
| schedule | VARCHAR(100) | 调度配置（CRON表达式） |
| task_config | TEXT | 任务配置（JSON格式） |
| status | VARCHAR(20) | 状态（PENDING, RUNNING, COMPLETED, FAILED） |
| last_run_at | BIGINT | 最后运行时间 |
| next_run_at | BIGINT | 下次运行时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # collected_data 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| source_id | BIGINT | 数据源ID |
| task_id | BIGINT | 任务ID |
| data_type | VARCHAR(50) | 数据类型 |
| raw_data | TEXT | 原始数据（JSON格式） |
| processed_data | TEXT | 处理后数据（JSON格式） |
| status | VARCHAR(20) | 状态（RECEIVED, PROCESSED, FAILED） |
| collected_at | BIGINT | 收集时间 |
| processed_at | BIGINT | 处理时间 |
| created_at | BIGINT | 创建时间 |

 # data_quality_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| source_id | BIGINT | 数据源ID |
| metric_type | VARCHAR(50) | 指标类型（COMPLETENESS, ACCURACY, CONSISTENCY等） |
| metric_value | DECIMAL(10,2) | 指标值 |
| threshold | DECIMAL(10,2) | 阈值 |
| status | VARCHAR(20) | 状态（NORMAL, WARNING, ALERT） |
| measured_at | BIGINT | 测量时间 |
| created_at | BIGINT | 创建时间 |

 # collection_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| task_id | BIGINT | 任务ID |
| source_id | BIGINT | 数据源ID |
| log_level | VARCHAR(20) | 日志级别（INFO, WARN, ERROR） |
| message | TEXT | 日志消息 |
| details | TEXT | 详细信息（JSON格式） |
| logged_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

  Kafka 主题设计

 # 数据收集主题

```
data-collection.events     # 业务事件数据
data-collection.batch      # 批量数据
data-collection.stream     # 实时数据流
data-collection.logs       # 收集日志
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Apache Kafka 消息队列
- Apache Spark 大数据处理框架

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8106

spring:
  application:
    name: service-data-collector
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_data_collector
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
```

 # 启动服务

```bash
cd service-data-collector
mvn spring-boot:run
```

或

```bash
cd service-data-collector
mvn clean package
java -jar target/service-data-collector-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-audio (音频服务)
- service-payment (支付服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/data/collector
├── DataCollectorApplication.java  # 应用入口
├── controller/                    # 控制器层
│   ├── DataCollectionController.java # 数据收集控制器
│   ├── DataSourceController.java  # 数据源控制器
│   ├── TaskController.java        # 任务控制器
│   └── QualityController.java     # 质量监控控制器
├── entity/                        # 实体类
│   ├── DataSource.java            # 数据源实体
│   ├── CollectionTask.java        # 收集任务实体
│   ├── CollectedData.java         # 收集数据实体
│   ├── DataQualityMetric.java     # 数据质量指标实体
│   └── CollectionLog.java         # 收集日志实体
├── repository/                    # 数据访问层
│   ├── DataSourceRepository.java  # 数据源数据访问
│   ├── CollectionTaskRepository.java # 收集任务数据访问
│   ├── CollectedDataRepository.java # 收集数据数据访问
│   ├── DataQualityMetricRepository.java # 数据质量指标数据访问
│   └── CollectionLogRepository.java # 收集日志数据访问
└── service/                       # 业务逻辑层
    ├── DataCollectionService.java # 数据收集服务
    ├── DataSourceService.java     # 数据源服务
    ├── TaskService.java           # 任务服务
    └── QualityService.java        # 质量监控服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8106/actuator/health