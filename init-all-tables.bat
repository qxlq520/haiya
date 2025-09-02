@echo off
setlocal

echo 开始初始化所有数据库表...

REM 数据库连接信息
set DB_HOST=localhost
set DB_PORT=3306
set DB_USER=root
set DB_PASS=qq9291923

REM 执行管理员表初始化
echo 正在初始化管理员表...
mysql -h%DB_HOST% -P%DB_PORT% -u%DB_USER% -p%DB_PASS% < init-admin-table.sql

if %ERRORLEVEL% EQU 0 (
    echo ✓ 管理员表初始化成功
) else (
    echo ✗ 管理员表初始化失败
)

echo 数据库表初始化完成。
pause