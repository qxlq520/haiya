# 数据仓库服务 (service-data-warehouse)

  服务概述

数据仓库服务是海牙平台的核心数据存储服务，负责存储和管理平台的各类历史数据，提供高效的数据查询、分析和报表功能。

  功能特性

1. 数据仓库建模与设计
2. 数据分层存储（ODS、DWD、DWS、ADS）
3. 数据ETL处理流程
4. 数据集市构建
5. 多维数据分析支持
6. 数据归档与备份
7. 数据安全管理
8. 数据血缘追踪

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Apache Hive 数据仓库工具
- Apache Spark 大数据处理框架
- Apache Airflow 工作流调度

  API 接口

 # 数据仓库接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/data-warehouse/tables` | GET | 获取数据表列表 |
| `/api/data-warehouse/tables/{name}` | GET | 获取数据表详情 |
| `/api/data-warehouse/tables/{name}/schema` | GET | 获取数据表结构 |
| `/api/data-warehouse/tables/{name}/preview` | GET | 预览数据表内容 |

 # 数据查询接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/data-warehouse/query` | POST | 执行数据查询 |
| `/api/data-warehouse/query/history` | GET | 获取查询历史 |
| `/api/data-warehouse/query/saved` | GET | 获取保存的查询 |
| `/api/data-warehouse/query/saved` | POST | 保存查询 |

 # ETL任务接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/etl-jobs` | GET | 获取ETL任务列表 |
| `/api/etl-jobs` | POST | 创建ETL任务 |
| `/api/etl-jobs/{id}` | GET | 获取ETL任务详情 |
| `/api/etl-jobs/{id}/run` | POST | 执行ETL任务 |
| `/api/etl-jobs/{id}/schedule` | PUT | 设置ETL任务调度 |

 # 数据血缘接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/data-lineage/tables/{name}` | GET | 获取表的数据血缘 |
| `/api/data-lineage/columns` | GET | 获取字段的数据血缘 |
| `/api/data-lineage/jobs/{jobId}` | GET | 获取任务的数据血缘 |

  数据库设计

 # warehouse_tables 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 表名 |
| description | TEXT | 表描述 |
| layer | VARCHAR(20) | 数据分层（ODS, DWD, DWS, ADS） |
| schema_info | TEXT | 表结构信息（JSON格式） |
| owner_id | BIGINT | 所有者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # warehouse_columns 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| table_id | BIGINT | 表ID |
| name | VARCHAR(100) | 字段名 |
| data_type | VARCHAR(50) | 数据类型 |
| length | INT | 长度 |
| precision | INT | 精度 |
| scale | INT | 小数位数 |
| is_nullable | BOOLEAN | 是否可为空 |
| description | TEXT | 字段描述 |
| created_at | BIGINT | 创建时间 |

 # etl_jobs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 任务名称 |
| description | TEXT | 任务描述 |
| source_tables | TEXT | 源表列表（JSON格式） |
| target_table | VARCHAR(100) | 目标表 |
| transformation_logic | TEXT | 转换逻辑（JSON格式） |
| schedule | VARCHAR(100) | 调度配置（CRON表达式） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, ERROR） |
| last_run_at | BIGINT | 最后运行时间 |
| next_run_at | BIGINT | 下次运行时间 |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # etl_job_runs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| job_id | BIGINT | 任务ID |
| status | VARCHAR(20) | 状态（RUNNING, COMPLETED, FAILED） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| records_processed | BIGINT | 处理记录数 |
| error_message | TEXT | 错误信息 |
| log_file | VARCHAR(500) | 日志文件路径 |
| created_at | BIGINT | 创建时间 |

 # data_lineage 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| source_table | VARCHAR(100) | 源表 |
| source_column | VARCHAR(100) | 源字段 |
| target_table | VARCHAR(100) | 目标表 |
| target_column | VARCHAR(100) | 目标字段 |
| transformation | TEXT | 转换逻辑 |
| job_id | BIGINT | 任务ID |
| created_at | BIGINT | 创建时间 |

 # query_history 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| query_text | TEXT | 查询语句 |
| executed_at | BIGINT | 执行时间 |
| execution_time | INT | 执行耗时（毫秒） |
| result_rows | INT | 结果行数 |
| status | VARCHAR(20) | 状态（SUCCESS, FAILED） |
| error_message | TEXT | 错误信息 |
| created_at | BIGINT | 创建时间 |

  Hive 数据库设计

 # ODS层（操作数据存储层）
```
ods_user_events      # 用户行为日志
ods_content_info     # 内容信息
ods_order_info       # 订单信息
ods_payment_info     # 支付信息
```

 # DWD层（数据仓库明细层）
```
dwd_user_events      # 清洗后的用户行为数据
dwd_content_info     # 标准化的内容信息
dwd_order_info       # 标准化的订单信息
dwd_payment_info     # 标准化的支付信息
```

 # DWS层（数据仓库汇总层）
```
dws_user_summary     # 用户汇总数据
dws_content_summary  # 内容汇总数据
dws_revenue_summary  # 收入汇总数据
```

 # ADS层（应用数据服务层）
```
ads_user_analysis    # 用户分析数据
ads_content_analysis # 内容分析数据
ads_business_metrics # 业务指标数据
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Apache Hive 数据仓库工具
- Apache Spark 大数据处理框架
- Apache Airflow 工作流调度

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8107

spring:
  application:
    name: service-data-warehouse
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_data_warehouse
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
cd service-data-warehouse
mvn spring-boot:run
```

或

```bash
cd service-data-warehouse
mvn clean package
java -jar target/service-data-warehouse-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-audio (音频服务)
- service-payment (支付服务)
- service-data-collector (数据收集服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/data/warehouse
├── DataWarehouseApplication.java  # 应用入口
├── controller/                    # 控制器层
│   ├── WarehouseController.java   # 数据仓库控制器
│   ├── QueryController.java       # 查询控制器
│   ├── EtlController.java         # ETL控制器
│   └── LineageController.java     # 血缘控制器
├── entity/                        # 实体类
│   ├── WarehouseTable.java        # 数据表实体
│   ├── WarehouseColumn.java       # 数据列实体
│   ├── EtlJob.java                # ETL任务实体
│   ├── EtlJobRun.java             # ETL任务运行实体
│   ├── DataLineage.java           # 数据血缘实体
│   └── QueryHistory.java          # 查询历史实体
├── repository/                    # 数据访问层
│   ├── WarehouseTableRepository.java # 数据表数据访问
│   ├── WarehouseColumnRepository.java # 数据列数据访问
│   ├── EtlJobRepository.java      # ETL任务数据访问
│   ├── EtlJobRunRepository.java   # ETL任务运行数据访问
│   ├── DataLineageRepository.java # 数据血缘数据访问
│   └── QueryHistoryRepository.java # 查询历史数据访问
└── service/                       # 业务逻辑层
    ├── WarehouseService.java      # 数据仓库服务
    ├── QueryService.java          # 查询服务
    ├── EtlService.java            # ETL服务
    └── LineageService.java        # 血缘服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8107/actuator/health