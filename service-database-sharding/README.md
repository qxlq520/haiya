# 数据库分片服务 (service-database-sharding)

  服务概述

数据库分片服务是海牙平台的数据库基础设施服务，负责实现数据库的水平分片、读写分离、分片路由等功能，以支持平台的高并发和大数据量需求。

  功能特性

1. 数据库水平分片
2. 读写分离
3. 分片路由策略
4. 分片键管理
5. 分片算法实现
6. 分片监控与管理
7. 数据迁移与扩容
8. 分片事务支持

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- ShardingSphere 数据库分片中间件

  API 接口

 # 分片配置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/sharding-configs` | GET | 获取分片配置列表 |
| `/api/sharding-configs` | POST | 创建分片配置 |
| `/api/sharding-configs/{id}` | GET | 获取分片配置详情 |
| `/api/sharding-configs/{id}` | PUT | 更新分片配置 |
| `/api/sharding-configs/{id}` | DELETE | 删除分片配置 |

 # 分片策略接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/sharding-strategies` | GET | 获取分片策略列表 |
| `/api/sharding-strategies` | POST | 创建分片策略 |
| `/api/sharding-strategies/{id}` | GET | 获取分片策略详情 |
| `/api/sharding-strategies/{id}` | PUT | 更新分片策略 |
| `/api/sharding-strategies/{id}` | DELETE | 删除分片策略 |

 # 分片监控接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/sharding-monitor/nodes` | GET | 获取分片节点信息 |
| `/api/sharding-monitor/traffic` | GET | 获取分片流量统计 |
| `/api/sharding-monitor/performance` | GET | 获取分片性能指标 |
| `/api/sharding-monitor/alerts` | GET | 获取分片告警信息 |

 # 数据迁移接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/data-migration/jobs` | GET | 获取数据迁移任务列表 |
| `/api/data-migration/jobs` | POST | 创建数据迁移任务 |
| `/api/data-migration/jobs/{id}` | GET | 获取数据迁移任务详情 |
| `/api/data-migration/jobs/{id}/start` | POST | 启动数据迁移任务 |
| `/api/data-migration/jobs/{id}/stop` | POST | 停止数据迁移任务 |

  数据库设计

 # sharding_configs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 配置名称 |
| description | TEXT | 配置描述 |
| database_name | VARCHAR(100) | 数据库名称 |
| table_name | VARCHAR(100) | 表名称 |
| sharding_column | VARCHAR(100) | 分片列 |
| sharding_algorithm | VARCHAR(50) | 分片算法 |
| sharding_params | TEXT | 分片参数（JSON格式） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # sharding_strategies 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 策略名称 |
| type | VARCHAR(50) | 策略类型（DATABASE, TABLE, DATABASE_TABLE） |
| algorithm | VARCHAR(50) | 算法类型（HASH, RANGE, MOD） |
| config | TEXT | 策略配置（JSON格式） |
| description | TEXT | 策略描述 |
| is_default | BOOLEAN | 是否默认策略 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # sharding_nodes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| node_name | VARCHAR(100) | 节点名称 |
| node_type | VARCHAR(50) | 节点类型（MASTER, SLAVE） |
| database_url | VARCHAR(500) | 数据库URL |
| username | VARCHAR(100) | 用户名 |
| password | VARCHAR(100) | 密码（加密存储） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, ERROR） |
| weight | INT | 权重 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # data_migration_jobs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 任务名称 |
| source_config_id | BIGINT | 源配置ID |
| target_config_id | BIGINT | 目标配置ID |
| migration_type | VARCHAR(50) | 迁移类型（INITIAL, INCREMENTAL） |
| status | VARCHAR(20) | 状态（PENDING, RUNNING, COMPLETED, FAILED） |
| progress | DECIMAL(5,2) | 进度百分比 |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # sharding_monitor_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| node_id | BIGINT | 节点ID |
| metric_type | VARCHAR(50) | 指标类型（QPS, RT, CONNECTIONS等） |
| metric_value | DECIMAL(15,2) | 指标值 |
| recorded_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

  ShardingSphere 配置示例

 # 分片规则配置
```yaml
sharding:
  tables:
    user:
      actualDataNodes: ds_${0..1}.user_${0..3}
      tableStrategy:
        standard:
          shardingColumn: user_id
          shardingAlgorithmName: user_table_inline
      databaseStrategy:
        standard:
          shardingColumn: user_id
          shardingAlgorithmName: user_db_inline
  shardingAlgorithms:
    user_table_inline:
      type: INLINE
      props:
        algorithm-expression: user_${user_id % 4}
    user_db_inline:
      type: INLINE
      props:
        algorithm-expression: ds_${user_id % 2}
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- ShardingSphere 5.x

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8108

spring:
  application:
    name: service-database-sharding
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_database_sharding
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
cd service-database-sharding
mvn spring-boot:run
```

或

```bash
cd service-database-sharding
mvn clean package
java -jar target/service-database-sharding-1.0.0.jar
```

  服务依赖

- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/database/sharding
├── DatabaseShardingApplication.java  # 应用入口
├── controller/                       # 控制器层
│   ├── ShardingConfigController.java # 分片配置控制器
│   ├── StrategyController.java       # 策略控制器
│   ├── MonitorController.java        # 监控控制器
│   └── MigrationController.java      # 迁移控制器
├── entity/                           # 实体类
│   ├── ShardingConfig.java           # 分片配置实体
│   ├── ShardingStrategy.java         # 分片策略实体
│   ├── ShardingNode.java             # 分片节点实体
│   ├── DataMigrationJob.java         # 数据迁移任务实体
│   └── MonitorMetric.java            # 监控指标实体
├── repository/                       # 数据访问层
│   ├── ShardingConfigRepository.java # 分片配置数据访问
│   ├── ShardingStrategyRepository.java # 分片策略数据访问
│   ├── ShardingNodeRepository.java   # 分片节点数据访问
│   ├── DataMigrationJobRepository.java # 数据迁移任务数据访问
│   └── MonitorMetricRepository.java  # 监控指标数据访问
└── service/                          # 业务逻辑层
    ├── ShardingConfigService.java    # 分片配置服务
    ├── StrategyService.java          # 策略服务
    ├── MonitorService.java           # 监控服务
    └── MigrationService.java         # 迁移服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8108/actuator/health