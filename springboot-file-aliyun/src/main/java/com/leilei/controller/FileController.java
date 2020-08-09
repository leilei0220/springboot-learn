package com.leilei.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.leilei.config.Result;
import com.leilei.support.AliOssFileSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/9 12:22
 * @desc
 */
@RestController
public class FileController {
    @Autowired
    private AliOssFileSupport aliOssFileSupport;

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return Result.ok(aliOssFileSupport.upload(multipartFile));
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/{fileName}")
    public Result<Boolean> deleteFile(@PathVariable("fileName") String fileName) {
        return Result.ok(aliOssFileSupport.deleteFile(fileName));
    }

    /**
     * 查询存储桶下的文件
     */
    @GetMapping("/findAll")
    public Result<List<OSSObjectSummary>> findAll() {
        List<OSSObjectSummary> fileList = aliOssFileSupport.findFileList();
        return Result.ok(fileList);
    }

    /**
     * 下载文件
     */
    @GetMapping("/{downFileName}")
    public Result down(@PathVariable("downFileName") String fileName, HttpServletResponse response) throws Exception {

        aliOssFileSupport.logDownload(fileName, response);
        return Result.ok();
    }
}
