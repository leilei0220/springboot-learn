package com.leilei.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预签名URL响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreSignResponse {
    
    /**
     * 预签名上传URL
     */
    private String uploadUrl;
    
    /**
     * 文件唯一Key
     */
    private String fileKey;
}

