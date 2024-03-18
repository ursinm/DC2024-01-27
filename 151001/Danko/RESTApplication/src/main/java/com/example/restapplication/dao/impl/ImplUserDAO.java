package com.example.restapplication.dao.impl;

import com.example.restapplication.dao.StoryDAO;
import com.example.restapplication.dao.UserDAO;
import com.example.restapplication.entites.Story;
import com.example.restapplication.entites.User;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.NotFoundException;
import com.example.restapplication.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class ImplUserDAO implements UserDAO {
    private long incId = 0;
    private final Map<Long, User> db = new HashMap<>();
    @Autowired
    private StoryDAO storyDAO;
    @Override
    public User save(User entity) {
        incId++;
        db.put(incId, entity);
        entity.setId(incId);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (db.remove(id) == null) {
            throw new DeleteException("Nothing to delete", 40003L);
        }
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public User update(User entity) throws UpdateException {
        Long id = entity.getId();

        if (db.containsKey(id)) {
            User toUpdate = db.get(id);
            BeanUtils.copyProperties(entity, toUpdate);
            return toUpdate;
        } else {
            throw new UpdateException("Update error", 40002L);
        }
    }

    @Override
    public Optional<User> getByStoryId(long storyId) {
        Story story = storyDAO.findById(storyId).orElseThrow(()-> new NotFoundException("Story not found!", 40004L));
        return Optional.ofNullable(db.get(story.getUserId()));
    }
}
