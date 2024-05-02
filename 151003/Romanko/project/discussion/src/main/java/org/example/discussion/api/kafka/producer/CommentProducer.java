package org.example.discussion.api.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.discussion.impl.comment.dto.CommentResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommentProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "InTopic";

    @Autowired
    public CommentProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResponseComment(CommentResponseTo comment, String requestId) {
        ObjectMapper mapper = new ObjectMapper();
        String message = "";
        try {
            message = mapper.writeValueAsString(comment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(TOPIC, requestId + "," + message);

    }
}
