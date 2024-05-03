package com.example.discussion.consumer;

import com.example.discussion.model.request.MessageRequestTo;
import com.example.discussion.model.response.MessageResponseTo;
import com.example.discussion.producer.KafkaMessageServer;
import com.example.discussion.service.MessageService;
import com.example.discussion.service.exceptions.ResourceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class MessageConsumer {

    private final KafkaMessageServer kafkaMessageServer;
    private final MessageService messageService;

     @Autowired
    public MessageConsumer(KafkaMessageServer messageProducer, MessageService messageService) {
        this.kafkaMessageServer = messageProducer;
        this.messageService = messageService;
    }

    @KafkaListener(topics = "OutTopic", groupId = "group")
    public void consume(String message_kafka) throws ResourceException, JsonProcessingException {
        String requestId = message_kafka.substring(0, message_kafka.indexOf(","));
        if (message_kafka.contains("GET:")) {
            String messageId = message_kafka.substring(message_kafka.indexOf(":") + 1);
            MessageResponseTo messageResponseTo = getMessageResponseTo(new BigInteger(messageId));
            kafkaMessageServer.sendMessage(messageResponseTo);
        } if (message_kafka.contains("POST:")) {
            String messageJson = message_kafka.substring(message_kafka.indexOf(":") + 1);
            MessageRequestTo message = parseToMessageRequestTo(messageJson);
            MessageResponseTo messageResponseTo = messageService.create(message);
            kafkaMessageServer.sendMessage(messageResponseTo);
        } if (message_kafka.contains("DELETE:")) {
            String messageId = message_kafka.substring(message_kafka.indexOf(":") + 1);
            messageService.removeById(new BigInteger(messageId));
            kafkaMessageServer.sendMessage(new MessageResponseTo());
        } if (message_kafka.contains("PUT:")) {
            String messageJson = message_kafka.substring(message_kafka.indexOf(":") + 1);
            MessageRequestTo message = parseToMessageRequestTo(messageJson);
            MessageResponseTo messageResponseTo = messageService.update(message);
            kafkaMessageServer.sendMessage(messageResponseTo);
        }
    }

    private MessageRequestTo parseToMessageRequestTo(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(message, MessageRequestTo.class);

    }

    private MessageResponseTo getMessageResponseTo(BigInteger id) throws ResourceException {
        return messageService.findById(id);
    }
}
