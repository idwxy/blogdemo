package com.idwxy.blogdemo.security;

import com.idwxy.blogdemo.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    // AuthenticationManager, UserDetailsService 会从 Spring Security 配置文件那里传过来
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    // 对请求进行判断
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        // 判断是否有 token，并且进行认证
        Authentication token = getAuthentication(request);
        // 认证失败
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        // 认证成功，把 token 存入 SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 获取 header 中的 Authorization 字段
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        // 拿到 Bearer 后面跟着的 token
        String token = header.split(" ")[1];
        // 通过 token 获取当前用户的用户名
        String userName = JwtUtil.getUserName(token);
        UserDetails userDetails = null;
        try {
            // 数据库查询此用户的信息
            userDetails = userDetailsService.loadUserByUsername(userName);
        } catch (UsernameNotFoundException e) {
            return null;
        }
        // 校验 token
        if (!JwtUtil.verify(token, userName, userDetails.getPassword())) {
            return null;
        }
        // Token 合法，这里返回 Security 的 UsernamePasswordAuthenticationToken，这里包含了用户的信息与权限
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
