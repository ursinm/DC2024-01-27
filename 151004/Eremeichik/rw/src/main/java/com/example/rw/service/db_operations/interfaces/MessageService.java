package com.example.rw.service.db_operations.interfaces;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.kafkacl.response.KafkaResponse;
import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.model.entity.implementations.Message;
import com.example.rw.model.entity.implementations.News;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface MessageService {
    KafkaResponse findById(Long id) throws JsonProcessingException;

    KafkaResponse findAll() throws JsonProcessingException;

    KafkaResponse save(MessageRequestTo entity) throws JsonProcessingException;

    void deleteById(Long id) throws JsonProcessingException;

    KafkaResponse update(MessageRequestTo request) throws JsonProcessingException;
}
