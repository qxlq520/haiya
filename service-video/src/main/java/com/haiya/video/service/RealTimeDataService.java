package com.haiya.video.service;

import com.haiya.video.entity.RealTimeData;
import com.haiya.video.repository.RealTimeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class RealTimeDataService {

    private static final Logger logger = Logger.getLogger(RealTimeDataService.class.getName());

    @Autowired
    private RealTimeDataRepository realTimeDataRepository;

    /**
     * 记录实时数据
     * @param realTimeData 实时数据
     * @return 保存的数据记录
     */
    @Transactional
    public RealTimeData recordRealTimeData(RealTimeData realTimeData) {
        try {
            RealTimeData savedData = realTimeDataRepository.save(realTimeData);
            logger.info("Recorded real-time data of type: " + realTimeData.getDataType());
            return savedData;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error recording real-time data", e);
            throw new RuntimeException("Failed to record real-time data", e);
        }
    }

    /**
     * 批量记录实时数据
     * @param realTimeDataList 实时数据列表
     * @return 保存的数据记录列表
     */
    @Transactional
    public List<RealTimeData> recordRealTimeDataBatch(List<RealTimeData> realTimeDataList) {
        try {
            List<RealTimeData> savedDataList = realTimeDataRepository.saveAll(realTimeDataList);
            logger.info("Recorded batch of " + savedDataList.size() + " real-time data entries");
            return savedDataList;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error recording batch of real-time data", e);
            throw new RuntimeException("Failed to record batch of real-time data", e);
        }
    }

    /**
     * 获取特定类型的实时数据
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 实时数据列表
     */
    public List<RealTimeData> getRealTimeDataByType(String dataType, int limit) {
        try {
            List<RealTimeData> dataList;
            
            if (limit <= 10) {
                List<RealTimeData> fullList = realTimeDataRepository
                    .findTop10ByDataTypeOrderByRecordedAtDesc(dataType);
                dataList = fullList.subList(0, Math.min(limit, fullList.size()));
            } else {
                dataList = realTimeDataRepository
                    .findByDataTypeOrderByRecordedAtDesc(dataType)
                    .subList(0, limit);
            }
            
            logger.info("Retrieved " + dataList.size() + " real-time data entries of type: " + dataType);
            return dataList;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving real-time data of type: " + dataType, e);
            throw new RuntimeException("Failed to retrieve real-time data", e);
        }
    }

    /**
     * 获取特定时间范围内的实时数据
     * @param dataType 数据类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    public List<RealTimeData> getRealTimeDataByTimeRange(String dataType, 
                                                        LocalDateTime startTime, LocalDateTime endTime) {
        try {
            List<RealTimeData> dataList = realTimeDataRepository
                .findByDataTypeAndRecordedAtBetweenOrderByRecordedAtDesc(dataType, startTime, endTime);
            logger.info("Retrieved " + dataList.size() + " real-time data entries of type: " + 
                       dataType + " in time range");
            return dataList;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving real-time data in time range for type: " + dataType, e);
            throw new RuntimeException("Failed to retrieve real-time data in time range", e);
        }
    }

    /**
     * 获取最新的实时数据
     * @param dataType 数据类型
     * @param dataKey 数据键
     * @return 实时数据
     */
    public Optional<RealTimeData> getLatestRealTimeData(String dataType, String dataKey) {
        try {
            Optional<RealTimeData> data = realTimeDataRepository
                .findFirstByDataTypeAndDataKeyOrderByRecordedAtDesc(dataType, dataKey);
            if (data.isPresent()) {
                logger.info("Retrieved latest real-time data of type: " + dataType + ", key: " + dataKey);
            } else {
                logger.info("No real-time data found of type: " + dataType + ", key: " + dataKey);
            }
            return data;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving latest real-time data of type: " + dataType + 
                       ", key: " + dataKey, e);
            throw new RuntimeException("Failed to retrieve latest real-time data", e);
        }
    }

    /**
     * 删除过期的实时数据
     * @param days 天数
     * @return 删除的记录数
     */
    @Transactional
    public int deleteExpiredRealTimeData(int days) {
        try {
            LocalDateTime expiryDate = LocalDateTime.now().minusDays(days);
            int deletedCount = realTimeDataRepository.deleteByRecordedAtBefore(expiryDate);
            logger.info("Deleted " + deletedCount + " expired real-time data entries");
            return deletedCount;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting expired real-time data", e);
            throw new RuntimeException("Failed to delete expired real-time data", e);
        }
    }
}