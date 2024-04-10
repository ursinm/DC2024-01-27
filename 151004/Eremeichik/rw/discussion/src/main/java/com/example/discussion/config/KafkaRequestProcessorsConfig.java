package com.example.discussion.config;

import com.example.discussion.exception.model.not_found.EntityNotFoundException;
import com.example.discussion.kafkaclasses.request.KafkaRequestType;
import com.example.discussion.kafkaclasses.response.KafkaRequestProcessor;
import com.example.discussion.model.dto.message.MessageRequestTo;
import com.example.discussion.model.dto.message.MessageResponseTo;
import com.example.discussion.model.entity.implementations.Message;
import com.example.discussion.service.db_operations.interfaces.MessageService;
import com.example.discussion.service.dto_converter.interfaces.MessageToConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaRequestProcessorsConfig {
    private final MessageService messageService;
    private final MessageToConverter messageToConverter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public Map<KafkaRequestType, KafkaRequestProcessor> createProcessors(){
        Map<KafkaRequestType, KafkaRequestProcessor> result = new HashMap<>();
        result.put(KafkaRequestType.POST, (values)->createMessage(values.get(0)));
        result.put(KafkaRequestType.GET_ALL, (values)->receiveAllMessages());
        result.put(KafkaRequestType.GET, (values)->receiveMessageById(values.get(0)));
        result.put(KafkaRequestType.PUT, (values)->updateMessage(values.get(0)));
        result.put(KafkaRequestType.DELETE, (values) -> deleteMessageById(values.get(0)));
        return result;
    }


    private ResponseEntity<MessageResponseTo> createMessage(String str) {
        MessageRequestTo messageRequestTo;
        try {
            messageRequestTo = objectMapper.readValue(str, MessageRequestTo.class);
        } catch (Exception e){
            return ResponseEntity.status(401).build();
        }
        Message message = messageToConverter.convertToEntity(messageRequestTo);
        messageService.save(message);
        MessageResponseTo messageResponseTo = messageToConverter.convertToDto(message);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(messageResponseTo);
    }

    private ResponseEntity<List<MessageResponseTo>> receiveAllMessages() {
        List<Message> messages = messageService.findAll();
        List<MessageResponseTo> responseList = messages.stream()
                .map(messageToConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    private ResponseEntity<MessageResponseTo> receiveMessageById(String str) {
        Long id = Long.valueOf(str);
        Message message = messageService.findById(id);
        MessageResponseTo messageResponseTo = messageToConverter.convertToDto(message);
        return ResponseEntity.ok(messageResponseTo);
    }

    private ResponseEntity<MessageResponseTo> updateMessage(String str) {
        MessageRequestTo messageRequestTo;
        try {
            messageRequestTo = objectMapper.readValue(str, MessageRequestTo.class);
        } catch (Exception e){
            return ResponseEntity.status(401).build();
        }
        Message message = messageToConverter.convertToEntity(messageRequestTo);
        messageService.update(message);
        MessageResponseTo messageResponseTo = messageToConverter.convertToDto(message);
        return ResponseEntity.ok(messageResponseTo);
    }

    private ResponseEntity<Void> deleteMessageById(String str) {
        Long id = Long.valueOf(str);
        try {
            messageService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
