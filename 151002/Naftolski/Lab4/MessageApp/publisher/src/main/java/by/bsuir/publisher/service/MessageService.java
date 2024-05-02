package by.bsuir.publisher.service;

import by.bsuir.publisher.event.InTopicMessage;
import by.bsuir.publisher.event.MessageInTopicTo;
import by.bsuir.publisher.event.MessageOutTopicTo;
import by.bsuir.publisher.event.OutTopicMessage;
import by.bsuir.publisher.model.entity.MessageState;
import by.bsuir.publisher.model.request.MessageRequestTo;
import by.bsuir.publisher.model.response.MessageResponseTo;
import by.bsuir.publisher.service.exceptions.MessageExchangeFailedException;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.service.mapper.MessageMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import by.bsuir.publisher.dao.repository.StoryRepository;

@Service
@Data
@RequiredArgsConstructor
public class MessageService {
    private final MessageMapper messageMapper;
    private final KafkaMessageClient kafkaMessageClient;
    private static final SecureRandom random;
    private final StoryRepository storyRepository;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }

    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }

    public List<MessageResponseTo> getAll() {
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_ALL);
        OutTopicMessage outTopicMsg = kafkaMessageClient.sync(inTopicMsg);
        return messageMapper.toDto(outTopicMsg.resultList());
    }

    public void deleteById(Long id) {
        MessageInTopicTo inTopicDto = new MessageInTopicTo();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.DELETE_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaMessageClient.sync(inTopicMsg);
        MessageOutTopicTo deleted = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(40489, "message with id = " + id + " is not found"));
        if (!Objects.equals(id, deleted.id())) {
            throw new MessageExchangeFailedException("Note request return invalid id: expected = " + id + ", returned = " + deleted.id());
        }
    }

    public MessageResponseTo create(MessageRequestTo dto, Locale locale) {
        Long storyId = dto.storyId();
        if (!storyRepository.existsById(storyId)) {
            throw new ResourceNotFoundException(40490, "story with id = " + storyId + " doesn't exist");
        }
        MessageInTopicTo inTopicDto = messageMapper.toInTopicDto(dto);
        inTopicDto.setId(getTimeBasedId());
        inTopicDto.setCountry(locale);
        inTopicDto.setState(MessageState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.CREATE, inTopicDto);
        System.err.println("\n\n" + inTopicMsg.operation() + "  " + inTopicMsg.messageDto().getContent()
                + "  " + inTopicMsg.messageDto().getState() + "  " + inTopicMsg.messageDto().getCountry() + "  " +
                inTopicMsg.messageDto().getId()  + "\n\n");
        OutTopicMessage outTopicMsg = kafkaMessageClient.sync(inTopicMsg);
        MessageOutTopicTo created = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new MessageExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return messageMapper.toDto(created);
    }

    public MessageResponseTo getById(Long id) {
        MessageInTopicTo inTopicDto = new MessageInTopicTo();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaMessageClient.sync(inTopicMsg);
        MessageOutTopicTo noteOut = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(40411, "Message with id = " + id + " is not found"));
        return messageMapper.toDto(noteOut);
    }

    public MessageResponseTo update(MessageRequestTo dto) {
        Long storyId = dto.storyId();
        if (!storyRepository.existsById(storyId)) {
            throw new ResourceNotFoundException(40424, "story with id = " + storyId + " doesn't exist");
        }
        MessageInTopicTo inTopicDto = messageMapper.toInTopicDto(dto);
        inTopicDto.setState(MessageState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.UPDATE, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaMessageClient.sync(inTopicMsg);
        MessageOutTopicTo updated = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new MessageExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return messageMapper.toDto(updated);
    }
}
