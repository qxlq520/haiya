# 存储服务 (service-storage)

  服务概述

存储服务是海牙平台的统一存储管理服务，提供文件上传、下载、存储管理、CDN分发、存储策略配置等功能，支持多种存储后端和存储类型。

  功能特性

1. 文件上传与下载
2. 存储空间管理
3. 存储策略配置
4. CDN分发管理
5. 文件访问控制
6. 存储配额管理
7. 文件版本控制
8. 存储监控与统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- MinIO 对象存储
- Amazon S3
- 阿里云OSS
- 腾讯云COS

  API 接口

 # 文件管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/storage/files` | POST | 上传文件 |
| `/api/storage/files` | GET | 获取文件列表 |
| `/api/storage/files/{id}` | GET | 获取文件详情 |
| `/api/storage/files/{id}` | PUT | 更新文件信息 |
| `/api/storage/files/{id}/download` | GET | 下载文件 |
| `/api/storage/files/{id}/delete` | DELETE | 删除文件 |

 # 存储空间接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/storage/buckets` | POST | 创建存储空间 |
| `/api/storage/buckets` | GET | 获取存储空间列表 |
| `/api/storage/buckets/{id}` | GET | 获取存储空间详情 |
| `/api/storage/buckets/{id}` | PUT | 更新存储空间 |
| `/api/storage/buckets/{id}/delete` | DELETE | 删除存储空间 |
| `/api/storage/buckets/{id}/usage` | GET | 获取存储空间使用情况 |

 # 存储策略接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/storage/policies` | POST | 创建存储策略 |
| `/api/storage/policies` | GET | 获取存储策略列表 |
| `/api/storage/policies/{id}` | GET | 获取存储策略详情 |
| `/api/storage/policies/{id}` | PUT | 更新存储策略 |
| `/api/storage/policies/{id}/apply` | POST | 应用存储策略 |

 # CDN管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/storage/cdn/domains` | POST | 创建CDN域名 |
| `/api/storage/cdn/domains` | GET | 获取CDN域名列表 |
| `/api/storage/cdn/domains/{id}` | GET | 获取CDN域名详情 |
| `/api/storage/cdn/domains/{id}/refresh` | POST | 刷新CDN缓存 |
| `/api/storage/cdn/domains/{id}/prefetch` | POST | 预取CDN内容 |

 # 访问控制接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/storage/acl/files/{fileId}` | PUT | 设置文件访问控制 |
| `/api/storage/acl/buckets/{bucketId}` | PUT | 设置存储空间访问控制 |
| `/api/storage/acl/presigned-url` | POST | 生成预签名URL |
| `/api/storage/acl/temp-token` | POST | 生成临时访问令牌 |

  数据库设计

 # storage_files 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 文件名 |
| original_name | VARCHAR(255) | 原始文件名 |
| size | BIGINT | 文件大小（字节） |
| mime_type | VARCHAR(100) | MIME类型 |
| extension | VARCHAR(20) | 文件扩展名 |
| md5 | VARCHAR(32) | MD5校验值 |
| bucket_id | BIGINT | 存储空间ID |
| storage_path | VARCHAR(500) | 存储路径 |
| access_url | VARCHAR(500) | 访问URL |
| status | VARCHAR(20) | 状态（UPLOADING, AVAILABLE, DELETED） |
| owner_id | BIGINT | 所有者ID |
| tags | TEXT | 标签（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # storage_buckets 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 存储空间名称 |
| description | TEXT | 存储空间描述 |
| region | VARCHAR(50) | 区域 |
| storage_type | VARCHAR(20) | 存储类型（LOCAL, S3, OSS, COS） |
| config | TEXT | 配置信息（JSON格式） |
| quota | BIGINT | 配额（字节） |
| used_space | BIGINT | 已使用空间（字节） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DELETED） |
| owner_id | BIGINT | 所有者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # storage_policies 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 策略名称 |
| description | TEXT | 策略描述 |
| rules | TEXT | 策略规则（JSON格式） |
| storage_type | VARCHAR(20) | 存储类型 |
| is_default | BOOLEAN | 是否默认策略 |
| owner_id | BIGINT | 所有者ID |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # cdn_domains 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| domain | VARCHAR(255) | 域名 |
| bucket_id | BIGINT | 存储空间ID |
| provider | VARCHAR(20) | CDN提供商 |
| config | TEXT | 配置信息（JSON格式） |
| ssl_cert_id | BIGINT | SSL证书ID |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE, DELETED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # storage_access_logs 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| file_id | BIGINT | 文件ID |
| user_id | BIGINT | 用户ID |
| access_type | VARCHAR(20) | 访问类型（READ, WRITE, DELETE） |
| ip_address | VARCHAR(50) | IP地址 |
| user_agent | VARCHAR(500) | 用户代理 |
| access_time | BIGINT | 访问时间 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 文件缓存
```
storage:file:{fileId} -> Hash结构存储文件信息
storage:file:md5:{md5} -> String结构存储文件ID（用于去重）
```

 # 存储空间缓存
```
storage:bucket:{bucketId} -> Hash结构存储存储空间信息
storage:bucket:user:{userId} -> Set结构存储用户存储空间
```

 # 访问令牌缓存
```
storage:token:{token} -> String结构存储令牌信息
storage:token:{token}:permissions -> Set结构存储令牌权限
```

  MinIO 配置示例

 # 存储配置
```yaml
minio:
  endpoint: http://localhost:9000
  access-key: minioadmin
  secret-key: minioadmin
  bucket: haiya-storage
```

  阿里云OSS配置示例

 # 存储配置
```yaml
aliyun:
  oss:
    endpoint: oss-cn-hangzhou.aliyuncs.com
    access-key-id: your-access-key-id
    access-key-secret: your-access-key-secret
    bucket-name: haiya-storage
```

  腾讯云COS配置示例

 # 存储配置
```yaml
tencent:
  cos:
    region: ap-beijing
    secret-id: your-secret-id
    secret-key: your-secret-key
    bucket-name: haiya-storage-1250000000
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- MinIO 对象存储（可选）
- 支持S3协议的对象存储服务

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8131

spring:
  application:
    name: service-storage
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_storage
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379

storage:
  default-provider: minio
  providers:
    minio:
      endpoint: http://localhost:9000
      access-key: minioadmin
      secret-key: minioadmin
    aliyun-oss:
      endpoint: oss-cn-hangzhou.aliyuncs.com
      access-key-id: your-access-key-id
      access-key-secret: your-access-key-secret
```

 # 启动服务

```bash
cd service-storage
mvn spring-boot:run
```

或

```bash
cd service-storage
mvn clean package
java -jar target/service-storage-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/storage
├── StorageServiceApplication.java  # 应用入口
├── config/                         # 配置类
│   └── StorageConfig.java          # 存储配置
├── controller/                     # 控制器层
│   ├── FileController.java         # 文件控制器
│   ├── BucketController.java       # 存储空间控制器
│   ├── PolicyController.java       # 策略控制器
│   └── CdnController.java          # CDN控制器
├── entity/                         # 实体类
│   ├── StorageFile.java            # 存储文件实体
│   ├── StorageBucket.java          # 存储空间实体
│   ├── StoragePolicy.java          # 存储策略实体
│   ├── CdnDomain.java              # CDN域名实体
│   └── AccessLog.java              # 访问日志实体
├── repository/                     # 数据访问层
│   ├── StorageFileRepository.java  # 存储文件数据访问
│   ├── StorageBucketRepository.java # 存储空间数据访问
│   ├── StoragePolicyRepository.java # 存储策略数据访问
│   ├── CdnDomainRepository.java    # CDN域名数据访问
│   └── AccessLogRepository.java    # 访问日志数据访问
└── service/                        # 业务逻辑层
    ├── FileService.java            # 文件服务
    ├── BucketService.java          # 存储空间服务
    ├── PolicyService.java          # 策略服务
    └── CdnService.java             # CDN服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8131/actuator/health