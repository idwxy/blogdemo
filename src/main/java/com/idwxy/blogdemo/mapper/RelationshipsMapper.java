package com.idwxy.blogdemo.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RelationshipsMapper {

    // 创建文章与 tag 的关联
    @Insert("INSERT INTO `relationships` (`cid`, `tid`) VALUES (#{cid}, #{tid})")
    void createRelationship(@Param("cid") int cid, @Param("tid") int tid);

    // 通过文章 id 拿到此文章的标签
    @Select("SELECT `cid`, `relationships`.`tid`, `name` FROM `relationships` LEFT JOIN `tags` ON `relationships`.`tid` =" +
            " `tags`.`tid` WHERE `cid`=#{cid}")
    List<Map<String, Object>> findRelationshipsByCid(@Param("cid") int cid);

    // 删除指定文章 id 的标签关联
    @Delete("DELETE FROM `relationships` WHERE cid = #{cid}")
    void deleteRelationshipsByCid(@Param("cid") int cid);
}
