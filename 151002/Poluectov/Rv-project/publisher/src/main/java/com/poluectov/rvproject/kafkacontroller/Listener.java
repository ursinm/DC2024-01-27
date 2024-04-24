package com.poluectov.rvproject.kafkacontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poluectov.rvproject.dto.KafkaMessageResponseTo;
import com.poluectov.rvproject.service.kafka.KafkaSendReceiver;
import com.poluectov.rvproject.service.kafka.KafkaSendReceiverMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
@Slf4j
public class Listener {

    KafkaSendReceiverMap<UUID> sendReceiverMap;



    @KafkaListener(topics = "${kafka.topic.message.response}")
    public void messageListener(KafkaMessageResponseTo response) {

        log.info("Message received: {}", response);

        UUID rqId = response.getRequestId();
        if (sendReceiverMap.containsKey(rqId)) {
            KafkaSendReceiver<KafkaMessageResponseTo> stringSenderReceiver = (KafkaSendReceiver<KafkaMessageResponseTo>) sendReceiverMap.get(rqId);
            stringSenderReceiver.send(response);
        }
    }
}
