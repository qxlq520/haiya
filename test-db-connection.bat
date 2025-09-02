@echo off
setlocal

echo ================================
echo Database Connection Test Tool
echo ================================

REM Database connection info
set DB_HOST=127.0.0.1
set DB_PORT=3306
set DB_USER=root
set DB_PASS=qq9291923

echo Testing parameters:
echo   Host: %DB_HOST%
echo   Port: %DB_PORT%
echo   User: %DB_USER%
echo.

REM Test 1: Connect using localhost
echo Test 1: Connecting with localhost...
mysql -hlocalhost -P%DB_PORT% -u%DB_USER% -p%DB_PASS% -e "SELECT 'Connection successful' as Result;" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo ^>^> localhost connection test successful
    echo.
    echo Listing all haiya databases:
    mysql -hlocalhost -P%DB_PORT% -u%DB_USER% -p%DB_PASS% -e "SHOW DATABASES LIKE 'haiya_%%';" 2>nul
) else (
    echo ^^ localhost connection test failed
    echo.
)

echo.
REM Test 2: Connect using IP address with quotes
echo Test 2: Connecting with IP address...
mysql -h"%DB_HOST%" -P%DB_PORT% -u%DB_USER% -p%DB_PASS% -e "SELECT 'Connection successful' as Result;" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo ^>^> IP address connection test successful
) else (
    echo ^^ IP address connection test failed
)

echo.
REM Test 3: Check if MySQL service is running
echo Test 3: Checking MySQL service status...
netstat -an | findstr :3306 >nul

if %ERRORLEVEL% EQU 0 (
    echo ^>^> Detected port 3306 is listening
) else (
    echo ^^ Port 3306 is not listening, MySQL service may not be running
)

echo.
echo ================================
echo Test completed
echo ================================
echo.
echo If all tests failed, please check:
echo 1. Is MySQL service running?
echo 2. Is MySQL listening on port 3306?
echo 3. Is firewall blocking the connection?
echo 4. Are username and password correct?
echo.
pause