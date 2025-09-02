-- 创建海牙平台所有服务所需的数据库
-- 完整数据库初始化脚本

-- 创建所有服务数据库
CREATE DATABASE IF NOT EXISTS haiya_user CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_video CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_comment CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_like CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_follow CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_gateway CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_registry CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_message CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_live CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_recommend CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_search CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_payment CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_ad CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_ad_analytics CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_analytics CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_ar_vr CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_article CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_audio CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_bi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_camera CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_campaign CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_cart CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_content_security CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_copyright CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_creator_tools CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_dashboard CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_data_collector CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_data_warehouse CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_database_sharding CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_distributed_transaction CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_editor CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_effect CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_feature_engineering CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_gift CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_group CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_im CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_live_interaction CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_localization CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_logistics CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_media_processing CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_mesh CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_minor_protection CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_ml_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_moderation CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_notification CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_order CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_product CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_qanda CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_real_time_processing CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_storage CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_story CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_supply_chain CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_topic CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_transcoder CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_translation CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS haiya_virtual_tryon CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 先创建用户再授权
-- 注意：在生产环境中，请使用更安全的密码
CREATE USER IF NOT EXISTS 'haiya'@'%' IDENTIFIED BY 'haiya123';
CREATE USER IF NOT EXISTS 'haiya'@'localhost' IDENTIFIED BY 'haiya123';

-- 授权haiya用户访问所有相关数据库
GRANT ALL PRIVILEGES ON haiya_user.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_video.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_comment.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_like.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_follow.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_gateway.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_registry.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_message.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_live.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_recommend.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_search.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_payment.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_ecommerce.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_ad.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_ad_analytics.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_analytics.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_ar_vr.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_article.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_audio.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_bi.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_camera.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_campaign.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_cart.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_content_security.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_copyright.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_creator_tools.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_dashboard.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_data_collector.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_data_warehouse.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_database_sharding.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_distributed_transaction.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_editor.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_effect.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_feature_engineering.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_gift.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_group.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_im.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_live_interaction.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_localization.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_logistics.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_media_processing.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_mesh.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_minor_protection.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_ml_platform.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_moderation.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_notification.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_order.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_product.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_qanda.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_real_time_processing.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_storage.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_story.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_supply_chain.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_topic.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_transcoder.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_translation.* TO 'haiya'@'%';
GRANT ALL PRIVILEGES ON haiya_virtual_tryon.* TO 'haiya'@'%';

GRANT ALL PRIVILEGES ON haiya_user.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_video.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_comment.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_like.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_follow.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_gateway.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_registry.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_message.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_live.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_recommend.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_search.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_payment.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_ecommerce.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_ad.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_ad_analytics.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_analytics.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_ar_vr.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_article.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_audio.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_bi.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_camera.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_campaign.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_cart.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_content_security.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_copyright.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_creator_tools.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_dashboard.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_data_collector.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_data_warehouse.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_database_sharding.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_distributed_transaction.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_editor.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_effect.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_feature_engineering.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_gift.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_group.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_im.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_live_interaction.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_localization.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_logistics.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_media_processing.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_mesh.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_minor_protection.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_ml_platform.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_moderation.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_notification.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_order.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_product.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_qanda.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_real_time_processing.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_storage.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_story.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_supply_chain.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_topic.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_transcoder.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_translation.* TO 'haiya'@'localhost';
GRANT ALL PRIVILEGES ON haiya_virtual_tryon.* TO 'haiya'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;

-- 为各个数据库创建表结构
-- 选择用户数据库并创建表
USE haiya_user;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS user_experiences;
DROP TABLE IF EXISTS user_profiles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    avatar_url VARCHAR(255),
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE user_profiles (
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

CREATE TABLE user_experiences (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS video_tags;
DROP TABLE IF EXISTS video_categories;
DROP TABLE IF EXISTS videos;

CREATE TABLE videos (
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

CREATE TABLE video_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at BIGINT
);

CREATE TABLE video_tags (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS comments;

CREATE TABLE comments (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS likes;

CREATE TABLE likes (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS follows;

CREATE TABLE follows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    created_at BIGINT
);

CREATE UNIQUE INDEX idx_follows_unique ON follows(follower_id, following_id);
CREATE INDEX idx_follows_follower ON follows(follower_id);
CREATE INDEX idx_follows_following ON follows(following_id);

-- 选择网关服务数据库（通常不需要表）
USE haiya_gateway;

-- 选择注册中心数据库（通常不需要表）
USE haiya_registry;

-- 选择消息数据库并创建表
USE haiya_message;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS conversations;
DROP TABLE IF EXISTS messages;

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at BIGINT
);

CREATE TABLE conversations (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS live_room_participants;
DROP TABLE IF EXISTS live_rooms;

CREATE TABLE live_rooms (
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

CREATE TABLE live_room_participants (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS recommendations;
DROP TABLE IF EXISTS user_preferences;

CREATE TABLE user_preferences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category VARCHAR(100),
    weight DOUBLE DEFAULT 1.0,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE recommendations (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS search_index;
DROP TABLE IF EXISTS search_history;

CREATE TABLE search_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    keyword VARCHAR(255) NOT NULL,
    created_at BIGINT
);

CREATE TABLE search_index (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS payment_methods;
DROP TABLE IF EXISTS payments;

CREATE TABLE payments (
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

CREATE TABLE payment_methods (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS product_reviews;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;

CREATE TABLE products (
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

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    created_at BIGINT
);

CREATE TABLE product_reviews (
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

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS ad_campaigns;
DROP TABLE IF EXISTS advertisements;

CREATE TABLE advertisements (
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

CREATE TABLE ad_campaigns (
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

-- 选择广告分析服务数据库并创建表
USE haiya_ad_analytics;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS ad_clicks;
DROP TABLE IF EXISTS ad_impressions;

CREATE TABLE ad_impressions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ad_id BIGINT NOT NULL,
    user_id BIGINT,
    timestamp BIGINT
);

CREATE TABLE ad_clicks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ad_id BIGINT NOT NULL,
    user_id BIGINT,
    timestamp BIGINT
);

CREATE INDEX idx_ad_impressions_ad ON ad_impressions(ad_id);
CREATE INDEX idx_ad_clicks_ad ON ad_clicks(ad_id);

-- 选择分析服务数据库并创建表
USE haiya_analytics;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS system_metrics;
DROP TABLE IF EXISTS user_activities;

CREATE TABLE user_activities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    activity_type VARCHAR(100) NOT NULL,
    target_id BIGINT,
    target_type VARCHAR(50),
    metadata JSON,
    created_at BIGINT
);

CREATE TABLE system_metrics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    metric_name VARCHAR(100) NOT NULL,
    value DOUBLE NOT NULL,
    recorded_at BIGINT
);

CREATE INDEX idx_user_activities_user ON user_activities(user_id);
CREATE INDEX idx_user_activities_type ON user_activities(activity_type);
CREATE INDEX idx_system_metrics_name ON system_metrics(metric_name);

-- 选择AR/VR服务数据库并创建表
USE haiya_ar_vr;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS ar_vr_assets;

CREATE TABLE ar_vr_assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type ENUM('model', 'texture', 'shader', 'effect') NOT NULL,
    url VARCHAR(500) NOT NULL,
    thumbnail_url VARCHAR(500),
    user_id BIGINT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_ar_vr_assets_user ON ar_vr_assets(user_id);
CREATE INDEX idx_ar_vr_assets_type ON ar_vr_assets(type);

-- 选择文章服务数据库并创建表
USE haiya_article;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS article_categories;
DROP TABLE IF EXISTS articles;

CREATE TABLE articles (
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

CREATE TABLE article_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at BIGINT
);

CREATE INDEX idx_articles_author ON articles(author_id);
CREATE INDEX idx_articles_status ON articles(status);

-- 选择音频服务数据库并创建表
USE haiya_audio;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS audio_files;

CREATE TABLE audio_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    url VARCHAR(500) NOT NULL,
    duration INT,
    user_id BIGINT NOT NULL,
    status ENUM('draft', 'published', 'private') DEFAULT 'draft',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_audio_files_user ON audio_files(user_id);
CREATE INDEX idx_audio_files_status ON audio_files(status);

-- 选择商业智能服务数据库并创建表
USE haiya_bi;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS bi_reports;

CREATE TABLE bi_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    query_text TEXT,
    created_by BIGINT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_bi_reports_creator ON bi_reports(created_by);

-- 选择相机服务数据库并创建表
USE haiya_camera;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS camera_photos;

CREATE TABLE camera_photos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    url VARCHAR(500) NOT NULL,
    user_id BIGINT NOT NULL,
    width INT,
    height INT,
    captured_at BIGINT,
    created_at BIGINT
);

CREATE INDEX idx_camera_photos_user ON camera_photos(user_id);

-- 选择活动服务数据库并创建表
USE haiya_campaign;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS campaigns;

CREATE TABLE campaigns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_time BIGINT,
    end_time BIGINT,
    status ENUM('draft', 'active', 'completed', 'cancelled') DEFAULT 'draft',
    created_by BIGINT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_campaigns_status ON campaigns(status);
CREATE INDEX idx_campaigns_creator ON campaigns(created_by);

-- 选择购物车服务数据库并创建表
USE haiya_cart;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;

CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    added_at BIGINT,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE
);

CREATE INDEX idx_carts_user ON carts(user_id);
CREATE INDEX idx_cart_items_cart ON cart_items(cart_id);

-- 选择内容安全服务数据库并创建表
USE haiya_content_security;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS content_reviews;

CREATE TABLE content_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content_id BIGINT NOT NULL,
    content_type ENUM('video', 'article', 'image', 'comment') NOT NULL,
    reviewer_id BIGINT,
    `status` ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    reason TEXT,
    reviewed_at BIGINT,
    created_at BIGINT
);

CREATE INDEX idx_content_reviews_content ON content_reviews(content_id, content_type);
CREATE INDEX idx_content_reviews_status ON content_reviews(`status`);

-- 选择版权服务数据库并创建表
USE haiya_copyright;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS copyright_claims;

CREATE TABLE copyright_claims (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content_id BIGINT NOT NULL,
    content_type ENUM('video', 'article', 'image', 'audio') NOT NULL,
    claimant_id BIGINT NOT NULL,
    description TEXT NOT NULL,
    status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_copyright_claims_content ON copyright_claims(content_id, content_type);
CREATE INDEX idx_copyright_claims_claimant ON copyright_claims(claimant_id);

-- 选择创作者工具服务数据库并创建表
USE haiya_creator_tools;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS creator_projects;

CREATE TABLE creator_projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    user_id BIGINT NOT NULL,
    status ENUM('draft', 'in_progress', 'completed') DEFAULT 'draft',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_creator_projects_user ON creator_projects(user_id);
CREATE INDEX idx_creator_projects_status ON creator_projects(status);

-- 选择仪表板服务数据库并创建表
USE haiya_dashboard;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS dashboard_widgets;

CREATE TABLE dashboard_widgets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    config JSON,
    position INT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_dashboard_widgets_user ON dashboard_widgets(user_id);

-- 选择数据收集服务数据库并创建表
USE haiya_data_collector;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS collected_data;

CREATE TABLE collected_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(100) NOT NULL,
    data_type VARCHAR(100) NOT NULL,
    content JSON,
    collected_at BIGINT
);

CREATE INDEX idx_collected_data_source ON collected_data(source);
CREATE INDEX idx_collected_data_type ON collected_data(data_type);

-- 选择数据仓库服务数据库并创建表
USE haiya_data_warehouse;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS warehouse_tables;

CREATE TABLE warehouse_tables (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    schema_definition JSON,
    row_count BIGINT DEFAULT 0,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_warehouse_tables_name ON warehouse_tables(name);

-- 选择数据库分片服务数据库并创建表
USE haiya_database_sharding;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS sharding_configs;

CREATE TABLE sharding_configs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(100) NOT NULL,
    sharding_strategy VARCHAR(50) NOT NULL,
    shard_count INT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

-- 选择分布式事务服务数据库并创建表
USE haiya_distributed_transaction;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS transaction_participants;
DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions (
    id VARCHAR(64) PRIMARY KEY,
    `status` ENUM('pending', 'committed', 'rolled_back') NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE transaction_participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(64) NOT NULL,
    service_name VARCHAR(100) NOT NULL,
    `status` ENUM('prepared', 'committed', 'rolled_back') NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE
);

CREATE INDEX idx_transaction_participants_tx ON transaction_participants(transaction_id);

-- 选择编辑器服务数据库并创建表
USE haiya_editor;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS editor_documents;

CREATE TABLE editor_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content JSON,
    user_id BIGINT NOT NULL,
    `status` ENUM('draft', 'published') DEFAULT 'draft',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_editor_documents_user ON editor_documents(user_id);
CREATE INDEX idx_editor_documents_status ON editor_documents(`status`);

-- 选择特效服务数据库并创建表
USE haiya_effect;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS effects;

CREATE TABLE effects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    type ENUM('filter', 'transition', 'overlay') NOT NULL,
    url VARCHAR(500),
    thumbnail_url VARCHAR(500),
    user_id BIGINT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_effects_user ON effects(user_id);
CREATE INDEX idx_effects_type ON effects(type);

-- 选择特征工程服务数据库并创建表
USE haiya_feature_engineering;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS features;

CREATE TABLE features (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    data_type VARCHAR(50) NOT NULL,
    config JSON,
    created_at BIGINT,
    updated_at BIGINT
);

-- 选择礼物服务数据库并创建表
USE haiya_gift;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS gift_transactions;
DROP TABLE IF EXISTS gifts;

CREATE TABLE gifts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE gift_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    gift_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_at BIGINT,
    FOREIGN KEY (gift_id) REFERENCES gifts(id)
);

CREATE INDEX idx_gifts_active ON gifts(is_active);
CREATE INDEX idx_gift_transactions_sender ON gift_transactions(sender_id);
CREATE INDEX idx_gift_transactions_receiver ON gift_transactions(receiver_id);

-- 选择群组服务数据库并创建表
USE haiya_group;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS group_members;
DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    owner_id BIGINT NOT NULL,
    avatar_url VARCHAR(500),
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE group_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role ENUM('member', 'admin', 'owner') DEFAULT 'member',
    joined_at BIGINT,
    FOREIGN KEY (group_id) REFERENCES `groups`(id) ON DELETE CASCADE
);

CREATE INDEX idx_groups_owner ON `groups`(owner_id);
CREATE INDEX idx_group_members_group ON group_members(group_id);
CREATE INDEX idx_group_members_user ON group_members(user_id);

-- 选择即时通讯服务数据库并创建表
USE haiya_im;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS im_messages;

CREATE TABLE im_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_type ENUM('user', 'group') NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    content_type ENUM('text', 'image', 'audio', 'video') DEFAULT 'text',
    is_read BOOLEAN DEFAULT FALSE,
    created_at BIGINT
);

CREATE INDEX idx_im_messages_receiver ON im_messages(receiver_type, receiver_id);
CREATE INDEX idx_im_messages_sender ON im_messages(sender_id);

-- 选择直播互动服务数据库并创建表
USE haiya_live_interaction;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS live_interactions;

CREATE TABLE live_interactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    interaction_type ENUM('like', 'comment', 'gift') NOT NULL,
    content TEXT,
    created_at BIGINT
);

CREATE INDEX idx_live_interactions_room ON live_interactions(room_id);
CREATE INDEX idx_live_interactions_user ON live_interactions(user_id);

-- 选择本地化服务数据库并创建表
USE haiya_localization;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS translations;

CREATE TABLE translations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(255) NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    text TEXT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE UNIQUE INDEX idx_translations_key_lang ON translations(`key`, language_code);

-- 选择物流服务数据库并创建表
USE haiya_logistics;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS shipments;

CREATE TABLE shipments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    tracking_number VARCHAR(100),
    carrier VARCHAR(100),
    `status` ENUM('pending', 'shipped', 'in_transit', 'delivered', 'returned') DEFAULT 'pending',
    estimated_delivery BIGINT,
    shipped_at BIGINT,
    delivered_at BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_shipments_order ON shipments(order_id);
CREATE INDEX idx_shipments_tracking ON shipments(tracking_number);
CREATE INDEX idx_shipments_status ON shipments(`status`);

-- 选择媒体处理服务数据库并创建表
USE haiya_media_processing;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS media_processing_jobs;

CREATE TABLE media_processing_jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_url VARCHAR(500) NOT NULL,
    target_url VARCHAR(500),
    operation_type ENUM('resize', 'compress', 'convert', 'watermark') NOT NULL,
    `status` ENUM('pending', 'processing', 'completed', 'failed') DEFAULT 'pending',
    progress INT DEFAULT 0,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_media_processing_jobs_status ON media_processing_jobs(`status`);

-- 选择服务网格数据库并创建表
USE haiya_mesh;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS service_endpoints;

CREATE TABLE service_endpoints (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    endpoint_url VARCHAR(500) NOT NULL,
    `status` ENUM('active', 'inactive') DEFAULT 'active',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_service_endpoints_name ON service_endpoints(service_name);

-- 选择未成年人保护服务数据库并创建表
USE haiya_minor_protection;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS minor_protections;

CREATE TABLE minor_protections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    guardian_id BIGINT,
    `status` ENUM('enabled', 'disabled') DEFAULT 'enabled',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_minor_protections_user ON minor_protections(user_id);

-- 选择机器学习平台服务数据库并创建表
USE haiya_ml_platform;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS ml_models;

CREATE TABLE ml_models (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    version VARCHAR(50) NOT NULL,
    url VARCHAR(500),
    `status` ENUM('draft', 'active', 'deprecated') DEFAULT 'draft',
    created_by BIGINT NOT NULL,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_ml_models_creator ON ml_models(created_by);
CREATE INDEX idx_ml_models_status ON ml_models(`status`);

-- 选择内容审核服务数据库并创建表
USE haiya_moderation;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS moderation_reports;

CREATE TABLE moderation_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id BIGINT NOT NULL,
    content_id BIGINT NOT NULL,
    content_type ENUM('video', 'article', 'comment', 'image') NOT NULL,
    reason VARCHAR(100) NOT NULL,
    description TEXT,
    `status` ENUM('pending', 'reviewed', 'dismissed') DEFAULT 'pending',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_moderation_reports_content ON moderation_reports(content_id, content_type);
CREATE INDEX idx_moderation_reports_status ON moderation_reports(`status`);

-- 选择通知服务数据库并创建表
USE haiya_notification;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS notifications;

CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    type ENUM('system', 'social', 'transactional') NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at BIGINT
);

CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_read ON notifications(is_read);

-- 选择订单服务数据库并创建表
USE haiya_order;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    `status` ENUM('pending', 'paid', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    shipping_address TEXT,
    payment_id BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(`status`);

-- 选择产品服务数据库并创建表
USE haiya_product;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS product_categories;
DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    category_id BIGINT,
    image_url VARCHAR(500),
    `status` ENUM('active', 'inactive', 'deleted') DEFAULT 'active',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE product_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    created_at BIGINT
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_status ON products(`status`);
CREATE INDEX idx_product_categories_parent ON product_categories(parent_id);

-- 选择问答服务数据库并创建表
USE haiya_qanda;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;

CREATE TABLE questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    answer_count INT DEFAULT 0,
    view_count BIGINT DEFAULT 0,
    `status` ENUM('open', 'answered', 'closed') DEFAULT 'open',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE answers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    is_accepted BOOLEAN DEFAULT FALSE,
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE INDEX idx_questions_user ON questions(user_id);
CREATE INDEX idx_questions_status ON questions(`status`);

-- 选择实时处理服务数据库并创建表
USE haiya_real_time_processing;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS real_time_streams;

CREATE TABLE real_time_streams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    source_url VARCHAR(500) NOT NULL,
    `status` ENUM('active', 'inactive', 'error') DEFAULT 'inactive',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_real_time_streams_status ON real_time_streams(`status`);

-- 选择存储服务数据库并创建表
USE haiya_storage;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS storage_files;

CREATE TABLE storage_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(500) NOT NULL,
    size BIGINT,
    mime_type VARCHAR(100),
    user_id BIGINT NOT NULL,
    created_at BIGINT
);

CREATE INDEX idx_storage_files_user ON storage_files(user_id);

-- 选择故事服务数据库并创建表
USE haiya_story;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS stories;

CREATE TABLE stories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    media_url VARCHAR(500) NOT NULL,
    media_type ENUM('image', 'video') NOT NULL,
    caption VARCHAR(255),
    expires_at BIGINT,
    created_at BIGINT
);

CREATE INDEX idx_stories_user ON stories(user_id);
CREATE INDEX idx_stories_expires ON stories(expires_at);

-- 选择供应链服务数据库并创建表
USE haiya_supply_chain;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS supply_chain_items;
DROP TABLE IF EXISTS suppliers;

CREATE TABLE suppliers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info TEXT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE supply_chain_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    lead_time INT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_supply_chain_items_product ON supply_chain_items(product_id);
CREATE INDEX idx_supply_chain_items_supplier ON supply_chain_items(supplier_id);

-- 选择话题服务数据库并创建表
USE haiya_topic;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS topic_followers;
DROP TABLE IF EXISTS topics;

CREATE TABLE topics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    cover_image_url VARCHAR(500),
    follower_count BIGINT DEFAULT 0,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE topic_followers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at BIGINT,
    FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE
);

CREATE INDEX idx_topics_name ON topics(name);
CREATE INDEX idx_topic_followers_topic ON topic_followers(topic_id);
CREATE INDEX idx_topic_followers_user ON topic_followers(user_id);

-- 选择转码服务数据库并创建表
USE haiya_transcoder;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS transcode_jobs;

CREATE TABLE transcode_jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_url VARCHAR(500) NOT NULL,
    target_formats JSON,
    status ENUM('pending', 'processing', 'completed', 'failed') DEFAULT 'pending',
    progress INT DEFAULT 0,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_transcode_jobs_status ON transcode_jobs(status);

-- 选择翻译服务数据库并创建表
USE haiya_translation;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS translation_jobs;

CREATE TABLE translation_jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_text TEXT NOT NULL,
    source_language VARCHAR(10) NOT NULL,
    target_language VARCHAR(10) NOT NULL,
    translated_text TEXT,
    status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
    created_at BIGINT,
    updated_at BIGINT
);

CREATE INDEX idx_translation_jobs_languages ON translation_jobs(source_language, target_language);
CREATE INDEX idx_translation_jobs_status ON translation_jobs(status);

-- 选择虚拟试穿服务数据库并创建表
USE haiya_virtual_tryon;

-- 删除已存在的表和索引（如果存在）
DROP TABLE IF EXISTS tryon_sessions;

CREATE TABLE tryon_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    image_url VARCHAR(500),
    result_url VARCHAR(500),
    created_at BIGINT
);

CREATE INDEX idx_tryon_sessions_user ON tryon_sessions(user_id);
CREATE INDEX idx_tryon_sessions_product ON tryon_sessions(product_id);

-- 显示创建的数据库
SHOW DATABASES LIKE 'haiya_%';