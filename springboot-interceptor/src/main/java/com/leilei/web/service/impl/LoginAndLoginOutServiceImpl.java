package com.leilei.web.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSON;
import com.leilei.annotation.LoginUserParam;
import com.leilei.entity.User;
import com.leilei.entity.vo.LoginUser;
import com.leilei.entity.vo.TokenConstant;
import com.leilei.enums.EnumRedisKey;
import com.leilei.redis.RedisUtil;
import com.leilei.utils.ajax.JsonReturn;
import com.leilei.web.service.LoginAndLoginOutService;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @date 2020/5/9 10:14
 * @desc
 */
@Service
public class LoginAndLoginOutServiceImpl implements LoginAndLoginOutService {

  @Autowired
  private RedisUtil redisUtil;

  @Override
  public JsonReturn login(String account, String password) {
    if ("leilei".equals(account) && "123456".equals(password)) {
      //获取token 以及将用户信息存入到登录对象
      LoginUser loginUser = new LoginUser();
      loginUser.setLoginTime(System.currentTimeMillis());
      User user = new User("leilei", "123456");
      loginUser.setUser(user);
      loginUser.setToken(IdUtil.simpleUUID());
      //获取用户的token存储的键值
      String key = EnumRedisKey.key(EnumRedisKey.userLoginInfo_token.name(), user.getAccount());
      String oldToken = (String) redisUtil.get(key);
      if (StringUtils.isNotEmpty(oldToken)) {
        if (oldToken.length() == 32) {
          loginUser.setToken(oldToken);
        } else {
          redisUtil.del(oldToken);
        }
      }
      redisUtil.set(key, loginUser.getToken(), 60 * 60 * 24*3);
      //加密token数据
      String token = loginUser.getToken();
      byte[] oldBytes = token.getBytes();
      byte[] newBytes = Arrays.copyOf(oldBytes, 16);
      SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, newBytes);
      //token 内容加密
      redisUtil
          .set(loginUser.getToken(), aes.encryptHex(JSON.toJSONString(loginUser)), 60 * 60 * 24*3);
      return JsonReturn
          .buildSuccess(TokenConstant.getHeader() + ":" + loginUser.getToken(), "登录成功");
    }
    return JsonReturn.buildFailure("账户名或密码不正确");
  }

  /**
   * 退出接口
   *
   * @param request
   * @return
   */
  @Override
  public JsonReturn loginOut(HttpServletRequest request, @LoginUserParam LoginUser loginUser) {
    //获取到当前请求token键值
    String header = request.getHeader(TokenConstant.getHeader());
    try {
      //移除全部token
      redisUtil.del(header);
      String account = loginUser.getUser().getAccount();
      String key = EnumRedisKey.key(EnumRedisKey.userLoginInfo_token.name(), account);
      redisUtil.del(key);
      return JsonReturn.buildSuccess(null, account + "退出成功");
    } catch (Exception e) {
      e.printStackTrace();
      return JsonReturn.buildFailure("退出失败");
    }

  }
}
