package com.example.storyteller.repository;

import com.example.storyteller.model.Creator;
import com.example.storyteller.model.Story;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StoryRepository extends CrudRepository<Story, Long> {

    Optional<Story> findByTitle(String title);
}
