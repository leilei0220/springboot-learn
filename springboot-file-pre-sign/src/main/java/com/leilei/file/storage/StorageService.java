package com.leilei.file.storage;

/**
 * 存储服务接口
 * 支持MinIO和阿里云OSS
 */
public interface StorageService {
    
    /**
     * 生成预签名上传URL
     * 
     * @param bucketName 存储桶名称
     * @param objectName 对象名称（文件路径）
     * @param md5 文件MD5值（用于验证）
     * @param fileSize 文件大小（用于验证）
     * @param expirySeconds URL有效期（秒）
     * @return 预签名URL
     */
    String generatePresignedUploadUrl(String bucketName, String objectName, String md5, Long fileSize, int expirySeconds);
    
    /**
     * 移动文件（从临时目录移动到业务目录）
     * 
     * @param bucketName 存储桶名称
     * @param sourcePath 源路径（临时目录）
     * @param targetPath 目标路径（业务目录）
     * @return 是否成功
     */
    boolean moveFile(String bucketName, String sourcePath, String targetPath);
    
    /**
     * 删除文件
     * 
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 是否成功
     */
    boolean deleteFile(String bucketName, String objectName);
    
    /**
     * 检查文件是否存在
     * 
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 是否存在
     */
    boolean fileExists(String bucketName, String objectName);
    
    /**
     * 获取存储类型名称
     * 
     * @return 存储类型（minio 或 oss）
     */
    String getStorageType();
}

