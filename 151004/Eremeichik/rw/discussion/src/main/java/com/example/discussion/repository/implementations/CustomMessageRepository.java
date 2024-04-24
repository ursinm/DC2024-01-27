package com.example.discussion.repository.implementations;

import com.example.discussion.model.entity.implementations.Message;
import com.example.discussion.repository.interfaces.MessageRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomMessageRepository extends CassandraRepository<Message,Long>, MessageRepository {
}
