package com.leilei.controller;

import com.leilei.entity.ChunkUploadRequest;
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
    @Autowired private FileService fileService;

    @PostMapping("/uploadChunk")
    public ResponseEntity<?> uploadChunk(  @RequestParam("file") MultipartFile file,
                                           @RequestParam("fileMd5") String fileMd5,
                                           @RequestParam("chunkIndex") Integer chunkIndex,
                                           @RequestParam("chunkSize") Long chunkSize,
                                           @RequestParam("totalChunks") Integer totalChunks,
                                           @RequestParam("fileName") String fileName,
                                           @RequestParam("fileSize") Long fileSiz) throws Exception {
        // 秒传判断
        if (fileService.isFileExists(fileMd5)) {
            return ResponseEntity.ok("File already uploaded");
        }

        String chunkObjectName = "chunks/" + fileMd5 + "/" + chunkIndex;
        if (minioUtil.exists(chunkObjectName)) {
            return ResponseEntity.ok("Chunk already exists");
        }

        minioUtil.upload(chunkObjectName, file.getInputStream(), file.getSize(),file.getContentType());

        fileService.saveChunk(fileMd5, chunkIndex, chunkSize);

        // 如果所有分片都上传完成，进行合并
        if (fileService.isUploadComplete(fileMd5, totalChunks)) {
            List<String> chunkPaths = fileService.getChunkPaths(fileMd5, totalChunks);
            String finalObjectName = "files/" + fileMd5 + "_" + fileName;
            minioUtil.merge(finalObjectName, chunkPaths);
            fileService.markUploadComplete(fileMd5, finalObjectName, fileMd5, fileSiz);
        }

        return ResponseEntity.ok("Chunk uploaded");
    }

    @GetMapping("/checkFile")
    public ResponseEntity<?> checkFile(@RequestParam String fileMd5) {
        boolean exists = fileService.isFileExists(fileMd5);
        HashMap<String, Object> map = new HashMap<>();
        map.put("exists", exists);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/checkChunk")
    public ResponseEntity<?> checkChunk(@RequestParam String fileMd5, @RequestParam int chunkIndex) {
        boolean exists = fileService.isChunkExists(fileMd5, chunkIndex);
        HashMap<String, Object> map = new HashMap<>();
        map.put("exists", exists);
        return ResponseEntity.ok(map);
    }
}
