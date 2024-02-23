package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Message;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.rest_basics.services.MessageService;
import by.bsuir.dc.rest_basics.services.common.AbstractServiceImpl;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl
        extends AbstractServiceImpl<MessageRequestTo, MessageResponseTo, Message>
        implements MessageService {

    public MessageServiceImpl(MemoryRepository<Message> dao,
                              EntityMapper<MessageRequestTo,
                                      MessageResponseTo, Message> mapper) {
        super(dao, mapper);
    }

    @Override
    public List<MessageResponseTo> getByStoryId(Long storyId) {
        List<MessageResponseTo> result = new ArrayList<>();

        Iterable<Message> messages = dao.getAll();
        for ( Message message : messages ) {
            if ( message.getStoryId().equals(storyId) ) {
                result.add(mapper.modelToResponse(message));
            }
        }

        return result;
    }

}
