-- 此文件已弃用，请使用 init-all-tables.sql 来初始化所有表结构
-- This file is deprecated, please use init-all-tables.sql to initialize all table structures

-- 选择用户数据库
USE haiya_user;

-- 管理员表结构已合并到 init-all-tables.sql 中
-- The admin table structure has been merged into init-all-tables.sql

-- 插入默认管理员账户
-- 密码: admin123 (实际应该使用加密后的密码)
INSERT INTO admins (username, password, role, email, phone_number, status, created_at, updated_at) VALUES
('admin', '$2a$10$w9ziPxn7EJq45sXfClp4QO/KvK47LhBL7f4a4wE/YqM888d1GdD5G', 'super_admin', 'admin@haiya.com', '13800000000', 'active', UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000),
('moderator', '$2a$10$w9ziPxn7EJq45sXfClp4QO/KvK47LhBL7f4a4wE/YqM888d1GdD5G', 'admin', 'moderator@haiya.com', '13800000001', 'active', UNIX_TIMESTAMP()*1000, UNIX_TIMESTAMP()*1000);

-- 创建管理员操作日志表
CREATE TABLE IF NOT EXISTS admin_operation_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id BIGINT NOT NULL,
    operation VARCHAR(100) NOT NULL,
    description TEXT,
    ip_address VARCHAR(50),
    user_agent TEXT,
    created_at BIGINT NOT NULL,
    INDEX idx_admin_operation_logs_admin_id (admin_id),
    INDEX idx_admin_operation_logs_operation (operation),
    INDEX idx_admin_operation_logs_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;