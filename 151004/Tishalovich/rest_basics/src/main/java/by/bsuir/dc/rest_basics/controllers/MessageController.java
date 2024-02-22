package by.bsuir.dc.rest_basics.controllers;

import by.bsuir.dc.rest_basics.controllers.common.AbstractController;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.rest_basics.services.common.AbstractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/messages")
public class MessageController
        extends AbstractController<MessageRequestTo, MessageResponseTo> {

    public MessageController(AbstractService<MessageRequestTo,
            MessageResponseTo> service) {
        super(service);
    }

}
