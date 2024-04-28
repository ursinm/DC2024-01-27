package com.example.publicator.controller;

import com.example.publicator.dto.MessageRequestTo;
import com.example.publicator.dto.MessageResponseTo;
import com.example.publicator.exception.NotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class MessageController {

    WebClient client = WebClient.create();
    private String uriBase = "http://localhost:24130/api/v1.0/";

    @PostMapping(value = "messages")
    public ResponseEntity<?> create(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody MessageRequestTo messageRequestTo) {
        MessageResponseTo message = client.post().uri(uriBase + "messages").header(HttpHeaders.ACCEPT_LANGUAGE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(messageRequestTo), MessageRequestTo.class).retrieve().bodyToMono(MessageResponseTo.class).onErrorMap(NotFoundException.class, ex -> new NotFoundException(ex.getMessage(), ex.getStatus())).block();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping(value = "messages", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<MessageResponseTo> list = client.get().uri(uriBase + "messages").retrieve().bodyToFlux(MessageResponseTo.class).collectList().block();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "messages/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        MessageResponseTo message = client.get().uri(uriBase + "messages/" + id).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                {
                    throw new NotFoundException("Message not found", 404);
                }
                )
                .bodyToMono(MessageResponseTo.class).block();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping(value = "messages")
    public ResponseEntity<?> update(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody MessageRequestTo messageRequestTo) {
        MessageResponseTo message = client.put().uri(uriBase + "messages").header(HttpHeaders.ACCEPT_LANGUAGE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(messageRequestTo), MessageRequestTo.class).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        {
                            throw new NotFoundException("Message not found", 404);
                        }
                )
                .bodyToMono(MessageResponseTo.class).block();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "messages/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = Boolean.TRUE.equals(client.delete().uri(uriBase + "messages/" + id).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        {
                            throw new NotFoundException("Message not found", 404);
                        }
                )
                .bodyToMono(Boolean.class).block());
        return new ResponseEntity<>(isDeleted, HttpStatus.NO_CONTENT);
    }
}
