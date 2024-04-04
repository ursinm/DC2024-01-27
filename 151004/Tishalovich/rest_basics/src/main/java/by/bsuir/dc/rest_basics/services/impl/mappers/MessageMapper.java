package by.bsuir.dc.rest_basics.services.impl.mappers;

import by.bsuir.dc.rest_basics.entities.Message;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {

    @Mapping(target = "story", expression = "java(new by.bsuir.dc.rest_basics.entities.Story())")
    @Mapping(target = "story.id", source = "storyId")
    Message requestToModel(MessageRequestTo requestTo);

    @Mapping(target = "storyId", source = "story.id")
    MessageResponseTo modelToResponse(Message model);

}
