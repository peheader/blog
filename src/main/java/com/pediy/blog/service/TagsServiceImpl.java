package com.pediy.blog.service;

import com.pediy.blog.NotFoundException;
import com.pediy.blog.dao.TagsRepository;
import com.pediy.blog.po.Tags;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    public TagsRepository tagsRepository;

    @Transactional
    @Override
    public Tags getTag(Long id) {
        return tagsRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tags getTagByTag(String tag) {
        return tagsRepository.findTagsByTag(tag);
    }

    @Transactional
    @Override
    public List<Tags> listTags() {
        return tagsRepository.findAll();
    }

    @Transactional
    @Override
    public Tags saveTag(Tags tags) {
        return tagsRepository.save(tags);
    }

    @Transactional
    @Override
    public void removeTags(Long id) {
        tagsRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tags updateTags(Tags tags) {
        Tags tags1 = tagsRepository.getOne(tags.getId());
        if(tags1 == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tags,tags1);
        return tagsRepository.save(tags1);
    }

    @Override
    public List<Tags> listTags(String ids) {
        if (ids!=null && !"".equals(ids)){
            List<Tags> res = new ArrayList<>();
            String[] idarr = ids.split(",");
            for (int i = 0; i<idarr.length;i++){
                res.add(getTag(new Long(idarr[i])));
            }
            return res;
        }
        return null;
    }

    @Transactional
    @Override
    public Page<Tags> listTags(Pageable pageable) {
        return tagsRepository.findAll(pageable);
    }
}
