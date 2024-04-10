package com.example.discussion.repository;

import com.example.discussion.entities.Message;
import com.example.discussion.entities.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    List<Message> findByStoryId(Long story_id);
    List<Message> findById(Long id);
    void deleteByCountryAndStoryIdAndId (String country, Long storyId, Long id);
    int countByCountry(String country);
}
