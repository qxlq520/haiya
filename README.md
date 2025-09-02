haiya-backend/
├── config-server/                 # 配置服务器
├── infrastructure-deploy/         # 基础设施部署模块
├── infrastructure-logging/        # 基础设施日志模块
├── infrastructure-monitoring/     # 基础设施监控模块
├── lib-effects/                   # 特效库
├── lib-videoengine/               # 视频引擎库
├── performance-optimizer/         # 性能优化器
├── security-encryption/           # 安全加密模块
├── service-accessibility/         # 无障碍功能服务
├── service-ad/                    # 广告服务
├── service-ad-analytics/          # 广告分析服务
├── service-analytics/             # 分析服务
├── service-ar-vr/                 # AR/VR服务
├── service-article/               # 文章服务
├── service-audio/                 # 音频服务
├── service-bi/                    # 商业智能服务
├── service-camera/                # 相机服务
├── service-campaign/              # 活动服务
├── service-cart/                  # 购物车服务
├── service-comment/               # 评论服务
├── service-content-security/      # 内容安全服务
├── service-copyright/             # 版权服务
├── service-creator-tools/         # 创作者工具服务
├── service-dashboard/             # 仪表板服务
├── service-data-collector/        # 数据收集服务
├── service-data-warehouse/        # 数据仓库服务
├── service-database-sharding/     # 数据库分片服务
├── service-distributed-transaction/ # 分布式事务服务
├── service-ecommerce/             # 电商服务
├── service-editor/                # 编辑器服务
├── service-effect/                # 特效服务
├── service-feature-engineering/   # 特征工程服务
├── service-follow/                # 关注服务
├── service-gateway/               # 网关服务
├── service-gift/                  # 礼物服务
├── service-group/                 # 群组服务
├── service-im/                    # 即时通讯服务
├── service-like/                  # 点赞服务
├── service-live/                  # 直播服务
├── service-live-interaction/      # 直播互动服务
├── service-localization/          # 本地化服务
├── service-logistics/             # 物流服务
├── service-media-processing/      # 媒体处理服务
├── service-mesh/                  # 服务网格
├── service-message/               # 消息服务
├── service-minor-protection/      # 未成年人保护服务
├── service-ml-platform/           # 机器学习平台服务
├── service-moderation/            # 内容审核服务
├── service-notification/          # 通知服务
├── service-order/                 # 订单服务
├── service-payment/               # 支付服务
├── service-product/               # 产品服务
├── service-qanda/                 # 问答服务
├── service-real-time-processing/  # 实时处理服务
├── service-recommend/             # 推荐服务
├── service-registry/              # 服务注册中心
├── service-search/                # 搜索服务
├── service-storage/               # 存储服务
├── service-story/                 # 故事服务
├── service-supply-chain/          # 供应链服务
├── service-topic/                 # 话题服务
├── service-transcoder/            # 转码服务
├── service-translation/           # 翻译服务
├── service-user/                  # 用户服务
├── service-video/                 # 视频服务
├── service-virtual-tryon/         # 虚拟试穿服务
├── test-automation/               # 自动化测试模块
```

  核心服务说明

 # 1. 基础设施服务
- **config-server**: 配置管理服务器
- **service-registry**: 服务注册与发现中心
- **service-gateway**: API网关
- **infrastructure-logging**: 统一日志管理
- **infrastructure-monitoring**: 系统监控
- **infrastructure-deploy**: 部署工具

 # 2. 用户与社交服务
- **service-user**: 用户管理
- **service-follow**: 关注系统
- **service-like**: 点赞功能
- **service-comment**: 评论系统
- **service-message**: 消息系统
- **service-im**: 即时通讯
- **service-group**: 群组功能
- **service-topic**: 话题功能

 # 3. 内容创作与处理服务
- **service-camera**: 相机功能
- **service-editor**: 视频编辑器
- **service-effect**: 特效处理
- **service-audio**: 音频处理
- **service-media-processing**: 媒体处理
- **service-transcoder**: 视频转码
- **service-story**: 故事功能

 # 4. 直播服务
- **service-live**: 直播核心功能
- **service-gift**: 礼物系统
- **service-live-interaction**: 直播互动

 # 5. 电商服务
- **service-ecommerce**: 电商核心
- **service-product**: 商品管理
- **service-cart**: 购物车
- **service-order**: 订单管理
- **service-payment**: 支付系统
- **service-logistics**: 物流系统

 # 6. 数据分析与推荐
- **service-analytics**: 数据分析
- **service-recommend**: 推荐系统
- **service-bi**: 商业智能
- **service-dashboard**: 数据仪表板
- **service-feature-engineering**: 特征工程
- **service-data-collector**: 数据收集
- **service-data-warehouse**: 数据仓库
- **service-real-time-processing**: 实时处理

 # 7. 广告与营销
- **service-ad**: 广告系统
- **service-ad-analytics**: 广告分析
- **service-campaign**: 营销活动

 # 8. 安全与合规
- **service-content-security**: 内容安全
- **service-moderation**: 内容审核
- **service-copyright**: 版权保护
- **service-minor-protection**: 未成年人保护

 # 9. 创作者服务
- **service-creator-tools**: 创作者工具
- **service-ml-platform**: 机器学习平台

 # 10. 搜索与发现
- **service-search**: 搜索服务
- **service-qanda**: 问答服务

 # 11. 国际化服务
- **service-localization**: 本地化服务
- **service-translation**: 翻译服务

 # 12. 基础技术组件
- **service-storage**: 存储服务
- **service-database-sharding**: 数据库分片
- **service-distributed-transaction**: 分布式事务
- **service-mesh**: 服务网格

  技术栈

- **核心框架**: Spring Boot 2.6.3, Spring Cloud 2021.0.1
- **安全框架**: Spring Security
- **数据库**: H2 (开发), MySQL (生产)
- **ORM**: JPA/Hibernate
- **API文档**: Swagger
- **消息队列**: RabbitMQ
- **缓存**: Redis
- **服务发现**: Eureka
- **配置管理**: Spring Cloud Config
- **网关**: Spring Cloud Gateway
- **监控**: Spring Boot Admin
- **测试**: JUnit 5, Mockito

  开发环境要求

- Java 11+
- Maven 3.6+
- Docker (可选，用于运行依赖服务)

  快速开始

1. 克隆项目
2. 导入IDE
3. 运行服务注册中心: `cd service-registry && mvn spring-boot:run`
4. 运行配置服务器: `cd config-server && mvn spring-boot:run`
5. 运行网关服务: `cd service-gateway && mvn spring-boot:run`
6. 运行其他需要的服务

  测试

使用JUnit 5编写测试用例，每个服务模块都有对应的测试代码。

  部署

支持Docker容器化部署，每个服务都可以独立部署和扩展。

  一键部署

使用以下脚本可一键部署后端服务:

Windows系统:
```bash
.\run-all.bat
```

Linux/macOS系统:
```bash
./run-all.sh
```

停止所有服务:

Windows系统:
```bash
.\stop-all.bat
```

Linux/macOS系统:
```bash
./stop-all.sh
```