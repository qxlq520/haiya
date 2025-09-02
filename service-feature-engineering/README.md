# 特征工程服务 (service-feature-engineering)

  服务概述

特征工程服务是海牙平台机器学习和数据分析的核心基础设施服务，负责数据特征提取、特征转换、特征选择和特征存储等功能，为推荐系统、风控系统、用户画像等提供高质量的特征数据。

  功能特性

1. 数据特征提取
2. 特征转换与标准化
3. 特征选择与降维
4. 特征存储与管理
5. 特征版本控制
6. 特征质量监控
7. 特征血缘追踪
8. 特征计算任务调度

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Apache Spark 大数据处理框架
- Apache Airflow 工作流调度

  API 接口

 # 特征管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/features` | GET | 获取特征列表 |
| `/api/features` | POST | 创建特征 |
| `/api/features/{id}` | GET | 获取特征详情 |
| `/api/features/{id}` | PUT | 更新特征信息 |
| `/api/features/{id}` | DELETE | 删除特征 |

 # 特征计算接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/feature-computations` | POST | 触发特征计算任务 |
| `/api/feature-computations/{id}` | GET | 获取计算任务状态 |
| `/api/feature-computations/{id}/cancel` | POST | 取消计算任务 |
| `/api/feature-computations/history` | GET | 获取计算历史 |

 # 特征数据接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/feature-data` | GET | 获取特征数据 |
| `/api/feature-data/batch` | POST | 批量获取特征数据 |
| `/api/feature-data/{entityType}/{entityId}` | GET | 获取指定实体的特征数据 |

 # 特征监控接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/feature-monitoring/stats` | GET | 获取特征统计信息 |
| `/api/feature-monitoring/quality` | GET | 获取特征质量报告 |
| `/api/feature-monitoring/alerts` | GET | 获取特征告警信息 |

  数据库设计

 # features 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 特征名称 |
| description | TEXT | 特征描述 |
| data_type | VARCHAR(50) | 数据类型（INT, FLOAT, STRING, BOOLEAN等） |
| category | VARCHAR(50) | 特征分类（USER, CONTENT, CONTEXT等） |
| computation_logic | TEXT | 计算逻辑（JSON格式） |
| source_tables | TEXT | 源数据表（JSON格式） |
| version | VARCHAR(20) | 版本号 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DEPRECATED） |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # feature_computations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| feature_id | BIGINT | 特征ID |
| computation_type | VARCHAR(50) | 计算类型（REALTIME, BATCH） |
| schedule | VARCHAR(100) | 调度配置（CRON表达式） |
| status | VARCHAR(20) | 状态（PENDING, RUNNING, COMPLETED, FAILED） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| records_processed | BIGINT | 处理记录数 |
| error_message | TEXT | 错误信息 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # feature_data 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| feature_id | BIGINT | 特征ID |
| entity_type | VARCHAR(50) | 实体类型（USER, VIDEO, ARTICLE等） |
| entity_id | BIGINT | 实体ID |
| feature_value | TEXT | 特征值 |
| computed_at | BIGINT | 计算时间 |
| version | VARCHAR(20) | 版本号 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # feature_monitoring_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| feature_id | BIGINT | 特征ID |
| metric_type | VARCHAR(50) | 指标类型（MISSING_RATE, OUTLIER_RATE等） |
| metric_value | DECIMAL(10,2) | 指标值 |
| threshold | DECIMAL(10,2) | 阈值 |
| status | VARCHAR(20) | 状态（NORMAL, WARNING, ALERT） |
| measured_at | BIGINT | 测量时间 |
| created_at | BIGINT | 创建时间 |

  Spark 特征计算示例

 # 特征提取代码
```scala
val userDF = spark.read.table("ods_users")
val videoDF = spark.read.table("ods_videos")
val userVideoInteractionDF = spark.read.table("ods_user_video_interactions")

val userFeatures = userDF.select(
  col("user_id"),
  countDistinct("video_id").as("video_view_count"),
  avg("watch_duration").as("avg_watch_duration")
).groupBy("user_id").agg(
  // 特征计算逻辑
)
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Apache Spark 大数据处理框架
- Apache Airflow 工作流调度

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8112

spring:
  application:
    name: service-feature-engineering
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_feature_engineering
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
cd service-feature-engineering
mvn spring-boot:run
```

或

```bash
cd service-feature-engineering
mvn clean package
java -jar target/service-feature-engineering-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-data-collector (数据收集服务)
- service-data-warehouse (数据仓库服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/feature/engineering
├── FeatureEngineeringApplication.java  # 应用入口
├── controller/                         # 控制器层
│   ├── FeatureController.java          # 特征控制器
│   ├── ComputationController.java      # 计算控制器
│   ├── DataController.java             # 数据控制器
│   └── MonitoringController.java       # 监控控制器
├── entity/                             # 实体类
│   ├── Feature.java                    # 特征实体
│   ├── FeatureComputation.java         # 特征计算实体
│   ├── FeatureData.java                # 特征数据实体
│   └── MonitoringMetric.java           # 监控指标实体
├── repository/                         # 数据访问层
│   ├── FeatureRepository.java          # 特征数据访问
│   ├── FeatureComputationRepository.java # 特征计算数据访问
│   ├── FeatureDataRepository.java      # 特征数据数据访问
│   └── MonitoringMetricRepository.java # 监控指标数据访问
└── service/                            # 业务逻辑层
    ├── FeatureService.java             # 特征服务
    ├── ComputationService.java         # 计算服务
    ├── DataService.java                # 数据服务
    └── MonitoringService.java          # 监控服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8112/actuator/health