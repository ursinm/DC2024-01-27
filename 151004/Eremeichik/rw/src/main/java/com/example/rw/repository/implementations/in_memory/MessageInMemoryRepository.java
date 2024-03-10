package com.example.rw.repository.implementations.in_memory;

import com.example.rw.model.entity.implementations.Message;
import com.example.rw.repository.interfaces.MessageRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MessageInMemoryRepository extends InMemoryRepositoryWithLongId<Message> implements MessageRepository {
}
