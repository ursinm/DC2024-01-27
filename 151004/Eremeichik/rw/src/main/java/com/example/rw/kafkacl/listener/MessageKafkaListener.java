package com.example.rw.kafkacl.listener;

import com.example.rw.kafkacl.request.KafkaRequestType;
import com.example.rw.kafkacl.response.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Getter
@RequiredArgsConstructor
public class MessageKafkaListener {
    private final Map<KafkaRequestType, KafkaResponse> responses = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "outTopic", groupId = "groupId1")
    public void listen(@Payload String payload) throws JsonProcessingException {
        KafkaResponse response = objectMapper.readValue(payload,KafkaResponse.class);
        responses.put(response.getRequestType(), response);
    }
}
