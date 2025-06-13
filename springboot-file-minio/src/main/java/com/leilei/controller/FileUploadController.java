package com.leilei.controller;

import com.leilei.entity.ChunkUploadRequest;
import com.leilei.entity.FileInfo;
import com.leilei.entity.ResultVO;
import com.leilei.service.FileService;
import com.leilei.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @author lei
     * @date 2025-06-12 15:37:53
     * @param file 当前文件
     * @param fileMd5 文件md5 （如果是分片，则这里是完整文件的MD5值）
     * @param fileName 文件名 （如果是分片，则这里是完整文件的名字）
     * @param fileSize 文件大小 (如果是分片，则这里是完整文件的大小)
     * @param chunkIndex 分片索引
     * @param chunkSize 分片大小
     * @param totalChunks 分片总数
     * @return ResultVO<String>
     */
    @PostMapping("/upload")
    public ResultVO<String> upload(@RequestParam("file") MultipartFile file,
                                        @RequestParam("fileMd5") String fileMd5,
                                        @RequestParam("chunkIndex") Integer chunkIndex,
                                        @RequestParam("chunkSize") Long chunkSize,
                                        @RequestParam("totalChunks") Integer totalChunks,
                                        @RequestParam("fileName") String fileName,
                                        @RequestParam("fileSize") Long fileSize) throws Exception {
        // 秒传判断
        if (fileService.isFileExists(fileMd5)) {
            return ResultVO.fail("File already uploaded");
        }
        String minoKey = "";
        // 小文件上传
        if (totalChunks == null || totalChunks == 1) {
            minoKey = "files/" + fileMd5 + "_" + fileName;
            minioUtil.upload(minoKey, file.getInputStream(), file.getSize(), file.getContentType());
            fileService.markUploadComplete(fileMd5, minoKey, fileName, fileSize,totalChunks);
            return ResultVO.ok(minoKey);
        }
        minoKey = "chunks/" + fileMd5 + "/" + chunkIndex;
        if (minioUtil.exists(minoKey)) {
            return ResultVO.fail("Chunk already exists");
        }
        minioUtil.upload(minoKey, file.getInputStream(), file.getSize(), file.getContentType());
        fileService.saveChunk(fileMd5, chunkIndex, chunkSize);
        // 如果所有分片都上传完成，进行合并
        if (fileService.isUploadComplete(fileMd5, totalChunks)) {
            List<String> chunkPaths = fileService.getChunkPaths(fileMd5, totalChunks);
            minoKey = "files/" + fileMd5 + "_" + fileName;
            minioUtil.merge(minoKey, chunkPaths);
            fileService.markUploadComplete(fileMd5, minoKey, fileMd5, fileSize, totalChunks);
        }
        return ResultVO.ok(minoKey);
    }

    @GetMapping("/checkFile")
    public ResultVO<Boolean> checkFile(@RequestParam String fileMd5) {
        boolean exists = fileService.isFileExists(fileMd5);
        return ResultVO.ok(exists);
    }

    @GetMapping("/checkChunk")
    public ResultVO<Boolean> checkChunk(@RequestParam String fileMd5, @RequestParam int chunkIndex) {
        boolean exists = fileService.isChunkExists(fileMd5, chunkIndex);
        return ResultVO.ok(exists);
    }
}
