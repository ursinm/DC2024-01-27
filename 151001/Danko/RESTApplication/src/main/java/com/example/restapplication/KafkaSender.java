package com.example.restapplication;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, MessageRequestTo> kafkaTemplate;

    public void sendMessage(MessageRequestTo messageRequestTo, String topicName)
    {
        log.info("Sending: {}", messageRequestTo);
        log.info("------------------------------");
        kafkaTemplate.send(topicName, messageRequestTo);
    }

}
