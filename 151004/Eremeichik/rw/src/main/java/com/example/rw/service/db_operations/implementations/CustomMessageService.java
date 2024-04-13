package com.example.rw.service.db_operations.implementations;

import com.example.rw.kafkacl.listener.MessageKafkaListener;
import com.example.rw.kafkacl.request.KafkaRequest;
import com.example.rw.kafkacl.request.KafkaRequestType;
import com.example.rw.kafkacl.response.KafkaResponse;
import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.service.db_operations.interfaces.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomMessageService implements MessageService {
    private static final String TOPIC_NAME = "inTopic";
    private final MessageKafkaListener messageKafkaListener;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Cacheable(value = "message", key = "#id", unless="#result==null")
    public KafkaResponse findById(Long id) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(KafkaRequestType.GET, List.of(objectMapper.writeValueAsString(id)));
        kafkaTemplate.send(TOPIC_NAME, objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        return messageKafkaListener.getResponses().get(KafkaRequestType.GET);
    }

    @Override
    public KafkaResponse findAll() throws JsonProcessingException{
        KafkaRequest request = new KafkaRequest(KafkaRequestType.GET_ALL, Collections.emptyList());
        kafkaTemplate.send(TOPIC_NAME, objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        return messageKafkaListener.getResponses().get(KafkaRequestType.GET_ALL);
    }

    @Override
    public KafkaResponse save(MessageRequestTo entity) throws JsonProcessingException{
        KafkaRequest request = new KafkaRequest(KafkaRequestType.POST, List.of(objectMapper.writeValueAsString(entity)));
        try {
            kafkaTemplate.send(TOPIC_NAME, objectMapper.writeValueAsString(request));
            Thread.sleep(1500);
        } catch (InterruptedException | JsonProcessingException exception){
            log.warn("Error while finding by id");
        }
        return messageKafkaListener.getResponses().get(KafkaRequestType.POST);
    }

    @Override
    @CacheEvict(cacheNames = "message", key = "#id")
    public void deleteById(Long id) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(KafkaRequestType.DELETE, List.of(objectMapper.writeValueAsString(id)));
        kafkaTemplate.send(TOPIC_NAME, objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
    }

    @Override
    @CachePut(value = "message", key = "#entity.id", unless="#result==null")
    public KafkaResponse update(MessageRequestTo entity) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(KafkaRequestType.PUT, List.of(objectMapper.writeValueAsString(entity)));
        kafkaTemplate.send(TOPIC_NAME, objectMapper.writeValueAsString(request));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        return messageKafkaListener.getResponses().get(KafkaRequestType.PUT);
    }
}
