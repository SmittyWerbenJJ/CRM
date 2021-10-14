package com.zhiyiyo.crm.settings.service;

import java.util.List;

import com.zhiyiyo.crm.settings.entity.User;

import com.zhiyiyo.crm.settings.exception.LoginException;

public interface UserService {
    /**
     * 用户登录
     *
     * @param loginAct 用户名
     * @param loginPwd 由前端传过来的第一次 MD5 加密后的密码
     * @return 如果登录成功则将用户返回，否则抛出异常
     */
    User login(String loginAct, String loginPwd) throws LoginException;


    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<User> getUserList();
}
