package com.example.restapplication.dao;

import com.example.restapplication.entites.User;

import java.util.Optional;

public interface UserDAO extends CrudDAO<User>{
    Optional<User> getByStoryId(long storyId);
}
