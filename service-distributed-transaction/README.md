# 分布式事务服务 (service-distributed-transaction)

  服务概述

分布式事务服务是海牙平台的事务管理基础设施服务，负责处理跨多个服务和数据库的分布式事务，确保数据的一致性和完整性。

  功能特性

1. 分布式事务管理
2. TCC事务模式支持
3. Saga事务模式支持
4. AT事务模式支持
5. 事务状态监控
6. 事务补偿机制
7. 事务日志管理
8. 事务超时处理

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Seata 分布式事务框架

  API 接口

 # 事务管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/distributed-transactions` | POST | 开启分布式事务 |
| `/api/distributed-transactions/{xid}` | GET | 获取事务状态 |
| `/api/distributed-transactions/{xid}/commit` | POST | 提交事务 |
| `/api/distributed-transactions/{xid}/rollback` | POST | 回滚事务 |
| `/api/distributed-transactions/{xid}` | DELETE | 删除事务记录 |

 # 事务组接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transaction-groups` | GET | 获取事务组列表 |
| `/api/transaction-groups` | POST | 创建事务组 |
| `/api/transaction-groups/{groupId}` | GET | 获取事务组详情 |
| `/api/transaction-groups/{groupId}/transactions` | GET | 获取事务组内事务列表 |

 # 事务补偿接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transaction-compensations` | GET | 获取补偿事务列表 |
| `/api/transaction-compensations/{id}/retry` | POST | 重试补偿事务 |
| `/api/transaction-compensations/batch-retry` | POST | 批量重试补偿事务 |
| `/api/transaction-compensations/{id}/manual` | POST | 手动处理补偿事务 |

 # 事务监控接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/transaction-monitor/overview` | GET | 获取事务监控概览 |
| `/api/transaction-monitor/statistics` | GET | 获取事务统计信息 |
| `/api/transaction-monitor/errors` | GET | 获取事务错误列表 |
| `/api/transaction-monitor/performance` | GET | 获取事务性能指标 |

  数据库设计

 # distributed_transactions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| xid | VARCHAR(100) | 全局事务ID |
| transaction_name | VARCHAR(255) | 事务名称 |
| status | VARCHAR(20) | 事务状态（BEGIN, COMMITTING, ROLLBACKING, COMMITTED, ROLLBACKED, ERROR） |
| application_id | VARCHAR(100) | 应用ID |
| transaction_service_group | VARCHAR(100) | 事务服务组 |
| transaction_type | VARCHAR(20) | 事务类型（AT, TCC, SAGA） |
| timeout | INT | 超时时间（秒） |
| begin_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transaction_branches 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| xid | VARCHAR(100) | 全局事务ID |
| branch_id | BIGINT | 分支事务ID |
| resource_id | VARCHAR(100) | 资源ID |
| lock_key | VARCHAR(500) | 锁定键 |
| branch_type | VARCHAR(20) | 分支类型（AT, TCC, SAGA） |
| status | VARCHAR(20) | 分支状态（REGISTERED, TRYING, TRY_SUCCESS, TRY_FAILED, COMMITTING, COMMITTED, ROLLBACKING, ROLLBACKED） |
| application_data | TEXT | 应用数据（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transaction_compensations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| xid | VARCHAR(100) | 全局事务ID |
| branch_id | BIGINT | 分支事务ID |
| compensation_type | VARCHAR(20) | 补偿类型（COMMIT, ROLLBACK） |
| status | VARCHAR(20) | 补偿状态（PENDING, PROCESSING, SUCCESS, FAILED） |
| retry_count | INT | 重试次数 |
| max_retry_count | INT | 最大重试次数 |
| error_message | TEXT | 错误信息 |
| last_retry_time | BIGINT | 最后重试时间 |
| next_retry_time | BIGINT | 下次重试时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transaction_groups 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| group_id | VARCHAR(100) | 事务组ID |
| group_name | VARCHAR(100) | 事务组名称 |
| description | TEXT | 描述 |
| owner_id | BIGINT | 所有者ID |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # transaction_monitor_metrics 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| metric_type | VARCHAR(50) | 指标类型（TPS, SUCCESS_RATE, AVG_RT等） |
| metric_value | DECIMAL(15,2) | 指标值 |
| dimension | VARCHAR(100) | 维度信息 |
| recorded_at | BIGINT | 记录时间 |
| created_at | BIGINT | 创建时间 |

  Seata 配置示例

 # file.conf 配置
```conf
transport {
  type = "TCP"
  server = "NIO"
  heartbeat = true
  serialization = "seata"
  compressor = "none"
}
service {
  vgroupMapping.my_tx_group = "default"
  default.grouplist = "127.0.0.1:8091"
  enableDegrade = false
  disableGlobalTransaction = false
}
client {
  rm {
    asyncCommitBufferLimit = 10000
    lock {
      retryInterval = 10
      retryTimes = 30
      retryPolicyBranchRollbackOnConflict = true
    }
    reportRetryCount = 5
    tableMetaCheckEnable = false
    reportSuccessEnable = false
  }
  tm {
    commitRetryCount = 5
    rollbackRetryCount = 5
  }
}
```

 # registry.conf 配置
```conf
registry {
  type = "eureka"
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "seata-server"
    weight = "1"
  }
}
config {
  type = "file"
  file {
    name = "file.conf"
  }
}
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Seata 1.4.x 分布式事务框架

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8109

spring:
  application:
    name: service-distributed-transaction
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_distributed_transaction
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379
    
seata:
  enabled: true
  application-id: service-distributed-transaction
  tx-service-group: my_tx_group
  config:
    type: file
  registry:
    type: eureka
```

 # 启动服务

```bash
cd service-distributed-transaction
mvn spring-boot:run
```

或

```bash
cd service-distributed-transaction
mvn clean package
java -jar target/service-distributed-transaction-1.0.0.jar
```

  服务依赖

- service-registry (服务注册中心)
- service-gateway (网关服务)
- Seata Server

  开发指南

 # 项目结构

```
src/main/java/com/haiya/distributed/transaction
├── DistributedTransactionApplication.java  # 应用入口
├── controller/                            # 控制器层
│   ├── TransactionController.java         # 事务控制器
│   ├── GroupController.java               # 事务组控制器
│   ├── CompensationController.java        # 补偿控制器
│   └── MonitorController.java             # 监控控制器
├── entity/                                # 实体类
│   ├── DistributedTransaction.java         # 分布式事务实体
│   ├── TransactionBranch.java             # 事务分支实体
│   ├── TransactionCompensation.java       # 事务补偿实体
│   ├── TransactionGroup.java              # 事务组实体
│   └── MonitorMetric.java                 # 监控指标实体
├── repository/                            # 数据访问层
│   ├── DistributedTransactionRepository.java # 分布式事务数据访问
│   ├── TransactionBranchRepository.java   # 事务分支数据访问
│   ├── TransactionCompensationRepository.java # 事务补偿数据访问
│   ├── TransactionGroupRepository.java    # 事务组数据访问
│   └── MonitorMetricRepository.java       # 监控指标数据访问
└── service/                               # 业务逻辑层
    ├── TransactionService.java            # 事务服务
    ├── GroupService.java                  # 事务组服务
    ├── CompensationService.java           # 补偿服务
    └── MonitorService.java                # 监控服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8109/actuator/health