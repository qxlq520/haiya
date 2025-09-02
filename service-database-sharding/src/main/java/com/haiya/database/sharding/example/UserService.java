package com.haiya.database.sharding.example;

import com.haiya.database.sharding.MasterOnly;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务示例
 * 演示读写分离和MasterOnly注解的使用
 */
@Service
public class UserService {

    /**
     * 查询用户列表（使用从库）
     *
     * @return 用户列表
     */
    public List<String> listUsers() {
        // 这里会自动使用从库进行查询
        // 实际项目中这里会执行数据库查询操作
        return List.of("User1", "User2", "User3");
    }

    /**
     * 根据ID获取用户信息（使用从库）
     *
     * @param id 用户ID
     * @return 用户信息
     */
    public String getUserById(Long id) {
        // 这里会自动使用从库进行查询
        return "User" + id;
    }

    /**
     * 获取刚创建的用户信息（使用主库）
     * 由于主从同步存在延迟，刚创建的用户可能在从库中查不到，
     * 所以需要强制使用主库查询
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @MasterOnly
    public String getNewlyCreatedUser(Long id) {
        // 这里会强制使用主库进行查询
        return "NewUser" + id;
    }

    /**
     * 创建用户（使用主库）
     *
     * @param user 用户信息
     * @return 创建结果
     */
    public String createUser(String user) {
        // 写操作会自动使用主库
        return "Created: " + user;
    }

    /**
     * 更新用户信息（使用主库）
     *
     * @param id   用户ID
     * @param user 用户信息
     * @return 更新结果
     */
    public String updateUser(Long id, String user) {
        // 写操作会自动使用主库
        return "Updated user " + id + ": " + user;
    }

    /**
     * 删除用户（使用主库）
     *
     * @param id 用户ID
     * @return 删除结果
     */
    public String deleteUser(Long id) {
        // 写操作会自动使用主库
        return "Deleted user " + id;
    }
}