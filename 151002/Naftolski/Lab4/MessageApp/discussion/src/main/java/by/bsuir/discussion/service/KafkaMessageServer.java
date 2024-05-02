package by.bsuir.discussion.service;

import by.bsuir.discussion.dao.MessageRepository;
import by.bsuir.discussion.event.*;
import by.bsuir.discussion.model.entity.Message;
import by.bsuir.discussion.model.entity.MessageState;
import by.bsuir.discussion.model.response.MessageResponseTo;
import by.bsuir.discussion.service.mapper.MessageKafkaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageServer {
    public static final String IN_TOPIC = "in-topic";
    public static final String OUT_TOPIC = "out-topic";
    private final MessageKafkaMapper messageKafkaMapper;
    private final MessageService messageService;
    private final MessageRepository messageRepository;
    private final KafkaTemplate<String, OutTopicEvent> kafkaTemplate;

    private List<MessageOutTopicTo> getById(Long id) {
        MessageResponseTo responseDto;
        try {
            responseDto = messageService.findById(id);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return Collections.singletonList(messageKafkaMapper.responseDtoToOutTopicDto(responseDto));
    }

    private List<MessageOutTopicTo> create(MessageInTopicTo dto) {
        Message newEntity = messageKafkaMapper.toEntity(dto);
        newEntity.setState(MessageState.APPROVE);
        try {
            return Collections.singletonList(messageKafkaMapper.entityToDto(messageRepository.save(newEntity)));
        } catch (Exception ex) {
            newEntity.setState(MessageState.DECLINE);
            return Collections.emptyList();
        }
    }

    private List<MessageOutTopicTo> update(MessageInTopicTo dto) {
        Optional<Message> optionalNote = messageRepository.findByKeyId(dto.id());
        if (optionalNote.isEmpty()) {
            return Collections.emptyList();
        }
        final Message entity = optionalNote.get();
        final Message updated = messageKafkaMapper.partialUpdate(dto, entity);
        return Collections.singletonList(messageKafkaMapper.entityToDto(messageRepository.save(updated)));
    }

    private List<MessageOutTopicTo> delete(Long id) {
        Optional<Message> optionalNote = messageRepository.findByKeyId(id);
        if (optionalNote.isEmpty()) {
            return Collections.emptyList();
        }
        final Message deleted = optionalNote.get();
        messageRepository.delete(deleted);
        return Collections.singletonList(messageKafkaMapper.entityToDto(deleted));
    }

    @KafkaListener(topics = IN_TOPIC, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void process(ConsumerRecord<String, InTopicEvent> record) {
        final String key = record.key();
        final InTopicEvent inTopicEvent = record.value();
        final InTopicMessage inTopicMsg = inTopicEvent.message();
        final MessageInTopicTo inTopicMessage = inTopicMsg.messageDto();
        log.info(IN_TOPIC + ": key = " + key + "; operation = " + inTopicMsg.operation() + "; data = " + inTopicMsg.messageDto());
        List<MessageOutTopicTo> resultList = switch (inTopicMsg.operation()) {
            case GET_ALL -> messageKafkaMapper.responseDtoToOutTopicDto(messageService.findAll());
            case GET_BY_ID -> getById(inTopicMessage.id());
            case DELETE_BY_ID -> delete(inTopicMessage.id());
            case CREATE -> create(inTopicMessage);
            case UPDATE -> update(inTopicMessage);
        };
        log.info(OUT_TOPIC + ": key = " + key + "; data = " + resultList);
        kafkaTemplate.send(
                OUT_TOPIC,
                key,
                new OutTopicEvent(
                        inTopicEvent.id(),
                        new OutTopicMessage(
                                resultList
                        )
                )
        );
    }
}
