import java.sql.*;
import java.util.*;

/**
 * 数据库管理服务
 * 实现对所有服务数据库的添加、删除、写入数据功能
 */
public class DatabaseManagementService {
    
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "qq9291923";
    
    // 所有服务列表
    private static final String[] SERVICES = {
        "user", "video", "comment", "like", "follow", "gateway", "registry",
        "message", "live", "recommend", "search", "payment", "ecommerce", "ad",
        "ad-analytics", "analytics", "ar-vr", "article", "audio", "bi", "camera",
        "campaign", "cart", "content-security", "copyright", "creator-tools",
        "dashboard", "data-collector", "data-warehouse", "database-sharding",
        "distributed-transaction", "editor", "effect", "feature-engineering",
        "gift", "group", "im", "live-interaction", "localization", "logistics",
        "media-processing", "mesh", "minor-protection", "ml-platform",
        "moderation", "notification", "order", "product", "qanda",
        "real-time-processing", "storage", "story", "supply-chain", "topic",
        "transcoder", "translation", "virtual-tryon"
    };
    
    public static void main(String[] args) {
        DatabaseManagementService service = new DatabaseManagementService();
        
        try {
            // 1. 初始化所有数据库
            System.out.println("开始初始化所有数据库...");
            service.initAllDatabases();
            System.out.println("所有数据库初始化完成。");
            
            // 2. 创建所有表结构
            System.out.println("开始创建所有表结构...");
            service.createAllTables();
            System.out.println("所有表结构创建完成。");
            
            // 3. 测试写入数据
            System.out.println("开始测试写入数据...");
            service.testDataInsertion();
            System.out.println("数据写入测试完成。");
            
        } catch (Exception e) {
            System.err.println("执行过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 初始化所有服务的数据库
     */
    public void initAllDatabases() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        
        try {
            Statement stmt = conn.createStatement();
            
            // 创建所有数据库
            for (String service : SERVICES) {
                String dbName = "haiya_" + service.replace("-", "_");
                String createDbSQL = String.format(
                    "CREATE DATABASE IF NOT EXISTS %s CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
                    dbName
                );
                stmt.executeUpdate(createDbSQL);
                System.out.println("已创建数据库: " + dbName);
            }
            
            // 创建管理用户（先创建用户再授权）
            try {
                stmt.executeUpdate("CREATE USER IF NOT EXISTS 'haiya'@'%' IDENTIFIED BY 'haiya123'");
                stmt.executeUpdate("CREATE USER IF NOT EXISTS 'haiya'@'localhost' IDENTIFIED BY 'haiya123'");
                System.out.println("已创建用户: haiya");
            } catch (SQLException e) {
                System.out.println("创建用户时出现错误: " + e.getMessage());
            }
            
            // 授权
            for (String service : SERVICES) {
                String dbName = "haiya_" + service.replace("-", "_");
                stmt.executeUpdate(String.format("GRANT ALL PRIVILEGES ON %s.* TO 'haiya'@'%%'", dbName));
                stmt.executeUpdate(String.format("GRANT ALL PRIVILEGES ON %s.* TO 'haiya'@'localhost'", dbName));
            }
            
            stmt.executeUpdate("FLUSH PRIVILEGES");
            System.out.println("权限刷新完成");
            
            stmt.close();
        } finally {
            conn.close();
        }
    }
    
    /**
     * 为所有服务创建表结构
     */
    public void createAllTables() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        
        try {
            Statement stmt = conn.createStatement();
            
            // 为用户服务创建表
            stmt.execute("USE haiya_user");
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, phone_number VARCHAR(20), avatar_url VARCHAR(255), created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS user_experiences (id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, title VARCHAR(100) NOT NULL, company VARCHAR(100), start_date BIGINT, end_date BIGINT, description TEXT, created_at BIGINT, updated_at BIGINT, FOREIGN KEY (user_id) REFERENCES users(id))");
            stmt.execute("CREATE INDEX idx_users_username ON users(username)");
            stmt.execute("CREATE INDEX idx_users_phone_number ON users(phone_number)");
            stmt.execute("CREATE INDEX idx_user_experiences_user_id ON user_experiences(user_id)");
            System.out.println("已为用户服务创建表结构");
            
            // 为视频服务创建表
            stmt.execute("USE haiya_video");
            stmt.execute("CREATE TABLE IF NOT EXISTS videos (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, description TEXT, url VARCHAR(500) NOT NULL, thumbnail_url VARCHAR(500), duration INT, view_count BIGINT DEFAULT 0, like_count BIGINT DEFAULT 0, comment_count BIGINT DEFAULT 0, user_id BIGINT NOT NULL, created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS video_categories (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, description TEXT, created_at BIGINT)");
            stmt.execute("CREATE INDEX idx_videos_user_id ON videos(user_id)");
            stmt.execute("CREATE INDEX idx_videos_created_at ON videos(created_at)");
            System.out.println("已为视频服务创建表结构");
            
            // 为评论服务创建表
            stmt.execute("USE haiya_comment");
            stmt.execute("CREATE TABLE IF NOT EXISTS comments (id BIGINT AUTO_INCREMENT PRIMARY KEY, content TEXT NOT NULL, user_id BIGINT NOT NULL, target_id BIGINT NOT NULL, target_type ENUM('video', 'article', 'post') NOT NULL, parent_id BIGINT DEFAULT NULL, reply_count INT DEFAULT 0, like_count INT DEFAULT 0, created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE INDEX idx_comments_target ON comments(target_id, target_type)");
            stmt.execute("CREATE INDEX idx_comments_user_id ON comments(user_id)");
            stmt.execute("CREATE INDEX idx_comments_parent_id ON comments(parent_id)");
            System.out.println("已为评论服务创建表结构");
            
            // 为点赞服务创建表
            stmt.execute("USE haiya_like");
            stmt.execute("CREATE TABLE IF NOT EXISTS likes (id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, target_id BIGINT NOT NULL, target_type ENUM('video', 'comment', 'article', 'post') NOT NULL, created_at BIGINT)");
            stmt.execute("CREATE UNIQUE INDEX idx_likes_user_target ON likes(user_id, target_id, target_type)");
            stmt.execute("CREATE INDEX idx_likes_target ON likes(target_id, target_type)");
            System.out.println("已为点赞服务创建表结构");
            
            // 为关注服务创建表
            stmt.execute("USE haiya_follow");
            stmt.execute("CREATE TABLE IF NOT EXISTS follows (id BIGINT AUTO_INCREMENT PRIMARY KEY, follower_id BIGINT NOT NULL, following_id BIGINT NOT NULL, created_at BIGINT)");
            stmt.execute("CREATE UNIQUE INDEX idx_follows_unique ON follows(follower_id, following_id)");
            stmt.execute("CREATE INDEX idx_follows_follower ON follows(follower_id)");
            stmt.execute("CREATE INDEX idx_follows_following ON follows(following_id)");
            System.out.println("已为关注服务创建表结构");
            
            // 为消息服务创建表
            stmt.execute("USE haiya_message");
            stmt.execute("CREATE TABLE IF NOT EXISTS messages (id BIGINT AUTO_INCREMENT PRIMARY KEY, sender_id BIGINT NOT NULL, receiver_id BIGINT NOT NULL, content TEXT NOT NULL, is_read BOOLEAN DEFAULT FALSE, created_at BIGINT)");
            stmt.execute("CREATE INDEX idx_messages_sender ON messages(sender_id)");
            stmt.execute("CREATE INDEX idx_messages_receiver ON messages(receiver_id)");
            stmt.execute("CREATE INDEX idx_messages_created_at ON messages(created_at)");
            System.out.println("已为消息服务创建表结构");
            
            // 为直播服务创建表
            stmt.execute("USE haiya_live");
            stmt.execute("CREATE TABLE IF NOT EXISTS live_rooms (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, description TEXT, host_id BIGINT NOT NULL, stream_url VARCHAR(500), viewer_count INT DEFAULT 0, status ENUM('pending', 'live', 'ended') DEFAULT 'pending', created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE INDEX idx_live_rooms_host ON live_rooms(host_id)");
            stmt.execute("CREATE INDEX idx_live_rooms_status ON live_rooms(status)");
            System.out.println("已为直播服务创建表结构");
            
            // 为推荐服务创建表
            stmt.execute("USE haiya_recommend");
            stmt.execute("CREATE TABLE IF NOT EXISTS user_preferences (id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, category VARCHAR(100), weight DOUBLE DEFAULT 1.0, created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE INDEX idx_user_preferences_user_id ON user_preferences(user_id)");
            System.out.println("已为推荐服务创建表结构");
            
            // 为搜索服务创建表
            stmt.execute("USE haiya_search");
            stmt.execute("CREATE TABLE IF NOT EXISTS search_history (id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT, keyword VARCHAR(255) NOT NULL, created_at BIGINT)");
            stmt.execute("CREATE INDEX idx_search_history_user_id ON search_history(user_id)");
            stmt.execute("CREATE INDEX idx_search_history_keyword ON search_history(keyword)");
            System.out.println("已为搜索服务创建表结构");
            
            // 为支付服务创建表
            stmt.execute("USE haiya_payment");
            stmt.execute("CREATE TABLE IF NOT EXISTS payments (id BIGINT AUTO_INCREMENT PRIMARY KEY, order_id BIGINT NOT NULL, user_id BIGINT NOT NULL, amount DECIMAL(10, 2) NOT NULL, status ENUM('pending', 'success', 'failed', 'refunded') DEFAULT 'pending', payment_method VARCHAR(50), transaction_id VARCHAR(255), created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE INDEX idx_payments_order_id ON payments(order_id)");
            stmt.execute("CREATE INDEX idx_payments_user_id ON payments(user_id)");
            stmt.execute("CREATE INDEX idx_payments_status ON payments(status)");
            System.out.println("已为支付服务创建表结构");
            
            // 为电商服务创建表
            stmt.execute("USE haiya_ecommerce");
            stmt.execute("CREATE TABLE IF NOT EXISTS products (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, description TEXT, price DECIMAL(10, 2) NOT NULL, stock INT DEFAULT 0, category_id BIGINT, image_url VARCHAR(500), created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS categories (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, parent_id BIGINT DEFAULT NULL, created_at BIGINT)");
            stmt.execute("CREATE INDEX idx_products_category ON products(category_id)");
            stmt.execute("CREATE INDEX idx_categories_parent ON categories(parent_id)");
            System.out.println("已为电商服务创建表结构");
            
            // 为广告服务创建表
            stmt.execute("USE haiya_ad");
            stmt.execute("CREATE TABLE IF NOT EXISTS advertisements (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, image_url VARCHAR(500), target_url VARCHAR(500), position VARCHAR(100), start_time BIGINT, end_time BIGINT, is_active BOOLEAN DEFAULT TRUE, created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE INDEX idx_advertisements_position ON advertisements(position)");
            stmt.execute("CREATE INDEX idx_advertisements_active ON advertisements(is_active)");
            System.out.println("已为广告服务创建表结构");
            
            // 为订单服务创建表
            stmt.execute("USE haiya_order");
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, total_amount DECIMAL(10, 2) NOT NULL, status ENUM('pending', 'paid', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending', shipping_address TEXT, payment_id BIGINT, created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS order_items (id BIGINT AUTO_INCREMENT PRIMARY KEY, order_id BIGINT NOT NULL, product_id BIGINT NOT NULL, quantity INT NOT NULL, price DECIMAL(10, 2) NOT NULL)");
            stmt.execute("CREATE INDEX idx_orders_user_id ON orders(user_id)");
            stmt.execute("CREATE INDEX idx_orders_status ON orders(status)");
            stmt.execute("CREATE INDEX idx_order_items_order_id ON order_items(order_id)");
            System.out.println("已为订单服务创建表结构");
            
            // 为文章服务创建表
            stmt.execute("USE haiya_article");
            stmt.execute("CREATE TABLE IF NOT EXISTS articles (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, content TEXT NOT NULL, author_id BIGINT NOT NULL, view_count BIGINT DEFAULT 0, like_count BIGINT DEFAULT 0, comment_count BIGINT DEFAULT 0, status ENUM('draft', 'published', 'archived') DEFAULT 'draft', published_at BIGINT, created_at BIGINT, updated_at BIGINT)");
            stmt.execute("CREATE INDEX idx_articles_author ON articles(author_id)");
            stmt.execute("CREATE INDEX idx_articles_status ON articles(status)");
            System.out.println("已为文章服务创建表结构");
            
            stmt.close();
        } finally {
            conn.close();
        }
    }
    
    /**
     * 添加新服务数据库
     */
    public void addServiceDatabase(String serviceName) throws SQLException {
        String dbName = "haiya_" + serviceName.replace("-", "_");
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        
        try {
            Statement stmt = conn.createStatement();
            String createDbSQL = String.format(
                "CREATE DATABASE IF NOT EXISTS %s CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
                dbName
            );
            stmt.executeUpdate(createDbSQL);
            
            // 授权
            stmt.executeUpdate(String.format("GRANT ALL PRIVILEGES ON %s.* TO 'haiya'@'%%'", dbName));
            stmt.executeUpdate(String.format("GRANT ALL PRIVILEGES ON %s.* TO 'haiya'@'localhost'", dbName));
            stmt.executeUpdate("FLUSH PRIVILEGES");
            
            stmt.close();
            System.out.println("已添加服务数据库: " + dbName);
        } finally {
            conn.close();
        }
    }
    
    /**
     * 删除服务数据库
     */
    public void deleteServiceDatabase(String serviceName) throws SQLException {
        String dbName = "haiya_" + serviceName.replace("-", "_");
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        
        try {
            Statement stmt = conn.createStatement();
            String dropDbSQL = String.format("DROP DATABASE IF EXISTS %s", dbName);
            stmt.executeUpdate(dropDbSQL);
            stmt.close();
            System.out.println("已删除服务数据库: " + dbName);
        } finally {
            conn.close();
        }
    }
    
    /**
     * 向指定服务数据库写入测试数据
     */
    public void writeTestData(String serviceName, String tableName, Map<String, Object> data) throws SQLException {
        String dbName = "haiya_" + serviceName.replace("-", "_");
        Connection conn = DriverManager.getConnection(DB_URL + "/" + dbName, USERNAME, PASSWORD);
        
        try {
            // 构造插入语句
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            List<Object> valueList = new ArrayList<>();
            
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (columns.length() > 0) {
                    columns.append(", ");
                    values.append(", ");
                }
                columns.append(entry.getKey());
                values.append("?");
                valueList.add(entry.getValue());
            }
            
            String insertSQL = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                columns.toString(),
                values.toString()
            );
            
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            for (int i = 0; i < valueList.size(); i++) {
                pstmt.setObject(i + 1, valueList.get(i));
            }
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("向表 " + tableName + " 写入数据，影响行数: " + result);
        } finally {
            conn.close();
        }
    }
    
    /**
     * 测试数据插入功能
     */
    public void testDataInsertion() throws SQLException {
        // 向用户服务数据库插入测试数据
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", "testuser");
        userData.put("password", "encrypted_password");
        userData.put("phone_number", "13800138000");
        userData.put("avatar_url", "https://example.com/avatar.jpg");
        userData.put("created_at", System.currentTimeMillis());
        userData.put("updated_at", System.currentTimeMillis());
        
        writeTestData("user", "users", userData);
        
        // 向视频服务数据库插入测试数据
        Map<String, Object> videoData = new HashMap<>();
        videoData.put("title", "测试视频");
        videoData.put("description", "这是一个测试视频");
        videoData.put("url", "https://example.com/video.mp4");
        videoData.put("user_id", 1);
        videoData.put("created_at", System.currentTimeMillis());
        videoData.put("updated_at", System.currentTimeMillis());
        
        writeTestData("video", "videos", videoData);
    }
}