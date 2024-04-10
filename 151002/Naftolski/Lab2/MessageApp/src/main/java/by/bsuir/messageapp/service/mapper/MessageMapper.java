package by.bsuir.messageapp.service.mapper;

import by.bsuir.messageapp.model.entity.Message;
import by.bsuir.messageapp.model.request.MessageRequestTo;
import by.bsuir.messageapp.model.response.MessageResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface MessageMapper {
    @Mapping(source = "story.id", target = "storyId")
    MessageResponseTo getResponse(Message message);
    @Mapping(source = "story.id", target = "storyId")
    List<MessageResponseTo> getListResponse(Iterable<Message> messages);
    @Mapping(source = "storyId", target = "story", qualifiedByName = "storyRefFromStoryId")
    Message getMessage(MessageRequestTo messageRequestTo);
}
