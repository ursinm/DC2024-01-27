package by.bsuir.publicator.consumer;

import by.bsuir.publicator.dto.CommentResponseTo;
import by.bsuir.publicator.producer.CommentProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class CommentConsumer {

    @Getter
    private volatile CommentResponseTo response;
    private volatile CountDownLatch latch;

    public void setLatchAndResponse(CountDownLatch latch, CommentResponseTo response) {
        this.latch = latch;
        this.response = response;
    }

    @KafkaListener(topics = "InTopic", groupId = "my-group")
    public void listen(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.response = objectMapper.readValue(message, CommentResponseTo.class);
        if (latch != null) {
            latch.countDown(); // Уменьшаем счетчик, чтобы сигнализировать о выполнении
        }
    }
}

