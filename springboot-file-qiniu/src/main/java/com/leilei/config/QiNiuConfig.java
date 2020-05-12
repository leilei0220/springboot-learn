package com.leilei.config;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @date 2020/5/12 17:32
 * @desc
 */
@Configuration
public class QiNiuConfig {

  /**
   * 配置自己空间所在的区域 我这里是华南 （个人选择开通的存储区域）
   */
  @Bean
  public com.qiniu.storage.Configuration qiniuConfig() {
    return new com.qiniu.storage.Configuration(Zone.huanan());
  }

  /**
   * 构建一个七牛上传工具实例
   */
  @Bean
  public UploadManager uploadManager() {
    return new UploadManager(qiniuConfig());
  }

  /**
   * 认证信息实例
   *
   * @return
   */
  @Bean
  public Auth auth() {

    return Auth.create(QiNiuConfigBean.getAccessKey(), QiNiuConfigBean.getSecretKey());
  }

  /**
   * 构建七牛空间管理实例
   */
  @Bean
  public BucketManager bucketManager() {
    return new BucketManager(auth(), qiniuConfig());
  }

  @Bean
  public Gson gson() {
    return new Gson();
  }

}
