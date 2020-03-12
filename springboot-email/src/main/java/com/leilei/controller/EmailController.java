package com.leilei.controller;

import com.leilei.entity.ToEmail;
import com.leilei.support.ToEmailService;
import com.leilei.util.response.JsonReturn;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/3/12 15:07
 * @desc:
 */
@RestController
@RequestMapping("email")
public class EmailController {

  @Autowired
  private ToEmailService toEmailService;

  /**
   * 测试普通邮件发送
   */
  @PostMapping("common")
  public JsonReturn common(ToEmail toEmail) {
    return toEmailService.commonEmail(toEmail);
  }

  /**
   * html 类型邮件发送
   */
  @PostMapping("/html")
  public JsonReturn html(ToEmail toEmail) throws MessagingException {
    return toEmailService.htmlEmail(toEmail);
  }

  /**
   * 发送附件 邮件发送
   */
  @PostMapping("enclosure")
  public JsonReturn enclosureEmail(ToEmail toEmail, MultipartFile multipartFile) {
    return toEmailService.enclosureEmail(toEmail, multipartFile);
  }

  /**
   * 发送包含静态资源的文件
   *
   * @param toEmail 接收方
   * @param multipartFile 静态资源
   * @param resId 资源唯一Id(随意传，唯一即可）
   */
  @PostMapping("static")
  public JsonReturn Static(ToEmail toEmail, MultipartFile multipartFile, String resId) {
    return toEmailService.staticEmail(toEmail, multipartFile, resId);
  }
}
