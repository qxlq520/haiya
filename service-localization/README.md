# 本地化服务 (service-localization)

  服务概述

本地化服务是海牙平台国际化支持的核心服务，负责处理多语言内容翻译、区域设置管理、本地化资源配置和文化适配等功能。

  功能特性

1. 多语言内容管理
2. 区域设置管理
3. 本地化资源翻译
4. 文化适配处理
5. 时区和货币转换
6. 本地化内容推荐
7. 本地化数据统计
8. 本地化配置管理

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存

  API 接口

 # 语言管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/languages` | GET | 获取语言列表 |
| `/api/languages` | POST | 创建语言 |
| `/api/languages/{id}` | GET | 获取语言详情 |
| `/api/languages/{id}` | PUT | 更新语言信息 |
| `/api/languages/{id}` | DELETE | 删除语言 |

 # 区域设置接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/locales` | GET | 获取区域设置列表 |
| `/api/locales` | POST | 创建区域设置 |
| `/api/locales/{id}` | GET | 获取区域设置详情 |
| `/api/locales/{id}/default` | PUT | 设置默认区域 |
| `/api/locales/{id}/activate` | PUT | 激活区域 |

 # 翻译内容接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/translations` | GET | 获取翻译内容列表 |
| `/api/translations` | POST | 创建翻译内容 |
| `/api/translations/batch` | POST | 批量创建翻译内容 |
| `/api/translations/{id}` | GET | 获取翻译内容详情 |
| `/api/translations/{id}` | PUT | 更新翻译内容 |
| `/api/translations/search` | GET | 搜索翻译内容 |

 # 本地化资源接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/localization-resources` | GET | 获取本地化资源列表 |
| `/api/localization-resources` | POST | 创建本地化资源 |
| `/api/localization-resources/{id}` | GET | 获取本地化资源详情 |
| `/api/localization-resources/{id}/export` | GET | 导出本地化资源 |
| `/api/localization-resources/{id}/import` | POST | 导入本地化资源 |

 # 文化适配接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/cultural-adaptations` | GET | 获取文化适配规则列表 |
| `/api/cultural-adaptations` | POST | 创建文化适配规则 |
| `/api/cultural-adaptations/{id}` | GET | 获取文化适配规则详情 |
| `/api/cultural-adaptations/{id}/apply` | POST | 应用文化适配规则 |

  数据库设计

 # languages 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| code | VARCHAR(10) | 语言代码（如：zh, en, ja等） |
| name | VARCHAR(100) | 语言名称 |
| native_name | VARCHAR(100) | 本地语言名称 |
| direction | VARCHAR(10) | 文字方向（LTR, RTL） |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| is_default | BOOLEAN | 是否默认语言 |
| sort_order | INT | 排序 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # locales 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| language_id | BIGINT | 语言ID |
| country_code | VARCHAR(10) | 国家代码（如：CN, US, JP等） |
| locale_code | VARCHAR(20) | 区域代码（如：zh_CN, en_US等） |
| name | VARCHAR(100) | 区域名称 |
| timezone | VARCHAR(50) | 时区 |
| currency | VARCHAR(10) | 货币代码 |
| date_format | VARCHAR(50) | 日期格式 |
| number_format | VARCHAR(50) | 数字格式 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| is_default | BOOLEAN | 是否默认区域 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # translations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| locale_id | BIGINT | 区域ID |
| key | VARCHAR(255) | 翻译键 |
| value | TEXT | 翻译值 |
| description | TEXT | 描述 |
| module | VARCHAR(100) | 模块 |
| version | VARCHAR(20) | 版本 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # localization_resources 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(100) | 资源名称 |
| type | VARCHAR(20) | 资源类型（STRING, IMAGE, VIDEO等） |
| locale_id | BIGINT | 区域ID |
| file_url | VARCHAR(500) | 文件URL |
| file_size | BIGINT | 文件大小 |
| content_type | VARCHAR(100) | 内容类型 |
| version | VARCHAR(20) | 版本 |
| status | VARCHAR(20) | 状态（ACTIVE, INACTIVE） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # cultural_adaptations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| locale_id | BIGINT | 区域ID |
| adaptation_type | VARCHAR(50) | 适配类型（COLOR, SYMBOL, CONTENT等） |
| rule_config | TEXT | 规则配置（JSON格式） |
| description | TEXT | 描述 |
| is_active | BOOLEAN | 是否激活 |
| priority | INT | 优先级 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # user_locales 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| locale_id | BIGINT | 区域ID |
| is_preferred | BOOLEAN | 是否首选 |
| last_used_at | BIGINT | 最后使用时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  Redis 缓存设计

 # 翻译内容缓存
```
localization:translations:{localeCode} -> Hash结构存储翻译内容
localization:translations:{localeCode}:{module} -> Hash结构存储模块翻译内容
```

 # 区域设置缓存
```
localization:locales -> List结构存储所有区域设置
localization:locale:{localeCode} -> String结构存储区域设置详情
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8119

spring:
  application:
    name: service-localization
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_localization
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
cd service-localization
mvn spring-boot:run
```

或

```bash
cd service-localization
mvn clean package
java -jar target/service-localization-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-translation (翻译服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/localization
├── LocalizationApplication.java     # 应用入口
├── controller/                      # 控制器层
│   ├── LanguageController.java      # 语言控制器
│   ├── LocaleController.java        # 区域控制器
│   ├── TranslationController.java   # 翻译控制器
│   ├── ResourceController.java      # 资源控制器
│   └── CulturalAdaptationController.java # 文化适配控制器
├── entity/                          # 实体类
│   ├── Language.java                # 语言实体
│   ├── Locale.java                  # 区域实体
│   ├── Translation.java             # 翻译实体
│   ├── LocalizationResource.java    # 本地化资源实体
│   ├── CulturalAdaptation.java      # 文化适配实体
│   └── UserLocale.java              # 用户区域实体
├── repository/                      # 数据访问层
│   ├── LanguageRepository.java      # 语言数据访问
│   ├── LocaleRepository.java        # 区域数据访问
│   ├── TranslationRepository.java   # 翻译数据访问
│   ├── LocalizationResourceRepository.java # 本地化资源数据访问
│   ├── CulturalAdaptationRepository.java # 文化适配数据访问
│   └── UserLocaleRepository.java    # 用户区域数据访问
└── service/                         # 业务逻辑层
    ├── LanguageService.java         # 语言服务
    ├── LocaleService.java           # 区域服务
    ├── TranslationService.java      # 翻译服务
    ├── ResourceService.java         # 资源服务
    └── CulturalAdaptationService.java # 文化适配服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8119/actuator/health