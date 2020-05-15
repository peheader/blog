package com.pediy.blog.service;

import com.pediy.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TypesService {
    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    void removeType(Long id);

    Type getTypeByName(String name);

    public List<Type> getAllType();
}
