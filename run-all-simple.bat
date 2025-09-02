@echo off
echo Starting HaiYa backend services...

echo Starting service registry...
start "service-registry" /min mvn spring-boot:run -f service-registry/pom.xml
timeout /t 30 /nobreak >nul

echo Starting config server...
start "config-server" /min mvn spring-boot:run -f config-server/pom.xml
timeout /t 30 /nobreak >nul

echo Starting gateway...
start "gateway-service" /min mvn spring-boot:run -f service-gateway/pom.xml
timeout /t 30 /nobreak >nul

echo Starting user service...
start "user-service" /min mvn spring-boot:run -f service-user/pom.xml
timeout /t 30 /nobreak >nul

echo Starting video service...
start "video-service" /min mvn spring-boot:run -f service-video/pom.xml
timeout /t 30 /nobreak >nul

echo Starting live service...
start "live-service" /min mvn spring-boot:run -f service-live/pom.xml
timeout /t 30 /nobreak >nul

echo Starting recommend service...
start "recommend-service" /min mvn spring-boot:run -f service-recommend/pom.xml
timeout /t 30 /nobreak >nul

echo Starting search service...
start "search-service" /min mvn spring-boot:run -f service-search/pom.xml
timeout /t 30 /nobreak >nul

echo Starting payment service...
start "payment-service" /min mvn spring-boot:run -f service-payment/pom.xml
timeout /t 30 /nobreak >nul

echo Starting message service...
start "message-service" /min mvn spring-boot:run -f service-message/pom.xml
timeout /t 30 /nobreak >nul

echo All services started! Check each service's log for status.