package com.example.restapplication.services.impl;

import com.example.restapplication.dao.TagDAO;
import com.example.restapplication.dto.TagRequestTo;
import com.example.restapplication.dto.TagResponseTo;
import com.example.restapplication.entites.Tag;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.NotFoundException;
import com.example.restapplication.exceptions.UpdateException;
import com.example.restapplication.mappers.TagListMapper;
import com.example.restapplication.mappers.TagMapper;
import com.example.restapplication.services.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ImplTagService implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    TagDAO tagDAO;

    @Autowired
    TagListMapper tagListMapper;
    @Override
    public TagResponseTo getById(Long id) throws NotFoundException {
        Optional<Tag> tag = tagDAO.findById(id);
        return tag.map(value -> tagMapper.toTagResponse(value)).orElseThrow(() -> new NotFoundException("Tag not found", 40004L));
    }

    @Override
    public List<TagResponseTo> getAll() {
        return tagListMapper.toTagResponseList(tagDAO.findAll());
    }

    @Override
    public TagResponseTo save(@Valid TagRequestTo requestTo) {
        Tag tagToSave = tagMapper.toTag(requestTo);
        return tagMapper.toTagResponse(tagDAO.save(tagToSave));
    }

    @Override
    public void delete(Long id) throws DeleteException {
        tagDAO.delete(id);
    }

    @Override
    public TagResponseTo update(@Valid TagRequestTo requestTo) throws UpdateException {
        Tag tagToUpdate = tagMapper.toTag(requestTo);
        return tagMapper.toTagResponse(tagDAO.update(tagToUpdate));
    }

    @Override
    public TagResponseTo getByStoryId(Long storyId) throws NotFoundException {
        Optional<Tag> label = tagDAO.getByStoryId(storyId);
        return label.map(value -> tagMapper.toTagResponse(value)).orElseThrow(() -> new NotFoundException("Tag not found!", 40004L));
    }
}
