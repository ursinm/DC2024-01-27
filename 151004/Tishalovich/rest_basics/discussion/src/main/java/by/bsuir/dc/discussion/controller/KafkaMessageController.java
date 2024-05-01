package by.bsuir.dc.discussion.controller;

import by.bsuir.dc.discussion.entity.MessageEvent;
import by.bsuir.dc.discussion.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageController {

    private static final String IN_TOPIC = "InTopic";

    private static final String OUT_TOPIC = "OutTopic";

    private final KafkaMessageService kafkaService;

    @KafkaListener(topics = IN_TOPIC, groupId = "2", containerFactory = "messageKafkaListenerFactory")
    public void listMessageEvents(MessageEvent messageEvent) {
        kafkaService.service(messageEvent);
    }

}
