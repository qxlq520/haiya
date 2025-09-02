package com.haiya.video.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class VideoUploadService {
    
    private final Path videoStorageLocation = Paths.get("uploads/videos");
    private final Path coverStorageLocation = Paths.get("uploads/covers");
    
    public VideoUploadService() {
        try {
            Files.createDirectories(videoStorageLocation);
            Files.createDirectories(coverStorageLocation);
        } catch (Exception ex) {
            // 处理异常
        }
    }
    
    public String storeVideoFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path targetLocation = this.videoStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    
    public String storeCoverFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path targetLocation = this.coverStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}