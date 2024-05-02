package by.bsuir.publisher.service.kafka;

import by.bsuir.publisher.config.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/*
Сервис Kafka producer для отправки сообщений в разделы (topic) Kafka.
Создаю простой producer позволяет KafkaTemplate, предоставленный Spring Kafka
 */
@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicConfig kafkaTopicConfig;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, KafkaTopicConfig kafkaTopicConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    // метод отправки сообщения
    public void sendMessage(String message) {
        System.out.println("Message send in topic " + kafkaTopicConfig.InTopic().name()+": "+message);
        kafkaTemplate.send(kafkaTopicConfig.InTopic().name(), message);

        //System.out.println("Message in topic InTopic" +"<"+message+">");
        //kafkaTemplate.send("InTopic", message);
    }
}
