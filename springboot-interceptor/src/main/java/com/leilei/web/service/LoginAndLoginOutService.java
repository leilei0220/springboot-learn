package com.leilei.web.service;

import com.leilei.annotation.LoginUserParam;
import com.leilei.entity.vo.LoginUser;
import com.leilei.utils.ajax.JsonReturn;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lei
 * @date 2020/5/9 10:14
 * @desc
 */
public interface LoginAndLoginOutService {

  /**
   * 登录接口
   * @param account
   * @param password
   * @return
   */
  JsonReturn login(String account, String password);

  /**
   * 退出接口
   * @param request
   * @return
   */
  JsonReturn loginOut(HttpServletRequest request,@LoginUserParam LoginUser loginUser);
}
