package com.leilei.genreator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leilei
 */
@Slf4j
public class CodeGenerator {
    /**
     * 连接名
     * 8 版本驱动连接后缀 charset=utf8mb4&useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=Asia/Shanghai";
     */
    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/mybatis-plus3?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
    /**
     * 驱动
     */
    public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    /**
     * 数据库账户
     */
    public static final String USERNAME = "root";
    /**
     * 数据库密码
     */
    public static final String PASSWORD = "root";
    /**
     * 需要生成的表 可多张 user
     */
    public static final String[] TABLE = {"role"};
    /**
     * 项目父路径
     */
    public static final String PARENT_PACKAGE = "com.leilei";
    /**
     * 创建者名字
     */
    public static final String Author = "leilei";
    /**
     * 生成mapper接口路径 若想所有文件在一个包下即去掉 one ，，two   直接mapper即可
     */
    public static final String MAPPER_PACKAGE = "mapper.three";
    /**
     * 生成实体类路径  若想所有文件在一个包下去掉 one ，，two   直接entity即可
     */
    public static final String ENTITY_PACKAGE = "entity.three";
    /**
     * 生成Mapper.xml路径
     */
    public static final String MAPPER_XML_PACKAGE = "/src/main/resources/com/leilei/mapper/three/";
    /**
     * 生成entity路径
     */
    public static final String ENTIEY_PACKAGE = "/src/main/java/com/leilei/entity/three/";


    public static void main(String[] args) throws InterruptedException {

        //项目根目录  不可修改
        String projectPath = System.getProperty("user.dir");
        log.info("项目根路径" + projectPath);
        //用来获取Mybatis-Plus.properties文件的配置信息
//        ResourceBundle rb = ResourceBundle.getBundle("genreator");

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);
        // 开启 activeRecord 模式
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap 映射图
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        gc.setAuthor(Author);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        //注意Mysql 驱动版本问题
        dsc.setDriverName(DRIVER_CLASS_NAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setUrl(JDBC_URL);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        /** 此处可以修改为您的表前缀，如果没有，注释掉即可*/
        //strategy.setTablePrefix(new String[] { "t_" });
        /** 表名生成策略*/
        strategy.setNaming(NamingStrategy.underline_to_camel);
        /** 需要生成的表*/
        strategy.setInclude(TABLE);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PARENT_PACKAGE);
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity(ENTITY_PACKAGE);
        pc.setMapper(MAPPER_PACKAGE);
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();

        // 调整 xml 生成目录演示
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {

                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + MAPPER_XML_PACKAGE + tableInfo.getEntityName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 调整 query 生成目录
/*        focList.add(new FileOutConfig("/templates/query.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rb.getString("OutputDomainDir")+ "/cn/leilei/query/" + tableInfo.getEntityName() + "Query.java";
            }
        });*/

        //调整 entity 生成目录
        focList.add(new FileOutConfig("/templates/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + ENTIEY_PACKAGE + tableInfo.getEntityName() + ".java";
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        //tc.setController("/templates/controller.java.vm");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        tc.setEntity(null);
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();
    }


}
