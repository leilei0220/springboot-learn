package com.leilei.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

/**
 * @author lei
 * @date 2020/5/10 17:01
 * @desc
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @NotBlank(message = "账户不能为空")
  @Length(min = 11,max = 11,message = "账户长度限定为十一位")
  private String account;
  @NotBlank(message = "用户密码不能为空")
  @Length(min = 6,max = 12,message = "密码长度不正确5<密码<12")
  private String passWord;
  @Max(value = 65, message = "age应<=65")
  @Min(value = 18, message = "age应=>18")
  private Integer age;
  @NotBlank(message = "邮箱不为空")
  @Email(message = "邮件格式不对")
  private String email;

  private Boolean sex;

}
