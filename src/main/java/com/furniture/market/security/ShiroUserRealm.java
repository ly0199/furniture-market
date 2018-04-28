package com.furniture.market.security;

import com.furniture.market.entity.Admin;
import com.furniture.market.service.IAdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Lijq
 * @date 2018/4/10 10:20
 * @description
 */
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private IAdminService adminService;

    /**
     * 认证 Authentication 验证用户身份
     *
     * @param token token
     * @return {@link AuthenticationInfo}
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = String.valueOf(token.getPrincipal());

        Admin admin = adminService.get(username);
        if (admin == null) {
            throw new AuthenticationException("用户或密码错误");
        }

        Account account = new Account();
        account.setId(admin.getId());
        account.setUsername(admin.getUsername());
        account.setName(admin.getName());
        account.setPhone(admin.getPhone());

        return new SimpleAuthenticationInfo(account, admin.getPassword(), admin.getName());
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // TODO

        return authorizationInfo;
    }



}
