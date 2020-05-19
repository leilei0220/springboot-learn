package com.leilei.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @date 2020/5/19 17:00
 * @desc 项目 pom 文件中的信息
 */
@ConfigurationProperties("myapplication")
@Data
@Component
public class ApplicationInfo {

  private String groupId;
  private String artifactId;
  private String version;
  private String name;
  private String description;
}
