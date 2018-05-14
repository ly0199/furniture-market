package com.furniture.market.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * 自己构造的权限管理器，方便获取相关信息
 *
 * @author Lijq
 */
public class ShiroManager {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Account getAccount() {
        return (Account) getSubject().getPrincipal();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

}
