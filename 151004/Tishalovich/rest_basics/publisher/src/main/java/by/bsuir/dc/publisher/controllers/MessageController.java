package by.bsuir.dc.publisher.controllers;

import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.impl.KafkaMessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaMessageServiceImpl service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public MessageResponseTo createMessage(
            @RequestBody MessageRequestTo requestTo,
            @RequestHeader(value = "Accept-Language", required = false) String lang) throws ApiException {

        return service.create(requestTo, lang);
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
    public void deleteMessage(@PathVariable(value = "id") Long id) throws ApiException {
        service.delete(id);
    }

    @PutMapping("")
    public MessageResponseTo updateMessage(@RequestBody MessageRequestTo message) throws ApiException {
        return service.update(message);
    }

}
