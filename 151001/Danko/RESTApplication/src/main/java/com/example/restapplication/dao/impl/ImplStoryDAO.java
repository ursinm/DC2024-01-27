package com.example.restapplication.dao.impl;

import com.example.restapplication.dao.StoryDAO;
import com.example.restapplication.dao.TagDAO;
import com.example.restapplication.dao.UserDAO;
import com.example.restapplication.entites.Story;
import com.example.restapplication.entites.Tag;
import com.example.restapplication.entites.User;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class ImplStoryDAO implements StoryDAO {
    private long incId = 0;
    private final Map<Long, Story> db = new HashMap<>();
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private UserDAO userDAO;
    @Override
    public Story save(Story entity) {
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
    public List<Story> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<Story> findById(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Story update(Story entity) throws UpdateException {
        Long id = entity.getId();

        if (db.containsKey(id)) {
            Story toUpdate = db.get(id);
            BeanUtils.copyProperties(entity, toUpdate);
            return toUpdate;
        } else {
            throw new UpdateException("Update error", 40002L);
        }
    }

    @Override
    public Optional<List<Story>> getByData(String tagName, Long tagId, String userLogin, String title, String content) {
        List<Story> stories = new ArrayList<>();
        for(Story story : db.values()) {
            if(equalData(story, tagName, tagId, userLogin, title, content)) {
                stories.add(story);
            }
        }
        if(!stories.isEmpty()) return Optional.ofNullable(stories);
        else return Optional.empty();
    }

    private boolean equalData(Story story, String tagName, Long tagId, String userLogin, String title, String content) {
        if (tagName != null) {
            Tag tag = tagDAO.getByStoryId(story.getId()).orElse(null);
            if (tag == null || !tag.getName().equals(tagName)) {
                return false;
            }
        }
        if(userLogin != null) {
            User user = userDAO.getByStoryId(story.getId()).orElse(null);
            if(user == null || !user.getLogin().equals(userLogin)) {
                return false;
            }
        }
        if (tagId != null) {
            if (!Objects.equals(story.getTagId(), tagId)) {
                return false;
            }
        }
        if (title != null) {
            if (!story.getTitle().equals(title)) {
                return false;
            }
        }
        if (content != null) {
            return story.getContent().equals(content);
        }
        return true;
    }
}
