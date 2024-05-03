package application.controllers;

import application.dto.NoteRequestTo;
import application.dto.NoteResponseTo;
import application.exceptions.NotFoundException;
import application.services.StoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1.0/notes")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NoteController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private RestClient restClient = RestClient.create();
    @Autowired
    private NoteController self;

    @Autowired
    private KafkaConsumer<String, NoteResponseTo> kafkaConsumer;
    @Autowired
    private KafkaSender kafkaSender;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";

    private String uriBase = "http://localhost:24130/api/v1.0/notes";

    private String getLanguage(HttpServletRequest httpServletRequest) {
        String acceptLanguageHeader = httpServletRequest.getHeader("Accept-Language");
        return acceptLanguageHeader == null ? "ru-RU, ru;q=0.9" : acceptLanguageHeader;
    }

    @GetMapping
    public ResponseEntity<List<?>> getNotes(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(200).body(self.onGetNotesListener());
    }

    @Cacheable(cacheNames = "notes")
    public List<?> onGetNotesListener(){
        return restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo> getNote(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.status(200).body(self.onGetListener(id));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) throws NotFoundException {
        NoteRequestTo noteRequestTo = new NoteRequestTo();
        noteRequestTo.setMethod("DELETE");
        noteRequestTo.setId(id);
        kafkaSender.sendCustomMessage(noteRequestTo, inTopic);
        listenKafka();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping
    @CacheEvict(value = "notes", allEntries = true)
    public ResponseEntity<NoteResponseTo> saveNote(HttpServletRequest request, @RequestHeader HttpHeaders headers, @RequestBody @Valid NoteRequestTo noteRequestTo) {
        storyService.getById(noteRequestTo.getStoryId());

        noteRequestTo.setCountry(getLanguage(request));
        noteRequestTo.setMethod("POST");
        kafkaSender.sendCustomMessage(noteRequestTo, inTopic);
        return ResponseEntity.status(201).body(listenKafka());
    }

    @PutMapping
    @CacheEvict(cacheNames = "notes", allEntries = true)
    public ResponseEntity<NoteResponseTo> updateNote(HttpServletRequest request, @RequestHeader HttpHeaders headers, @RequestBody @Valid NoteRequestTo noteRequestTo) {
        storyService.getById(noteRequestTo.getStoryId());

        noteRequestTo.setCountry(getLanguage(request));
        noteRequestTo.setMethod("PUT");
        kafkaSender.sendCustomMessage(noteRequestTo, inTopic);

        return ResponseEntity.status(200).body(listenKafka());
    }

    @Cacheable(value = "notes", key = "#id")
    public NoteResponseTo onGetListener(Long id) {
        NoteRequestTo noteRequestTo = new NoteRequestTo();
        noteRequestTo.setMethod("GET");
        noteRequestTo.setId(id);
        kafkaSender.sendCustomMessage(noteRequestTo, inTopic);
        return listenKafka();
    }

    private NoteResponseTo listenKafka() throws NotFoundException {
        ConsumerRecords<String, NoteResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, NoteResponseTo> record : records) {

            String key = record.key();
            NoteResponseTo value = record.value();
            if (value == null || (value.getContent() != null && value.getContent().contentEquals("DELETE EXCEPTION"))) {
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

    @GetMapping("/story/{id}")
    public ResponseEntity<?> getAuthorByStoryId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/story/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }
}