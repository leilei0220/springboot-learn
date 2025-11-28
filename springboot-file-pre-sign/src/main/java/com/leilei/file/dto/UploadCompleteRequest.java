package com.leilei.file.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 上传完成通知请求DTO
 */
@Data
public class UploadCompleteRequest {
    
    /**
     * 文件唯一Key
     */
    @NotBlank(message = "文件Key不能为空")
    private String fileKey;
}

