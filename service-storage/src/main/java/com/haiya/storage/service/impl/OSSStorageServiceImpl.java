package com.haiya.storage.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.haiya.storage.entity.FileMetadata;
import com.haiya.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OSSStorageServiceImpl implements StorageService {
    
    @Value("${oss.endpoint}")
    private String endpoint;
    
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    
    @Value("${oss.bucketName}")
    private String bucketName;
    
    private OSS ossClient;
    
    // 在实际项目中，这些数据应该存储在数据库中
    private final Map<String, FileMetadata> fileMetadataMap = new ConcurrentHashMap<>();
    private final Map<Long, List<String>> userFilesMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 创建OSSClient实例
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public FileMetadata uploadFile(MultipartFile file, Long userId) {
        try {
            return uploadFile(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                userId
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public FileMetadata uploadFile(InputStream inputStream, String fileName, String contentType, Long fileSize, Long userId) {
        // 生成唯一的文件ID
        String fileId = UUID.randomUUID().toString().replace("-", "");
        
        // 构造OSS中的对象名
        String objectName = userId + "/" + fileId + "/" + fileName;
        
        // 上传文件到OSS
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
        ossClient.putObject(putObjectRequest);
        
        // 构造文件访问URL
        String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
        
        // 保存文件元数据
        FileMetadata metadata = new FileMetadata(fileId, fileName, contentType, fileSize, url, bucketName, userId);
        fileMetadataMap.put(fileId, metadata);
        
        // 更新用户文件列表
        userFilesMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(fileId);
        
        return metadata;
    }

    @Override
    public byte[] downloadFile(String fileId) {
        FileMetadata metadata = fileMetadataMap.get(fileId);
        if (metadata == null) {
            throw new RuntimeException("File not found: " + fileId);
        }
        
        // 从OSS下载文件
        String objectName = metadata.getUserId() + "/" + fileId + "/" + metadata.getFileName();
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        
        try {
            return ossObject.getObjectContent().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

    @Override
    public boolean deleteFile(String fileId) {
        FileMetadata metadata = fileMetadataMap.get(fileId);
        if (metadata == null) {
            return false;
        }
        
        // 从OSS删除文件
        String objectName = metadata.getUserId() + "/" + fileId + "/" + metadata.getFileName();
        ossClient.deleteObject(bucketName, objectName);
        
        // 从元数据中删除
        fileMetadataMap.remove(fileId);
        
        // 从用户文件列表中删除
        List<String> userFiles = userFilesMap.get(metadata.getUserId());
        if (userFiles != null) {
            userFiles.remove(fileId);
        }
        
        return true;
    }

    @Override
    public FileMetadata getFileMetadata(String fileId) {
        return fileMetadataMap.get(fileId);
    }

    @Override
    public List<FileMetadata> listFilesByUser(Long userId) {
        List<String> fileIds = userFilesMap.get(userId);
        if (fileIds == null) {
            return new ArrayList<>();
        }
        
        List<FileMetadata> result = new ArrayList<>();
        for (String fileId : fileIds) {
            FileMetadata metadata = fileMetadataMap.get(fileId);
            if (metadata != null) {
                result.add(metadata);
            }
        }
        return result;
    }

    @Override
    public String generatePresignedUrl(String fileId, Long expirationTime) {
        FileMetadata metadata = fileMetadataMap.get(fileId);
        if (metadata == null) {
            throw new RuntimeException("File not found: " + fileId);
        }
        
        // 生成预签名URL
        String objectName = metadata.getUserId() + "/" + fileId + "/" + metadata.getFileName();
        java.util.Date expiration = new java.util.Date(new java.util.Date().getTime() + expirationTime);
        
        return ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
    }
}