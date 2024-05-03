package by.bsuir.discussion.service.kafka;

import by.bsuir.discussion.config.KafkaTopicConfig;
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
        System.out.println("Message send in topic " + kafkaTopicConfig.OutTopic().name()+": "+message);
        kafkaTemplate.send(kafkaTopicConfig.OutTopic().name(), message);
    }
}
