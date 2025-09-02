package com.haiya.video.repository;

import com.haiya.video.entity.PointsExchangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsExchangeRecordRepository extends JpaRepository<PointsExchangeRecord, Long> {
    List<PointsExchangeRecord> findByUserIdOrderByExchangeTimeDesc(Long userId);
    List<PointsExchangeRecord> findAllByOrderByExchangeTimeDesc();
    List<PointsExchangeRecord> findByExchangeStatusOrderByExchangeTimeDesc(String exchangeStatus);
}