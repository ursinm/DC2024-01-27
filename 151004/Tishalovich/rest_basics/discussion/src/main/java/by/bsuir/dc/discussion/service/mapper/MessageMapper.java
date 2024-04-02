package by.bsuir.dc.discussion.service.mapper;

import by.bsuir.dc.discussion.entity.Message;
import by.bsuir.dc.discussion.entity.MessageRequestTo;
import by.bsuir.dc.discussion.entity.MessageResponseTo;
import org.mapstruct.Mapper;

@Mapper
public interface MessageMapper {

    Message requestToModel(MessageRequestTo requestTo);

    MessageResponseTo modelToResponse(Message model);

}
