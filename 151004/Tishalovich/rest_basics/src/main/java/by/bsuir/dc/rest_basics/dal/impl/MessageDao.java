package by.bsuir.dc.rest_basics.dal.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Message;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageDao extends MemoryRepository<Message> {

    @Override
    public Optional<Message> update(Message message) {
        Long id = message.getId();
        Message memoryMessage = map.get(id);

        if (message.getStoryId() != null) {
            memoryMessage.setStoryId(message.getStoryId());
        }
        if (message.getContent() != null) {
            memoryMessage.setContent(message.getContent());
        }

        return Optional.of(memoryMessage);
    }

}
