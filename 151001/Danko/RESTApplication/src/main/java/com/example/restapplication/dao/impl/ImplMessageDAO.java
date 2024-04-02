package com.example.restapplication.dao.impl;

import com.example.restapplication.dao.MessageDAO;
import com.example.restapplication.dao.StoryDAO;
import com.example.restapplication.entites.Message;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class ImplMessageDAO implements MessageDAO {
    private long incId = 0;
    private final Map<Long, Message> db = new HashMap<>();
    @Autowired
    private StoryDAO storyDAO;
    @Override
    public Message save(Message entity) {
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
    public List<Message> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<Message> findById(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Message update(Message entity) throws UpdateException {
        Long id = entity.getId();

        if (db.containsKey(id)) {
            Message toUpdate = db.get(id);
            BeanUtils.copyProperties(entity, toUpdate);
            return toUpdate;
        } else {
            throw new UpdateException("Update error", 40002L);
        }
    }

    @Override
    public Optional<Message> getByStoryId(long storyId) {
        for (Message message : db.values()) {
            if (message.getStoryId() != null && message.getStoryId() == storyId) {
                return Optional.of(message);
            }
        }
        return Optional.empty();
    }
}
