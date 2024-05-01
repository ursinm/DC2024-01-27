package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.request.MessageRequestTo;
import by.bsuir.publisher.model.response.MessageResponseTo;
import by.bsuir.publisher.service.MessageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getAll() {
        return ResponseEntity.ok(messageService.getAll());
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> create(@Valid @RequestBody MessageRequestTo dto,
                                                    HttpServletRequest request) {
        final Locale locale = request.getLocale();
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(dto, locale));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> get(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(messageService.getById(id));
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> update(@Valid @RequestBody MessageRequestTo dto) {
        return ResponseEntity.ok(messageService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        messageService.deleteById(id);
    }
}
