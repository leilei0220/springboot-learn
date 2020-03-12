package com.leilei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/3/12 12:08
 * @desc:
 */
@Component
@ConfigurationProperties(prefix = "qrcode.info")
public class QrCodeConfig {

  /**
   * 二维码编码
   */
  private static String charset;
  /**
   * 二维码宽度
   */
  private static Integer qrWidth;
  /**
   * 二维码高度
   */
  private static Integer qrHeight;
  /**
   * logo宽度
   */
  private static Integer logoWidth;
  /**
   * logo高度
   */
  private static Integer logoHeight;
  /**
   * 二维码图片类型 png jpg
   */
  private static String qrPicType;

  public static String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    QrCodeConfig.charset = charset;
  }

  public static Integer getQrWidth() {
    return qrWidth;
  }

  public void setQrWidth(Integer qrWidth) {
    QrCodeConfig.qrWidth = qrWidth;
  }

  public static Integer getQrHeight() {
    return qrHeight;
  }

  public void setQrHeight(Integer qrHeight) {
    QrCodeConfig.qrHeight = qrHeight;
  }

  public static Integer getLogoWidth() {
    return logoWidth;
  }

  public void setLogoWidth(Integer logoWidth) {
    QrCodeConfig.logoWidth = logoWidth;
  }

  public static Integer getLogoHeight() {
    return logoHeight;
  }

  public void setLogoHeight(Integer logoHeight) {
    QrCodeConfig.logoHeight = logoHeight;
  }

  public static String getQrPicType() {
    return qrPicType;
  }

  public void setQrPicType(String qrPicType) {
    QrCodeConfig.qrPicType = qrPicType;
  }
}
