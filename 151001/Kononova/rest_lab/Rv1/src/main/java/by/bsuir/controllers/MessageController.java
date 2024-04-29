package by.bsuir.controllers;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MessageController {

    @Autowired
    MessageService MessageService;

    private static final int SUCCESS_CODE = 200;

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getMessages(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(MessageService.getMessages(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessage(@PathVariable Long id) {
        return ResponseEntity.status(SUCCESS_CODE).body(MessageService.getMessageById(id));
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> saveMessage(@RequestBody MessageRequestTo Message) {
        MessageResponseTo savedMessage = MessageService.saveMessage(Message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @PutMapping()
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody MessageRequestTo Message) {
        return ResponseEntity.status(HttpStatus.OK).body(MessageService.updateMessage(Message));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<List<MessageResponseTo>> getUserByIssueId(@PathVariable Long id){
        return ResponseEntity.status(SUCCESS_CODE).body(MessageService.getMessageByIssueId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        MessageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
