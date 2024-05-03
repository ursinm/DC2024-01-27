package by.bsuir.controllers;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.exceptions.NotFoundException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
@CacheConfig(cacheNames = "messageCache")
public class MessageController {
    @Autowired
    private RestClient restClient;
    @Autowired
    private KafkaConsumer<String, MessageResponseTo> kafkaConsumer;
    @Autowired
    private KafkaSender kafkaSender;
    @Autowired
    private CacheManager cacheManager;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";
    private String uriBase = "http://localhost:24130/api/v1.0/messages";

    @GetMapping
    public ResponseEntity<List<?>> getMessages() {
        //kafkaSender.sendCustomMessage();
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessage(@PathVariable Long id) throws NotFoundException {
        Cache cache = cacheManager.getCache("messages");
        if (cache != null) {
            MessageResponseTo cachedResponse = cache.get(id, MessageResponseTo.class);
            if (cachedResponse != null) {
                return ResponseEntity.status(200).body(cachedResponse);
            }
        }
        MessageRequestTo messageRequestTo = new MessageRequestTo();
        messageRequestTo.setMethod("GET");
        messageRequestTo.setId(id);
        kafkaSender.sendCustomMessage(messageRequestTo, inTopic);
        MessageResponseTo response = listenKafka();

        if (response != null) {
            if (cache != null) {
                cache.put(id, response);
            }
            return ResponseEntity.status(200).body(response);
        } else {
            throw new NotFoundException("Message not found", 404L);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) throws NotFoundException {
        MessageRequestTo messageRequestTo = new MessageRequestTo();
        messageRequestTo.setMethod("DELETE");
        messageRequestTo.setId(id);
        kafkaSender.sendCustomMessage(messageRequestTo, inTopic);
        listenKafka();
        Cache cache = cacheManager.getCache("messages");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> saveMessage(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody MessageRequestTo message) throws NotFoundException {
        message.setCountry(acceptLanguageHeader);
        message.setMethod("POST");
        kafkaSender.sendCustomMessage(message, inTopic);
        return ResponseEntity.status(201).body(listenKafka());
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody MessageRequestTo message) throws NotFoundException {
        Cache cache = cacheManager.getCache("messages");
        if (cache != null) {
            cache.evict(message.getId());
        }
        message.setCountry(acceptLanguageHeader);
        message.setMethod("PUT");
        kafkaSender.sendCustomMessage(message, inTopic);
        return ResponseEntity.status(200).body(listenKafka());
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<?> getCreatorByIssueId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/byIssue/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }

    private MessageResponseTo listenKafka() throws NotFoundException {
        ConsumerRecords<String, MessageResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, MessageResponseTo> record : records) {

            String key = record.key();
            MessageResponseTo value = record.value();
            if (value == null) {
                throw new NotFoundException("Not found", 40400L);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("Received message: key = " + key + ", value = " + value +
                    ", offset = " + offset + ", partition = " + partition);

            return value;
        }
        return null;
    }
}
