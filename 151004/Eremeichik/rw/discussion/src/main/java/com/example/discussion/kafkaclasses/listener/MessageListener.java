package com.example.discussion.kafkaclasses.listener;

import com.example.discussion.kafkaclasses.request.KafkaRequest;
import com.example.discussion.kafkaclasses.request.KafkaRequestType;
import com.example.discussion.kafkaclasses.response.KafkaRequestProcessor;
import com.example.discussion.kafkaclasses.response.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageListener {
    private final Map<KafkaRequestType, KafkaRequestProcessor> processors;
    private final KafkaTemplate<String, String> template;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "inTopic", groupId = "groupId1")
    public void listen(@Payload String payload) throws JsonProcessingException {
        KafkaRequest request = objectMapper.readValue(payload, KafkaRequest.class);
        ResponseEntity<?> response = processors.get(request.getRequestType()).process(request.getArguments());
        KafkaResponse kafkaResponse = new KafkaResponse(request.getRequestType(), objectMapper.writeValueAsString(response.getBody()),response.getStatusCodeValue());
        template.send("outTopic", objectMapper.writeValueAsString(kafkaResponse));
    }
}
