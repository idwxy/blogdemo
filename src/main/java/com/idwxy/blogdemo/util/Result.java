package com.idwxy.blogdemo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    // 状态码
    private Integer status;
    // 状态信息
    private String msg;
    // 返回数据
    private Object data;
}
