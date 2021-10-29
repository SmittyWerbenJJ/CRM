package com.zhiyiyo.crm.settings.service.impl;

import com.zhiyiyo.crm.settings.dao.UserDao;
import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.exception.LoginException;
import com.zhiyiyo.crm.settings.exception.SignupException;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd) throws LoginException {
        String encyptPwd = DigestUtils.md5DigestAsHex((loginPwd + "zhiyiYo").getBytes());
        User user = userDao.login(loginAct, encyptPwd);

        // 验证用户的账号和密码
        if (user == null) {
            throw new LoginException("用户名或密码错误");
        }

        // 验证用户的锁定状态
        if ("0".equals(user.getLockState())) {
            throw new LoginException("用户被锁定");
        }

        // 验证过期时间
        String nowTime = DateTimeUtil.getSysTime();
        String expireTime = user.getExpireTime();
        if (expireTime != null && nowTime.compareTo(expireTime) > 0) {
            throw new LoginException("用户已过期");
        }

        // 验证 IP 地址

        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.findAllUsers();
    }

    @Override
    public User signup(String loginAct, String loginPwd, String name) throws SignupException {
        User user = new User();
        user.setId(UUIDUtil.getUUID());
        user.setName(name);
        user.setLoginAct(loginAct);
        user.setLoginPwd(DigestUtils.md5DigestAsHex((loginPwd + "zhiyiYo").getBytes()));
        user.setCreateTime(DateTimeUtil.getSysTime());

        // 注册用户
        boolean success = false;
        try {
            success = userDao.insertUser(user).equals(1);
        } catch (DuplicateKeyException e) {
            throw new SignupException("用户名已存在，请前辈更换用户名后再重新尝试");
        }

        if (!success) {
            throw new SignupException("遇到未知错误，请前辈稍后再试");
        }

        return user;
    }
}
