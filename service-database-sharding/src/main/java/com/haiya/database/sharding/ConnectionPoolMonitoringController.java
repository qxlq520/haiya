package com.haiya.database.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据库连接池监控控制器
 */
@RestController
@RequestMapping("/api/database/monitoring")
public class ConnectionPoolMonitoringController {

    @Autowired
    private ConnectionPoolMonitoringService monitoringService;

    /**
     * 获取主库连接池状态
     *
     * @return 连接池状态信息
     */
    @GetMapping("/master-pool")
    public ResponseEntity<Map<String, Object>> getMasterPoolStatus() {
        return ResponseEntity.ok(monitoringService.getMasterPoolStatus());
    }

    /**
     * 获取从库连接池状态
     *
     * @return 连接池状态信息
     */
    @GetMapping("/slave-pool")
    public ResponseEntity<Map<String, Object>> getSlavePoolStatus() {
        return ResponseEntity.ok(monitoringService.getSlavePoolStatus());
    }

    /**
     * 获取所有连接池状态
     *
     * @return 所有连接池状态信息
     */
    @GetMapping("/all-pools")
    public ResponseEntity<Map<String, Map<String, Object>>> getAllPoolStatus() {
        return ResponseEntity.ok(monitoringService.getAllPoolStatus());
    }

    /**
     * 检查连接池是否存在潜在问题
     *
     * @return 检查结果
     */
    @GetMapping("/pool-issues")
    public ResponseEntity<Map<String, Object>> checkPoolIssues() {
        boolean hasIssues = monitoringService.checkPoolIssues();
        Map<String, Object> result = Map.of(
                "hasIssues", hasIssues,
                "message", hasIssues ? "连接池存在潜在问题，请关注" : "连接池状态正常"
        );
        return ResponseEntity.ok(result);
    }
}