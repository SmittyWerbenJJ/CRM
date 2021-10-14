package com.zhiyiyo.crm.settings.dao;

import java.util.List;

import com.zhiyiyo.crm.settings.entity.User;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
    /**
     * 用户登录
     *
     * @param loginAct 用户名
     * @param loginPwd 密码
     * @return 如果查询到用户则将其返回，否则返回 null
     */
    User login(@Param("loginAct") String loginAct, @Param("loginPwd") String loginPwd);

    /**
     * 添加用户
     * @param user 用户
     * @return 受影响的行数
     */
    int insertUser(User user);

    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> findAllUsers();
}
