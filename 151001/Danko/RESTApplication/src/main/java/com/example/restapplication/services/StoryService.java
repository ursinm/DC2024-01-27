package com.example.restapplication.services;

import com.example.restapplication.dto.StoryRequestTo;
import com.example.restapplication.dto.StoryResponseTo;
import com.example.restapplication.exceptions.NotFoundException;

import java.util.List;

public interface StoryService extends CrudService<StoryRequestTo, StoryResponseTo>{
    List<StoryResponseTo> getByData(String tagName, Long tagId, String userLogin, String title, String content) throws NotFoundException;
}
