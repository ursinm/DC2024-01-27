package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.model.entity.Message;
import by.bsuir.discussion.model.request.MessageRequestTo;
import by.bsuir.discussion.model.response.MessageResponseTo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel =  "spring")
public interface MessageMapper {
    @Mapping(target = "id", source = "key.id")
    @Mapping(target = "storyId", source = "key.storyId")
    MessageResponseTo getResponseTo(Message message);

    List<MessageResponseTo> getListResponseTo(Iterable<Message> messages);

    @Mapping(target = "key.id", source = "id")
    @Mapping(target = "key.storyId", source = "storyId")
    @Mapping(target = "key.country", ignore = true)
    Message getMessage(MessageRequestTo messageRequestTo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "key.storyId", source = "storyId")
    @Mapping(target = "key.country", ignore = true)
    Message partialUpdate(MessageRequestTo messageRequestTo, @MappingTarget Message message);
}
