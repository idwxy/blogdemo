package com.idwxy.blogdemo.service.impl;

import com.idwxy.blogdemo.entity.ContentEntity;
import com.idwxy.blogdemo.entity.TagEntity;
import com.idwxy.blogdemo.mapper.ContentMapper;
import com.idwxy.blogdemo.mapper.RelationshipsMapper;
import com.idwxy.blogdemo.mapper.TagsMapper;
import com.idwxy.blogdemo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RelationshipsMapper relationshipsMapper;

    // 开启事务，如果中间有 SQL 语句执行出错，会自动回滚
    @Transactional
    @Override
    public int createContent(String title, String content, String[] tags, int authorId) {
        List<Integer> relationships = new ArrayList<>();
        for (String tag : tags) {
            // 寻找是否存在此 tag
            TagEntity tagEntity = tagsMapper.findTag(tag);
            if (tagEntity == null) {
                // 创建 tag
                tagEntity = new TagEntity(null, tag);
                tagsMapper.createTag(tagEntity);
                relationships.add(tagEntity.getTid());
            } else {
                // 添加已存在 tag id
                relationships.add(tagEntity.getTid());
            }
        }
        // 创建文章
        ContentEntity contentEntity = new ContentEntity(null, title, content, authorId, System.currentTimeMillis());
        contentMapper.createContent(contentEntity);
        // 创建 tag 和 content 的关联
        for (Integer tid : relationships) {
            relationshipsMapper.createRelationship(contentEntity.getCid(), tid);
        }
        return contentEntity.getCid();
    }

    @Transactional
    @Override
    public void deleteContent(int cid) {
        contentMapper.deleteContent(cid);
        relationshipsMapper.deleteRelationshipsByCid(cid);
    }

    @Override
    public ContentEntity getContent(int cid) {
        return contentMapper.findContentByCid(cid);
    }

    @Transactional
    @Override
    public void updateContent(int cid, String title, String content, String[] tags) {
        // 删除原有的 tags 联系
        relationshipsMapper.deleteRelationshipsByCid(cid);
        // 创建新的 tags 联系
        List<Integer> relationships = new ArrayList<>();
        for (String tag: tags) {
            // 寻找是否存在此 tag
            TagEntity tagEntity = tagsMapper.findTag(tag);
            if (tagEntity == null) {
                // 创建 tag
                tagEntity = new TagEntity(null, tag);
                tagsMapper.createTag(tagEntity);
                relationships.add(tagEntity.getTid());
            } else {
                // 添加已存在 tag id
                relationships.add(tagEntity.getTid());
            }
        }
        contentMapper.updateContent(cid, title, content);
        // 创建 tag 和 content 的关联
        for (Integer tid: relationships) {
            relationshipsMapper.createRelationship(cid, tid);
        }
    }

    @Override
    public List<ContentEntity> getContents() {
        return contentMapper.findContents();
    }

    @Override
    public List<Map<String, Object>> getContentsByTag(String tag) {
        return contentMapper.findContentsByTag(tag);
    }

    @Override
    public List<ContentEntity> getContentsByAuthorId(int authorId) {
        return contentMapper.findContentsByAuthorId(authorId);
    }
}
