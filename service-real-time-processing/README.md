# 实时处理服务 (service-real-time-processing)

  服务概述

实时处理服务是海牙平台的数据处理核心服务，负责处理用户行为数据、业务事件、系统日志等实时数据流，提供实时计算、实时分析和实时响应能力。

  功能特性

1. 实时数据流处理
2. 实时事件处理
3. 实时计算与聚合
4. 实时监控与告警
5. 实时数据分发
6. 流式ETL处理
7. 实时数据存储
8. 实时数据可视化

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Apache Kafka 消息队列
- Apache Flink 流处理引擎
- Apache Storm 流处理框架

  API 接口

 # 数据流管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/real-time-processing/streams` | POST | 创建数据流 |
| `/api/real-time-processing/streams` | GET | 获取数据流列表 |
| `/api/real-time-processing/streams/{id}` | GET | 获取数据流详情 |
| `/api/real-time-processing/streams/{id}` | PUT | 更新数据流配置 |
| `/api/real-time-processing/streams/{id}/start` | POST | 启动数据流处理 |
| `/api/real-time-processing/streams/{id}/stop` | POST | 停止数据流处理 |

 # 实时计算接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/real-time-processing/computations` | POST | 创建实时计算任务 |
| `/api/real-time-processing/computations` | GET | 获取计算任务列表 |
| `/api/real-time-processing/computations/{id}` | GET | 获取计算任务详情 |
| `/api/real-time-processing/computations/{id}/results` | GET | 获取计算结果 |
| `/api/real-time-processing/computations/{id}/metrics` | GET | 获取计算指标 |

 # 实时监控接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/real-time-processing/monitoring/metrics` | GET | 获取实时监控指标 |
| `/api/real-time-processing/monitoring/alerts` | GET | 获取告警信息 |
| `/api/real-time-processing/monitoring/status` | GET | 获取处理状态 |
| `/api/real-time-processing/monitoring/performance` | GET | 获取性能指标 |

 # 数据分发接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/real-time-processing/distribution/rules` | POST | 创建分发规则 |
| `/api/real-time-processing/distribution/rules` | GET | 获取分发规则列表 |
| `/api/real-time-processing/distribution/rules/{id}` | GET | 获取分发规则详情 |
| `/api/real-time-processing/distribution/rules/{id}` | PUT | 更新分发规则 |
| `/api/real-time-processing/distribution/rules/{id}/delete` | DELETE | 删除分发规则 |

  数据库设计

 # real_time_streams 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 数据流名称 |
| description | TEXT | 数据流描述 |
| source_config | TEXT | 源配置（JSON格式） |
| sink_config | TEXT | 汇配置（JSON格式） |
| processing_logic | TEXT | 处理逻辑（JSON格式） |
| status | VARCHAR(20) | 状态（CREATED, RUNNING, STOPPED, ERROR） |
| parallelism | INT | 并行度 |
| start_time | BIGINT | 启动时间 |
| stop_time | BIGINT | 停止时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # real_time_computations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 计算任务名称 |
| description | TEXT | 任务描述 |
| stream_id | BIGINT | 数据流ID |
| computation_type | VARCHAR(50) | 计算类型（AGGREGATION, JOIN, FILTER等） |
| config | TEXT | 计算配置（JSON格式） |
| output_config | TEXT | 输出配置（JSON格式） |
| status | VARCHAR(20) | 状态（CREATED, RUNNING, COMPLETED, ERROR） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # real_time_results 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| computation_id | BIGINT | 计算任务ID |
| result_data | TEXT | 结果数据（JSON格式） |
| timestamp | BIGINT | 时间戳 |
| created_at | BIGINT | 创建时间 |

 # distribution_rules 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 规则名称 |
| description | TEXT | 规则描述 |
| source_stream_id | BIGINT | 源数据流ID |
| target_config | TEXT | 目标配置（JSON格式） |
| filter_condition | TEXT | 过滤条件（JSON格式） |
| transformation_logic | TEXT | 转换逻辑（JSON格式） |
| is_active | BOOLEAN | 是否激活 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # processing_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| stream_id | BIGINT | 数据流ID |
| metric_type | VARCHAR(50) | 指标类型（THROUGHPUT, LATENCY, ERROR_RATE等） |
| metric_value | DECIMAL(15,2) | 指标值 |
| dimension | VARCHAR(100) | 维度信息 |
| recorded_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

  Kafka 主题设计

 # 实时数据流主题
```
Topic: real-time.data-streams
  - 分区: 12
  - 副本: 3
```

 # 处理结果主题
```
Topic: real-time.processing-results
  - 分区: 12
  - 副本: 3
```

 # 监控指标主题
```
Topic: real-time.monitoring-metrics
  - 分区: 6
  - 副本: 3
```

  Flink 流处理作业配置

 # 流处理作业示例
```java
StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
env.setParallelism(4);

DataStream<String> inputStream = env.addSource(new FlinkKafkaConsumer<>(
    "real-time.data-streams",
    new SimpleStringSchema(),
    kafkaProps
));

DataStream<ProcessedData> processedStream = inputStream
    .map(new DataProcessor())
    .keyBy(data -> data.getKey())
    .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
    .reduce(new DataAggregator());

processedStream.addSink(new FlinkKafkaProducer<>(
    "real-time.processing-results",
    new ProcessingResultSchema(),
    kafkaProps
));

env.execute("Real-time Data Processing Job");
```

  Redis 缓存设计

 # 实时数据缓存
```
real-time:data:latest -> Hash结构存储最新数据
real-time:metrics:{streamId} -> Hash结构存储流指标
```

 # 处理状态缓存
```
real-time:processing:status:{streamId} -> String结构存储处理状态
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Apache Kafka 消息队列
- Apache Flink 1.14.x
- Apache Storm 2.3.x

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8130

spring:
  application:
    name: service-real-time-processing
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_real_time_processing
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

real-time-processing:
  flink:
    job-manager: localhost:8081
    parallelism: 4
  storm:
    nimbus-host: localhost
    nimbus-port: 6627
```

 # 启动服务

```bash
cd service-real-time-processing
mvn spring-boot:run
```

或

```bash
cd service-real-time-processing
mvn clean package
java -jar target/service-real-time-processing-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-data-collector (数据收集服务)
- service-analytics (分析服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/real/time/processing
├── RealTimeProcessingApplication.java  # 应用入口
├── config/                             # 配置类
│   ├── FlinkConfig.java                # Flink配置
│   └── StormConfig.java                # Storm配置
├── controller/                         # 控制器层
│   ├── StreamController.java           # 数据流控制器
│   ├── ComputationController.java      # 计算控制器
│   ├── MonitoringController.java       # 监控控制器
│   └── DistributionController.java     # 分发控制器
├── entity/                             # 实体类
│   ├── RealTimeStream.java             # 实时数据流实体
│   ├── ComputationTask.java            # 计算任务实体
│   ├── ProcessingResult.java           # 处理结果实体
│   ├── DistributionRule.java           # 分发规则实体
│   └── ProcessingMetric.java           # 处理指标实体
├── repository/                         # 数据访问层
│   ├── RealTimeStreamRepository.java   # 实时数据流数据访问
│   ├── ComputationTaskRepository.java  # 计算任务数据访问
│   ├── ProcessingResultRepository.java # 处理结果数据访问
│   ├── DistributionRuleRepository.java # 分发规则数据访问
│   └── ProcessingMetricRepository.java # 处理指标数据访问
└── service/                            # 业务逻辑层
    ├── StreamService.java              # 数据流服务
    ├── ComputationService.java         # 计算服务
    ├── MonitoringService.java          # 监控服务
    └── DistributionService.java        # 分发服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8130/actuator/health