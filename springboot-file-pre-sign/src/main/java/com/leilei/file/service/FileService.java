package com.leilei.file.service;

import com.leilei.file.dto.*;
import com.leilei.file.entity.FileMetadata;
import com.leilei.file.mapper.FileMetadataMapper;
import com.leilei.file.storage.StorageService;
import com.leilei.file.storage.impl.MinioStorageService;
import com.leilei.file.storage.impl.OssStorageService;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 文件服务
 */
@Slf4j
@Service
public class FileService {
    
    @Autowired(required = false)
    private MinioStorageService minioStorageService;
    
    @Autowired(required = false)
    private OssStorageService ossStorageService;
    
    @Autowired
    private FileMetadataMapper fileMetadataMapper;
    
    @Value("${file.storage.type:minio}")
    private String storageType;
    
    @Value("${file.temp-path:temp}")
    private String tempPathPrefix;
    
    @Value("${file.presigned-url-expiry:3600}")
    private int presignedUrlExpiry;
    
    /**
     * 获取当前存储服务
     */
    private StorageService getStorageService() {
        if ("oss".equalsIgnoreCase(storageType)) {
            return ossStorageService;
        }
        return minioStorageService;
    }
    
    /**
     * 获取存储桶名称
     */
    private String getBucketName() {
        StorageService storageService = getStorageService();
        if (storageService instanceof MinioStorageService) {
            return ((MinioStorageService) storageService).getDefaultBucket();
        } else if (storageService instanceof OssStorageService) {
            return ((OssStorageService) storageService).getDefaultBucket();
        }
        throw new RuntimeException("未找到可用的存储服务");
    }
    
    /**
     * 生成预签名上传URL
     * 
     * @param request 请求参数
     * @return 预签名URL和文件Key
     */
    @Transactional
    public PreSignResponse generatePresignedUrl(PreSignRequest request) {
        // 生成文件唯一Key
        String fileKey = UUID.randomUUID().toString().replace("-", "");
        
        // 构建临时文件路径：temp/{fileKey}/{fileName}
        String fileName = request.getFileName();
        // 处理文件名中的特殊字符
        String safeFileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String tempObjectPath = String.format("%s/%s/%s", tempPathPrefix, fileKey, safeFileName);
        
        // 获取存储服务
        StorageService storageService = getStorageService();
        String bucketName = getBucketName();
        
        // 生成预签名URL
        String uploadUrl = storageService.generatePresignedUploadUrl(
                bucketName,
                tempObjectPath,
                request.getMd5(),
                request.getFileSize(),
                presignedUrlExpiry
        );
        
        // 保存文件元数据
        FileMetadata metadata = new FileMetadata();
        metadata.setFileKey(fileKey);
        metadata.setFileName(request.getFileName());
        metadata.setMd5(request.getMd5());
        metadata.setFileSize(request.getFileSize());
        metadata.setTempPath(tempObjectPath);
        metadata.setStorageType(storageService.getStorageType());
        metadata.setStatus("UPLOADING");
        metadata.setCreateTime(LocalDateTime.now());
        metadata.setUpdateTime(LocalDateTime.now());
        
        fileMetadataMapper.insert(metadata);
        
        log.info("生成预签名URL成功: fileKey={}, fileName={}", fileKey, request.getFileName());

        return new PreSignResponse(uploadUrl, fileKey);
    }
    
    /**
     * 通知文件上传完成
     * 
     * @param request 请求参数
     * @return 是否成功
     */
    @Transactional
    public boolean notifyUploadComplete(UploadCompleteRequest request) {
        String fileKey = request.getFileKey();
        
        // 查询文件元数据
        FileMetadata metadata = fileMetadataMapper.selectOneByQuery(
                QueryWrapper.create()
                        .where("file_key = ?", fileKey)
        );
        
        if (metadata == null) {
            log.warn("文件不存在: fileKey={}", fileKey);
            return false;
        }
        
        // 验证文件是否已上传到存储
        StorageService storageService = getStorageService();
        String bucketName = getBucketName();
        if (!storageService.fileExists(bucketName, metadata.getTempPath())) {
            log.warn("文件在存储中不存在: fileKey={}, path={}", fileKey, metadata.getTempPath());
            return false;
        }
        
        // 更新状态
        metadata.setStatus("UPLOADED");
        metadata.setUpdateTime(LocalDateTime.now());
        fileMetadataMapper.update(metadata);
        
        log.info("文件上传完成: fileKey={}", fileKey);
        return true;
    }
    
    /**
     * 移动文件到业务目录
     * 
     * @param request 请求参数
     * @return 是否成功
     */
    @Transactional
    public boolean moveFileToBusiness(MoveFileRequest request) {
        String fileKey = request.getFileKey();
        String businessPath = request.getBusinessPath();
        
        // 查询文件元数据
        FileMetadata metadata = fileMetadataMapper.selectOneByQuery(
                QueryWrapper.create()
                        .where("file_key = ?", fileKey)
        );
        
        if (metadata == null) {
            log.warn("文件不存在: fileKey={}", fileKey);
            return false;
        }
        
        if (!"UPLOADED".equals(metadata.getStatus())) {
            log.warn("文件状态不正确，无法移动: fileKey={}, status={}", fileKey, metadata.getStatus());
            return false;
        }
        
        // 构建目标路径：{businessPath}/{fileKey}/{fileName}
        String fileName = metadata.getFileName();
        String safeFileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String targetPath = String.format("%s/%s/%s", businessPath, fileKey, safeFileName);
        
        // 执行移动操作
        StorageService storageService = getStorageService();
        String bucketName = getBucketName();
        boolean success = storageService.moveFile(bucketName, metadata.getTempPath(), targetPath);
        
        if (success) {
            // 更新元数据
            metadata.setBusinessPath(targetPath);
            metadata.setStatus("MOVED");
            metadata.setUpdateTime(LocalDateTime.now());
            fileMetadataMapper.update(metadata);
            
            log.info("文件移动成功: fileKey={}, targetPath={}", fileKey, targetPath);
        } else {
            log.error("文件移动失败: fileKey={}, targetPath={}", fileKey, targetPath);
        }
        
        return success;
    }
    
    /**
     * 根据文件Key获取文件信息
     * 
     * @param fileKey 文件Key
     * @return 文件元数据
     */
    public FileMetadata getFileMetadata(String fileKey) {
        return fileMetadataMapper.selectOneByQuery(
                QueryWrapper.create()
                        .where("file_key = ?", fileKey)
        );
    }
}

