package by.bsuir.dc.discussion.controller;

import by.bsuir.dc.discussion.entity.MessageRequestTo;
import by.bsuir.dc.discussion.entity.MessageResponseTo;
import by.bsuir.dc.discussion.service.MessageService;
import by.bsuir.dc.discussion.service.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public MessageResponseTo createMessage(
            @RequestBody MessageRequestTo requestTo) throws ApiException {

        return service.create(requestTo);
    }

    @GetMapping("")
    public List<MessageResponseTo> getMessages() throws ApiException {
        return service.getAll();
    }

    @GetMapping("{id}")
    public MessageResponseTo getMessage(@PathVariable Long id) throws ApiException {
        return service.get(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable(value = "id") Long id)
            throws ApiException {

        service.delete(id);
    }

    @PutMapping("")
    public MessageResponseTo updateMessage(@RequestBody MessageRequestTo message)
            throws ApiException {

        return service.update(message);
    }

}
