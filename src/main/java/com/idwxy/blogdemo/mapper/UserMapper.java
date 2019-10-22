package com.idwxy.blogdemo.mapper;

import com.idwxy.blogdemo.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

// 声明 MyBatis 的 Mapper
@Mapper
// Spring Boot 的注解，是这个文件能够被扫描到
@Repository
public interface UserMapper {

    @Insert("INSERT INTO 'user' ('username', 'password', 'register_time')" +
            "VALUES (#{userName}, #{password}, #{registerTime})")
    int createUser(@Param("userName") String userName, @Param("password") String password,
                   @Param("registerTime") Long registerTime);

    @Results(id = "userMap", value = {
            @Result(property = "uid", column = "uid"),
            @Result(property = "userName", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role"),
            @Result(property = "registerTime", column = "register_time", jdbcType = JdbcType.BIGINT)
    })
    @Select("SELECT * FROM 'user' WHERE 'username' = #{userName} AND 'password' = #{password} ")
    UserEntity findByUserNameAndPassword(@Param("userName") String userName,
                                         @Param("password") String password);

    @Select("SELECT * FROM 'user' WHERE 'username' = #{userName}")
    @ResultMap("userMap")
    UserEntity findByUserName(@Param("userName") String userName);

    @Update("UPDATE `user` SET `password`=#{password} WHERE `uid`=#{uid}")
    void updatePassword(@Param("uid") int uid, @Param("password") String password);
}
