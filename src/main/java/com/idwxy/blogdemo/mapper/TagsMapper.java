package com.idwxy.blogdemo.mapper;

import com.idwxy.blogdemo.entity.TagEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TagsMapper {

    // 创建标签，创建成功返回自增 id
    @Insert("INSERT INTO `tags` (`name`) VALUES (#{tag.name})")
    @Options(useGeneratedKeys = true, keyProperty = "tag.tid")
    void createTag(@Param("tag")TagEntity tagEntity);

    // 根据标签名字进行查询
    @Select("SELECT * FROM `tags` WHERE `name` = #{name}")
    TagEntity findTag(@Param("name") String name);
}
