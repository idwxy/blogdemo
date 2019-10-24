package com.idwxy.blogdemo.service;

import com.idwxy.blogdemo.entity.ContentEntity;

import java.util.List;
import java.util.Map;

public interface ContentService {

    // 创建文章，创建成功返回文章 id
    int createContent(String title, String content, String[] tags, int authorId);

    // 删除指定 id 的文章
    void deleteContent(int cid);

    // 获取指定 id 的文章
    ContentEntity getContent(int cid);

    // 更新指定 id 文章的标题、内容和标签
    void updateContent(int cid, String title, String content, String[] tags);

    // 获取文章列表
    List<ContentEntity> getContents();

    // 根据标签获取文章列表
    List<Map<String, Object>> getContentsByTag(String tag);

    // 获取该作者发布的文章
    List<ContentEntity> getContentsByAuthorId(int authorId);
}
