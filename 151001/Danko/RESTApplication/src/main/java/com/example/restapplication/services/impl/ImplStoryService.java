package com.example.restapplication.services.impl;

import com.example.restapplication.dao.StoryDAO;
import com.example.restapplication.dto.StoryRequestTo;
import com.example.restapplication.dto.StoryResponseTo;
import com.example.restapplication.entites.Story;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.NotFoundException;
import com.example.restapplication.exceptions.UpdateException;
import com.example.restapplication.mappers.StoryListMapper;
import com.example.restapplication.mappers.StoryMapper;
import com.example.restapplication.services.StoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ImplStoryService implements StoryService {

    @Autowired
    StoryMapper storyMapper;

    @Autowired
    StoryDAO storyDAO;

    @Autowired
    StoryListMapper storyListMapper;
    @Override
    public StoryResponseTo getById(Long id) throws NotFoundException {
        Optional<Story> story = storyDAO.findById(id);
        return story.map(value -> storyMapper.toStoryResponse(value)).orElseThrow(() -> new NotFoundException("Story not found", 40004L));
    }

    @Override
    public List<StoryResponseTo> getAll() {
        return storyListMapper.toStoryResponseList(storyDAO.findAll());
    }

    @Override
    public StoryResponseTo save(@Valid StoryRequestTo requestTo) {
        Story storyToSave = storyMapper.toStory(requestTo);
        return storyMapper.toStoryResponse(storyDAO.save(storyToSave));
    }

    @Override
    public void delete(Long id) throws DeleteException {
        storyDAO.delete(id);
    }

    @Override
    public StoryResponseTo update(@Valid StoryRequestTo requestTo) throws UpdateException {
        Story storyToUpdate = storyMapper.toStory(requestTo);
        return storyMapper.toStoryResponse(storyDAO.update(storyToUpdate));
    }


    @Override
    public List<StoryResponseTo> getByData(String tagName, Long tagId, String userLogin, String title, String content) {
        return storyListMapper.toStoryResponseList(storyDAO.getByData(tagName, tagId, userLogin, title, content).orElseThrow(() -> new NotFoundException("Story not found", 40005L)));
    }
}
