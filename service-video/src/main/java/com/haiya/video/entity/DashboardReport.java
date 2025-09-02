package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dashboard_reports")
public class DashboardReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "report_name", nullable = false)
    private String reportName;
    
    @Column(name = "report_type")
    private String reportType; // 报告类型：USER_ANALYTICS(用户分析), CONTENT_ANALYTICS(内容分析), BUSINESS_ANALYTICS(业务分析)
    
    @Column(name = "report_data")
    private String reportData; // 报告数据（JSON格式）
    
    @Column(name = "time_range")
    private String timeRange; // 时间范围：HOURLY, DAILY, WEEKLY, MONTHLY
    
    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt; // 生成时间
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.generatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public DashboardReport() {}
    
    public DashboardReport(String reportName, String reportType, String reportData, String timeRange) {
        this.reportName = reportName;
        this.reportType = reportType;
        this.reportData = reportData;
        this.timeRange = timeRange;
        this.createdAt = LocalDateTime.now();
        this.generatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReportName() {
        return reportName;
    }
    
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getReportData() {
        return reportData;
    }
    
    public void setReportData(String reportData) {
        this.reportData = reportData;
    }
    
    public String getTimeRange() {
        return timeRange;
    }
    
    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
    
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}