# 基础设施监控 (infrastructure-monitoring)

## 概述

基础设施监控是海牙平台的统一监控系统，负责收集、存储和展示整个平台的运行状态信息，包括基础设施、应用服务、业务指标等各个层面的监控数据。

## 功能特性

1. 基础设施监控（CPU、内存、磁盘、网络）
2. 应用性能监控（APM）
3. 业务指标监控
4. 日志收集与分析
5. 告警管理
6. 可视化展示
7. 历史数据存储
8. 监控仪表板
9. 自动扩容与故障自愈

## 技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Prometheus 监控系统
- Grafana 可视化面板
- ELK Stack（Elasticsearch, Logstash, Kibana）
- Apache SkyWalking APM

  核心监控组件

 # 基础设施监控

1. **主机监控**
   - CPU使用率监控
   - 内存使用情况监控
   - 磁盘空间和IO监控
   - 网络流量和连接监控

2. **容器监控**
   - Docker容器资源使用监控
   - Kubernetes集群状态监控
   - 容器编排和服务发现监控

3. **网络监控**
   - 网络延迟和丢包率监控
   - 带宽使用情况监控
   - HTTP请求成功率监控

 # 应用性能监控（APM）

1. **服务监控**
   - 服务响应时间监控
   - 服务可用性监控
   - 服务调用链路追踪
   - 服务错误率监控

2. **数据库监控**
   - 数据库连接数监控
   - SQL执行性能监控
   - 慢查询监控
   - 数据库资源使用监控

3. **缓存监控**
   - 缓存命中率监控
   - 缓存响应时间监控
   - 缓存内存使用监控
   - 缓存操作成功率监控

  API 接口

 # 监控数据接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/monitoring/metrics` | GET | 获取监控指标数据 |
| `/api/monitoring/metrics/realtime` | GET | 获取实时监控指标 |
| `/api/monitoring/metrics/history` | GET | 获取历史监控指标 |
| `/api/monitoring/metrics/aggregated` | GET | 获取聚合监控指标 |

 # 告警管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/monitoring/alerts` | GET | 获取告警列表 |
| `/api/monitoring/alerts` | POST | 创建告警规则 |
| `/api/monitoring/alerts/{id}` | GET | 获取告警详情 |
| `/api/monitoring/alerts/{id}/acknowledge` | POST | 确认告警 |
| `/api/monitoring/alerts/{id}/resolve` | POST | 解决告警 |

 # 仪表板接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/monitoring/dashboards` | GET | 获取仪表板列表 |
| `/api/monitoring/dashboards` | POST | 创建仪表板 |
| `/api/monitoring/dashboards/{id}` | GET | 获取仪表板详情 |
| `/api/monitoring/dashboards/{id}` | PUT | 更新仪表板 |
| `/api/monitoring/dashboards/{id}/delete` | DELETE | 删除仪表板 |

  数据库设计

 # monitoring_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| service_name | VARCHAR(100) | 服务名称 |
| metric_name | VARCHAR(100) | 指标名称 |
| metric_type | VARCHAR(50) | 指标类型 |
| metric_value | DECIMAL(15,2) | 指标值 |
| tags | TEXT | 标签（JSON格式） |
| timestamp | BIGINT | 时间戳 |
| created_at | BIGINT | 创建时间 |

 # alert_rules 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 告警名称 |
| description | TEXT | 告警描述 |
| metric_name | VARCHAR(100) | 指标名称 |
| condition | TEXT | 告警条件（JSON格式） |
| severity | VARCHAR(20) | 严重级别（INFO, WARNING, CRITICAL） |
| enabled | BOOLEAN | 是否启用 |
| notification_channels | TEXT | 通知渠道（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # alert_events 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| rule_id | BIGINT | 告警规则ID |
| service_name | VARCHAR(100) | 服务名称 |
| metric_name | VARCHAR(100) | 指标名称 |
| current_value | DECIMAL(15,2) | 当前值 |
| threshold | DECIMAL(15,2) | 阈值 |
| severity | VARCHAR(20) | 严重级别 |
| status | VARCHAR(20) | 状态（TRIGGERED, ACKNOWLEDGED, RESOLVED） |
| triggered_at | BIGINT | 触发时间 |
| acknowledged_at | BIGINT | 确认时间 |
| resolved_at | BIGINT | 解决时间 |
| created_at | BIGINT | 创建时间 |

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

 # dashboard_panels 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| dashboard_id | BIGINT | 仪表板ID |
| title | VARCHAR(100) | 面板标题 |
| type | VARCHAR(20) | 面板类型（GRAPH, TABLE, SINGLESTAT等） |
| query | TEXT | 查询语句 |
| visualization | TEXT | 可视化配置（JSON格式） |
| position | TEXT | 位置信息（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  Prometheus 集成配置

 # 全局配置
```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

rule_files:
  - "alert.rules"

scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: ['localhost:9090']
      
  - job_name: 'haiya-services'
    static_configs:
      - targets: ['service-user:8080', 'service-video:8081', 'service-comment:8082']
    metrics_path: '/actuator/prometheus'
```

 # 告警规则配置
```yaml
groups:
- name: haiya-alerts
  rules:
  - alert: ServiceDown
    expr: up == 0
    for: 1m
    labels:
      severity: critical
    annotations:
      summary: "Service {{ $labels.job }} is down"
      
  - alert: HighResponseTime
    expr: histogram_quantile(0.95, sum(rate(http_request_duration_seconds_bucket[5m])) by (le, job)) > 1
    for: 2m
    labels:
      severity: warning
    annotations:
      summary: "High response time for service {{ $labels.job }}"
```

  Grafana 仪表板配置

 # 核心监控仪表板

1. **系统概览仪表板**
   - 显示所有关键服务的运行状态
   - 展示整体系统性能指标
   - 提供快速故障诊断入口

2. **服务性能仪表板**
   - 显示各服务的详细性能指标
   - 展示服务调用链路
   - 提供服务间依赖关系图

3. **数据库监控仪表板**
   - 显示数据库性能指标
   - 展示慢查询统计
   - 提供连接池使用情况

4. **业务指标仪表板**
   - 显示关键业务指标
   - 展示用户行为分析
   - 提供转化率和留存率数据

  ELK日志分析配置

 # Logstash配置
```ruby
input {
  beats {
    port => 5044
  }
}

filter {
  if [type] == "haiya-log" {
    grok {
      match => { "message" => "%{TIMESTAMP_ISO8601:timestamp} \[%{DATA:thread}\] %{LOGLEVEL:level} %{DATA:class} - %{GREEDYDATA:message}" }
    }
    
    date {
      match => [ "timestamp", "ISO8601" ]
    }
  }
}

output {
  elasticsearch {
    hosts => ["localhost:9200"]
    index => "haiya-logs-%{+YYYY.MM.dd}"
  }
}
```

 # Elasticsearch索引模板
```json
{
  "index_patterns": ["haiya-logs-*"],
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 1
  },
  "mappings": {
    "properties": {
      "timestamp": { "type": "date" },
      "thread": { "type": "keyword" },
      "level": { "type": "keyword" },
      "class": { "type": "keyword" },
      "message": { "type": "text" }
    }
  }
}
```

## 部署与配置

### 环境要求

- Java 11+
- Maven 3.6+
- SkyWalking OAP Server 8.10.0
- Prometheus Server
- Grafana

### 配置说明

配置文件位于 `src/main/resources/application.yml`：

```yaml
# SkyWalking APM 配置
skywalking:
  agent:
    service_name: haiya-infrastructure-monitoring
    collector_backend_services: localhost:11800
  meter:
    report_interval: 10

# 基础设施监控配置
infrastructure:
  monitoring:
    # CPU监控配置
    cpu:
      enabled: true
      threshold:
        warning: 80 # CPU使用率警告阈值(%)
        critical: 90 # CPU使用率严重阈值(%)
    
    # 内存监控配置
    memory:
      enabled: true
      threshold:
        warning: 80 # 内存使用率警告阈值(%)
        critical: 90 # 内存使用率严重阈值(%)
    
    # 磁盘监控配置
    disk:
      enabled: true
      threshold:
        warning: 85 # 磁盘使用率警告阈值(%)
        critical: 95 # 磁盘使用率严重阈值(%)
    
    # 网络监控配置
    network:
      enabled: true
      threshold:
        latency:
          warning: 100 # 网络延迟警告阈值(ms)
          critical: 500 # 网络延迟严重阈值(ms)
        packetLoss:
          warning: 1 # 丢包率警告阈值(%)
          critical: 5 # 丢包率严重阈值(%)

# 应用性能监控配置
application:
  monitoring:
    # QPS监控配置
    qps:
      threshold:
        warning: 1000 # QPS警告阈值
        critical: 5000 # QPS严重阈值
    
    # 响应时间监控配置
    responseTime:
      threshold:
        warning: 500 # 响应时间警告阈值(ms)
        critical: 2000 # 响应时间严重阈值(ms)
    
    # 错误率监控配置
    errorRate:
      threshold:
        warning: 1 # 错误率警告阈值(%)
        critical: 5 # 错误率严重阈值(%)

# 告警配置
alerting:
  enabled: true
  # 告警级别配置
  levels:
    warning:
      channels: [email] # 告警方式
      repeatInterval: 300 # 重复告警间隔(秒)
    critical:
      channels: [email, sms] # 告警方式
      repeatInterval: 60 # 重复告警间隔(秒)
    emergency:
      channels: [email, sms, phone] # 告警方式
      repeatInterval: 30 # 重复告警间隔(秒)
  
  # 告警通道配置
  channels:
    email:
      enabled: true
      smtp:
        host: smtp.example.com
        port: 587
        username: monitoring@haiya.com
        password: password
      recipients:
        - admin@haiya.com
        - devops@haiya.com
    
    sms:
      enabled: true
      provider: aliyun
      accessKeyId: your-access-key-id
      accessKeySecret: your-access-key-secret
      signName: 海牙平台
      templateCode: SMS_XXXXXXXXX
    
    phone:
      enabled: true
      provider: aliyun
      accessKeyId: your-access-key-id
      accessKeySecret: your-access-key-secret
      ttsCode: TTS_XXXXXXXXX

# 自动扩容配置
autoscaling:
  enabled: true
  # CPU使用率触发自动扩容阈值
  cpuThreshold: 75
  # 内存使用率触发自动扩容阈值
  memoryThreshold: 80
  # 最小实例数
  minInstances: 2
  # 最大实例数
  maxInstances: 10
  # 扩容冷却时间(秒)
  cooldown: 300
```

### SkyWalking 配置

SkyWalking配置文件位于 `src/main/resources/skywalking.yml`：

```yaml
# Agent配置
agent:
  service_name: haiya-infrastructure-monitoring
  ignore_suffix: .jpg,.jpeg,.js,.css,.png,.bmp,.gif,.ico,.mp3,.mp4,.html,.svg
  is_open_debugging_class: false
  heartbeat_period: 30

# Collector配置
collector:
  backend_service: localhost:11800

# 日志配置
log:
  enable: true

# 插件配置
plugin:
  spring:
    mvc:
      enabled: true
  database:
    enabled: true
  http:
    client:
      enabled: true

# Meter配置
meter:
  report_interval: 10
```

## 部署步骤

1. 启动SkyWalking OAP Server
2. 启动Prometheus Server
3. 启动Grafana
4. 部署基础设施监控服务：
   ```bash
   cd haiya-backend/infrastructure-monitoring
   mvn clean package
   java -jar target/infrastructure-monitoring-1.0.0.jar
   ```

  服务依赖

- service-registry (服务注册中心)
- service-gateway (网关服务)

## 监控面板

系统集成以下监控面板：

1. **Grafana仪表板**
   - 基础设施监控面板
   - 应用性能监控面板
   - 业务指标监控面板

2. **SkyWalking UI**
   - 全链路追踪面板
   - 服务拓扑图
   - 性能分析面板

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8151/actuator/health