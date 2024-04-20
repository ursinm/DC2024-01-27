package com.example.discussion.utils;

import com.example.discussion.dto.MessageResponseTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Slf4j
public class MessageResponseToDeserializer implements Serializer<MessageResponseTo>, Deserializer<MessageResponseTo> {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String s, MessageResponseTo data) {
        try {
            log.info("RESPONSE SERIALIZE",objectMapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8));
            return objectMapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public MessageResponseTo deserialize(String topic, byte[] data) {
        try {
            log.info("RESPONSE DESERIALIZE", objectMapper.readValue(data, MessageResponseTo.class));
            return objectMapper.readValue(data, MessageResponseTo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
