package com.idwxy.blogdemo.security;

import com.idwxy.blogdemo.entity.UserEntity;
import com.idwxy.blogdemo.mapper.UserMapper;
import com.idwxy.blogdemo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.findByUserName(userName);
        if (userEntity == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 存储当前线程的 uid 到 ThreadLocal 中
        Util.setCurrentUid(userEntity.getUid());
        return new User(userEntity.getUserName(), userEntity.getPassword(),
                AuthorityUtils.createAuthorityList(userEntity.getRole()));
    }
}
