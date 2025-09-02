# 性能优化器 (performance-optimizer)

  概述

性能优化器是海牙平台的系统性能优化核心组件，专门负责监控、分析和优化整个平台的性能表现，确保系统在高并发场景下依然能够稳定运行。

  功能特性

1. 系统性能监控
2. 性能瓶颈分析
3. 自动优化建议
4. 资源使用优化
5. 缓存策略优化
6. 数据库查询优化
7. 微服务调用优化
8. 实时性能调优

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Elasticsearch 搜索引擎
- Prometheus 监控系统
- Grafana 可视化面板

  核心优化策略

 # 数据库优化策略

1. **查询优化**
   - 自动分析慢查询日志，识别性能瓶颈
   - 为高频查询字段推荐合适的索引策略
   - 优化复杂JOIN查询，提供查询重写建议

2. **连接池优化**
   - 监控数据库连接使用情况
   - 动态调整连接池参数
   - 提供连接池配置优化建议

3. **分库分表优化**
   - 分析数据分布情况，优化分片策略
   - 提供数据迁移和重新分片建议
   - 监控各分片负载均衡情况

 # 缓存优化策略

1. **多级缓存架构**
   - 实施本地缓存（Caffeine）+分布式缓存（Redis）的多级缓存策略
   - 根据数据访问频率和实时性要求推荐合适的缓存层级
   - 提供缓存预热和失效策略建议

2. **缓存更新机制**
   - 分析缓存命中率，优化缓存策略
   - 提供Cache-Aside、Read-Through、Write-Through等模式选择建议
   - 针对热点数据提供缓存穿透、击穿和雪崩防护方案

 # 微服务优化策略

1. **服务治理优化**
   - 分析服务间调用链路，识别性能瓶颈
   - 提供服务降级和限流策略建议
   - 优化服务网格配置，实现流量控制和熔断机制

2. **异步处理优化**
   - 分析消息队列使用情况，优化消费者配置
   - 提供耗时操作异步化建议
   - 优化事件驱动架构，降低服务间耦合度

  API 接口

 # 性能监控接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/performance/metrics` | GET | 获取系统性能指标 |
| `/api/performance/services` | GET | 获取服务性能指标 |
| `/api/performance/database` | GET | 获取数据库性能指标 |
| `/api/performance/cache` | GET | 获取缓存性能指标 |
| `/api/performance/network` | GET | 获取网络性能指标 |

 # 性能分析接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/performance/analysis/slow-queries` | GET | 获取慢查询分析结果 |
| `/api/performance/analysis/bottlenecks` | GET | 获取性能瓶颈分析结果 |
| `/api/performance/analysis/recommendations` | GET | 获取优化建议 |
| `/api/performance/analysis/resource-usage` | GET | 获取资源使用分析结果 |

 # 优化操作接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/performance/optimization/auto` | POST | 执行自动优化 |
| `/api/performance/optimization/cache` | POST | 执行缓存优化 |
| `/api/performance/optimization/database` | POST | 执行数据库优化 |
| `/api/performance/optimization/services` | POST | 执行服务优化 |

  数据库设计

 # performance_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_name | VARCHAR(100) | 服务名称 |
| metric_type | VARCHAR(50) | 指标类型 |
| metric_value | DECIMAL(15,2) | 指标值 |
| dimension | VARCHAR(100) | 维度信息 |
| recorded_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

 # optimization_recommendations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_name | VARCHAR(100) | 服务名称 |
| recommendation_type | VARCHAR(50) | 建议类型 |
| description | TEXT | 描述 |
| priority | VARCHAR(20) | 优先级 |
| status | VARCHAR(20) | 状态 |
| implemented_at | BIGINT | 实施时间 |
| created_at | BIGINT | 创建时间 |

 # slow_queries 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_name | VARCHAR(100) | 服务名称 |
| query_text | TEXT | 查询语句 |
| execution_time | INT | 执行时间（毫秒） |
| frequency | INT | 执行频率 |
| last_executed_at | BIGINT | 最后执行时间 |
| created_at | BIGINT | 创建时间 |

 # cache_statistics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_name | VARCHAR(100) | 服务名称 |
| cache_type | VARCHAR(20) | 缓存类型 |
| hit_count | BIGINT | 命中次数 |
| miss_count | BIGINT | 未命中次数 |
| hit_rate | DECIMAL(5,2) | 命中率 |
| evict_count | BIGINT | 驱逐次数 |
| recorded_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

  Prometheus 集成配置

 # 监控指标配置
```yaml
prometheus:
  scrape_configs:
    - job_name: 'haiya-services'
      static_configs:
        - targets: ['service-user:8080', 'service-video:8081', 'service-comment:8082']
      metrics_path: '/actuator/prometheus'
      scrape_interval: 15s
```

 # 告警规则配置
```yaml
groups:
- name: haiya-alerts
  rules:
  - alert: HighResponseTime
    expr: histogram_quantile(0.95, sum(rate(http_request_duration_seconds_bucket[5m])) by (le)) > 1
    for: 2m
    labels:
      severity: warning
    annotations:
      summary: "High response time detected"
```

  Grafana 仪表板配置

 # 核心指标面板
1. **服务响应时间面板**
   - 显示各服务95th响应时间
   - 设置阈值告警线
   - 支持时间范围选择

2. **数据库性能面板**
   - 显示慢查询数量趋势
   - 展示连接池使用情况
   - 显示QPS和TPS指标

3. **缓存性能面板**
   - 显示缓存命中率
   - 展示各缓存层使用情况
   - 显示缓存驱逐情况

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Prometheus 监控系统
- Grafana 可视化工具

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8150

spring:
  application:
    name: performance-optimizer
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_performance
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  redis:
    host: localhost
    port: 6379

performance-optimizer:
  monitoring:
    enabled: true
    interval: 30s
  optimization:
    auto-apply: false
  prometheus:
    endpoint: http://prometheus:9090
  grafana:
    endpoint: http://grafana:3000
```

 # 启动服务

```bash
cd performance-optimizer
mvn spring-boot:run
```

或

```bash
cd performance-optimizer
mvn clean package
java -jar target/performance-optimizer-1.0.0.jar
```

  服务依赖

- service-registry (服务注册中心)
- service-gateway (网关服务)
- infrastructure-monitoring (基础设施监控)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/performance/optimizer
├── PerformanceOptimizerApplication.java  # 应用入口
├── config/                              # 配置类
│   └── PerformanceConfig.java           # 性能优化配置
├── controller/                          # 控制器层
│   ├── MetricController.java            # 指标控制器
│   ├── AnalysisController.java          # 分析控制器
│   └── OptimizationController.java      # 优化控制器
├── entity/                              # 实体类
│   ├── PerformanceMetric.java           # 性能指标实体
│   ├── OptimizationRecommendation.java  # 优化建议实体
│   ├── SlowQuery.java                   # 慢查询实体
│   └── CacheStatistic.java              # 缓存统计实体
├── repository/                          # 数据访问层
│   ├── PerformanceMetricRepository.java # 性能指标数据访问
│   ├── OptimizationRecommendationRepository.java # 优化建议数据访问
│   ├── SlowQueryRepository.java         # 慢查询数据访问
│   └── CacheStatisticRepository.java    # 缓存统计数据访问
└── service/                             # 业务逻辑层
    ├── MetricService.java               # 指标服务
    ├── AnalysisService.java             # 分析服务
    └── OptimizationService.java         # 优化服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8150/actuator/health