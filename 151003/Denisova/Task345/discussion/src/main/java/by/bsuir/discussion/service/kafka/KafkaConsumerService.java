package by.bsuir.discussion.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/*
 Kafka Consumer для получения и обработки сообщений из разделов Kafka.
 Использую аннотацию @KafkaListener, предоставленную Spring Kafka, для прослушивания
*/
@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "InTopic", groupId = "my-group")
    public void receiveMessage(String message) {
        // Обрабатываю полученное сообщение
        System.out.println("Received message from InTopic: " + message);
    }
}
