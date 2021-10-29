package com.zhiyiyo.crm.settings.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.exception.LoginException;
import com.zhiyiyo.crm.settings.exception.SignupException;
import com.zhiyiyo.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


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
     * @return 登陆消息
     * @throws LoginException 登录失败异常
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpSession session, String loginAct, String loginPwd) throws LoginException {
        User user = userService.login(loginAct, loginPwd);
        session.setAttribute("user", user);

        // 前端使用 ajax 发送请求，不能直接重定向，因为 ajax 只会拿到 HTML 的数据
        Map<String, Object> data = new HashMap<>();
        data.put("msg", "登陆成功");
        data.put("success", true);
        return data;
    }

    @PostMapping("/signup")
    @ResponseBody
    public Map<String, Object> signup(HttpSession session, String loginAct, String loginPwd, String name) throws SignupException {
        Map<String, Object> data = new HashMap<>();

        // 注册用户
        User user = userService.signup(loginAct, loginPwd, name);
        session.setAttribute("user", user);

        data.put("msg", "注册成功");
        data.put("success", true);
        return data;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
