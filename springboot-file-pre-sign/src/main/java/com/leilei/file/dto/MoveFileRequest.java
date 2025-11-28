package com.leilei.file.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 文件移动请求DTO
 */
@Data
public class MoveFileRequest {
    
    /**
     * 文件唯一Key
     */
    @NotBlank(message = "文件Key不能为空")
    private String fileKey;
    
    /**
     * 目标业务目录路径（例如：business/order/2024/01）
     */
    @NotBlank(message = "业务目录路径不能为空")
    private String businessPath;
}

