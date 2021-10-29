package com.zhiyiyo.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "settings/user/login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "settings/user/signup";
    }
}
