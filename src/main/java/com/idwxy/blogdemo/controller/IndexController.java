package com.idwxy.blogdemo.controller;

import com.idwxy.blogdemo.service.UserService;
import com.idwxy.blogdemo.util.Result;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// 开启参数校验
@Validated
public class IndexController {

    @Autowired
    private UserService userService;

    // Swagger 文档注释
    @ApiOperation(value = "用户注册", notes = "登入成功返回 token，此后每次请求需要携带 token")
    @PostMapping("/register")
    public Result register(@RequestParam @Length(min = 1, max = 100) String userName,
                           @RequestParam @Length(min = 1, max = 100) String password) {
        userService.register(userName, password);
        return new Result(HttpStatus.OK.value(), "注册成功", null);
    }
}
