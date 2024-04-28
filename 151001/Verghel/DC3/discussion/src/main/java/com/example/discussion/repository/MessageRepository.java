package com.example.discussion.repository;

import com.example.discussion.model.Message;
import com.example.discussion.model.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    List<Message> findAll();
    void deleteByCountryAndIssueIdAndId(String country, int issueId, int id);
    List<Message> findById (int id);

    Double countByCountry(String country);
}
