package com.luschickij.publisher.controller.kafka;

import com.luschickij.publisher.config.KafkaConfig;
import com.luschickij.publisher.dto.KafkaPostRequestTo;
import com.luschickij.publisher.dto.KafkaPostResponseTo;
import com.luschickij.publisher.service.kafka.KafkaSendReceiver;
import com.luschickij.publisher.service.kafka.KafkaSendReceiverMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class Sender {

    @Value("${kafka.topic.post.request}")
    public static String MESSAGE_REQUEST_TOPIC;

    public static Long timeout = 10000L;

    KafkaSendReceiverMap<UUID> sendReceiverMap;

    private final KafkaTemplate<String, KafkaPostRequestTo> template;

    public Sender(KafkaTemplate<String, KafkaPostRequestTo> template,
                  KafkaSendReceiverMap<UUID> sendReceiverMap) {
        this.template = template;
        this.sendReceiverMap = sendReceiverMap;
    }

    public void send(String topic, KafkaPostRequestTo post) {

        UUID id = post.getId();

        log.info("KAFKA: Sending post: {}", post);
        var msg = MessageBuilder.withPayload(post)
                .setHeader(KafkaHeaders.TOPIC, topic);
        if (id != null) {
            msg.setHeader(KafkaHeaders.KEY, id.toString());
        }
        Message<KafkaPostRequestTo> msgToSend = msg.build();

        template.send(msgToSend);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConfig.class);


        //context.getBean(Sender.class).send("post-request", "test-key", error);
    }

    public KafkaPostResponseTo sendAndReceive(String topic, KafkaPostRequestTo post) throws TimeoutException {
        UUID requestId = UUID.randomUUID();

        while (sendReceiverMap.containsKey(requestId)) {
            requestId = UUID.randomUUID();
        }

        post.setId(requestId);

        this.send(topic, post);

        Future<KafkaSendReceiver<?>> future = sendReceiverMap.add(requestId, timeout);

        KafkaPostResponseTo response;
        try {
            response = (KafkaPostResponseTo) future.get().getPost();
        } catch (TimeoutException e) {
            throw new TimeoutException("Timeout: " + e);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Failed to send post: " + e);
        } finally {
            sendReceiverMap.remove(requestId);
        }
        return response;
    }
}
