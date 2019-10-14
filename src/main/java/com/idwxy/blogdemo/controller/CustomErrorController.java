package com.idwxy.blogdemo.controller;

import com.idwxy.blogdemo.util.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiIgnore
@RestController
public class CustomErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Result handleError(HttpServletRequest request, HttpServletResponse response) {
        return new Result(response.getStatus(),
                (String) request.getAttribute("javax.servlet.error.message"),null);
    }
}
