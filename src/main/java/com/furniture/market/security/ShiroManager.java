package com.furniture.market.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * My shiro api
 *
 * @author Lijq
 * @date 2018/4/16 10:26
 * @description
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
