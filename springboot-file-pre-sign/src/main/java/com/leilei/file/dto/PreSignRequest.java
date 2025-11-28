package com.leilei.file.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 预签名URL请求DTO
 */
@Data
public class PreSignRequest {
    
    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;
    
    /**
     * 文件MD5值
     */
    @NotBlank(message = "MD5值不能为空")
    private String md5;
    
    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    @Positive(message = "文件大小必须大于0")
    private Long fileSize;
}

