package com.leilei.file.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.leilei.file.dto.*;
import com.leilei.file.entity.FileMetadata;
import com.leilei.file.service.FileService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件服务控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {
    
    @Autowired
    private FileService fileService;
    
    /**
     * 获取预签名上传URL
     * 
     * @param request 请求参数（文件名、MD5、文件大小）
     * @return 预签名URL和文件Key
     */
    @PostMapping("/presign")
    public ResponseEntity<PreSignResponse> getPresignedUrl(@Validated @RequestBody PreSignRequest request) {
        log.info("获取预签名URL请求: fileName={}, md5={}, fileSize={}", 
                request.getFileName(), request.getMd5(), request.getFileSize());
        
        PreSignResponse response = fileService.generatePresignedUrl(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 通知文件上传完成
     * 
     * @param request 请求参数（文件Key）
     * @return 操作结果
     */
    @PostMapping("/upload-complete")
    public ResponseEntity<Result> notifyUploadComplete(@Validated @RequestBody UploadCompleteRequest request) {
        log.info("文件上传完成通知: fileKey={}", request.getFileKey());
        
        boolean success = fileService.notifyUploadComplete(request);
        if (success) {
            return ResponseEntity.ok(new Result(true, "文件上传完成"));
        } else {
            return ResponseEntity.ok(new Result(false, "文件上传完成通知失败"));
        }
    }
    
    /**
     * 移动文件到业务目录
     * 
     * @param request 请求参数（文件Key、业务目录路径）
     * @return 操作结果
     */
    @PostMapping("/move")
    public ResponseEntity<Result> moveFile(@Validated @RequestBody MoveFileRequest request) {
        log.info("移动文件请求: fileKey={}, businessPath={}", 
                request.getFileKey(), request.getBusinessPath());
        
        boolean success = fileService.moveFileToBusiness(request);
        if (success) {
            return ResponseEntity.ok(new Result(true, "文件移动成功"));
        } else {
            return ResponseEntity.ok(new Result(false, "文件移动失败"));
        }
    }
    
    /**
     * 获取文件信息
     * 
     * @param fileKey 文件Key
     * @return 文件元数据
     */
    @GetMapping("/info/{fileKey}")
    public ResponseEntity<FileMetadata> getFileInfo(@PathVariable String fileKey) {
        FileMetadata metadata = fileService.getFileMetadata(fileKey);
        if (metadata != null) {
            return ResponseEntity.ok(metadata);
        } else {
            return ResponseEntity.notFound().build();
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
            return java.util.Base64.getEncoder().encodeToString(md5Bytes);
        } catch (Exception e) {
            log.warn("MD5格式转换失败，使用原始值: {}", hexMd5, e);
            return hexMd5;
        }
    }
    
    /**
     * 测试接口：上传文件并生成getPresignedUrl所需的参数
     * 该接口会计算文件的MD5、文件大小等信息，返回PreSignRequest对象
     * 然后可以使用返回的参数调用 /api/file/presign 接口
     * 
     * @param file 上传的文件
     * @return PreSignRequest对象（包含fileName、md5、fileSize）
     */
    @PostMapping("/test/calculate-file-info")
    public ResponseEntity<PreSignRequest> calculateFileInfo(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            // 获取文件名
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                fileName = "unknown";
            }
            
            // 获取文件大小
            long fileSize = file.getSize();
            
            // 计算文件MD5（十六进制格式）
            String md5Hex;
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                md5Hex = DigestUtil.md5Hex(inputStream);
            } finally {
                IoUtil.close(inputStream);
            }
            
            // 构建PreSignRequest对象
            PreSignRequest request = new PreSignRequest();
            request.setFileName(fileName);
            request.setMd5(md5Hex);
            request.setFileSize(fileSize);
            
            log.info("文件信息计算完成: fileName={}, md5Hex={}, md5Base64={}, fileSize={}", fileName, md5Hex, hexMd5ToBase64(md5Hex), fileSize);
            
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            log.error("计算文件信息失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    
    /**
     * MD5转换响应DTO
     */
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class Md5ConvertResponse {
        /**
         * 十六进制MD5
         */
        private String hexMd5;
        
        /**
         * Base64编码的MD5（用于Content-MD5请求头）
         */
        private String base64Md5;
    }
    
    /**
     * 测试接口：模拟前端通过预签名URL上传文件
     * 该接口接收预签名URL和文件，使用PUT请求将文件上传到存储服务
     * 
     * @param uploadRequest 上传请求（包含预签名URL、文件、MD5）
     * @return 上传结果
     */
    @PostMapping("/test/upload-by-presigned-url")
    public ResponseEntity<UploadResult> uploadByPresignedUrl(@ModelAttribute PresignedUploadRequest uploadRequest) {
        try {
            if (uploadRequest == null || uploadRequest.getFile() == null || uploadRequest.getFile().isEmpty()) {
                return ResponseEntity.badRequest().body(new UploadResult(false, "文件不能为空", null));
            }
            
            if (uploadRequest.getPresignedUrl() == null || uploadRequest.getPresignedUrl().isEmpty()) {
                return ResponseEntity.badRequest().body(new UploadResult(false, "预签名URL不能为空", null));
            }
            
            MultipartFile file = uploadRequest.getFile();
            String presignedUrl = uploadRequest.getPresignedUrl();
            String md5 = uploadRequest.getMd5();
            
            log.info("开始通过预签名URL上传文件: url={}, fileName={}, fileSize={}, md5={}", 
                    presignedUrl, file.getOriginalFilename(), file.getSize(), md5);
            
            // 读取文件内容
            byte[] fileBytes = file.getBytes();
            
            // 构建HTTP PUT请求
            HttpRequest request = HttpRequest.put(presignedUrl)
                    .body(fileBytes)
                    .contentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
            
            // 如果提供了MD5，添加到请求头
            // MinIO需要Base64编码的MD5值
            if (md5 != null && !md5.isEmpty()) {
                String base64Md5 = hexMd5ToBase64(md5);
                request.header("Content-MD5", base64Md5);
                log.debug("上传文件MD5转换: 十六进制={}, Base64={}", md5, base64Md5);
            }
            
            // 设置Content-Length
            request.contentLength(fileBytes.length);
            
            // 执行上传
            HttpResponse response = request.execute();
            
            int statusCode = response.getStatus();
            boolean success = statusCode >= 200 && statusCode < 300;
            
            if (success) {
                log.info("文件上传成功: statusCode={}, fileName={}", statusCode, file.getOriginalFilename());
                return ResponseEntity.ok(new UploadResult(true, "文件上传成功", String.valueOf(statusCode)));
            } else {
                String errorMsg = "文件上传失败: HTTP " + statusCode;
                log.error("{}: response={}", errorMsg, response.body());
                return ResponseEntity.ok(new UploadResult(false, errorMsg, String.valueOf(statusCode)));
            }
            
        } catch (Exception e) {
            log.error("通过预签名URL上传文件异常", e);
            return ResponseEntity.ok(new UploadResult(false, "上传异常: " + e.getMessage(), null));
        }
    }
    
    /**
     * 预签名上传请求DTO
     */
    @Data
    public static class PresignedUploadRequest {
        /**
         * 预签名URL
         */
        private String presignedUrl;
        
        /**
         * 要上传的文件
         */
        private MultipartFile file;
        
        /**
         * 文件MD5值（可选，如果提供会添加到请求头）
         */
        private String md5;
    }
    
    /**
     * 上传结果DTO
     */
    @Data
    @lombok.AllArgsConstructor
    public static class UploadResult {
        /**
         * 是否成功
         */
        private boolean success;
        
        /**
         * 消息
         */
        private String message;
        
        /**
         * HTTP状态码
         */
        private String statusCode;
    }
    
    /**
     * 通用响应结果类
     */
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class Result {
        private boolean success;
        private String message;
    }
}

