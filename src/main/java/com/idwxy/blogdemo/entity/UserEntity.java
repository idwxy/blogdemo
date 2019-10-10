package com.idwxy.blogdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Integer uid;

    private String userName;

    private String password;

    private String role;

    private Long registerTime;
}
