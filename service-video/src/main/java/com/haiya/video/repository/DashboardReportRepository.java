package com.haiya.video.repository;

import com.haiya.video.entity.DashboardReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DashboardReportRepository extends JpaRepository<DashboardReport, Long> {
    List<DashboardReport> findByReportTypeOrderByGeneratedAtDesc(String reportType);
    List<DashboardReport> findAllByOrderByGeneratedAtDesc();
    int deleteByGeneratedAtBefore(LocalDateTime dateTime);
}