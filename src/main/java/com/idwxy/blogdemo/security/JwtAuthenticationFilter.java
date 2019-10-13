package com.idwxy.blogdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idwxy.blogdemo.util.JwtUtil;
import com.idwxy.blogdemo.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // 过滤器一定要设置 AuthenticationManager，这里的 AuthenticationManager 从 Security 配置的时候传入
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        //运行父类 UsernamePasswordAuthenticationFilter 的构造方法，能够设置此滤器指定方法为 POST [\login]
        super();
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {
        // 从请求的 POST 中拿去 userNam 和 password 两个字段进行登入
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
        // 设置一些客户 IP 信息
        setDetails(request, token);
        // 交给 AuthenticationManager 进行鉴权
        return getAuthenticationManager().authenticate(token);
    }

    // 鉴权成功进行的操作，我们这里设置返回加密后的 token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
//        handlerResponse(request, response, authentication, null);
    }

    // 鉴权失败进行的操作，我们这里就返回 用户名或密码错误 的信息
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException{
//        handleResponse(request, response, null, failed);
    }

    private void handleResponse(HttpServletRequest request, HttpServletResponse response, Authentication authResult,
                                AuthenticationException failed) throws IOException, ServletException{
        // 此处使用 Spring 自带的 Jackson 来把实体类转换为 json
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Result();
        // 返回请求 content-type 设置为支持 json
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (authResult != null) {
            // 处理登入成功请求
            User user = (User) authResult.getPrincipal();
            String token = JwtUtil.sign(user.getUsername(), user.getPassword());
            result.setStatus(HttpStatus.OK.value());
            result.setMsg("登入成功");
            result.setData("Bearer" + token);
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(mapper.writeValueAsString(result));
        } else {
            // 处理登入失败请求
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            result.setMsg("用户名或密码错误");
            result.setData(null);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(mapper.writeValueAsString(result));
        }
    }
}
