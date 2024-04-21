package by.denisova.jpa.service;

import by.denisova.jpa.model.Story;

import java.util.List;

public interface StoryService {

    Story findById(Long id);

    void deleteById(Long id);

    Story save(Story story);

    Story update(Story story);

    List<Story> findAll();
}
