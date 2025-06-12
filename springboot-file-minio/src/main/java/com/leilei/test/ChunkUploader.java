package com.leilei.test;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.io.resource.MultiResource;
import cn.hutool.core.util.HexUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

@Slf4j
@Component
public class ChunkUploader {

    private final String uploadUrl = "http://localhost:8080/file/uploadChunk"; // 改为你的后端接口地址

    public void uploadInChunks(File file, int chunkSizeMB) throws Exception {
        int chunkSize = chunkSizeMB * 1024 * 1024;
        long fileSize = file.length();
        String fileMd5 = getFileMd5(file);
        String fileName = file.getName();
        int totalChunks = (int) Math.ceil((double) fileSize / chunkSize);

        log.info("开始上传文件：{}, 共{}分片, MD5={}", fileName, totalChunks, fileMd5);
        try (FileInputStream fis = new FileInputStream(file)) {
            for (int i = 0; i < totalChunks; i++) {
                int currentChunkSize = (int) Math.min(chunkSize, fileSize - (long)i * chunkSize);
                byte[] curChunkDate = new byte[currentChunkSize];
                int read = fis.read(curChunkDate);
                if (read != currentChunkSize) {
                    throw new IOException("读取分片失败");
                }
                InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(curChunkDate),fileName);
                MultiResource multiResource = new MultiResource(Collections.singleton(inputStreamResource));
                HttpRequest request = HttpRequest.post(uploadUrl)
                        .form("fileMd5", fileMd5)
                        .form("chunkIndex", i)
                        .form("totalChunks", totalChunks)
                        .form("chunkSize", curChunkDate.length)
                        .form("fileName", fileName)
                        .form("fileSize", fileSize)
                        .form("file",multiResource);
                String response = request.execute().body();
                log.info("上传分片 {} / {} 结果{}", i + 1, totalChunks,response);
            }
        }
    }

    private String getFileMd5(File file) throws Exception {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                md5Digest.update(byteArray, 0, bytesCount);
            }
        }
        byte[] digest = md5Digest.digest();
        return HexUtil.encodeHexStr(digest);
    }
}
