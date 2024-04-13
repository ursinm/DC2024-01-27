package com.example.restapplication.services;

import com.example.restapplication.dto.TagRequestTo;
import com.example.restapplication.dto.TagResponseTo;
import com.example.restapplication.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TagService extends CrudService<TagRequestTo, TagResponseTo>{
    List<TagResponseTo> getByStoryId(Long storyId) throws NotFoundException;
}
