package com.example.discussion.kafkaclasses.response;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KafkaRequestProcessor {
    ResponseEntity<?> process(List<String> arguments);
}
