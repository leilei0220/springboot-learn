package com.leilei.controller;

import com.leilei.util.FileUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author lei
 * @date 2020/5/12 14:48
 * @desc
 */
@RestController
public class FileController {


  /**
   * 实现单个文件上传
   */
  @PostMapping("uploadOne")
  public Boolean fileUpload(@RequestParam("file") MultipartFile file) {
    return FileUtils.uploadOne(file);
  }

  /**
   * 多文件上传
   */
  @PostMapping("uploadMore")
  public Boolean fileUploadMore(HttpServletRequest request) {
    List<MultipartFile> files = ((MultipartHttpServletRequest) request)
        .getFiles("files");
    return FileUtils.uploadMore(files);
  }

  /**
   * 文件下载
   *
   * @param res
   * @param fileName 要下載的文件名
   */
  @GetMapping("download/{fileName}")
  public void testDownload(HttpServletResponse res, @PathVariable("fileName") String fileName) {
    FileUtils.download(res, fileName);
  }

  /**
   * 文件删除
   *
   * @param fileName 要删除的文件名
   * @return
   */
  @GetMapping("delete/{fileName}")
  public Boolean delete(@PathVariable("fileName") String fileName) {
    return FileUtils.deleteFile(fileName);
  }
}
