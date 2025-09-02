package com.haiya.video.controller;

import com.haiya.video.entity.RealTimeData;
import com.haiya.video.service.RealTimeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/real-time-data")
public class RealTimeDataController {

    @Autowired
    private RealTimeDataService realTimeDataService;

    /**
     * 记录实时数据
     * @param realTimeData 实时数据
     * @return 保存的数据记录
     */
    @PostMapping
    public ResponseEntity<RealTimeData> recordRealTimeData(@RequestBody RealTimeData realTimeData) {
        RealTimeData recordedData = realTimeDataService.recordRealTimeData(realTimeData);
        return ResponseEntity.ok(recordedData);
    }

    /**
     * 批量记录实时数据
     * @param realTimeDataList 实时数据列表
     * @return 保存的数据记录列表
     */
    @PostMapping("/batch")
    public ResponseEntity<List<RealTimeData>> recordRealTimeDataBatch(
            @RequestBody List<RealTimeData> realTimeDataList) {
        List<RealTimeData> recordedDataList = realTimeDataService.recordRealTimeDataBatch(realTimeDataList);
        return ResponseEntity.ok(recordedDataList);
    }

    /**
     * 获取特定类型的实时数据
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 实时数据列表
     */
    @GetMapping("/type/{dataType}")
    public ResponseEntity<List<RealTimeData>> getRealTimeDataByType(
            @PathVariable String dataType,
            @RequestParam(defaultValue = "50") int limit) {
        List<RealTimeData> dataList = realTimeDataService.getRealTimeDataByType(dataType, limit);
        return ResponseEntity.ok(dataList);
    }

    /**
     * 获取特定时间范围内的实时数据
     * @param dataType 数据类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 实时数据列表
     */
    @GetMapping("/range/{dataType}")
    public ResponseEntity<List<RealTimeData>> getRealTimeDataByTimeRange(
            @PathVariable String dataType,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        List<RealTimeData> dataList = realTimeDataService.getRealTimeDataByTimeRange(dataType, start, end);
        return ResponseEntity.ok(dataList);
    }

    /**
     * 获取最新的实时数据
     * @param dataType 数据类型
     * @param dataKey 数据键
     * @return 实时数据
     */
    @GetMapping("/latest")
    public ResponseEntity<RealTimeData> getLatestRealTimeData(
            @RequestParam String dataType,
            @RequestParam String dataKey) {
        Optional<RealTimeData> data = realTimeDataService.getLatestRealTimeData(dataType, dataKey);
        if (data.isPresent()) {
            return ResponseEntity.ok(data.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除过期的实时数据
     * @param days 天数
     * @return 删除结果
     */
    @DeleteMapping("/expired")
    public ResponseEntity<Map<String, Object>> deleteExpiredRealTimeData(@RequestParam int days) {
        int deletedCount = realTimeDataService.deleteExpiredRealTimeData(days);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expired real-time data deleted successfully");
        response.put("deletedCount", deletedCount);
        return ResponseEntity.ok(response);
    }
}