package com.haiya.video.service;

import com.haiya.video.entity.DashboardReport;
import com.haiya.video.repository.DashboardReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class DashboardService {

    private static final Logger logger = Logger.getLogger(DashboardService.class.getName());

    @Autowired
    private DashboardReportRepository dashboardReportRepository;

    /**
     * 生成仪表板报告
     * @param reportName 报告名称
     * @param reportType 报告类型
     * @param reportData 报告数据
     * @param timeRange 时间范围
     * @return 生成的报告
     */
    @Transactional
    public DashboardReport generateDashboardReport(String reportName, String reportType, 
                                                  String reportData, String timeRange) {
        try {
            DashboardReport report = new DashboardReport(reportName, reportType, reportData, timeRange);
            DashboardReport savedReport = dashboardReportRepository.save(report);
            logger.info("Generated dashboard report: " + reportName + " of type: " + reportType);
            return savedReport;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating dashboard report: " + reportName, e);
            throw new RuntimeException("Failed to generate dashboard report", e);
        }
    }

    /**
     * 获取所有仪表板报告
     * @return 报告列表
     */
    public List<DashboardReport> getAllDashboardReports() {
        try {
            List<DashboardReport> reports = dashboardReportRepository.findAllByOrderByGeneratedAtDesc();
            logger.info("Retrieved " + reports.size() + " dashboard reports");
            return reports;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving dashboard reports", e);
            throw new RuntimeException("Failed to retrieve dashboard reports", e);
        }
    }

    /**
     * 根据类型获取仪表板报告
     * @param reportType 报告类型
     * @return 报告列表
     */
    public List<DashboardReport> getDashboardReportsByType(String reportType) {
        try {
            List<DashboardReport> reports = dashboardReportRepository
                .findByReportTypeOrderByGeneratedAtDesc(reportType);
            logger.info("Retrieved " + reports.size() + " " + reportType + " dashboard reports");
            return reports;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving " + reportType + " dashboard reports", e);
            throw new RuntimeException("Failed to retrieve dashboard reports by type", e);
        }
    }

    /**
     * 根据ID获取仪表板报告
     * @param reportId 报告ID
     * @return 报告
     */
    public Optional<DashboardReport> getDashboardReportById(Long reportId) {
        try {
            Optional<DashboardReport> report = dashboardReportRepository.findById(reportId);
            if (report.isPresent()) {
                logger.info("Retrieved dashboard report with ID: " + reportId);
            } else {
                logger.info("Dashboard report not found with ID: " + reportId);
            }
            return report;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving dashboard report: " + reportId, e);
            throw new RuntimeException("Failed to retrieve dashboard report", e);
        }
    }

    /**
     * 更新仪表板报告
     * @param reportId 报告ID
     * @param reportData 新的报告数据
     * @return 更新后的报告
     */
    @Transactional
    public DashboardReport updateDashboardReport(Long reportId, String reportData) {
        try {
            Optional<DashboardReport> reportOptional = dashboardReportRepository.findById(reportId);
            if (!reportOptional.isPresent()) {
                throw new RuntimeException("Dashboard report not found with ID: " + reportId);
            }
            
            DashboardReport report = reportOptional.get();
            report.setReportData(reportData);
            report.setGeneratedAt(LocalDateTime.now());
            
            DashboardReport savedReport = dashboardReportRepository.save(report);
            logger.info("Updated dashboard report with ID: " + reportId);
            return savedReport;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating dashboard report: " + reportId, e);
            throw new RuntimeException("Failed to update dashboard report", e);
        }
    }

    /**
     * 删除仪表板报告
     * @param reportId 报告ID
     */
    @Transactional
    public void deleteDashboardReport(Long reportId) {
        try {
            dashboardReportRepository.deleteById(reportId);
            logger.info("Deleted dashboard report with ID: " + reportId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting dashboard report: " + reportId, e);
            throw new RuntimeException("Failed to delete dashboard report", e);
        }
    }

    /**
     * 删除过期的仪表板报告
     * @param days 天数
     * @return 删除的记录数
     */
    @Transactional
    public int deleteExpiredDashboardReports(int days) {
        try {
            LocalDateTime expiryDate = LocalDateTime.now().minusDays(days);
            int deletedCount = dashboardReportRepository.deleteByGeneratedAtBefore(expiryDate);
            logger.info("Deleted " + deletedCount + " expired dashboard reports");
            return deletedCount;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting expired dashboard reports", e);
            throw new RuntimeException("Failed to delete expired dashboard reports", e);
        }
    }
}