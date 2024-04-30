package by.bsuir.controllers;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final int SUCCESS_CODE = 200;

    @GetMapping
    public ResponseEntity<List<?>> getMessages(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(SUCCESS_CODE).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessage(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return ResponseEntity.status(SUCCESS_CODE).body(restClient.get()
                .uri(uriBase + "/" + id)
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> saveMessage(@RequestHeader HttpHeaders headers, @RequestBody MessageRequestTo comment) {
        return ResponseEntity.status(201).body(restClient.post()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(49)
                .body(comment)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestHeader HttpHeaders headers, @RequestBody MessageRequestTo comment) {
        return ResponseEntity.status(SUCCESS_CODE).body(restClient.put()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(62)
                .body(comment)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(MessageResponseTo.class));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<?> getUserByIssueId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/byIssue/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(restClient.delete()
                .uri(uriBase + "/" + id)
                .retrieve()
                .toBodilessEntity().getBody());
    }
}
