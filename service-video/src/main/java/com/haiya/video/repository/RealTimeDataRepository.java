package com.haiya.video.repository;

import com.haiya.video.entity.RealTimeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RealTimeDataRepository extends JpaRepository<RealTimeData, Long> {
    List<RealTimeData> findByDataTypeOrderByRecordedAtDesc(String dataType);
    List<RealTimeData> findTop10ByDataTypeOrderByRecordedAtDesc(String dataType);
    List<RealTimeData> findByDataTypeAndRecordedAtBetweenOrderByRecordedAtDesc(
        String dataType, LocalDateTime startTime, LocalDateTime endTime);
    
    Optional<RealTimeData> findFirstByDataTypeAndDataKeyOrderByRecordedAtDesc(String dataType, String dataKey);
    
    // 使用标准的Spring Data JPA查询方法命名，直接在方法名中指定数量
    List<RealTimeData> findTop5ByDataTypeOrderByRecordedAtDesc(String dataType);
    
    int deleteByRecordedAtBefore(LocalDateTime dateTime);
}