# 基于官方OpenJDK镜像
FROM openjdk:11-jre-slim

# 设置维护者信息
LABEL maintainer="haiya-team"

# 设置工作目录
WORKDIR /app

# 复制JAR文件到容器中
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# 暴露端口
EXPOSE 8080

# 设置JVM参数和启动应用
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]