package com.example.restapplication.dao;

import com.example.restapplication.entites.Tag;

import java.util.Optional;

public interface TagDAO extends CrudDAO<Tag> {
    Optional<Tag> getByStoryId(long storyId);
}
