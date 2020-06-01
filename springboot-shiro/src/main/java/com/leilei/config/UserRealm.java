package com.leilei.config;

import com.leilei.entity.AuthUser;
import com.leilei.entity.Permission;
import com.leilei.service.IAuthPermissionService;
import com.leilei.service.IAuthUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 21:05
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private IAuthUserService authUserService;
    @Autowired
    private IAuthPermissionService authPermissionService;

    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AuthUser authUser = (AuthUser) principalCollection.getPrimaryPrincipal();
        Long userId = authUser.getId();
        //查询出权限
        List<Permission> permissions = authPermissionService.findPermissionById(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予相关权限
        for (Permission permission : permissions) {
            info.addStringPermission(permission.getSn());
        }
        return info;
    }

    /**
     * 认证方法
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //封装为登录认证的token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //先查询账号 返回null 会自动抛出账号不存在信息
        AuthUser authUser = authUserService.findOneByName(username);
        if (authUser == null) {
            return null;
        }
        //比对密码 根据账户进行添加盐值比对
        return new SimpleAuthenticationInfo(authUser,
                authUser.getPassword(),
                ByteSource.Util.bytes(authUser.getUsername()),
                getName());
    }
}
