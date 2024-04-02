package by.bsuir.dc.publisher.controllers;

import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.publisher.services.common.AbstractService;
import by.bsuir.dc.publisher.controllers.common.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/messages")
public class MessageController extends AbstractController<MessageRequestTo, MessageResponseTo> {

    public MessageController(AbstractService<MessageRequestTo,
                MessageResponseTo> service) {
        super(service);
    }

}
