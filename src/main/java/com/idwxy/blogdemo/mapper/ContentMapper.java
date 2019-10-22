package com.idwxy.blogdemo.mapper;

import com.idwxy.blogdemo.entity.ContentEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ContentMapper {

    // 创建文章，成功返回自主键
    @Insert("INSERT INTO `content` (`title`, `content`, `author_id`, `create_time`) " +
            "VALUES (#{content.title}, #{content.content}, #{content.authorId}, #{content.createTime})")
    // 返回自增主键，把返回的主键赋值到 ContentEntity 中的 cid 字段
    @Options(useGeneratedKeys = true, keyProperty = "content.cid")
    void createContent(@Param("content") ContentEntity contentEntity);

    // 通过文章 id 查找文章
    @Results(id = "contentMap", value = {
            @Result(property = "cid", column = "cid"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "authorId", column = "author_id"),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.BIGINT)
    })
    @Select("SELECT * FROM `content` WHERE cid = #{cid}")
    ContentEntity findContentByCid(@Param("cid") int cid);

    // 删除指定 id 的文章
    @Delete("DELETE FROM `content` WHERE `cid` = #{cid}")
    void deleteContent(@Param("cid") int cid);

    // 更新指定 id 文章的标题和内容
    @Update("UPDATE `content` SET `title`=#{title}, `content`=#{content} WHERE `cid`=#{cid}")
    void updateContent(@Param("cid") int cid, @Param("title") String title, @Param("content") String content);

    // 显示文章列表-根据创建时间倒序
    @Select("SELECT * FROM `content` ORDER BY `create_time` DESC")
    @ResultMap("contentMap")
    List<ContentEntity> findContents();

    // 查找某个标签下的所有文章
    @Select("SELECT `tags`.`tid`, `tags`.`name`, `content`.`cid`, `content`.`title`, `content`.`content`,`content`.`author_id`, `content`.`create_time` " +
            "FROM tags LEFT JOIN relationships ON tags.tid=relationships.tid " +
            "LEFT JOIN content ON relationships.cid=content.cid WHERE tags.`name`=#{tag} " +
            "ORDER BY `content`.`create_time` DESC")
    List<Map<String, Object>> findContentsByTag(@Param("tag") String tag);

    // 查找某个作者 id 下的所有文章
    @Select("SELECT * FROM `content` WHERE `author_id` = #{authorId} ORDER BY `create_time` DESC")
    @ResultMap("contentMap")
    List<ContentEntity> findContentsByAuthorId(@Param("authorId") int authorId);
}
