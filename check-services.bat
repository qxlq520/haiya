@echo off

echo Checking HaiYa Platform Services Status...
echo ----------------------------------------

:: Check Service Registry (port 8761)
netstat -an | findstr ":8761 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Service Registry ^(port 8761^)
) else (
    echo [STOPPED] Service Registry ^(port 8761^)
)

:: Check Config Server (port 8888)
netstat -an | findstr ":8888 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Config Server ^(port 8888^)
) else (
    echo [STOPPED] Config Server ^(port 8888^)
)

:: Check API Gateway (port 8080)
netstat -an | findstr ":8080 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] API Gateway ^(port 8080^)
) else (
    echo [STOPPED] API Gateway ^(port 8080^)
)

:: Check User Service (port 8081)
netstat -an | findstr ":8081 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] User Service ^(port 8081^)
) else (
    echo [STOPPED] User Service ^(port 8081^)
)

:: Check Video Service (port 8082)
netstat -an | findstr ":8082 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Video Service ^(port 8082^)
) else (
    echo [STOPPED] Video Service ^(port 8082^)
)

:: Check Live Service (port 8083)
netstat -an | findstr ":8083 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Live Service ^(port 8083^)
) else (
    echo [STOPPED] Live Service ^(port 8083^)
)

:: Check Recommend Service (port 8084)
netstat -an | findstr ":8084 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Recommend Service ^(port 8084^)
) else (
    echo [STOPPED] Recommend Service ^(port 8084^)
)

:: Check Search Service (port 8085)
netstat -an | findstr ":8085 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Search Service ^(port 8085^)
) else (
    echo [STOPPED] Search Service ^(port 8085^)
)

:: Check Payment Service (port 8086)
netstat -an | findstr ":8086 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Payment Service ^(port 8086^)
) else (
    echo [STOPPED] Payment Service ^(port 8086^)
)

:: Check Message Service (port 8087)
netstat -an | findstr ":8087 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Message Service ^(port 8087^)
) else (
    echo [STOPPED] Message Service ^(port 8087^)
)

:: Check Notification Service (port 8088)
netstat -an | findstr ":8088 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Notification Service ^(port 8088^)
) else (
    echo [STOPPED] Notification Service ^(port 8088^)
)

:: Check Analytics Service (port 8089)
netstat -an | findstr ":8089 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Analytics Service ^(port 8089^)
) else (
    echo [STOPPED] Analytics Service ^(port 8089^)
)

:: Check Comment Service (port 8090)
netstat -an | findstr ":8090 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Comment Service ^(port 8090^)
) else (
    echo [STOPPED] Comment Service ^(port 8090^)
)

:: Check Like Service (port 8091)
netstat -an | findstr ":8091 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Like Service ^(port 8091^)
) else (
    echo [STOPPED] Like Service ^(port 8091^)
)

:: Check Follow Service (port 8092)
netstat -an | findstr ":8092 " | findstr "LISTENING" >nul
if %errorlevel% == 0 (
    echo [RUNNING] Follow Service ^(port 8092^)
) else (
    echo [STOPPED] Follow Service ^(port 8092^)
)

echo.
echo Status check completed.
echo.
pause