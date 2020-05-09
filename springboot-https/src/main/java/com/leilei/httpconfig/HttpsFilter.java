package com.leilei.httpconfig;

import java.io.IOException;
import java.net.URL;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author lei
 * @date 2020/5/9 10:11
 * @desc 过滤器，将http 请求转发到https请求上来 重定向类型：307
 * 不配置此过滤器 那么当https 协议访问的时候  所有的post 请求均会变为get
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "HttpsFilter")
@Slf4j
public class HttpsFilter implements Filter {

  private static final String HTTPS = "https";
  private static final int HTTPS_PORT = 443;

  @Override
  public void destroy() {
      log.info("------------destroy HttpsFilter --------------");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    URL newUrl = null;
    if (request.getScheme().equals(HTTPS)) {
      chain.doFilter(request, response);
    } else {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      String queryString =
          httpRequest.getQueryString() == null ? "" : "?" + httpRequest.getQueryString();
      httpResponse.setStatus(307);
      String requestUrl = httpRequest.getRequestURL().toString();
      URL reqUrl = new URL(requestUrl + queryString);
      log.info("【original request-】 " + reqUrl.toString());
      newUrl = new URL(HTTPS, reqUrl.getHost(), HTTPS_PORT, reqUrl.getFile());
      //进行重定向
      log.info("【new request-】 " + newUrl.toString());
      httpResponse.setHeader("Location", newUrl.toString());
      httpResponse.setHeader("Connection", "close");
      //允许所有跨域请求
      httpResponse.addHeader("Access-Control-Allow-Origin", "*");
    }

  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    log.info("------------init HttpsFilter --------------");
  }

}