import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * 数据库连接测试类
 * 用于检查后端服务与数据库的连接是否正常
 */
public class DatabaseConnectionTest {
    
    // 数据库连接配置
    private static final String DB_HOST = "127.0.0.1";
    private static final String DB_PORT = "3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qq9291923";
    
    // 测试的数据库列表
    private static final String[] DATABASES = {
        "haiya_user",
        "haiya_video", 
        "haiya_comment",
        "haiya_like",
        "haiya_follow",
        "haiya_message",
        "haiya_live",
        "haiya_recommend",
        "haiya_search",
        "haiya_payment",
        "haiya_ecommerce",
        "haiya_ad",
        "haiya_article",
        "haiya_order",
        "haiya_product"
    };
    
    public static void main(String[] args) {
        System.out.println("开始测试数据库连接...");
        
        // 测试主数据库连接
        testMainConnection();
        
        // 测试各个服务数据库连接
        for (String database : DATABASES) {
            testDatabaseConnection(database);
        }
        
        System.out.println("数据库连接测试完成。");
    }
    
    /**
     * 测试主数据库连接（不指定具体数据库）
     */
    private static void testMainConnection() {
        String url = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useSSL=false", DB_HOST, DB_PORT);
        
        try {
            System.out.println("正在测试主数据库连接...");
            Connection conn = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            System.out.println("✓ 主数据库连接成功");
            
            // 检查数据库列表
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE 'haiya_%'");
            
            System.out.println("已创建的haiya数据库:");
            while (rs.next()) {
                System.out.println("  - " + rs.getString(1));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("✗ 主数据库连接失败: " + e.getMessage());
        }
    }
    
    /**
     * 测试指定数据库连接
     */
    private static void testDatabaseConnection(String databaseName) {
        String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useSSL=false", DB_HOST, DB_PORT, databaseName);
        
        try {
            System.out.printf("正在测试数据库 %s 连接... ", databaseName);
            Connection conn = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            
            // 简单查询测试
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");
            rs.next();
            
            System.out.println("✓ 连接成功");
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("✗ 连接失败: " + e.getMessage());
        }
    }
}