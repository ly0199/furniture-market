package com.furniture.market.controller;

import com.furniture.market.security.ShiroManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 主控制器
 *
 * @author Lijq
 */
@Slf4j
@Controller
public class HomeController {

    /**
     * 首页
     *
     * @param model ModelMap
     * @return String 首页页面
     */
    @RequestMapping(value = {"", "/", "/index"})
    public String index(ModelMap model) {
        return "index";
    }


    /**
     * 登录页面
     *
     * @param model   ModelMap
     * @param message 信息
     * @return 登录页面
     */
    @GetMapping(value = "/login")
    public String login(ModelMap model, @ModelAttribute(name = "message") String message) {
        if (StringUtils.isNotBlank(message)) {
            model.put("message", message);
        }
        return "login";
    }


    @PostMapping(value = "/login")
    public String login(RedirectAttributes model, @RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            model.addAttribute("message", "用户名或密码不能为空");
            return "redirect:/login";
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取当前的subject
        Subject currentUser = ShiroManager.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            log.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            log.info("对用户[" + username + "]进行登录验证..验证通过");

            ShiroManager.getSession().setAttribute("userInfo", ShiroManager.getAccount());
            ShiroManager.getSession().setAttribute("loginName", ShiroManager.getAccount().getUsername());
        } catch (UnknownAccountException uae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            model.addAttribute("message", "用户名不存在");
        } catch (IncorrectCredentialsException ice) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            model.addAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            model.addAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            model.addAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            model.addAttribute("message", "用户名或密码不正确");
        }

        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            return "redirect:/index";
        } else {
            token.clear();
            return "redirect:/login";
        }
    }


    @GetMapping(value = "/logout")
    public String logout(ModelMap model) {
        SecurityUtils.getSubject().logout();
        return "login";
    }

}
