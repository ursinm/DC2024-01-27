package com.luschickij.discussion.kafkacontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luschickij.discussion.model.KafkaPostResponseTo;
import com.luschickij.discussion.service.kafka.KafkaSendReceiverMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class Sender {

    @Value("${kafka.topic.post.request}")
    public static String MESSAGE_REQUEST_TOPIC;

    public static Long timeout = 1000L;
    ObjectMapper mapper = new ObjectMapper();

    KafkaSendReceiverMap<UUID> sendReceiverMap;

    private final KafkaTemplate<String, KafkaPostResponseTo> template;

    public Sender(KafkaTemplate<String, KafkaPostResponseTo> template,
                  KafkaSendReceiverMap<UUID> sendReceiverMap) {
        this.template = template;
        this.sendReceiverMap = sendReceiverMap;
    }

    public void send(String topic, KafkaPostResponseTo post) {

        log.info("Sending post: {}", post);
        String key =null;
        if(post.getRequestId() != null){
            key = post.getRequestId().toString();
        }
        Message<KafkaPostResponseTo> msg = MessageBuilder
                .withPayload(post)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, key)
                .build();
        template.send(msg);
    }
}
