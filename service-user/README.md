# 用户服务 (service-user)

  服务概述

用户服务是海牙平台的核心基础服务，负责用户注册、登录、个人信息管理、账户安全、权限控制等功能，为整个平台提供用户身份认证和授权服务。

  功能特性

1. 用户注册与登录
2. 用户信息管理
3. 账户安全与隐私设置
4. 权限管理与角色控制
5. 用户状态管理
6. 用户搜索与发现
7. 用户数据分析
8. 第三方登录集成

  技术栈

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Security
- Spring Data JPA
- H2/MySQL 数据库
- Redis 缓存
- JWT Token 认证
- Eureka 服务注册与发现

  API 接口

 # 用户认证接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/auth/register` | POST | 用户注册 |
| `/api/auth/login` | POST | 用户登录 |
| `/api/auth/logout` | POST | 用户登出 |
| `/api/auth/refresh` | POST | 刷新访问令牌 |
| `/api/auth/password/forgot` | POST | 忘记密码 |
| `/api/auth/password/reset` | POST | 重置密码 |
| `/api/auth/verify-email` | POST | 验证邮箱 |

 # 用户信息接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/users/profile` | GET | 获取当前用户信息 |
| `/api/users/profile` | PUT | 更新用户信息 |
| `/api/users/{id}` | GET | 获取指定用户信息 |
| `/api/users/avatar` | POST | 上传用户头像 |
| `/api/users/cover` | POST | 上传用户封面 |
| `/api/users/preferences` | GET | 获取用户偏好设置 |
| `/api/users/preferences` | PUT | 更新用户偏好设置 |

 # 用户管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/users` | GET | 获取用户列表 |
| `/api/users/{id}/status` | PUT | 更新用户状态 |
| `/api/users/{id}/role` | PUT | 更新用户角色 |
| `/api/users/search` | GET | 搜索用户 |
| `/api/users/blocked` | GET | 获取被屏蔽用户列表 |
| `/api/users/block/{id}` | POST | 屏蔽用户 |
| `/api/users/unblock/{id}` | POST | 解除屏蔽用户 |

 # 社交关系接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/users/following` | GET | 获取关注列表 |
| `/api/users/followers` | GET | 获取粉丝列表 |
| `/api/users/follow/{id}` | POST | 关注用户 |
| `/api/users/unfollow/{id}` | POST | 取消关注 |
| `/api/users/friends` | GET | 获取好友列表 |
| `/api/users/friend-requests` | GET | 获取好友请求 |
| `/api/users/friend-request/{id}` | POST | 发送好友请求 |

  数据库设计

 # users 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| username | VARCHAR(50) | 用户名 |
| email | VARCHAR(100) | 邮箱 |
| phone | VARCHAR(20) | 手机号 |
| password | VARCHAR(255) | 密码（加密存储） |
| nickname | VARCHAR(100) | 昵称 |
| avatar_url | VARCHAR(500) | 头像URL |
| cover_url | VARCHAR(500) | 封面URL |
| bio | TEXT | 个人简介 |
| gender | VARCHAR(10) | 性别 |
| birthday | DATE | 生日 |
| location | VARCHAR(100) | 地理位置 |
| website | VARCHAR(255) | 个人网站 |
| status | VARCHAR(20) | 用户状态（ACTIVE, INACTIVE, SUSPENDED, DELETED） |
| role | VARCHAR(20) | 用户角色（USER, CREATOR, ADMIN, SUPER_ADMIN） |
| verified | BOOLEAN | 是否已验证 |
| follower_count | INT | 粉丝数 |
| following_count | INT | 关注数 |
| video_count | INT | 视频数 |
| last_login_at | BIGINT | 最后登录时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # user_profiles 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| real_name | VARCHAR(100) | 真实姓名 |
| id_card | VARCHAR(50) | 身份证号 |
| education | VARCHAR(50) | 教育背景 |
| occupation | VARCHAR(100) | 职业 |
| interests | TEXT | 兴趣爱好（JSON格式） |
| social_links | TEXT | 社交链接（JSON格式） |
| privacy_settings | TEXT | 隐私设置（JSON格式） |
| notification_settings | TEXT | 通知设置（JSON格式） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # user_sessions 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| token | VARCHAR(500) | 访问令牌 |
| refresh_token | VARCHAR(500) | 刷新令牌 |
| device_info | TEXT | 设备信息 |
| ip_address | VARCHAR(50) | IP地址 |
| expires_at | BIGINT | 过期时间 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # user_follows 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| follower_id | BIGINT | 关注者ID |
| following_id | BIGINT | 被关注者ID |
| status | VARCHAR(20) | 关注状态（FOLLOWING, UNFOLLOWED, BLOCKED） |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

 # user_blocks 表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| blocker_id | BIGINT | 屏蔽者ID |
| blocked_id | BIGINT | 被屏蔽者ID |
| reason | VARCHAR(255) | 屏蔽原因 |
| created_at | BIGINT | 创建时间 |
| updated_at | BIGINT | 更新时间 |

  Redis 缓存设计

 # 用户信息缓存
```
user:info:{userId} -> Hash结构存储用户基本信息
user:profile:{userId} -> Hash结构存储用户详细信息
```

 # 会话缓存
```
user:session:{token} -> String结构存储会话信息
user:sessions:{userId} -> Set结构存储用户所有会话
```

 # 社交关系缓存
```
user:following:{userId} -> Set结构存储用户关注列表
user:followers:{userId} -> Set结构存储用户粉丝列表
user:isFollowing:{userId}:{targetUserId} -> String结构存储关注关系
```

  JWT Token 配置

 # Token 结构
```json
{
  "sub": "用户ID",
  "username": "用户名",
  "role": "用户角色",
  "iat": "签发时间",
  "exp": "过期时间"
}
```

 # 安全配置
```yaml
jwt:
  secret: your_jwt_secret_key
  expiration: 86400  # 24小时
  refresh-expiration: 604800  # 7天
```

  Spring Security 配置

 # 安全策略
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated();
    }
}
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
  port: 8080

spring:
  application:
    name: service-user
    
  datasource:
    url: jdbc:mysql://localhost:3306/haiya_user
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
    
  jpa:
    hibernate:
      ddl-auto: update
      
  redis:
    host: localhost
    port: 6379

jwt:
  secret: haiya_user_secret_key
  expiration: 86400
```

 # 启动服务

```bash
cd service-user
mvn spring-boot:run
```

或

```bash
cd service-user
mvn clean package
java -jar target/service-user-1.0.0.jar
```

  服务依赖

- service-registry (服务注册中心)
- service-gateway (网关服务)
- service-storage (存储服务)

  开发指南

 # 项目结构

```
src/main/java/com/haiya/user
├── UserServiceApplication.java  # 应用入口
├── config/                      # 配置类
│   ├── SecurityConfig.java      # 安全配置
│   └── JwtConfig.java           # JWT配置
├── controller/                  # 控制器层
│   ├── AuthController.java      # 认证控制器
│   ├── UserController.java      # 用户控制器
│   └── SocialController.java    # 社交控制器
├── entity/                      # 实体类
│   ├── User.java                # 用户实体
│   ├── UserProfile.java         # 用户资料实体
│   ├── UserSession.java         # 用户会话实体
│   ├── UserFollow.java          # 用户关注实体
│   └── UserBlock.java           # 用户屏蔽实体
├── repository/                  # 数据访问层
│   ├── UserRepository.java      # 用户数据访问
│   ├── UserProfileRepository.java # 用户资料数据访问
│   ├── UserSessionRepository.java # 用户会话数据访问
│   ├── UserFollowRepository.java # 用户关注数据访问
│   └── UserBlockRepository.java # 用户屏蔽数据访问
└── service/                     # 业务逻辑层
    ├── AuthService.java         # 认证服务
    ├── UserService.java         # 用户服务
    └── SocialService.java       # 社交服务
```

  测试

运行单元测试：

```bash
mvn test
```

  监控与健康检查

服务健康检查端点：

```
GET http://localhost:8080/actuator/health