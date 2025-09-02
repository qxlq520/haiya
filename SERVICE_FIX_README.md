# 服务启动问题修复说明

## 问题分析

在检查用户服务和视频服务启动失败的问题时，我们发现两个服务都出现了相同的错误：

```
Caused by: java.lang.IllegalStateException: Cannot load driver class: com.mysql.cj.jdbc.Driver
```

这个错误表明Spring Boot应用无法加载MySQL JDBC驱动类。通过检查[pom.xml](file:///e:/root/haiya/haiya-backend/service-video/pom.xml)文件，我们发现问题出在MySQL驱动依赖的配置上。

## 问题原因

在原始的[pom.xml](file:///e:/root/haiya/haiya-backend/service-video/pom.xml)文件中，MySQL驱动依赖被配置为：

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

`runtime`作用域意味着该依赖仅在运行时可用，但在应用启动过程中需要在类路径中加载驱动类，这导致了启动失败。

## 解决方案

我们将MySQL驱动依赖的作用域从`runtime`修改为`compile`：

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>compile</scope>
</dependency>
```

这样可以确保MySQL驱动在编译时和运行时都可用，解决了启动时无法加载驱动类的问题。

## 修改的文件

1. [haiya-backend/service-user/pom.xml](file:///e:/root/haiya/haiya-backend/service-user/pom.xml)
2. [haiya-backend/service-video/pom.xml](file:///e:/root/haiya/haiya-backend/service-video/pom.xml)

## 验证修复

修改完成后，重新构建并启动服务：

```bash
# 构建用户服务
cd haiya-backend/service-user
mvn clean install

# 启动用户服务
mvn spring-boot:run

# 构建视频服务
cd ../service-video
mvn clean install

# 启动视频服务
mvn spring-boot:run