package by.bsuir.taskrest.mapper;

import by.bsuir.taskrest.dto.request.MessageRequestTo;
import by.bsuir.taskrest.dto.response.MessageResponseTo;
import by.bsuir.taskrest.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    Message toEntity(MessageRequestTo request);
    MessageResponseTo toResponseTo(Message entity);
    Message updateEntity(@MappingTarget Message entity, MessageRequestTo request);
}
