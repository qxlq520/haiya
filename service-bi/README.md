# 商业智能服务 (service-bi)

  服务概述

商业智能服务是海牙平台的数据分析服务之一，负责处理平台业务数据的收集、处理、分析和可视化展示等核心功能。

  功能特性

1. 业务数据收集与整合
2. 数据清洗与预处理
3. 多维度数据分析
4. 数据可视化展示
5. 业务指标监控
6. 数据报表生成
7. 预测分析功能
8. 实时数据监控

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎
- Apache Kafka 消息队列
- Apache Spark 大数据处理框架

  API 接口

 # 数据收集接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/bi/data-collection` | POST | 收集业务数据 |
| `/api/bi/data-collection/batch` | POST | 批量收集业务数据 |

 # 数据分析接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/bi/analytics/user-growth` | GET | 获取用户增长分析数据 |
| `/api/bi/analytics/content-performance` | GET | 获取内容表现分析数据 |
| `/api/bi/analytics/revenue` | GET | 获取收入分析数据 |
| `/api/bi/analytics/engagement` | GET | 获取用户参与度分析数据 |

 # 数据可视化接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/bi/charts/user-growth` | GET | 获取用户增长图表数据 |
| `/api/bi/charts/content-performance` | GET | 获取内容表现图表数据 |
| `/api/bi/charts/revenue-trend` | GET | 获取收入趋势图表数据 |

 # 报表接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/bi/reports` | GET | 获取报表列表 |
| `/api/bi/reports` | POST | 生成报表 |
| `/api/bi/reports/{id}` | GET | 获取报表详情 |
| `/api/bi/reports/{id}/download` | GET | 下载报表 |

  数据库设计

 # bi_data_warehouse 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| data_type | VARCHAR(50) | 数据类型 |
| data_content | TEXT | 数据内容（JSON格式） |
| source_service | VARCHAR(100) | 来源服务 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # bi_analytics_results 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| analysis_type | VARCHAR(100) | 分析类型 |
| result_data | TEXT | 结果数据（JSON格式） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| created_at | BIGINT | 创建时间 |

 # bi_reports 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 报表名称 |
| description | TEXT | 报表描述 |
| type | VARCHAR(50) | 报表类型 |
| format | VARCHAR(20) | 报表格式（PDF, EXCEL, CSV） |
| file_url | VARCHAR(500) | 文件URL |
| status | VARCHAR(20) | 状态（GENERATING, COMPLETED, FAILED） |
| generated_at | BIGINT | 生成时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # bi_dashboards 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 仪表板名称 |
| layout | TEXT | 布局配置（JSON格式） |
| creator_id | BIGINT | 创建者ID |
| is_public | BOOLEAN | 是否公开 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # bi_kpis 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | KPI名称 |
| value | DECIMAL(15,2) | KPI值 |
| target | DECIMAL(15,2) | 目标值 |
| unit | VARCHAR(20) | 单位 |
| date | DATE | 日期 |
| created_at | BIGINT | 创建时间 |

  Elasticsearch 索引设计

 # bi_analytics 索引

```
{
  "analysis_id": 12345,
  "analysis_type": "USER_GROWTH",
  "data": {
    "new_users": 1000,
    "active_users": 5000,
    "retention_rate": 0.75
  },
  "start_time": 1612345678901,
  "end_time": 1612432078901,
  "created_at": 1612432078901
}
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Elasticsearch 搜索引擎
- Apache Kafka 消息队列
- Apache Spark 大数据处理框架

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8098

spring:
  application:
    name: service-bi
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_bi
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
  
kafka:
  bootstrap-servers: localhost:9092
```

 # 启动服务

```bash
cd service-bi
mvn spring-boot:run
```

或

```bash
cd service-bi
mvn clean package
java -jar target/service-bi-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-audio (音频服务)
- service-payment (支付服务)
- service-analytics (分析服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/bi
├── BiServiceApplication.java     # 应用入口
├── controller/                   # 控制器层
│   ├── DataCollectionController.java # 数据收集控制器
│   ├── AnalyticsController.java  # 分析控制器
│   ├── ChartController.java      # 图表控制器
│   └── ReportController.java     # 报表控制器
├── entity/                       # 实体类
│   ├── BiDataWarehouse.java      # 数据仓库实体
│   ├── BiAnalyticsResult.java    # 分析结果实体
│   ├── BiReport.java             # 报表实体
│   ├── BiDashboard.java          # 仪表板实体
│   └── BiKpi.java                # KPI实体
├── repository/                   # 数据访问层
│   ├── BiDataWarehouseRepository.java # 数据仓库数据访问
│   ├── BiAnalyticsResultRepository.java # 分析结果数据访问
│   ├── BiReportRepository.java   # 报表数据访问
│   ├── BiDashboardRepository.java # 仪表板数据访问
│   └── BiKpiRepository.java      # KPI数据访问
└── service/                      # 业务逻辑层
    ├── DataCollectionService.java # 数据收集服务
    ├── AnalyticsService.java     # 分析服务
    ├── ChartService.java         # 图表服务
    ├── ReportService.java        # 报表服务
    └── DashboardService.java     # 仪表板服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8098/actuator/health