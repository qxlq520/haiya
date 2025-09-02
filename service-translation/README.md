# 翻译服务 (service-translation)

  服务概述

翻译服务是海牙平台的多语言支持核心服务，提供文本翻译、语音翻译、图片翻译等多种翻译功能，支持平台全球化发展和多语言用户需求。

  功能特性

1. 文本翻译
2. 语音翻译
3. 图片翻译
4. 实时翻译
5. 翻译记忆库
6. 术语库管理
7. 翻译质量评估
8. 多引擎翻译支持

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Google Translate API
- 百度翻译API
- 讯飞语音识别与合成

  API 接口

 # 文本翻译接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/translation/text` | POST | 文本翻译 |
| `/api/translation/text/batch` | POST | 批量文本翻译 |
| `/api/translation/text/detect` | POST | 语言检测 |
| `/api/translation/text/supported-languages` | GET | 获取支持的语言列表 |
| `/api/translation/text/transliterate` | POST | 文字音译 |

 # 语音翻译接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/translation/speech` | POST | 语音翻译 |
| `/api/translation/speech/to-text` | POST | 语音转文本 |
| `/api/translation/speech/to-speech` | POST | 文本转语音 |
| `/api/translation/speech/supported-voices` | GET | 获取支持的语音列表 |
| `/api/translation/speech/languages` | GET | 获取支持的语音语言列表 |

 # 图片翻译接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/translation/image` | POST | 图片翻译 |
| `/api/translation/image/ocr` | POST | 图片OCR识别 |
| `/api/translation/image/text-extract` | POST | 图片文字提取 |
| `/api/translation/image/languages` | GET | 获取支持的图片翻译语言列表 |

 # 翻译记忆库接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/translation/memory` | POST | 创建翻译记忆 |
| `/api/translation/memory` | GET | 获取翻译记忆列表 |
| `/api/translation/memory/{id}` | GET | 获取翻译记忆详情 |
| `/api/translation/memory/{id}` | PUT | 更新翻译记忆 |
| `/api/translation/memory/{id}/delete` | DELETE | 删除翻译记忆 |
| `/api/translation/memory/search` | POST | 搜索翻译记忆 |

 # 术语库接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/translation/terminology` | POST | 创建术语 |
| `/api/translation/terminology` | GET | 获取术语列表 |
| `/api/translation/terminology/{id}` | GET | 获取术语详情 |
| `/api/translation/terminology/{id}` | PUT | 更新术语 |
| `/api/translation/terminology/{id}/delete` | DELETE | 删除术语 |
| `/api/translation/terminology/import` | POST | 导入术语库 |
| `/api/translation/terminology/export` | GET | 导出术语库 |

  数据库设计

 # translation_records 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| source_text | TEXT | 源文本 |
| translated_text | TEXT | 翻译文本 |
| source_language | VARCHAR(10) | 源语言 |
| target_language | VARCHAR(10) | 目标语言 |
| engine_used | VARCHAR(20) | 使用的翻译引擎 |
| quality_score | DECIMAL(3,2) | 质量评分 |
| user_id | BIGINT | 用户ID |
| context | VARCHAR(100) | 翻译上下文 |
| created_at | BIGINT | 创建时间 |

 # translation_memories 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| source_text | TEXT | 源文本 |
| translated_text | TEXT | 翻译文本 |
| source_language | VARCHAR(10) | 源语言 |
| target_language | VARCHAR(10) | 目标语言 |
| domain | VARCHAR(50) | 领域 |
| usage_count | INT | 使用次数 |
| quality_score | DECIMAL(3,2) | 质量评分 |
| owner_id | BIGINT | 所有者ID |
| is_public | BOOLEAN | 是否公开 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # translation_terminologies 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| term | VARCHAR(255) | 术语 |
| definition | TEXT | 定义 |
| source_language | VARCHAR(10) | 源语言 |
| target_language | VARCHAR(10) | 目标语言 |
| part_of_speech | VARCHAR(20) | 词性 |
| domain | VARCHAR(50) | 领域 |
| usage_examples | TEXT | 使用示例（JSON格式） |
| owner_id | BIGINT | 所有者ID |
| is_public | BOOLEAN | 是否公开 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # speech_translations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| audio_url | VARCHAR(500) | 音频URL |
| source_language | VARCHAR(10) | 源语言 |
| target_language | VARCHAR(10) | 目标语言 |
| transcription | TEXT | 语音转录 |
| translation | TEXT | 翻译结果 |
| engine_used | VARCHAR(20) | 使用的翻译引擎 |
| duration | INT | 音频时长（秒） |
| user_id | BIGINT | 用户ID |
| created_at | BIGINT | 创建时间 |

 # image_translations 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| image_url | VARCHAR(500) | 图片URL |
| source_language | VARCHAR(10) | 源语言 |
| target_language | VARCHAR(10) | 目标语言 |
| extracted_text | TEXT | 提取的文本 |
| translation | TEXT | 翻译结果 |
| ocr_result | TEXT | OCR识别结果（JSON格式） |
| engine_used | VARCHAR(20) | 使用的翻译引擎 |
| user_id | BIGINT | 用户ID |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 翻译缓存
```
translation:cache:{sourceLang}:{targetLang}:{sourceText} -> String结构存储翻译结果
translation:cache:ttl -> 86400 (1天)
```

 # 翻译记忆库缓存
```
translation:memory:{sourceLang}:{targetLang}:{sourceText} -> String结构存储匹配的翻译记忆
```

 # 语言支持缓存
```
translation:supported-languages -> List结构存储支持的语言列表
translation:supported-voices -> List结构存储支持的语音列表
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Google Translate API 密钥
- 百度翻译API 密钥
- 讯飞语音识别与合成服务密钥

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8136

spring:
  application:
    name: service-translation
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_translation
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379

translation:
  engines:
    google:
      enabled: true
      api-key: your_google_api_key
    baidu:
      enabled: true
      app-id: your_baidu_app_id
      secret-key: your_baidu_secret_key
    iflytek:
      enabled: true
      app-id: your_iflytek_app_id
      api-key: your_iflytek_api_key
```

 # 启动服务

```bash
cd service-translation
mvn spring-boot:run
```

或

```bash
cd service-translation
mvn clean package
java -jar target/service-translation-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-storage (存储服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/translation
├── TranslationApplication.java     # 应用入口
├── config/                         # 配置类
│   └── TranslationConfig.java      # 翻译配置
├── controller/                     # 控制器层
│   ├── TextController.java         # 文本翻译控制器
│   ├── SpeechController.java       # 语音翻译控制器
│   ├── ImageController.java        # 图片翻译控制器
│   ├── MemoryController.java       # 翻译记忆控制器
│   └── TerminologyController.java  # 术语库控制器
├── entity/                         # 实体类
│   ├── TranslationRecord.java      # 翻译记录实体
│   ├── TranslationMemory.java      # 翻译记忆实体
│   ├── Terminology.java            # 术语实体
│   ├── SpeechTranslation.java      # 语音翻译实体
│   └── ImageTranslation.java       # 图片翻译实体
├── repository/                     # 数据访问层
│   ├── TranslationRecordRepository.java # 翻译记录数据访问
│   ├── TranslationMemoryRepository.java # 翻译记忆数据访问
│   ├── TerminologyRepository.java  # 术语数据访问
│   ├── SpeechTranslationRepository.java # 语音翻译数据访问
│   └── ImageTranslationRepository.java # 图片翻译数据访问
└── service/                        # 业务逻辑层
    ├── TextTranslationService.java # 文本翻译服务
    ├── SpeechTranslationService.java # 语音翻译服务
    ├── ImageTranslationService.java # 图片翻译服务
    ├── MemoryService.java          # 翻译记忆服务
    └── TerminologyService.java     # 术语库服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8136/actuator/health