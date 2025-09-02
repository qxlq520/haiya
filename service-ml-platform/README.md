# 机器学习平台服务 (service-ml-platform)

  服务概述

机器学习平台服务是海牙平台AI能力的核心基础设施，提供模型训练、模型部署、在线预测、A/B测试和模型管理等功能，支持推荐、搜索、风控、内容理解等各类AI应用场景。

  功能特性

1. 机器学习模型管理
2. 模型训练与优化
3. 在线推理服务
4. A/B测试框架
5. 特征工程支持
6. 模型性能监控
7. 自动化机器学习
8. 模型版本控制

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- TensorFlow/PyTorch 机器学习框架
- Apache Kafka 消息队列
- Docker 容器化部署

  API 接口

 # 模型管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ml/models` | GET | 获取模型列表 |
| `/api/ml/models` | POST | 创建模型 |
| `/api/ml/models/{id}` | GET | 获取模型详情 |
| `/api/ml/models/{id}` | PUT | 更新模型信息 |
| `/api/ml/models/{id}` | DELETE | 删除模型 |
| `/api/ml/models/{id}/deploy` | POST | 部署模型 |
| `/api/ml/models/{id}/undeploy` | POST | 卸载模型 |

 # 模型训练接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ml/training/jobs` | POST | 创建训练任务 |
| `/api/ml/training/jobs` | GET | 获取训练任务列表 |
| `/api/ml/training/jobs/{id}` | GET | 获取训练任务详情 |
| `/api/ml/training/jobs/{id}/cancel` | POST | 取消训练任务 |
| `/api/ml/training/jobs/{id}/logs` | GET | 获取训练日志 |

 # 在线预测接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ml/predictions/{modelId}` | POST | 模型预测 |
| `/api/ml/predictions/batch/{modelId}` | POST | 批量预测 |
| `/api/ml/predictions/feedback/{predictionId}` | POST | 预测反馈 |

 # A/B测试接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ml/ab-tests` | POST | 创建A/B测试 |
| `/api/ml/ab-tests` | GET | 获取A/B测试列表 |
| `/api/ml/ab-tests/{id}` | GET | 获取A/B测试详情 |
| `/api/ml/ab-tests/{id}/metrics` | GET | 获取A/B测试指标 |

 # 特征管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ml/features` | GET | 获取特征列表 |
| `/api/ml/features` | POST | 创建特征 |
| `/api/ml/features/{id}` | GET | 获取特征详情 |
| `/api/ml/features/{id}/stats` | GET | 获取特征统计信息 |

  数据库设计

 # ml_models 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 模型名称 |
| description | TEXT | 模型描述 |
| model_type | VARCHAR(50) | 模型类型（RECOMMENDATION, CLASSIFICATION, REGRESSION等） |
| algorithm | VARCHAR(100) | 算法名称 |
| version | VARCHAR(20) | 版本号 |
| status | VARCHAR(20) | 状态（DRAFT, TRAINING, DEPLOYED, UNDEPLOYED, ARCHIVED） |
| model_path | VARCHAR(500) | 模型文件路径 |
| config | TEXT | 模型配置（JSON格式） |
| metrics | TEXT | 模型指标（JSON格式） |
| creator_id | BIGINT | 创建者ID |
| deployed_at | BIGINT | 部署时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ml_training_jobs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| model_id | BIGINT | 模型ID |
| job_name | VARCHAR(100) | 任务名称 |
| dataset_info | TEXT | 数据集信息（JSON格式） |
| training_config | TEXT | 训练配置（JSON格式） |
| status | VARCHAR(20) | 状态（PENDING, RUNNING, COMPLETED, FAILED, CANCELLED） |
| progress | DECIMAL(5,2) | 训练进度百分比 |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| error_message | TEXT | 错误信息 |
| log_path | VARCHAR(500) | 日志路径 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ml_predictions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| model_id | BIGINT | 模型ID |
| input_data | TEXT | 输入数据（JSON格式） |
| prediction_result | TEXT | 预测结果（JSON格式） |
| confidence | DECIMAL(5,2) | 置信度 |
| response_time | INT | 响应时间（毫秒） |
| predicted_at | BIGINT | 预测时间 |
| created_at | BIGINT | 创建时间 |

 # ml_ab_tests 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 测试名称 |
| description | TEXT | 测试描述 |
| model_a_id | BIGINT | 模型A ID |
| model_b_id | BIGINT | 模型B ID |
| traffic_split | VARCHAR(20) | 流量分配比例 |
| status | VARCHAR(20) | 状态（RUNNING, PAUSED, COMPLETED） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| metrics_config | TEXT | 指标配置（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ml_features 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 特征名称 |
| description | TEXT | 特征描述 |
| data_type | VARCHAR(50) | 数据类型（INT, FLOAT, STRING, BOOLEAN等） |
| category | VARCHAR(50) | 特征分类（USER, ITEM, CONTEXT等） |
| statistics | TEXT | 统计信息（JSON格式） |
| source_tables | TEXT | 源数据表（JSON格式） |
| version | VARCHAR(20) | 版本号 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DEPRECATED） |
| creator_id | BIGINT | 创建者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # ml_model_versions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| model_id | BIGINT | 模型ID |
| version | VARCHAR(20) | 版本号 |
| model_path | VARCHAR(500) | 模型文件路径 |
| config | TEXT | 模型配置（JSON格式） |
| metrics | TEXT | 模型指标（JSON格式） |
| deployed_at | BIGINT | 部署时间 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 模型缓存
```
ml:model:{modelId} -> String结构存储模型信息
ml:model:{modelId}:version:{version} -> String结构存储模型版本信息
```

 # 预测缓存
```
ml:prediction:cache:{modelId}:{inputHash} -> String结构存储预测结果缓存
```

 # A/B测试缓存
```
ml:ab-test:{testId}:traffic -> Hash结构存储流量分配配置
```

  Docker 镜像管理

 # 模型服务镜像
```
haiya/ml-model-service:{version}
  - 基础镜像: python:3.8-slim
  - 包含: TensorFlow/PyTorch, Flask, Gunicorn
```

 # 训练任务镜像
```
haiya/ml-training-job:{version}
  - 基础镜像: python:3.8-slim
  - 包含: TensorFlow/PyTorch, Scikit-learn, Pandas
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- TensorFlow/PyTorch 机器学习框架
- Apache Kafka 消息队列
- Docker 20.10+

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8124

spring:
  application:
    name: service-ml-platform
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_ml_platform
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

ml-platform:
  model-storage:
    path: /data/ml/models
  training:
    job-image: haiya/ml-training-job:latest
  prediction:
    service-image: haiya/ml-model-service:latest
```

 # 启动服务

```bash
cd service-ml-platform
mvn spring-boot:run
```

或

```bash
cd service-ml-platform
mvn clean package
java -jar target/service-ml-platform-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-feature-engineering (特征工程服务)
- service-data-warehouse (数据仓库服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/ml/platform
├── MlPlatformApplication.java   # 应用入口
├── config/                      # 配置类
│   └── MLPlatformConfig.java    # 机器学习平台配置
├── controller/                  # 控制器层
│   ├── ModelController.java     # 模型控制器
│   ├── TrainingController.java  # 训练控制器
│   ├── PredictionController.java # 预测控制器
│   ├── ABTestController.java    # A/B测试控制器
│   └── FeatureController.java   # 特征控制器
├── entity/                      # 实体类
│   ├── MLModel.java             # 机器学习模型实体
│   ├── TrainingJob.java         # 训练任务实体
│   ├── Prediction.java          # 预测实体
│   ├── ABTest.java              # A/B测试实体
│   ├── Feature.java             # 特征实体
│   └── ModelVersion.java        # 模型版本实体
├── repository/                  # 数据访问层
│   ├── MLModelRepository.java   # 机器学习模型数据访问
│   ├── TrainingJobRepository.java # 训练任务数据访问
│   ├── PredictionRepository.java # 预测数据访问
│   ├── ABTestRepository.java    # A/B测试数据访问
│   ├── FeatureRepository.java   # 特征数据访问
│   └── ModelVersionRepository.java # 模型版本数据访问
└── service/                     # 业务逻辑层
    ├── ModelService.java        # 模型服务
    ├── TrainingService.java     # 训练服务
    ├── PredictionService.java   # 预测服务
    ├── ABTestService.java       # A/B测试服务
    └── FeatureService.java      # 特征服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8124/actuator/health