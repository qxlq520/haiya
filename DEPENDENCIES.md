# 海牙平台后端依赖库说明

  核心依赖

 # 1. Spring Boot 系列
- spring-boot-starter-web: Web应用核心依赖
- spring-boot-starter-data-jpa: JPA数据访问
- spring-boot-starter-security: 安全框架
- spring-boot-starter-test: 测试框架

 # 2. Spring Cloud 系列
- spring-cloud-starter-netflix-eureka-client: 服务发现客户端
- spring-cloud-starter-gateway: 网关组件
- spring-cloud-config-client: 配置客户端
- spring-cloud-starter-openfeign: 声明式REST客户端
- spring-cloud-starter-netflix-hystrix: 熔断器

 # 3. 数据库相关
- com.h2database:h2: H2内存数据库(开发环境)
- mysql:mysql-connector-java: MySQL连接器(生产环境)
- org.hibernate:hibernate-core: Hibernate ORM框架

 # 4. 安全认证
- io.jsonwebtoken:jjwt: JWT Token处理
- spring-security-jwt: Spring Security JWT支持

 # 5. 工具类
- com.fasterxml.jackson.core:jackson-core: JSON处理
- org.apache.commons:commons-lang3: Apache工具类
- com.google.guava:guava: Google工具类

 # 6. 测试框架
- junit:junit: JUnit 4测试框架
- org.junit.jupiter:junit-jupiter: JUnit 5测试框架
- org.mockito:mockito-core: Mock框架
- org.hamcrest:hamcrest: 匹配器库

 # 7. 日志系统
- org.slf4j:slf4j-api: SLF4J日志接口
- ch.qos.logback:logback-classic: Logback实现

 # 8. 缓存系统
- org.springframework.boot:spring-boot-starter-cache: Spring缓存
- redis.clients:jedis: Redis客户端
- org.springframework.boot:spring-boot-starter-data-redis: Spring Data Redis

 # 9. 消息队列
- org.springframework.boot:spring-boot-starter-amqp: RabbitMQ支持
- org.apache.rocketmq:rocketmq-spring-boot-starter: RocketMQ支持

 # 10. 监控与运维
- org.springframework.boot:spring-boot-starter-actuator: 应用监控
- io.micrometer:micrometer-core: 度量指标收集
- de.codecentric:spring-boot-admin-starter-client: Admin客户端

  第三方SDK

 # 1. 云服务SDK
- com.aliyun:aliyun-java-sdk-core: 阿里云核心SDK
- com.qiniu:qiniu-java-sdk: 七牛云存储SDK
- com.tencentcloudapi:tencentcloud-sdk-java: 腾讯云SDK

 # 2. 支付SDK
- com.alipay.sdk:alipay-sdk-java: 支付宝SDK
- com.github.wxpay:wxpay-sdk: 微信支付SDK

 # 3. 短信服务
- com.aliyun:aliyun-java-sdk-dysmsapi: 阿里云短信服务

 # 4. 推送服务
- com.getui:gexin-rp-sdk-http: 个推推送服务

  构建工具

 # Maven依赖管理
```xml
<properties>
    <java.version>11</java.version>
    <spring-boot.version>2.6.3</spring-boot.version>
    <spring-cloud.version>2021.0.1</spring-cloud.version>
</properties>
```

  运行环境要求

 # JDK版本
- 最低要求: Java 11
- 推荐版本: Java 11 或 Java 17

 # 操作系统
- Linux (推荐Ubuntu 18.04+ 或 CentOS 7+)
- Windows Server 2016+
- macOS 10.15+

 # 内存要求
- 最低: 4GB RAM
- 推荐: 8GB+ RAM

 # 存储要求
- 最小: 10GB可用空间
- 推荐: 50GB+可用空间

  容器化部署依赖

 # Docker
- Docker Engine 20.10+
- Docker Compose 1.29+

 # Kubernetes
- Kubernetes 1.20+
- Helm 3.5+

  网络依赖服务

 # 数据库
- MySQL 8.0+ (生产环境)
- Redis 6.0+ (缓存)
- MongoDB 4.4+ (文档存储)

 # 消息队列
- RabbitMQ 3.8+ 或 RocketMQ 4.9+

 # 搜索引擎
- Elasticsearch 7.10+

  新增服务模块

 # 评论服务 (service-comment)
- 端口: 8083
- 功能: 提供评论相关API
- 数据库: commentdb (H2/MySQL)
- 依赖: service-user, service-video

 # 点赞服务 (service-like)
- 端口: 8084
- 功能: 提供点赞相关API
- 数据库: likedb (H2/MySQL)
- 依赖: service-user, service-video, service-comment

 # 关注服务 (service-follow)
- 端口: 8085
- 功能: 提供关注相关API
- 数据库: followdb (H2/MySQL)
- 依赖: service-user

  配置管理

所有依赖配置通过Spring Cloud Config统一管理，支持不同环境的配置文件:
- application.yml (默认配置)
- application-dev.yml (开发环境)
- application-test.yml (测试环境)
- application-prod.yml (生产环境)

  版本兼容性

| 组件 | 版本 | 兼容性说明 |
|------|------|------------|
| Spring Boot | 2.6.3 | 稳定版本 |
| Spring Cloud | 2021.0.1 | 与Spring Boot 2.6.x兼容 |
| Java | 11/17 | LTS版本 |
| MySQL | 8.0+ | 支持最新特性 |
| Redis | 6.0+ | 支持集群模式 |

  依赖更新策略

1. 核心框架(Spring Boot/Spring Cloud)每季度评估更新
2. 安全相关依赖及时更新到最新稳定版本
3. 功能性依赖按需更新
4. 所有更新需要通过完整的回归测试