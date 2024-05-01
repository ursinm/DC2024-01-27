package com.example.lab1.Service;

import com.example.lab1.DAO.StoryDao;
import com.example.lab1.DTO.StoryRequestTo;
import com.example.lab1.DTO.StoryResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.StoryListMapper;
import com.example.lab1.Mapper.StoryMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class StoryService {
    @Autowired
    StoryMapper storyMapper;
    @Autowired
    StoryListMapper storyListMapper;
    @Autowired
    StoryDao storyDao;

    public StoryResponseTo read(@Min(0) int id) throws NotFoundException {
        StoryResponseTo story = storyMapper.storyToStoryResponse(storyDao.read(id));
        if(story != null)
            return story;
        else
            throw new NotFoundException("Story not found", 404);
    }
    public List<StoryResponseTo> readAll() {
        return storyListMapper.toStoryResponseList(storyDao.readAll());
    }
    public StoryResponseTo create(@Valid StoryRequestTo storyRequestTo){
        return storyMapper.storyToStoryResponse(storyDao.create(storyMapper.storyReguestToStory(storyRequestTo)));
    }
    public StoryResponseTo update(@Valid StoryRequestTo storyRequestTo, @Min(0) int id) throws NotFoundException {
        StoryResponseTo story = storyMapper.storyToStoryResponse(storyDao.update(storyMapper.storyReguestToStory(storyRequestTo),id));
        if(story != null)
            return story;
        else
            throw new NotFoundException("Story not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException{
        boolean isDeleted = storyDao.delete(id);
        if(isDeleted)
            return true;
        else
            throw new NotFoundException("Story not found", 404);
    }

}
