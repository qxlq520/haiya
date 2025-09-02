-- 为所有服务数据库创建表结构

-- 选择用户数据库并创建表
USE haiya_user;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    avatar_url VARCHAR(255),
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS user_experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    company VARCHAR(100),
    start_date BIGINT,
    end_date BIGINT,
    description TEXT,
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_phone_number ON users(phone_number);
CREATE INDEX idx_user_experiences_user_id ON user_experiences(user_id);

-- 创建管理员表
CREATE TABLE IF NOT EXISTS admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(20),
    last_login_time BIGINT,
    status VARCHAR(20) NOT NULL DEFAULT 'active',
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL,
    INDEX idx_admins_username (username),
    INDEX idx_admins_email (email),
    INDEX idx_admins_phone_number (phone_number),
    INDEX idx_admins_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

-- 选择视频数据库并创建表
USE haiya_video;

CREATE TABLE IF NOT EXISTS videos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    url VARCHAR(500) NOT NULL,
    thumbnail_url VARCHAR(500),
    duration INT,
    view_count BIGINT DEFAULT 0,
    like_count BIGINT DEFAULT 0,
    comment_count BIGINT DEFAULT 0,
    user_id BIGINT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS video_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_videos_user_id ON videos(user_id);
CREATE INDEX idx_videos_created_at ON videos(created_at);

-- 选择评论数据库并创建表
USE haiya_comment;

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_type ENUM('video', 'article', 'post') NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    reply_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_comments_target ON comments(target_id, target_type);
CREATE INDEX idx_comments_user_id ON comments(user_id);
CREATE INDEX idx_comments_parent_id ON comments(parent_id);

-- 选择点赞数据库并创建表
USE haiya_like;

CREATE TABLE IF NOT EXISTS likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_type ENUM('video', 'comment', 'article', 'post') NOT NULL,
    created_at BIGINT
);

CREATE UNIQUE INDEX idx_likes_user_target ON likes(user_id, target_id, target_type);
CREATE INDEX idx_likes_target ON likes(target_id, target_type);

-- 选择关注数据库并创建表
USE haiya_follow;

CREATE TABLE IF NOT EXISTS follows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    created_at BIGINT
);

CREATE UNIQUE INDEX idx_follows_unique ON follows(follower_id, following_id);
CREATE INDEX idx_follows_follower ON follows(follower_id);
CREATE INDEX idx_follows_following ON follows(following_id);

-- 选择消息数据库并创建表
USE haiya_message;

CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at BIGINT
);

CREATE INDEX idx_messages_sender ON messages(sender_id);
CREATE INDEX idx_messages_receiver ON messages(receiver_id);
CREATE INDEX idx_messages_created_at ON messages(created_at);

-- 选择直播数据库并创建表
USE haiya_live;

CREATE TABLE IF NOT EXISTS live_rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    host_id BIGINT NOT NULL,
    stream_url VARCHAR(500),
    viewer_count INT DEFAULT 0,
    status ENUM('pending', 'live', 'ended') DEFAULT 'pending',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_live_rooms_host ON live_rooms(host_id);
CREATE INDEX idx_live_rooms_status ON live_rooms(status);

-- 选择推荐数据库并创建表
USE haiya_recommend;

CREATE TABLE IF NOT EXISTS user_preferences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category VARCHAR(100),
    weight DOUBLE DEFAULT 1.0,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_user_preferences_user_id ON user_preferences(user_id);

-- 选择搜索数据库并创建表
USE haiya_search;

CREATE TABLE IF NOT EXISTS search_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    keyword VARCHAR(255) NOT NULL,
    created_at BIGINT
);

CREATE INDEX idx_search_history_user_id ON search_history(user_id);
CREATE INDEX idx_search_history_keyword ON search_history(keyword);

-- 选择支付数据库并创建表
USE haiya_payment;

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'success', 'failed', 'refunded') DEFAULT 'pending',
    payment_method VARCHAR(50),
    transaction_id VARCHAR(255),
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_payments_order_id ON payments(order_id);
CREATE INDEX idx_payments_user_id ON payments(user_id);
CREATE INDEX idx_payments_status ON payments(status);

-- 选择电商数据库并创建表
USE haiya_ecommerce;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    category_id BIGINT,
    image_url VARCHAR(500),
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    created_at BIGINT
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_categories_parent ON categories(parent_id);

-- 选择广告数据库并创建表
USE haiya_ad;

CREATE TABLE IF NOT EXISTS advertisements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image_url VARCHAR(500),
    target_url VARCHAR(500),
    position VARCHAR(100),
    start_time BIGINT,
    end_time BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_advertisements_position ON advertisements(position);
CREATE INDEX idx_advertisements_active ON advertisements(is_active);

-- 选择订单数据库并创建表
USE haiya_order;

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'paid', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    shipping_address TEXT,
    payment_id BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);

-- 选择文章数据库并创建表
USE haiya_article;

CREATE TABLE IF NOT EXISTS articles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author_id BIGINT NOT NULL,
    view_count BIGINT DEFAULT 0,
    like_count BIGINT DEFAULT 0,
    comment_count BIGINT DEFAULT 0,
    status ENUM('draft', 'published', 'archived') DEFAULT 'draft',
    published_at BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_articles_author ON articles(author_id);
CREATE INDEX idx_articles_status ON articles(status);

-- 显示所有创建的表
SHOW TABLES;