package com.leilei.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChunkUploadRequest {
    private String fileMd5;
    private Integer chunkIndex;
    private Integer totalChunks;
    private Long chunkSize;
    private MultipartFile file;
    private String fileName;
    private Long fileSize;
}
