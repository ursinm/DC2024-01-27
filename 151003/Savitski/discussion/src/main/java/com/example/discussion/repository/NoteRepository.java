package com.example.discussion.repository;

import com.example.discussion.model.entity.Note;
import com.example.discussion.model.entity.NoteKey;
import jdk.jfr.Registered;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends CassandraRepository<Note, NoteKey> {
    Optional<Note> findById (Long id);
    int countByCountry(String country);

    void deleteByCountryAndStoryIdAndId (String country, Long storyId, Long id);
}
