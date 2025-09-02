@echo off
echo Starting HaiYa Platform Services...

echo Starting Service Registry...
cd service-registry
start "Service Registry" /min cmd /c "mvn spring-boot:run 2>&1 && echo Service Registry started successfully || echo Service Registry failed to start"
cd ..
timeout /t 30 /nobreak >nul

echo Starting Config Server...
cd config-server
start "Config Server" /min cmd /c "mvn spring-boot:run 2>&1 && echo Config Server started successfully || echo Config Server failed to start"
cd ..
timeout /t 30 /nobreak >nul

echo Starting Service Gateway...
cd service-gateway
start "Service Gateway" /min cmd /c "mvn spring-boot:run 2>&1 && echo Service Gateway started successfully || echo Service Gateway failed to start"
cd ..
timeout /t 30 /nobreak >nul

echo Starting User Service...
cd service-user
start "User Service" /min cmd /c "mvn spring-boot:run 2>&1 && echo User Service started successfully || echo User Service failed to start"
cd ..
timeout /t 30 /nobreak >nul

echo Starting Storage Service...
cd service-storage
start "Storage Service" /min cmd /c "mvn spring-boot:run 2>&1 && echo Storage Service started successfully || echo Storage Service failed to start"
cd ..
timeout /t 30 /nobreak >nul

echo Starting Live Service...
cd service-live
start "Live Service" /min cmd /c "mvn spring-boot:run 2>&1 && echo Live Service started successfully || echo Live Service failed to start"
cd ..
timeout /t 30 /nobreak >nul

echo Starting Search Service...
cd service-search
start "Search Service" /min cmd /c "mvn spring-boot:run 2>&1 && echo Search Service started successfully || echo Search Service failed to start"
cd ..
timeout /t 30 /nobreak >nul

echo All services started.
pause