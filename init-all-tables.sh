#!/bin/bash

# 数据库初始化脚本
# 用于创建所有服务所需的表结构

echo "开始初始化所有数据库表..."

# 数据库连接信息
DB_HOST="localhost"
DB_PORT="3306"
DB_USER="root"
DB_PASS="qq9291923"

# 执行管理员表初始化
echo "正在初始化管理员表..."
mysql -h${DB_HOST} -P${DB_PORT} -u${DB_USER} -p${DB_PASS} < init-admin-table.sql

if [ $? -eq 0 ]; then
    echo "✓ 管理员表初始化成功"
else
    echo "✗ 管理员表初始化失败"
fi

# 可以在这里添加更多表的初始化脚本
# 例如:
# echo "正在初始化用户表..."
# mysql -h${DB_HOST} -P${DB_PORT} -u${DB_USER} -p${DB_PASS} < init-user-table.sql

echo "数据库表初始化完成。"