-- 创建海牙平台所需的数据库和用户
-- 数据库初始化脚本

-- 创建用户服务数据库
CREATE DATABASE IF NOT EXISTS haiya_user CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建视频服务数据库
CREATE DATABASE IF NOT EXISTS haiya_video CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建推荐服务数据库
CREATE DATABASE IF NOT EXISTS haiya_recommend CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建haiya用户并授权
-- 注意：在生产环境中，请使用更安全的密码
CREATE USER IF NOT EXISTS 'haiya'@'localhost' IDENTIFIED BY 'haiya123';

-- 授权用户访问所有相关数据库
GRANT ALL PRIVILEGES ON haiya_user.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_video.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_recommend.* TO 'haiya'@'localhost';

-- 授权root用户访问所有相关数据库
GRANT ALL PRIVILEGES ON haiya_user.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON haiya_video.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON haiya_recommend.* TO 'root'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;

-- 显示创建的数据库
SHOW DATABASES LIKE 'haiya_%';