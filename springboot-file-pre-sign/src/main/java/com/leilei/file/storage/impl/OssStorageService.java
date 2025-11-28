package com.leilei.file.storage.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.leilei.file.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云OSS存储服务实现
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "file.storage.type", havingValue = "oss")
public class OssStorageService implements StorageService {
    
    @Value("${oss.endpoint}")
    private String endpoint;
    
    @Value("${oss.access-key-id}")
    private String accessKeyId;
    
    @Value("${oss.access-key-secret}")
    private String accessKeySecret;
    
    @Value("${oss.bucket}")
    private String defaultBucket;
    
    private OSS ossClient;
    
    @PostConstruct
    public void init() {
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            
            // 检查存储桶是否存在
            if (!ossClient.doesBucketExist(defaultBucket)) {
                ossClient.createBucket(defaultBucket);
                log.info("创建OSS存储桶: {}", defaultBucket);
            }
            log.info("阿里云OSS存储服务初始化成功");
        } catch (Exception e) {
            log.error("阿里云OSS存储服务初始化失败", e);
            throw new RuntimeException("阿里云OSS存储服务初始化失败", e);
        }
    }
    
    @PreDestroy
    public void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
    
    @Override
    public String generatePresignedUploadUrl(String bucketName, String objectName, String md5, Long fileSize, int expirySeconds) {
        try {
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
            request.setMethod(com.aliyun.oss.HttpMethod.PUT);
            request.setExpiration(new Date(System.currentTimeMillis() + expirySeconds * 1000L));
            
            // 设置Content-MD5（OSS会验证）
            if (md5 != null && !md5.isEmpty()) {
                request.addHeader("Content-MD5", md5);
            }
            
            // 设置Content-Length
            if (fileSize != null && fileSize > 0) {
                request.addHeader("Content-Length", String.valueOf(fileSize));
            }
            
            URL url = ossClient.generatePresignedUrl(request);
            String urlString = url.toString();
            
            log.info("生成OSS预签名URL: bucket={}, object={}, expiry={}s", bucketName, objectName, expirySeconds);
            return urlString;
        } catch (Exception e) {
            log.error("生成OSS预签名URL失败", e);
            throw new RuntimeException("生成预签名URL失败", e);
        }
    }
    
    @Override
    public boolean moveFile(String bucketName, String sourcePath, String targetPath) {
        try {
            // OSS使用copyObject + deleteObject实现移动
            ossClient.copyObject(bucketName, sourcePath, bucketName, targetPath);
            ossClient.deleteObject(bucketName, sourcePath);
            
            log.info("OSS文件移动成功: {} -> {}", sourcePath, targetPath);
            return true;
        } catch (Exception e) {
            log.error("OSS文件移动失败: {} -> {}", sourcePath, targetPath, e);
            return false;
        }
    }
    
    @Override
    public boolean deleteFile(String bucketName, String objectName) {
        try {
            ossClient.deleteObject(bucketName, objectName);
            log.info("OSS文件删除成功: {}", objectName);
            return true;
        } catch (Exception e) {
            log.error("OSS文件删除失败: {}", objectName, e);
            return false;
        }
    }
    
    @Override
    public boolean fileExists(String bucketName, String objectName) {
        try {
            return ossClient.doesObjectExist(bucketName, objectName);
        } catch (Exception e) {
            log.error("检查OSS文件是否存在失败: {}", objectName, e);
            return false;
        }
    }
    
    @Override
    public String getStorageType() {
        return "oss";
    }
    
    public String getDefaultBucket() {
        return defaultBucket;
    }
}

