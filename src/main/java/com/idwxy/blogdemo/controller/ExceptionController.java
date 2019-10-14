package com.idwxy.blogdemo.controller;

import com.idwxy.blogdemo.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 声明为异常处理器
@RestControllerAdvice
public class ExceptionController {

    // 捕获 RuntimeException 的异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleException(RuntimeException e) {
        return new Result(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }
}
