package com.pediy.blog.dao;

import com.pediy.blog.po.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags,Long> {
    public Tags findTagsByTag(String tag);
}
