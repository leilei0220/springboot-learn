package com.leilei.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lei
 * @date 2020/5/12 14:17
 * @desc 添加虚拟映射路径  以   FileConfigBean.getUrlPath() + "/**" 访问的静态资源 都会去  "file:"+ FileConfigBean.getUploadPath() 寻找
 * 若部署在linux 中 需注意更改 FileConfigBean.getUploadPath() 的路径  linux 上可没有 CDEFG 盘符  exmaple: file:/home/app/
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //可以添加多个   registry.
    registry.addResourceHandler(FileConfigBean.getUrlPath() + "/**")
        .addResourceLocations("file:"+ FileConfigBean.getUploadPath());
  }
}
