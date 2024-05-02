package by.bsuir.discussion.controller;

import by.bsuir.discussion.model.request.MessageRequestTo;
import by.bsuir.discussion.model.response.MessageResponseTo;
import by.bsuir.discussion.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> findById(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.findAll());
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> create(@Valid @RequestBody MessageRequestTo request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(request));
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> update(@Valid @RequestBody MessageRequestTo request) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.update(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@Valid @PathVariable("id") Long id) {
        messageService.removeById(id);
    }
}
