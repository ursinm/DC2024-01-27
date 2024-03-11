package com.example.rw.repository.implementations.jpa;

import com.example.rw.model.entity.implementations.Message;
import com.example.rw.repository.interfaces.MessageRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomMessageRepository extends JpaRepository<Message,Long>,MessageRepository {
}
