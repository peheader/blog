package com.pediy.blog.service;

import com.pediy.blog.NotFoundException;
import com.pediy.blog.dao.TypesRepository;
import com.pediy.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypesService {
    @Autowired
    public TypesRepository typesRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typesRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typesRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typesRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typesRepository.getOne(id);
        if(type1 == null)
            throw new NotFoundException("分类未找到");
        BeanUtils.copyProperties(type,type1);
        return typesRepository.save(type1);
    }

    @Transactional
    @Override
    public void removeType(Long id) {
        typesRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typesRepository.findTypeByName(name);
    }

    @Transactional
    @Override
    public List<Type> getAllType() {
        return typesRepository.findAll();
    }
}
