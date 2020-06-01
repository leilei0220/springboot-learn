package com.leilei.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.leilei.entity.Permission;
import com.leilei.service.IAuthPermissionService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 21:04
 * @desc shiro 的核心配置类
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private IAuthPermissionService authPermissionService;

    /**
     * 认证授权 realm
     * @return
     */
    //1.realm
    @Bean(name = "getRealm")
    public UserRealm getRealm() {
        UserRealm userRealm = new UserRealm();
        //设置密码加密凭证，登录时会对密码进行加密匹配
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 凭证比较器-加密加盐加次数
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        hashedCredentialsMatcher.setHashIterations(10);
        return hashedCredentialsMatcher;
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 安全管理器 管理 realm
     * @param realm
     * @return
     */
    //2.DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("getRealm") Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入Realm
        securityManager.setRealm(realm);
        //注入记住我管理器 开启 rememberMe 功能
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 过滤工厂 根据配置进项过滤放行等
     * @param manager
     * @return
     */
    //3.ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager manager) {

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        //设置过滤规则
        Map<String, String> filterMap = new LinkedHashMap<>();
        //anon  不过滤 直接放行
        filterMap.put("/static/**", "anon");
        //权限拦截，查出所有权限 并进行全部拦截 ----如用户授权时 包含某些权限 则可以访问
        List<Permission> permissions = authPermissionService.findAll();
        for (Permission permission : permissions) {
            filterMap.put(permission.getUrl(),"perms["+permission.getSn()+"]");
        }
        // shiro的退出方法，会注销自己的认证
        filterMap.put("/logout", "logout");
        //设置登录方法
        factoryBean.setLoginUrl("/toLogin");

        //无权限时 进入此路径
        factoryBean.setUnauthorizedUrl("/unauthorized");

        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    /**
     * thymeleaf 整合 Shiro  使用 shiro 标签
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
