package com.haiya.storage.service;

import com.haiya.storage.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface StorageService {
    FileMetadata uploadFile(MultipartFile file, Long userId);
    FileMetadata uploadFile(InputStream inputStream, String fileName, String contentType, Long fileSize, Long userId);
    byte[] downloadFile(String fileId);
    boolean deleteFile(String fileId);
    FileMetadata getFileMetadata(String fileId);
    List<FileMetadata> listFilesByUser(Long userId);
    String generatePresignedUrl(String fileId, Long expirationTime);
}