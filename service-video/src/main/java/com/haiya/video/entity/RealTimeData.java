package com.haiya.video.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "real_time_data")
public class RealTimeData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_type", nullable = false)
    private String dataType; // 数据类型：USER_ACTIVE(用户活跃), CONTENT_TREND(内容趋势), SYSTEM_PERFORMANCE(系统性能)
    
    @Column(name = "data_key")
    private String dataKey; // 数据键
    
    @Column(name = "data_value")
    private String dataValue; // 数据值
    
    @Column(name = "metric_value")
    private Double metricValue = 0.0; // 指标值
    
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt; // 记录时间
    
    @PrePersist
    public void prePersist() {
        this.recordedAt = LocalDateTime.now();
        if (this.metricValue == null) this.metricValue = 0.0;
    }
    
    // Constructors
    public RealTimeData() {}
    
    public RealTimeData(String dataType, String dataKey, String dataValue, Double metricValue) {
        this.dataType = dataType;
        this.dataKey = dataKey;
        this.dataValue = dataValue;
        this.metricValue = metricValue != null ? metricValue : 0.0;
        this.recordedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDataType() {
        return dataType;
    }
    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    public String getDataKey() {
        return dataKey;
    }
    
    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
    
    public String getDataValue() {
        return dataValue;
    }
    
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
    
    public Double getMetricValue() {
        return metricValue;
    }
    
    public void setMetricValue(Double metricValue) {
        this.metricValue = metricValue != null ? metricValue : 0.0;
    }
    
    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
    
    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}