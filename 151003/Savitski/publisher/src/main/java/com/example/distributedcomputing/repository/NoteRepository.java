package com.example.distributedcomputing.repository;

import com.example.distributedcomputing.model.entity.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    Optional<Note> findByStoryId(Long id);
}
