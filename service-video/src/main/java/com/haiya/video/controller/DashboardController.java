package com.haiya.video.controller;

import com.haiya.video.entity.DashboardReport;
import com.haiya.video.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 生成仪表板报告
     * @param reportName 报告名称
     * @param reportType 报告类型
     * @param reportData 报告数据
     * @param timeRange 时间范围
     * @return 生成的报告
     */
    @PostMapping("/reports")
    public ResponseEntity<DashboardReport> generateDashboardReport(
            @RequestParam String reportName,
            @RequestParam String reportType,
            @RequestBody String reportData,
            @RequestParam String timeRange) {
        DashboardReport report = dashboardService.generateDashboardReport(
            reportName, reportType, reportData, timeRange);
        return ResponseEntity.ok(report);
    }

    /**
     * 获取所有仪表板报告
     * @return 报告列表
     */
    @GetMapping("/reports")
    public ResponseEntity<List<DashboardReport>> getAllDashboardReports() {
        List<DashboardReport> reports = dashboardService.getAllDashboardReports();
        return ResponseEntity.ok(reports);
    }

    /**
     * 根据类型获取仪表板报告
     * @param reportType 报告类型
     * @return 报告列表
     */
    @GetMapping("/reports/type/{reportType}")
    public ResponseEntity<List<DashboardReport>> getDashboardReportsByType(@PathVariable String reportType) {
        List<DashboardReport> reports = dashboardService.getDashboardReportsByType(reportType);
        return ResponseEntity.ok(reports);
    }

    /**
     * 根据ID获取仪表板报告
     * @param reportId 报告ID
     * @return 报告
     */
    @GetMapping("/reports/{reportId}")
    public ResponseEntity<DashboardReport> getDashboardReportById(@PathVariable Long reportId) {
        Optional<DashboardReport> report = dashboardService.getDashboardReportById(reportId);
        if (report.isPresent()) {
            return ResponseEntity.ok(report.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新仪表板报告
     * @param reportId 报告ID
     * @param reportData 新的报告数据
     * @return 更新后的报告
     */
    @PutMapping("/reports/{reportId}")
    public ResponseEntity<DashboardReport> updateDashboardReport(
            @PathVariable Long reportId,
            @RequestBody String reportData) {
        DashboardReport updatedReport = dashboardService.updateDashboardReport(reportId, reportData);
        return ResponseEntity.ok(updatedReport);
    }

    /**
     * 删除仪表板报告
     * @param reportId 报告ID
     * @return 操作结果
     */
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Map<String, String>> deleteDashboardReport(@PathVariable Long reportId) {
        dashboardService.deleteDashboardReport(reportId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Dashboard report deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * 删除过期的仪表板报告
     * @param days 天数
     * @return 删除结果
     */
    @DeleteMapping("/reports/expired")
    public ResponseEntity<Map<String, Object>> deleteExpiredDashboardReports(@RequestParam int days) {
        int deletedCount = dashboardService.deleteExpiredDashboardReports(days);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expired dashboard reports deleted successfully");
        response.put("deletedCount", deletedCount);
        return ResponseEntity.ok(response);
    }
}