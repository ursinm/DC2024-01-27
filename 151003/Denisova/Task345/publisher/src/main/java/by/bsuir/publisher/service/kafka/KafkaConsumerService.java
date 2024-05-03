package by.bsuir.publisher.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*
 Kafka Consumer для получения и обработки сообщений из разделов Kafka.
 Использую аннотацию @KafkaListener, предоставленную Spring Kafka, для прослушивания
*/
@Component
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "OutTopic", groupId = "my-group")
    public void receiveMessage(String message) {
        // Обрабатываю полученное сообщение
        System.out.println("Received message from OutTopic: " + message);
    }
}
