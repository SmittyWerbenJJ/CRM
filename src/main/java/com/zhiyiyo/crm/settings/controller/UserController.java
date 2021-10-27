package com.zhiyiyo.crm.settings.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.exception.LoginException;
import com.zhiyiyo.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/settings/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param loginAct 用户名
     * @param loginPwd 加密一次的密码
     * @return 登陆异常消息
     * @throws LoginException 登录失败异常
     */
    @PostMapping("/login")
    public String login(HttpSession session, String loginAct, String loginPwd) throws LoginException {
        User user = userService.login(loginAct, loginPwd);
        session.setAttribute("user", user);
        // 必须写一个处理器来处理这个请求，因为放在 templates 里面的资源不能被浏览器直接访问
        return "redirect:/workbench/index.html";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
