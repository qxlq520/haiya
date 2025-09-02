@echo off
setlocal

echo 开始测试数据库连接...

REM 数据库连接信息
set DB_HOST=127.0.0.1
set DB_PORT=3306
set DB_USER=root
set DB_PASS=qq9291923

REM 测试主数据库连接
echo 测试主数据库连接...
mysql -h%DB_HOST% -P%DB_PORT% -u%DB_USER% -p%DB_PASS% -e "SELECT '数据库连接成功';" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo ✓ 主数据库连接成功
    
    REM 显示已创建的数据库
    echo 已创建的haiya数据库:
    mysql -h%DB_HOST% -P%DB_PORT% -u%DB_USER% -p%DB_PASS% -e "SHOW DATABASES LIKE 'haiya_%%';" 2>nul
) else (
    echo ✗ 主数据库连接失败
)

echo 数据库连接测试完成。
pause