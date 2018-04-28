package com.furniture.market.security;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Lijq
 * @date 2018/4/10 10:24
 * @description
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 拦截器
        Map<String, String> filerChainDefinitionMap = new LinkedHashMap<>();

        // 配置不会被拦截的链接 顺序判断
        filerChainDefinitionMap.put("/basic/**", "anon");
        filerChainDefinitionMap.put("/scripts/**", "anon");
        filerChainDefinitionMap.put("/resources/**", "anon");

        // 登录相关页面不会被拦截
        filerChainDefinitionMap.put("/login/**", "anon");

        // 配置退出
        filerChainDefinitionMap.put("/logout", "logout");

        // 配置过滤链
        // authc:所有URL都必须认证通过才可以访问; anon：所有url都可以匿名访问
        filerChainDefinitionMap.put("/**", "authc");

        // 配置登录，如果不配置则会自动寻找web工程根目录下的"login.html"页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 配置登录后，跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");

        // 配置未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 配置过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filerChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 用户权限认证
        securityManager.setRealm(shiroUserRealm());
        // sessionManager
        securityManager.setSessionManager(defaultWebSessionManager());
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        // 缓存
        //securityManager.setCacheManager();
        return securityManager;
    }

    @Bean
    public ShiroUserRealm shiroUserRealm() {
        ShiroUserRealm shiroUserRealm = new ShiroUserRealm();
        return shiroUserRealm;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
     * @param securityManager securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie对象;
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // <!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }


    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return ShiroDialect
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * @see DefaultWebSessionManager
     * @return
     */
    @Bean(name="sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setCacheManager(cacheManager());
        //超时时间
        sessionManager.setGlobalSessionTimeout(1800000);
        //定时清除无效的session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //半个小时清理一次失效的session
        sessionManager.setSessionValidationInterval(1800000);
        //删除无效的session
        sessionManager.setDeleteInvalidSessions(true);
        //sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }
}
