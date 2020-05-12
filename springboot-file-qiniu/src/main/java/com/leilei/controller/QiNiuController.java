package com.leilei.controller;

import com.leilei.util.QiNiuSupport;
import com.qiniu.common.QiniuException;
import java.io.File;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lei
 * @date 2020/5/12 18:05
 * @desc
 */
@RequestMapping("qiniu")
@RestController
public class QiNiuController {

  @Autowired
  private QiNiuSupport qiNiuSupport;

  /**
   * 根据文件名删除
   * @param name
   * @return
   * @throws QiniuException
   */
  @GetMapping("delete/{name}")
  public String delete(@PathVariable("name") String name) throws QiniuException {
    return qiNiuSupport.delete(name);
  }

  /**
   * 直接传输文件 到七牛云
   * @param file
   * @param fileName
   * @return
   * @throws Exception
   */
  @PostMapping("upload/{fileName}")
  public String upload(@RequestParam("file")MultipartFile file, @PathVariable("fileName") String fileName) throws Exception {
    File file1 = qiNiuSupport.multipartFileToFile(file);
    return qiNiuSupport.uploadFile(file1, fileName);
  }

  /**
   * 直接传输文件 到七牛云 读取文件类型 传输 MIME 保存
   * @param file
   * @param fileName
   * @return
   * @throws Exception
   */
  @PostMapping("uploadMime/{fileName}")
  public String uploadMIme(@RequestParam("file")MultipartFile file, @PathVariable("fileName") String fileName) throws Exception {
    File file1 = qiNiuSupport.multipartFileToFile(file);
    return qiNiuSupport.uploadFileMimeType(file1, fileName);
  }


  /**
   * 以流的形式进行上传
   * @param file
   * @param fileName
   * @return
   * @throws Exception
   */
  @PostMapping("uploadByStream/{fileName}")
  public String uploadInputStream(@RequestParam("file")MultipartFile file, @PathVariable("fileName") String fileName) throws Exception {
    InputStream inputStream = file.getInputStream();
    return qiNiuSupport.uploadFileInputStream(inputStream, fileName);
  }

}
