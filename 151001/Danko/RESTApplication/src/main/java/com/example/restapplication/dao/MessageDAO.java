package com.example.restapplication.dao;

import com.example.restapplication.entites.Message;

import java.util.Optional;

public interface MessageDAO extends CrudDAO<Message>{
    Optional<Message> getByStoryId(long storyId);
}
