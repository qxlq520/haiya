#!/bin/bash

# 视频服务修复脚本

echo "开始修复视频服务..."

# 检查当前目录
if [ ! -f "pom.xml" ]; then
    echo "错误: 未找到 pom.xml 文件，请在 service-video 目录中运行此脚本"
    exit 1
fi

# 检查MySQL驱动依赖
echo "检查MySQL驱动依赖..."
if grep -q "mysql-connector-java" pom.xml; then
    if grep -q "<version>8.0.28</version>" pom.xml; then
        echo "✓ MySQL驱动依赖配置正确"
    else
        echo "⚠ 检测到MySQL驱动版本未明确指定，建议添加版本号"
        echo "  请确保 pom.xml 中的 mysql-connector-java 依赖包含:"
        echo "  <version>8.0.28</version>"
    fi
else
    echo "✗ 未找到MySQL驱动依赖，请添加以下依赖到 pom.xml:"
    echo "
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.28</version>
        <scope>compile</scope>
    </dependency>"
fi

# 清理并重新构建
echo "清理并重新构建项目..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "✓ 项目构建成功"
    
    # 启动服务
    echo "启动视频服务..."
    mvn spring-boot:run
else
    echo "✗ 项目构建失败，请检查错误信息"
    exit 1
fi