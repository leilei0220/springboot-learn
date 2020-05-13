package com.leilei.util;

import com.leilei.config.FileConfigBean;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lei
 * @date 2020/5/12 14:49
 * @desc 文件上传下载工具类
 */
@Slf4j
public class FileUtils {

  /**
   * 上传单个文件
   *
   * @param file
   * @return
   */
  public static Boolean uploadOne(MultipartFile file) {
    if (file.isEmpty()) {
      return false;
    }
    Long time = System.currentTimeMillis();
    String fullSpell = Chines2PingUtils.getFullSpell(file.getOriginalFilename());
    String fileName = time + fullSpell;
    int size = (int) file.getSize() / 1024;
    if (size<1){
      log.info("当前上传文件名：{}，上传时间戳：{},保存后文件名：{},-->文件大小为：{}B", file.getOriginalFilename(), time,
          fileName, file.getSize());
    }else {
      log.info("当前上传文件名：{}，上传时间戳：{},保存后文件名：{},-->文件大小为：{}KB", file.getOriginalFilename(), time,
          fileName, size);
    }
    File dest = new File(FileConfigBean.getUploadPath() + "/" + fileName);
    //判断文件父目录是否存在 不存在则创建
    if (!dest.getParentFile().exists()) {
      dest.getParentFile().mkdir();
    }
    try {
      file.transferTo(dest);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("单文件上传失败");
      return false;
    }
  }

  /**
   * 批量上传  实质就是 单文件上传 循环版罢了
   *
   * @param files
   * @return
   */
  public static Boolean uploadMore(List<MultipartFile> files) {

    if (files != null && files.size() > 0) {
      for (int i = 0; i < files.size(); i++) {
        MultipartFile file = files.get(i);
        if (!file.isEmpty()) {
          Long time = System.currentTimeMillis();
          String fullSpell = Chines2PingUtils.getFullSpell(file.getOriginalFilename());
          String fileName = time + fullSpell;
          int size = (int) file.getSize() / 1024;
          if (size<1){
            log.info("当前上传文件名：{}，上传时间戳：{},保存后文件名：{},-->文件大小为：{}B", file.getOriginalFilename(), time,
                fileName, file.getSize());
          }else {
            log.info("当前上传文件名：{}，上传时间戳：{},保存后文件名：{},-->文件大小为：{}KB", file.getOriginalFilename(), time,
                fileName, size);
          }
          File dest = new File(FileConfigBean.getUploadPath() + "/" + fileName);
          //判断文件父目录是否存在 不存在则创建
          if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
          }
          try {
            file.transferTo(dest);
            continue;
          } catch (IOException e) {
            e.printStackTrace();
            log.error("第{}个文件上传失败", i + 1);
            continue;
          }
        }
      }
      return true;
    }
    return false;
  }

  /**
   * 文件下载
   *
   * @param res
   * @param fileName 要下载的文件名
   * @return
   */
  public static void download(HttpServletResponse res, String fileName) {
    //响应头设置
    res.setHeader("content-type", "application/octet-stream");
    res.setContentType("application/octet-stream");
    res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    byte[] buff = new byte[1024];
    BufferedInputStream bis = null;
    OutputStream os = null;
    try {
      os = res.getOutputStream();
      bis = new BufferedInputStream(new FileInputStream(new File(FileConfigBean.getUploadPath()
          + fileName)));
      int i = bis.read(buff);
      while (i != -1) {
        os.write(buff, 0, buff.length);
        os.flush();
        i = bis.read(buff);
      }
      log.info("文件下载成功：{}", fileName);

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bis != null) {
        try {
          bis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 文件删除
   *
   * @param fileName 要删除的文件名
   * @return
   */
  public static Boolean deleteFile(String fileName) {
    File file = new File(FileConfigBean.getUploadPath() + fileName);
    if (file.exists()) {
      return file.delete();
    }
    return false;
  }
}
