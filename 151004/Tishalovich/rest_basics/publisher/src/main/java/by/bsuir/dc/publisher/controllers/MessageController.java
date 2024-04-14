package by.bsuir.dc.publisher.controllers;

import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.publisher.services.MessageService;
import by.bsuir.dc.publisher.services.common.AbstractService;
import by.bsuir.dc.publisher.controllers.common.AbstractController;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.impl.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageServiceImpl service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public MessageResponseTo createMessage(
            @RequestBody MessageRequestTo requestTo,
            @RequestHeader("Accept-Language") String lang) throws ApiException {

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
