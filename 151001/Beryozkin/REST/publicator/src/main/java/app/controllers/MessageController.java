package app.controllers;

import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
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
    private String uriBase = "http://localhost:24130/api/v1.0/messages";

    @GetMapping
    public ResponseEntity<List<?>> getMessages(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessage(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase + "/" + id)
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(restClient.delete()
                .uri(uriBase + "/" + id)
                .retrieve()
                .toBodilessEntity().getBody());
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> saveMessage(@RequestHeader HttpHeaders headers, @RequestBody MessageRequestTo message) {
        return ResponseEntity.status(201).body(restClient.post()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(49)
                .body(message)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestHeader HttpHeaders headers, @RequestBody MessageRequestTo message) {
        return ResponseEntity.status(200).body(restClient.put()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(61)
                .body(message)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @GetMapping("/byTweet/{id}")
    public ResponseEntity<?> getAuthorByTweetId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/byTweet/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }
}
