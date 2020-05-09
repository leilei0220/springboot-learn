package com.leilei.annotation;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSON;
import com.leilei.entity.vo.LoginUser;
import com.leilei.entity.vo.TokenConstant;
import com.leilei.redis.RedisUtil;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author lei
 * @date 2020/5/9 9:52
 * @desc 自定义的 @loginUserParam 注解解析， 用来获取登录对象 实现 HandlerMethodArgumentResolver 复写其中的 两个方法
 */
//@Component(value = "TokenConvertUserResolver")
@Slf4j
public class LoginUserParamAnylysis implements HandlerMethodArgumentResolver {

  @Autowired
  private RedisUtil redisUtil;

  /**
   * 需要进行解析的参数 我这里就判断方法参数中是否包含 @loginUserParam注解 包含就进行解析
   *
   * @param methodParameter
   * @return 返回布尔值 为true 才会进入到下方的 resolveArgument 进行解析逻辑
   */
  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.hasParameterAnnotation(LoginUserParam.class);
  }

  /**
   * 解析方法
   *
   * @param methodParameter
   * @param modelAndViewContainer
   * @param nativeWebRequest
   * @param webDataBinderFactory
   * @return
   * @throws Exception
   */
  @Override
  public Object resolveArgument(MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) throws Exception {
    //获取http 请求对象
    HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
    // 取出其头部header中的token表示
    String token = request.getHeader(TokenConstant.getHeader());

    log.info("正在进行参数解析，用户token为：{}", token);
    if (StringUtils.isBlank(token)) {
      log.info("未请求头中获取到用户token-----无法进行解析");
      return null;
    }
    //获取到token中的内容  当前是加密状态
    String tokenJSon = (String) redisUtil.get(token);
    if (StringUtils.isBlank(tokenJSon)) {
      log.info("未从缓存中获取到用户token-----无法进行解析");
      return null;
    }
    //解密token数据
    byte[] oldBytes = token.getBytes();
    byte[] newBytes = Arrays.copyOf(oldBytes, 16);
    SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, newBytes);

    String userContent = aes.decryptStr(tokenJSon, CharsetUtil.CHARSET_UTF_8);
    //将解析的数据 封装到 LoginUser 实体中
    return JSON.parseObject(userContent, LoginUser.class);
  }

}
