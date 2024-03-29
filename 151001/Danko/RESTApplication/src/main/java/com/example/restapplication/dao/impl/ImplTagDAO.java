package com.example.restapplication.dao.impl;

import com.example.restapplication.dao.StoryDAO;
import com.example.restapplication.dao.TagDAO;
import com.example.restapplication.entites.Tag;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class ImplTagDAO implements TagDAO {
    private long incId = 0;
    private final Map<Long, Tag> db = new HashMap<>();
    @Override
    public Tag save(Tag entity) {
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
    public List<Tag> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Tag update(Tag entity) throws UpdateException {
        Long id = entity.getId();

        if (db.containsKey(id)) {
            Tag toUpdate = db.get(id);
            BeanUtils.copyProperties(entity, toUpdate);
            return toUpdate;
        } else {
            throw new UpdateException("Update error", 40002L);
        }
    }

    @Override
    public Optional<Tag> getByStoryId(long storyId) {
        for (Tag tag : db.values()) {
            if (tag.getStoryId() != null && tag.getStoryId() == storyId) {
                return Optional.of(tag);
            }
        }
        return Optional.empty();
    }
}
