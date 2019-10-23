package com.idwxy.blogdemo.service.impl;


import com.idwxy.blogdemo.mapper.RelationshipsMapper;
import com.idwxy.blogdemo.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private RelationshipsMapper relationshipsMapper;

    @Override
    public List<Map<String, Object>> getRelationshipsByCid(int cid) {
        return relationshipsMapper.findRelationshipsByCid(cid);
    }
}
