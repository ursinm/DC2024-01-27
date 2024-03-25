package com.example.restapplication.dao;

import com.example.restapplication.entites.Story;

import java.util.List;
import java.util.Optional;

public interface StoryDAO extends CrudDAO<Story> {
    Optional<List<Story>> getByData(String tagName, Long tagId, String userLogin, String title, String content);
}
