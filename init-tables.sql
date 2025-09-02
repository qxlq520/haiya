-- 海牙平台表结构初始化脚本
-- 用于为已创建的数据库添加表结构

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

CREATE TABLE IF NOT EXISTS user_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    nickname VARCHAR(100),
    bio TEXT,
    gender ENUM('male', 'female', 'other'),
    birthday BIGINT,
    location VARCHAR(255),
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
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
CREATE INDEX idx_users_phone ON users(phone_number);
CREATE INDEX idx_user_profiles_user ON user_profiles(user_id);
CREATE INDEX idx_user_experiences_user ON user_experiences(user_id);

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
    status ENUM('draft', 'published', 'private', 'deleted') DEFAULT 'draft',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS video_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at BIGINT
);

CREATE TABLE IF NOT EXISTS video_tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_id BIGINT NOT NULL,
    tag VARCHAR(50) NOT NULL,
    created_at BIGINT,
    FOREIGN KEY (video_id) REFERENCES videos(id) ON DELETE CASCADE
);

CREATE INDEX idx_videos_user ON videos(user_id);
CREATE INDEX idx_videos_created ON videos(created_at);
CREATE INDEX idx_videos_status ON videos(status);
CREATE INDEX idx_video_tags_video ON video_tags(video_id);
CREATE INDEX idx_video_tags_tag ON video_tags(tag);

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
    status ENUM('active', 'deleted') DEFAULT 'active',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_comments_target ON comments(target_id, target_type);
CREATE INDEX idx_comments_user ON comments(user_id);
CREATE INDEX idx_comments_parent ON comments(parent_id);
CREATE INDEX idx_comments_status ON comments(status);

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

CREATE TABLE IF NOT EXISTS conversations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_id BIGINT NOT NULL,
    user2_id BIGINT NOT NULL,
    last_message_id BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_messages_sender ON messages(sender_id);
CREATE INDEX idx_messages_receiver ON messages(receiver_id);
CREATE INDEX idx_messages_created ON messages(created_at);
CREATE INDEX idx_conversations_user1 ON conversations(user1_id);
CREATE INDEX idx_conversations_user2 ON conversations(user2_id);

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

CREATE TABLE IF NOT EXISTS live_room_participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    join_time BIGINT,
    leave_time BIGINT,
    FOREIGN KEY (room_id) REFERENCES live_rooms(id) ON DELETE CASCADE
);

CREATE INDEX idx_live_rooms_host ON live_rooms(host_id);
CREATE INDEX idx_live_rooms_status ON live_rooms(status);
CREATE INDEX idx_live_room_participants_room ON live_room_participants(room_id);
CREATE INDEX idx_live_room_participants_user ON live_room_participants(user_id);

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

CREATE TABLE IF NOT EXISTS recommendations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_type ENUM('video', 'article', 'product') NOT NULL,
    score DOUBLE DEFAULT 0.0,
    created_at BIGINT
);

CREATE INDEX idx_user_preferences_user ON user_preferences(user_id);
CREATE INDEX idx_recommendations_user ON recommendations(user_id);
CREATE INDEX idx_recommendations_target ON recommendations(target_id, target_type);

-- 选择搜索数据库并创建表
USE haiya_search;

CREATE TABLE IF NOT EXISTS search_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    keyword VARCHAR(255) NOT NULL,
    created_at BIGINT
);

CREATE TABLE IF NOT EXISTS search_index (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    target_id BIGINT NOT NULL,
    target_type ENUM('video', 'article', 'product', 'user') NOT NULL,
    title VARCHAR(255),
    content TEXT,
    tags TEXT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_search_history_user ON search_history(user_id);
CREATE INDEX idx_search_history_keyword ON search_history(keyword);
CREATE INDEX idx_search_index_target ON search_index(target_id, target_type);
CREATE INDEX idx_search_index_title ON search_index(title);

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

CREATE TABLE IF NOT EXISTS payment_methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type ENUM('credit_card', 'debit_card', 'paypal', 'alipay', 'wechat') NOT NULL,
    provider VARCHAR(100),
    account_last_four VARCHAR(4),
    is_default BOOLEAN DEFAULT FALSE,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_payments_order ON payments(order_id);
CREATE INDEX idx_payments_user ON payments(user_id);
CREATE INDEX idx_payments_status ON payments(status);
CREATE INDEX idx_payment_methods_user ON payment_methods(user_id);

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
    status ENUM('active', 'inactive', 'deleted') DEFAULT 'active',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    created_at BIGINT
);

CREATE TABLE IF NOT EXISTS product_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT NOT NULL,
    review TEXT,
    created_at BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_categories_parent ON categories(parent_id);
CREATE INDEX idx_product_reviews_product ON product_reviews(product_id);

-- 选择广告服务数据库并创建表
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

CREATE TABLE IF NOT EXISTS ad_campaigns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    budget DECIMAL(12, 2),
    start_time BIGINT,
    end_time BIGINT,
    status ENUM('draft', 'active', 'paused', 'completed') DEFAULT 'draft',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_advertisements_position ON advertisements(position);
CREATE INDEX idx_advertisements_active ON advertisements(is_active);
CREATE INDEX idx_ad_campaigns_status ON ad_campaigns(status);

-- 选择文章服务数据库并创建表
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

CREATE TABLE IF NOT EXISTS article_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at BIGINT
);

CREATE INDEX idx_articles_author ON articles(author_id);
CREATE INDEX idx_articles_status ON articles(status);

-- 选择订单服务数据库并创建表
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
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_order_items_order ON order_items(order_id);

-- 选择产品服务数据库并创建表
USE haiya_product;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    category_id BIGINT,
    image_url VARCHAR(500),
    status ENUM('active', 'inactive', 'deleted') DEFAULT 'active',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS product_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    created_at BIGINT
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_product_categories_parent ON product_categories(parent_id);

-- 显示所有数据库中的表
SHOW TABLES FROM haiya_user;
SHOW TABLES FROM haiya_video;
SHOW TABLES FROM haiya_comment;
SHOW TABLES FROM haiya_like;
SHOW TABLES FROM haiya_follow;
SHOW TABLES FROM haiya_message;
SHOW TABLES FROM haiya_live;
SHOW TABLES FROM haiya_recommend;
SHOW TABLES FROM haiya_search;
SHOW TABLES FROM haiya_payment;
SHOW TABLES FROM haiya_ecommerce;
SHOW TABLES FROM haiya_ad;
SHOW TABLES FROM haiya_article;
SHOW TABLES FROM haiya_order;
SHOW TABLES FROM haiya_product;