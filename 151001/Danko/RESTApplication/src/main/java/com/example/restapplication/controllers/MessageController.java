package com.example.restapplication.controllers;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MessageController {

    @Autowired
    private RestClient restClient;
    private String urlRebase = "http://localhost:24130/api/v1.0/messages";

    @GetMapping
    public ResponseEntity<List<?>> getAll()
    {
        return ResponseEntity.status(200).body(restClient.get().uri(urlRebase).retrieve().body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(restClient.get().uri(urlRebase + "/" + id).retrieve().body(MessageResponseTo.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(restClient.delete().uri(urlRebase + "/" + id).retrieve().toBodilessEntity().getBody());
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> save(@RequestHeader HttpHeaders headers, @RequestBody MessageRequestTo message) {
        return ResponseEntity.status(201).body(restClient.post()
                .uri(urlRebase)
                .contentType(MediaType.APPLICATION_JSON)
                        .contentLength(48)
                .body(message)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> update(@RequestHeader HttpHeaders headers, @RequestBody MessageRequestTo message) {
        return ResponseEntity.status(200).body(restClient.put()
                .uri(urlRebase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(61)
                .body(message)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<?> getByStoryId(@RequestHeader HttpHeaders headers, @PathVariable Long id){
        return restClient.get()
                .uri(urlRebase + "/story/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }
}
