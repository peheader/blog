package com.pediy.blog.service;

import com.pediy.blog.po.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagsService {
    Tags getTag(Long id);

    Tags getTagByTag(String tag);

    List<Tags> listTags();

    Tags saveTag(Tags tags);

    void removeTags(Long id);

    Tags updateTags(Tags tags);

    List<Tags> listTags(String ids);

    Page<Tags> listTags(Pageable pageable);
}
