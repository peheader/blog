package com.pediy.blog.dao;

import com.pediy.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypesRepository extends JpaRepository<Type,Long> {
    Type findTypeByName(String name);
}
