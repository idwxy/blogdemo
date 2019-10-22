package com.idwxy.blogdemo.service;

import java.util.List;
import java.util.Map;

public interface TagsService {

    // 获取某个文章的标签
    List<Map<String, Object>> getRelationshipsByCid(int cid);
}
