package com.example.restapplication.services;

import com.example.restapplication.dto.StoryRequestTo;
import com.example.restapplication.dto.StoryResponseTo;
import com.example.restapplication.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StoryService extends CrudService<StoryRequestTo, StoryResponseTo>{
    List<StoryResponseTo> getByData(List<String> tagName, List<Long> tagId, String userLogin, String title, String content) throws NotFoundException;
}
