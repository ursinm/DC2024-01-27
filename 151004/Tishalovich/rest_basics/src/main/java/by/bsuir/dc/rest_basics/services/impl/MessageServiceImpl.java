package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Message;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.rest_basics.services.MessageService;
import by.bsuir.dc.rest_basics.services.common.AbstractServiceImpl;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl
        extends AbstractServiceImpl<MessageRequestTo, MessageResponseTo, Message>
        implements MessageService {

    public MessageServiceImpl(MemoryRepository<Message> dao,
                              EntityMapper<MessageRequestTo,
                                      MessageResponseTo, Message> mapper) {
        super(dao, mapper);
    }

}
