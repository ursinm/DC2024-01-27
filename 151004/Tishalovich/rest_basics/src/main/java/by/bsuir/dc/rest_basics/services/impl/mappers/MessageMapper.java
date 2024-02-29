package by.bsuir.dc.rest_basics.services.impl.mappers;

import by.bsuir.dc.rest_basics.entities.Message;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import org.mapstruct.Mapper;

@Mapper
public interface MessageMapper
        extends EntityMapper<MessageRequestTo, MessageResponseTo, Message> {
}
