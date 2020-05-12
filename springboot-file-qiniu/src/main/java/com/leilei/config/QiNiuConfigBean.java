package com.leilei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @date 2020/5/12 17:20
 * @desc
 */
@ConfigurationProperties(prefix = "qiniu")
@Component
public class QiNiuConfigBean {

  private static String accessKey;
  private static String secretKey;
  private static String bucket;
  private static String cdnProfile;
  private static String protocol;

  public static String getAccessKey() {
    return accessKey;
  }

  public  void setAccessKey(String accessKey) {
    QiNiuConfigBean.accessKey = accessKey;
  }

  public static String getSecretKey() {
    return secretKey;
  }

  public  void setSecretKey(String secretKey) {
    QiNiuConfigBean.secretKey = secretKey;
  }

  public static String getBucket() {
    return bucket;
  }

  public  void setBucket(String bucket) {
    QiNiuConfigBean.bucket = bucket;
  }

  public static String getCdnProfile() {
    return cdnProfile;
  }

  public  void setCdnProfile(String cdnProfile) {
    QiNiuConfigBean.cdnProfile = cdnProfile;
  }

  public static String getProtocol() {
    return protocol;
  }

  public  void setProtocol(String protocol) {
    QiNiuConfigBean.protocol = protocol;
  }
}
