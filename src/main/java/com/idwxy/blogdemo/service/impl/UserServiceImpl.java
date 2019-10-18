package com.idwxy.blogdemo.service.impl;

import com.idwxy.blogdemo.entity.UserEntity;
import com.idwxy.blogdemo.mapper.UserMapper;
import com.idwxy.blogdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(String userName, String password) {
        UserEntity userEntity = userMapper.findByUserName(userName);
        // 使用 Spring 自带的 Assert，这里抛出的异常会被 ExceptionController 捕获并处理返回给前端
        Assert.isNull(userEntity, "该用户名已被注册");
        // 对用户的密码进行加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        int status = userMapper.createUser(userName, encoder.encode(password), System.currentTimeMillis());
        Assert.isTrue(status == 1, "注册失败");
    }

    @Override
    public UserEntity findUser(String userName) {
        UserEntity userEntity = userMapper.findByUserName(userName);
        Assert.notNull(userEntity, "该用户不存在");
        return userEntity;
    }

    @Override
    public UserEntity login(String userName, String password) {
        UserEntity userEntity = userMapper.findByUserNameAndPassword(userName, password);
        Assert.notNull(userEntity, "用户名或密码错误");
        return userEntity;
    }
}
