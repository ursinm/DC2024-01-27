package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.model.request.NoteRequestTo;
import com.example.distributedcomputing.model.response.NoteResponseTo;
import com.example.distributedcomputing.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notes")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "commentCache")
public class NoteController {
    private final NoteService editorService;

    private final RestClient restClient;
    private final String uriBase = "http://localhost:24130/api/v1.0/notes";

    private final KafkaConsumer<String, NoteResponseTo> kafkaConsumer;

    private final KafkaSender kafkaSender;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";
    private final CacheManager cacheManager;

    @PostMapping
    public ResponseEntity<NoteResponseTo> saveComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                                      @RequestBody NoteRequestTo comment) throws NotFoundException {
        comment.setCountry(acceptLanguageHeader);
        comment.setMethod("POST");
        kafkaSender.sendCustomMessage(comment, inTopic);
        return ResponseEntity.status(201).body(listenKafka());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        NoteRequestTo commentRequestTo = new NoteRequestTo();
        commentRequestTo.setMethod("DELETE");
        commentRequestTo.setId(id);
        kafkaSender.sendCustomMessage(commentRequestTo, inTopic);
        listenKafka();
        Cache cache = cacheManager.getCache("comments");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo> getComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        Cache cache = cacheManager.getCache("comments");
        if (cache != null) {
            NoteResponseTo cachedResponse = cache.get(id, NoteResponseTo.class);
            if (cachedResponse != null) {
                return ResponseEntity.status(200).body(cachedResponse);
            }
        }
        NoteRequestTo commentRequestTo = new NoteRequestTo();
        commentRequestTo.setMethod("GET");
        commentRequestTo.setId(id);
        kafkaSender.sendCustomMessage(commentRequestTo, inTopic);
        NoteResponseTo response = listenKafka();

        if (response != null) {
            if (cache != null) {
                cache.put(id, response);
            }
            return ResponseEntity.status(200).body(response);
        } else {
            throw new NotFoundException(404L, "Comment not found");
        }
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTo> updateComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                                        @RequestBody NoteRequestTo comment) throws NotFoundException {
        Cache cache = cacheManager.getCache("comments");
        if (cache != null) {
            cache.evict(comment.getId());
        }
        comment.setCountry(acceptLanguageHeader);
        comment.setMethod("PUT");
        kafkaSender.sendCustomMessage(comment, inTopic);
        return ResponseEntity.status(200).body(listenKafka());
    }

    @GetMapping
    public ResponseEntity<List<?>> getComments(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    private NoteResponseTo listenKafka() throws NotFoundException {
        ConsumerRecords<String, NoteResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, NoteResponseTo> record : records) {

            String key = record.key();
            NoteResponseTo value = record.value();
            if (value == null) {
                throw new NotFoundException(404L, "Not found");
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
