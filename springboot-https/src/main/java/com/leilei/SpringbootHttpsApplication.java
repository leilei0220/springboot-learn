package com.leilei;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootHttpsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootHttpsApplication.class, args);
  }

  /**
   * 下方两个bean配置后 项目同时支持 http  和 https 两种协议
   * @return
   */
  @Bean
  public TomcatServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    tomcat.addAdditionalTomcatConnectors(httpConnector());
    return tomcat;
  }

  @Bean
  public Connector httpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setPort(8080);
    return connector;
  }

}
