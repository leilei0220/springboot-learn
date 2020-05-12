package com.leilei.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @date 2020/5/12 14:18
 * @desc
 */
@ConfigurationProperties(prefix = "file")
@Component
public class FileConfigBean {
  /**图片上传后 需加入的访问前缀*/
  private static String urlPath;
  /**图片上传的路径*/
  private static String uploadPath;

  public static String getUrlPath() {
    return urlPath;
  }

  public  void setUrlPath(String urlPath) {
    FileConfigBean.urlPath = urlPath;
  }

  public static String getUploadPath() {
    return uploadPath;
  }

  public  void setUploadPath(String uploadPath) {
    FileConfigBean.uploadPath = uploadPath;
  }
}
