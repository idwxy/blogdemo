package com.idwxy.blogdemo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.idwxy.blogdemo.security.IsUser;
import com.idwxy.blogdemo.service.ContentService;
import com.idwxy.blogdemo.service.UserService;
import com.idwxy.blogdemo.util.Result;
import com.idwxy.blogdemo.util.Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@IsUser
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private CacheManager cacheManager;

    @ApiOperation("更新用户的密码")
    @PutMapping("/password")
    public Result updateUser(@RequestParam String password,
                             @AuthenticationPrincipal UsernamePasswordAuthenticationToken token) {
        userService.updatePassword(Util.getCurrentUid(), password);
        // 更新完密码我们需要清除缓存中原有的老账号信息，不然用户仍然可以以老密码登入
        User user = (User) token.getPrincipal();
        cacheManager.getCache("jwt-cache").evict(user.getUsername());
        return new Result(HttpStatus.OK.value(), "修改成功", null);
    }

    @ApiOperation("获取当前用户发表的所有文章")
    @GetMapping("/contents")
    public Result getUsereContents(@RequestParam(required = false, defaultValue = "1", value = "page") int page,
                                   @RequestParam(required = false, defaultValue = "10", value = "page_size") int pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<List<Map<String, Object>>> articles = (Page) contentService.getContentsByAuthorId(Util.getCurrentUid());
        Map<String, Object> data = new HashMap<>(2);
        data.put("atricales", articles);
        data.put("count", articles.getPages());
        return new Result(HttpStatus.OK.value(), "获取成功", data);
    }
}
