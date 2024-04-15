package by.bsuir.dc.publisher.services;

import by.bsuir.dc.publisher.services.common.AbstractService;
import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;

public interface MessageService
        extends AbstractService<MessageRequestTo, MessageResponseTo> {
}
