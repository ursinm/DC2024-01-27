package com.example.distributedcomputing.repository;

import com.example.distributedcomputing.model.entity.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryRepository extends CrudRepository<Story, Long> {
    Optional<Story> findByTitle(String title);
}
