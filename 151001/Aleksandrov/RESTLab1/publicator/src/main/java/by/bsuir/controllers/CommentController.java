package by.bsuir.controllers;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
public class CommentController {
    @Autowired
    private RestClient restClient;
    private String uriBase = "http://localhost:24130/api/v1.0/comments";

    @GetMapping
    public ResponseEntity<List<?>> getComments(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase + "/" + id)
                .retrieve()
                .body(CommentResponseTo.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(restClient.delete()
                .uri(uriBase + "/" + id)
                .retrieve()
                .toBodilessEntity().getBody());
    }

    @PostMapping
    public ResponseEntity<CommentResponseTo> saveComment(@RequestHeader HttpHeaders headers, @RequestBody CommentRequestTo comment) {
        return ResponseEntity.status(201).body(restClient.post()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(49)
                .body(comment)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(CommentResponseTo.class));
    }

    @PutMapping()
    public ResponseEntity<CommentResponseTo> updateComment(@RequestHeader HttpHeaders headers, @RequestBody CommentRequestTo comment) {
        return ResponseEntity.status(200).body(restClient.put()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(61)
                .body(comment)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(CommentResponseTo.class));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<?> getEditorByIssueId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/byIssue/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }
}
