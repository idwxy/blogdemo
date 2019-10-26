package com.idwxy.blogdemo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.idwxy.blogdemo.entity.ContentEntity;
import com.idwxy.blogdemo.service.ContentService;
import com.idwxy.blogdemo.service.UserService;
import com.idwxy.blogdemo.util.Result;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// 开启参数校验
@Validated
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

  // Swagger 文档注释
    @ApiOperation(value = "用户注册", notes = "登入成功返回 token，此后每次请求需要携带 token")
    @PostMapping("/register")
    public Result register(@RequestParam @Length(min = 1, max = 100) String userName,
                           @RequestParam @Length(min = 1, max = 100) String password) {
        userService.register(userName, password);
        return new Result(HttpStatus.OK.value(), "注册成功", null);
    }

    @ApiOperation(value = "获取首页文章列表")
    @GetMapping("/contents")
    public Result index(@RequestParam(required = false, defaultValue = "1", value = "page") int page,
                        @RequestParam(required = false, defaultValue = "10", value = "page_size") int pageSize) {
        // 进行分页
        PageHelper.startPage(page, pageSize);
        Map<String, Object> data = new HashMap<>(2);
        Page<List<ContentEntity>> articles = (Page) contentService.getContents();
        data.put("articles", articles);
        data.put("count", articles.getPages());
        return new Result(200, "获取成功", data);
    }

    @ApiOperation("根据标签获取文章列表")
    @GetMapping("/tag/{tag}")
    public Result tags(@PathVariable @Length(min = 1) String tag,
                       @RequestParam(required = false, defaultValue = "1", value = "page") int page,
                       @RequestParam(required = false, defaultValue = "10", value = "page_size") int pageSize) {
        PageHelper.startPage(page,pageSize);
        Page<List<Map<String, Object>>> articles =(Page) contentService.getContentsByTag(tag);
        Map<String, Object> data = new HashMap<>(2);
        data.put("articles", articles);
        data.put("count", articles.getPages());
        return new Result(HttpStatus.OK.value(), "获取成功", data);
    }
}