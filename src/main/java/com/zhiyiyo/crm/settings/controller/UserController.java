package com.zhiyiyo.crm.settings.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.exception.LoginException;
import com.zhiyiyo.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @return 登陆状态消息
     * @throws LoginException 登录失败异常
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpSession session, String loginAct, String loginPwd) throws LoginException {
        User user = userService.login(loginAct, loginPwd);
        session.setAttribute("user", user);

        // 返回 json 数据
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        data.put("msg", "登录成功");
        return data;
    }

    @RequestMapping("/logout.do")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login.html";
    }
}
