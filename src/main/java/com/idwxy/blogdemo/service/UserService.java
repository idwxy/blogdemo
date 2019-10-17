package com.idwxy.blogdemo.service;

import com.idwxy.blogdemo.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void register(String userName, String password);

    UserEntity findUser(String userName);

    UserEntity login(String userName, String password);
}
