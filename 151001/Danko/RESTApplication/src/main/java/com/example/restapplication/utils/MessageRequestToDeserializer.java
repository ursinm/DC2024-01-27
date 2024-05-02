package com.example.restapplication.utils;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class MessageRequestToDeserializer implements Deserializer<MessageRequestTo>, Serializer<MessageRequestTo>
{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public MessageRequestTo deserialize(String topic, byte[] data) {
        try {
            log.info("REQUEST DESERIALIZE",objectMapper.readValue(data, MessageRequestTo.class));
            return objectMapper.readValue(data, MessageRequestTo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public byte[] serialize(String s, MessageRequestTo messageRequestTo) {
        try {
            log.info("REQUEST SERIALIZE", objectMapper.writeValueAsString(messageRequestTo).getBytes(StandardCharsets.UTF_8));
            return objectMapper.writeValueAsString(messageRequestTo).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}