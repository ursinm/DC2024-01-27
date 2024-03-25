package com.example.lab1.service;

import com.example.lab1.dao.TagDao;
import com.example.lab1.dto.TagRequestTo;
import com.example.lab1.dto.TagResponseTo;
import com.example.lab1.exception.NotFoundException;
import com.example.lab1.mapper.TagListMapper;
import com.example.lab1.mapper.TagMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class TagService {
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TagListMapper tagListMapper;
    @Autowired
    TagDao tagDao;

    public TagResponseTo read(@Min(0) int id) throws NotFoundException{
        TagResponseTo tag = tagMapper.tagToTagResponse(tagDao.read(id));
        if(tag != null)
            return tagMapper.tagToTagResponse(tagDao.read(id));
        else
            throw new NotFoundException("Tag not found",404);
    }
    public List<TagResponseTo> readAll() {
        return tagListMapper.toTagResponseList(tagDao.readAll());
    }

    public TagResponseTo create(@Valid TagRequestTo tagRequestTo){
        return tagMapper.tagToTagResponse(tagDao.create(tagMapper.tagRequestToTag(tagRequestTo)));
    }

    public TagResponseTo update(@Valid TagRequestTo tagRequestTo, @Min(0) int id) throws NotFoundException{
        TagResponseTo tag = tagMapper.tagToTagResponse(tagDao.update(tagMapper.tagRequestToTag(tagRequestTo),id));
        if(tag != null)
            return tag;
        else throw new NotFoundException("Tag not found",404);
    }

    public boolean delete(@Min(0) int id) throws NotFoundException{
        if(tagDao.delete(id))
            return true;
        else
            throw new NotFoundException("Tag not found",404);
    }



}
