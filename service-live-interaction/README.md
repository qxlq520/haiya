# 直播互动服务 (service-live-interaction)

  服务概述

直播互动服务是海牙平台直播功能的核心组件，负责处理直播间内的实时互动功能，包括弹幕、礼物、连麦、投票、抽奖等互动形式。

  功能特性

1. 实时弹幕系统
2. 礼物连击系统
3. 连麦互动功能
4. 直播间投票功能
5. 直播间抽奖功能
6. 直播间禁言管理
7. 直播间管理员设置
8. 实时互动数据统计

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Data JPA
- H2/MySQL 数据库
- Eureka 服务注册与发现
- Redis 缓存
- Netty 网络通信框架
- WebSocket 实时通信协议

  API 接口

 # 弹幕管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/live-interactions/barrages` | POST | 发送弹幕 |
| `/api/live-interactions/barrages/{liveId}` | GET | 获取直播间弹幕列表 |
| `/api/live-interactions/barrages/{id}/delete` | POST | 删除违规弹幕 |
| `/api/live-interactions/barrages/filter-words` | GET | 获取弹幕过滤词列表 |

 # 礼物互动接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/live-interactions/gifts/combo` | POST | 发送连击礼物 |
| `/api/live-interactions/gifts/combo-status` | GET | 获取礼物连击状态 |
| `/api/live-interactions/gifts/top-contributors` | GET | 获取礼物贡献榜 |
| `/api/live-interactions/gifts/effects` | POST | 触发礼物特效 |

 # 连麦管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/live-interactions/co-hosts/apply` | POST | 申请连麦 |
| `/api/live-interactions/co-hosts/{id}/accept` | POST | 接受连麦申请 |
| `/api/live-interactions/co-hosts/{id}/reject` | POST | 拒绝连麦申请 |
| `/api/live-interactions/co-hosts/{id}/end` | POST | 结束连麦 |
| `/api/live-interactions/co-hosts/{liveId}/list` | GET | 获取连麦列表 |

 # 投票管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/live-interactions/polls` | POST | 创建投票 |
| `/api/live-interactions/polls/{id}/vote` | POST | 参与投票 |
| `/api/live-interactions/polls/{id}/results` | GET | 获取投票结果 |
| `/api/live-interactions/polls/{id}/end` | POST | 结束投票 |

 # 抽奖管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/live-interactions/lotteries` | POST | 创建抽奖 |
| `/api/live-interactions/lotteries/{id}/participate` | POST | 参与抽奖 |
| `/api/live-interactions/lotteries/{id}/draw` | POST | 开奖 |
| `/api/live-interactions/lotteries/{id}/winners` | GET | 获取中奖名单 |

  WebSocket 接口

 # 连接地址
```
ws://localhost:8117/live-interaction/ws?liveId={liveId}&token={access_token}
```

 # 客户端发送消息格式
```json
{
  "type": "barrage",
  "data": {
    "content": "Hello World",
    "color": "#FF0000"
  }
}
```

 # 服务端推送消息格式
```json
{
  "type": "barrage",
  "data": {
    "userId": "user_id",
    "userName": "User Name",
    "content": "Hello World",
    "color": "#FF0000",
    "timestamp": 1612345678901
  }
}
```

  数据库设计

 # live_barrages 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| live_id | BIGINT | 直播间ID |
| user_id | BIGINT | 用户ID |
| content | VARCHAR(255) | 弹幕内容 |
| color | VARCHAR(20) | 弹幕颜色 |
| font_size | INT | 字体大小 |
| position | VARCHAR(20) | 弹幕位置（TOP, BOTTOM, NORMAL） |
| status | VARCHAR(20) | 状态（NORMAL, DELETED, BLOCKED） |
| sent_at | BIGINT | 发送时间 |
| created_at | BIGINT | 创建时间 |

 # live_gift_combos 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| live_id | BIGINT | 直播间ID |
| user_id | BIGINT | 用户ID |
| gift_id | BIGINT | 礼物ID |
| combo_count | INT | 连击次数 |
| total_price | DECIMAL(10,2) | 总价 |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| status | VARCHAR(20) | 状态（ACTIVE, COMPLETED, EXPIRED） |
| created_at | BIGINT | 创建时间 |

 # live_co_hosts 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| live_id | BIGINT | 直播间ID |
| host_user_id | BIGINT | 主播ID |
| co_host_user_id | BIGINT | 连麦用户ID |
| apply_message | TEXT | 申请消息 |
| status | VARCHAR(20) | 状态（APPLYING, ACCEPTED, REJECTED, ENDED） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # live_polls 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| live_id | BIGINT | 直播间ID |
| creator_id | BIGINT | 创建者ID |
| title | VARCHAR(255) | 投票标题 |
| options | TEXT | 投票选项（JSON格式） |
| duration | INT | 持续时间（秒） |
| max_selections | INT | 最大选择数 |
| status | VARCHAR(20) | 状态（ACTIVE, ENDED） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # live_lotteries 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| live_id | BIGINT | 直播间ID |
| creator_id | BIGINT | 创建者ID |
| title | VARCHAR(255) | 抽奖标题 |
| description | TEXT | 抽奖描述 |
| prize_details | TEXT | 奖品详情（JSON格式） |
| participant_condition | VARCHAR(50) | 参与条件（ANYONE, FOLLOWER, GIFT_SENDER等） |
| winner_count | INT | 中奖人数 |
| status | VARCHAR(20) | 状态（ACTIVE, DRAWN, CANCELLED） |
| start_time | BIGINT | 开始时间 |
| end_time | BIGINT | 结束时间 |
| draw_time | BIGINT | 开奖时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # live_poll_votes 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| poll_id | BIGINT | 投票ID |
| user_id | BIGINT | 用户ID |
| selected_options | TEXT | 选择的选项（JSON格式） |
| voted_at | BIGINT | 投票时间 |
| created_at | BIGINT | 创建时间 |

 # live_lottery_participants 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| lottery_id | BIGINT | 抽奖ID |
| user_id | BIGINT | 用户ID |
| participated_at | BIGINT | 参与时间 |
| created_at | BIGINT | 创建时间 |

  Redis 缓存设计

 # 直播间互动数据缓存
```
live:interaction:{liveId}:barrages -> List结构存储实时弹幕
live:interaction:{liveId}:online-users -> Set结构存储在线用户
live:interaction:{liveId}:gift-combos -> Hash结构存储礼物连击
```

 # 投票和抽奖缓存
```
live:poll:{pollId}:results -> Hash结构存储投票结果
live:lottery:{lotteryId}:participants -> Set结构存储抽奖参与者
```

  Netty 服务器配置

 # 服务器启动配置
```java
ServerBootstrap bootstrap = new ServerBootstrap();
bootstrap.group(bossGroup, workerGroup)
    .channel(NioServerSocketChannel.class)
    .childHandler(new LiveInteractionChannelInitializer())
    .option(ChannelOption.SO_BACKLOG, 128)
    .childOption(ChannelOption.SO_KEEPALIVE, true);
```

  部署说明

 # 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或 H2 数据库
- Redis 缓存服务
- Netty 4.x 网络通信框架

 # 配置文件

在 `src/main/resources/application.yml` 中配置：

```yaml
server:
  port: 8117

spring:
  application:
    name: service-live-interaction
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_live_interaction
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379

live-interaction:
  netty:
    port: 8118
    boss-threads: 2
    worker-threads: 10
```

 # 启动服务

```bash
cd service-live-interaction
mvn spring-boot:run
```

或

```bash
cd service-live-interaction
mvn clean package
java -jar target/service-live-interaction-1.0.0.jar
```

  服务依赖

- service-user (用户服务)
- service-live (直播服务)
- service-gift (礼物服务)
- service-registry (服务注册中心)
- service-gateway (网关服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/live/interaction
├── LiveInteractionServiceApplication.java  # 应用入口
├── config/                                 # 配置类
│   └── NettyConfig.java                    # Netty配置
├── controller/                             # 控制器层
│   ├── BarrageController.java              # 弹幕控制器
│   ├── GiftInteractionController.java      # 礼物互动控制器
│   ├── CoHostController.java               # 连麦控制器
│   ├── PollController.java                 # 投票控制器
│   └── LotteryController.java              # 抽奖控制器
├── entity/                                 # 实体类
│   ├── LiveBarrage.java                    # 直播弹幕实体
│   ├── GiftCombo.java                      # 礼物连击实体
│   ├── CoHost.java                         # 连麦实体
│   ├── LivePoll.java                       # 直播投票实体
│   ├── LiveLottery.java                    # 直播抽奖实体
│   ├── PollVote.java                       # 投票记录实体
│   └── LotteryParticipant.java             # 抽奖参与者实体
├── repository/                             # 数据访问层
│   ├── LiveBarrageRepository.java          # 直播弹幕数据访问
│   ├── GiftComboRepository.java            # 礼物连击数据访问
│   ├── CoHostRepository.java               # 连麦数据访问
│   ├── LivePollRepository.java             # 直播投票数据访问
│   ├── LiveLotteryRepository.java          # 直播抽奖数据访问
│   ├── PollVoteRepository.java             # 投票记录数据访问
│   └── LotteryParticipantRepository.java   # 抽奖参与者数据访问
├── service/                                # 业务逻辑层
│   ├── BarrageService.java                 # 弹幕服务
│   ├── GiftInteractionService.java         # 礼物互动服务
│   ├── CoHostService.java                  # 连麦服务
│   ├── PollService.java                    # 投票服务
│   └── LotteryService.java                 # 抽奖服务
└── netty/                                  # Netty网络通信层
    ├── LiveInteractionServer.java          # 直播互动服务器
    ├── LiveInteractionChannelInitializer.java # 通道初始化器
    ├── LiveInteractionHandler.java         # 消息处理器
    └── MessageEncoderDecoder.java          # 消息编解码器
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8117/actuator/health