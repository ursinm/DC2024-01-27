package com.example.restapplication.services;

import com.example.restapplication.dto.TagRequestTo;
import com.example.restapplication.dto.TagResponseTo;
import com.example.restapplication.exceptions.NotFoundException;

public interface TagService extends CrudService<TagRequestTo, TagResponseTo>{
    TagResponseTo getByStoryId(Long storyId) throws NotFoundException;
}
