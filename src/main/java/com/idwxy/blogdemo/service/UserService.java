package com.idwxy.blogdemo.service;

import com.idwxy.blogdemo.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    // 注册
    void register(String userName, String password);

    // 通过用户名查询用户
    UserEntity findUser(String userName);

    // 登入操作
    UserEntity login(String userName, String password);

    // 更新指定用户的密码
    void updatePassword(int uid, String password);
}
