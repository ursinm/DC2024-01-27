package by.bsuir.dc.publisher.services.impl.mappers;

import by.bsuir.dc.publisher.entities.Message;
import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {

    Message requestToModel(MessageRequestTo requestTo);

    MessageResponseTo modelToResponse(Message model);

}
