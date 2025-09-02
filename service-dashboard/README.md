# 仪表板服务 (service-dashboard)

  服务概述

仪表板服务是海牙平台的数据可视化服务，负责为平台管理员和运营人员提供各类数据的可视化展示、实时监控和分析报告等功能。

  功能特性

1. 实时数据监控仪表板
2. 用户数据可视化
3. 内容数据可视化
4. 收入数据可视化
5. 运营数据可视化
6. 系统性能监控
7. 自定义仪表板配置
8. 数据报告生成与导出

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎
- Grafana/Kibana 可视化工具

  API 接口

 # 仪表板配置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/dashboards` | GET | 获取仪表板列表 |
| `/api/dashboards` | POST | 创建仪表板 |
| `/api/dashboards/{id}` | GET | 获取仪表板详情 |
| `/api/dashboards/{id}` | PUT | 更新仪表板配置 |
| `/api/dashboards/{id}` | DELETE | 删除仪表板 |
| `/api/dashboards/{id}/widgets` | GET | 获取仪表板组件列表 |

 # 实时数据接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/realtime/users` | GET | 获取实时用户数据 |
| `/api/realtime/content` | GET | 获取实时内容数据 |
| `/api/realtime/revenue` | GET | 获取实时收入数据 |
| `/api/realtime/system` | GET | 获取实时系统性能数据 |

 # 数据可视化接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/visualizations/user-growth` | GET | 获取用户增长图表数据 |
| `/api/visualizations/content-performance` | GET | 获取内容表现图表数据 |
| `/api/visualizations/revenue-trend` | GET | 获取收入趋势图表数据 |
| `/api/visualizations/engagement` | GET | 获取用户参与度图表数据 |

 # 报告接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/reports` | GET | 获取报告列表 |
| `/api/reports` | POST | 生成报告 |
| `/api/reports/{id}` | GET | 获取报告详情 |
| `/api/reports/{id}/download` | GET | 下载报告 |
| `/api/reports/scheduled` | GET | 获取定时报告列表 |

  数据库设计

 # dashboards 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 仪表板名称 |
| description | TEXT | 仪表板描述 |
| layout | TEXT | 布局配置（JSON格式） |
| owner_id | BIGINT | 所有者ID |
| is_public | BOOLEAN | 是否公开 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # dashboard_widgets 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| dashboard_id | BIGINT | 仪表板ID |
| widget_type | VARCHAR(50) | 组件类型（CHART, TABLE, METRIC等） |
| title | VARCHAR(100) | 组件标题 |
| config | TEXT | 组件配置（JSON格式） |
| position | VARCHAR(50) | 位置信息 |
| size | VARCHAR(50) | 大小信息 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # visualization_data 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| data_type | VARCHAR(50) | 数据类型 |
| data_source | VARCHAR(100) | 数据源 |
| data_content | TEXT | 数据内容（JSON格式） |
| generated_at | BIGINT | 生成时间 |
| expiry_at | BIGINT | 过期时间 |
| created_at | BIGINT | 创建时间 |

 # reports 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 报告名称 |
| description | TEXT | 报告描述 |
| type | VARCHAR(50) | 报告类型（DAILY, WEEKLY, MONTHLY, CUSTOM） |
| format | VARCHAR(20) | 报告格式（PDF, EXCEL, CSV） |
| file_url | VARCHAR(500) | 文件URL |
| status | VARCHAR(20) | 状态（GENERATING, COMPLETED, FAILED） |
| schedule | VARCHAR(100) | 调度配置（CRON表达式） |
| creator_id | BIGINT | 创建者ID |
| generated_at | BIGINT | 生成时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # realtime_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| metric_name | VARCHAR(100) | 指标名称 |
| metric_value | DECIMAL(15,2) | 指标值 |
| metric_type | VARCHAR(50) | 指标类型 |
| dimension | VARCHAR(100) | 维度信息 |
| recorded_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

  Elasticsearch 索引设计

 # dashboard_logs 索引

```
{
  "dashboard_id": 12345,
  "user_id": 67890,
  "action": "VIEW",
  "ip_address": "192.168.1.1",
  "user_agent": "Mozilla/5.0...",
  "created_at": 1612345678901
}
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Elasticsearch 搜索引擎
- Grafana/Kibana 可视化工具

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8105

spring:
  application:
    name: service-dashboard
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_dashboard
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
```

 # 启动服务

```bash
cd service-dashboard
mvn spring-boot:run
```

或

```bash
cd service-dashboard
mvn clean package
java -jar target/service-dashboard-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-video (视频服务)
- service-article (文章服务)
- service-audio (音频服务)
- service-payment (支付服务)
- service-analytics (分析服务)
- service-bi (商业智能服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/dashboard
├── DashboardServiceApplication.java  # 应用入口
├── controller/                       # 控制器层
│   ├── DashboardController.java      # 仪表板控制器
│   ├── RealtimeController.java       # 实时数据控制器
│   ├── VisualizationController.java  # 数据可视化控制器
│   └── ReportController.java         # 报告控制器
├── entity/                           # 实体类
│   ├── Dashboard.java                # 仪表板实体
│   ├── DashboardWidget.java          # 仪表板组件实体
│   ├── VisualizationData.java        # 可视化数据实体
│   ├── Report.java                   # 报告实体
│   └── RealtimeMetric.java           # 实时指标实体
├── repository/                       # 数据访问层
│   ├── DashboardRepository.java      # 仪表板数据访问
│   ├── DashboardWidgetRepository.java # 仪表板组件数据访问
│   ├── VisualizationDataRepository.java # 可视化数据访问
│   ├── ReportRepository.java         # 报告数据访问
│   └── RealtimeMetricRepository.java # 实时指标数据访问
└── service/                          # 业务逻辑层
    ├── DashboardService.java         # 仪表板服务
    ├── RealtimeService.java          # 实时数据服务
    ├── VisualizationService.java     # 数据可视化服务
    └── ReportService.java            # 报告服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8105/actuator/health