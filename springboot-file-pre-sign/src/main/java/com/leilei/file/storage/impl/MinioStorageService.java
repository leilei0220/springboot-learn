package com.leilei.file.storage.impl;

import com.leilei.file.storage.StorageService;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * MinIO存储服务实现
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "file.storage.type", havingValue = "minio", matchIfMissing = true)
public class MinioStorageService implements StorageService {
    
    @Value("${minio.endpoint}")
    private String endpoint;
    
    @Value("${minio.access-key}")
    private String accessKey;
    
    @Value("${minio.secret-key}")
    private String secretKey;
    
    @Value("${minio.bucket}")
    private String defaultBucket;
    
    private MinioClient minioClient;
    
    @PostConstruct
    public void init() {
        try {
            minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
            
            // 检查并创建存储桶
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(defaultBucket)
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(defaultBucket)
                        .build());
                log.info("创建MinIO存储桶: {}", defaultBucket);
            }
            log.info("MinIO存储服务初始化成功");
        } catch (Exception e) {
            log.error("MinIO存储服务初始化失败", e);
            throw new RuntimeException("MinIO存储服务初始化失败", e);
        }
    }
    
    /**
     * 将十六进制MD5字符串转换为Base64编码
     * MinIO的Content-MD5头需要Base64编码的MD5值
     * 
     * @param hexMd5 十六进制MD5字符串（32位）
     * @return Base64编码的MD5值
     */
    private String hexMd5ToBase64(String hexMd5) {
        if (hexMd5 == null || hexMd5.isEmpty()) {
            return null;
        }
        
        // 如果已经是Base64格式（通常以=结尾或长度不是32），直接返回
        if (hexMd5.length() != 32) {
            return hexMd5;
        }
        
        try {
            // 将十六进制字符串转换为字节数组
            byte[] md5Bytes = new byte[16];
            for (int i = 0; i < 16; i++) {
                md5Bytes[i] = (byte) Integer.parseInt(hexMd5.substring(i * 2, i * 2 + 2), 16);
            }
            
            // 转换为Base64编码
            return Base64.getEncoder().encodeToString(md5Bytes);
        } catch (Exception e) {
            log.warn("MD5格式转换失败，使用原始值: {}", hexMd5, e);
            return hexMd5;
        }
    }
    
    @Override
    public String generatePresignedUploadUrl(String bucketName, String objectName, String md5, Long fileSize, int expirySeconds) {
        try {
            Map<String, String> headers = new HashMap<>();
            // 设置Content-MD5头（MinIO会验证）
            // MinIO需要Base64编码的MD5值，而不是十六进制字符串
            if (md5 != null && !md5.isEmpty()) {
                String base64Md5 = hexMd5ToBase64(md5);
                headers.put("Content-MD5", base64Md5);
                log.debug("MD5转换: 十六进制={}, Base64={}", md5, base64Md5);
            }
            
            // 设置Content-Length头
            if (fileSize != null && fileSize > 0) {
                headers.put("Content-Length", String.valueOf(fileSize));
            }
            
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expirySeconds, TimeUnit.SECONDS)
                            .extraHeaders(headers)
                            .build()
            );
            
            log.info("生成MinIO预签名URL: bucket={}, object={}, expiry={}s", bucketName, objectName, expirySeconds);
            return url;
        } catch (Exception e) {
            log.error("生成MinIO预签名URL失败", e);
            throw new RuntimeException("生成预签名URL失败", e);
        }
    }
    
    @Override
    public boolean moveFile(String bucketName, String sourcePath, String targetPath) {
        try {
            // MinIO使用copyObject + removeObject实现移动
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(bucketName)
                            .object(targetPath)
                            .source(CopySource.builder()
                                    .bucket(bucketName)
                                    .object(sourcePath)
                                    .build())
                            .build()
            );
            
            // 删除源文件
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(sourcePath)
                            .build()
            );
            
            log.info("MinIO文件移动成功: {} -> {}", sourcePath, targetPath);
            return true;
        } catch (Exception e) {
            log.error("MinIO文件移动失败: {} -> {}", sourcePath, targetPath, e);
            return false;
        }
    }
    
    @Override
    public boolean deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            log.info("MinIO文件删除成功: {}", objectName);
            return true;
        } catch (Exception e) {
            log.error("MinIO文件删除失败: {}", objectName, e);
            return false;
        }
    }
    
    @Override
    public boolean fileExists(String bucketName, String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String getStorageType() {
        return "minio";
    }
    
    public String getDefaultBucket() {
        return defaultBucket;
    }
}

