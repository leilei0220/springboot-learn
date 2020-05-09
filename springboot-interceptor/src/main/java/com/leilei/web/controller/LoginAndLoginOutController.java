package com.leilei.web.controller;

import com.leilei.annotation.LoginUserParam;
import com.leilei.annotation.NotNeedLogin;
import com.leilei.entity.vo.LoginUser;
import com.leilei.utils.ajax.JsonReturn;
import com.leilei.web.service.LoginAndLoginOutService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @date 2020/5/9 10:11
 * @desc 登录退出接口
 */
@RestController
public class LoginAndLoginOutController {

  @Autowired
  private LoginAndLoginOutService loginAndLoginOutService;

  @PostMapping("/login")
  public JsonReturn login(String account, String password) {
    return loginAndLoginOutService.login(account, password);
  }

  @PostMapping("/loginOut")
  public JsonReturn loginOut(HttpServletRequest request, @LoginUserParam LoginUser loginUser) {
    return loginAndLoginOutService.loginOut(request, loginUser);

  }


  @GetMapping("getMyInfo")
  public JsonReturn getMyInfo(@LoginUserParam LoginUser loginUser) {
    return JsonReturn.buildSuccess(loginUser);
  }
  @NotNeedLogin //此注解打上表示此接口 不需要登录即可访问
  @GetMapping("/test")
  public JsonReturn test() {
    return JsonReturn.buildSuccess("通过@NotNeedLogin注解 我成功匿名访问了");
  }
}
