@echo off
chcp 65001 > nul
setlocal

REM 数据库初始化批处理脚本
REM 用于在Windows环境下创建所有服务的数据库

echo 开始初始化所有服务数据库...

REM MySQL连接信息
set MYSQL_HOST=127.0.0.1
set MYSQL_PORT=3306
set MYSQL_USER=root
set MYSQL_PASS=qq9291923

REM 执行SQL脚本
mysql -h%MYSQL_HOST% -P%MYSQL_PORT% -u%MYSQL_USER% -p%MYSQL_PASS% < init-all-databases.sql

if %ERRORLEVEL% EQU 0 (
    echo 所有服务数据库初始化成功!
) else (
    echo 数据库初始化失败，请检查错误信息!
    exit /b 1
)

pause