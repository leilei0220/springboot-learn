package com.leilei.file.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件元数据实体类
 */
@Data
@Table("file_metadata")
public class FileMetadata {
    
    /**
     * 文件唯一Key（主键）
     */
    @Id(keyType = KeyType.Generator, value = "uuid")
    private String fileKey;
    
    /**
     * 原始文件名
     */
    private String fileName;
    
    /**
     * 文件MD5值
     */
    private String md5;
    
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    
    /**
     * 文件在存储中的路径（临时目录）
     */
    private String tempPath;
    
    /**
     * 文件在存储中的路径（业务目录，移动后更新）
     */
    private String businessPath;
    
    /**
     * 存储类型：minio 或 oss
     */
    private String storageType;
    
    /**
     * 文件状态：UPLOADING-上传中, UPLOADED-已上传, MOVED-已移动到业务目录
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

