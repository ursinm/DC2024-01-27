package by.bsuir.publisher.controller;

import by.bsuir.publisher.service.kafka.KafkaProducerService;
import by.bsuir.publisher.util.HeadersUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {
    @Value("${app.comments-path}")
    private String notesPath;
    private final RestClient restClient;
    private final KafkaProducerService producerService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader HttpHeaders headers) {

        producerService.sendMessage("PENDING");

        return restClient.get()
                .uri(notesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader HttpHeaders headers, @RequestBody Object o) {
        // посылаю сообщение через Kafka
        producerService.sendMessage("PENDING");

        return restClient.post()
                .uri(notesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .body(o)
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@RequestHeader HttpHeaders headers,
                                 @PathVariable Map<String, Object> pathVars) {

        producerService.sendMessage("APPROVE");

        return restClient.get()
                .uri(notesPath + "/{id}", pathVars)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                                    @RequestBody Object o) {

        producerService.sendMessage("APPROVE");

        return restClient.put()
                .uri(notesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .body(o)
                .retrieve()
                .toEntity(String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader HttpHeaders headers,
                                    @PathVariable Map<String, Object> pathVars) {
        // посылаю сообщение через Kafka
        producerService.sendMessage("APPROVE");

        return restClient.delete()
                .uri(notesPath + "/{id}", pathVars)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }
}