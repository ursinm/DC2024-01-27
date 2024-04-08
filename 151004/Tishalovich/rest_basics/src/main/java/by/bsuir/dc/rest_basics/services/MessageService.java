package by.bsuir.dc.rest_basics.services;

import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.rest_basics.services.common.AbstractService;

public interface MessageService
        extends AbstractService<MessageRequestTo, MessageResponseTo> {
}
