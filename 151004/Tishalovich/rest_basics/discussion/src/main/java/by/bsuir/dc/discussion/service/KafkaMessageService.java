package by.bsuir.dc.discussion.service;

import by.bsuir.dc.discussion.entity.MessageEvent;
import by.bsuir.dc.discussion.entity.MessageRequestTo;
import by.bsuir.dc.discussion.entity.MessageResponseTo;
import by.bsuir.dc.discussion.service.exception.ApiException;
import by.bsuir.dc.discussion.service.exception.ErrorInfo;
import by.bsuir.dc.discussion.service.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaMessageService {

    private static final String OUT_TOPIC = "OutTopic";

    private final MessageService service;

    private final MessageMapper mapper;

    private final KafkaTemplate<String, MessageEvent> kafkaTemplate;

    public void service(MessageEvent messageEvent) {
        switch (messageEvent.getOperation()) {
            case CREATE -> {
                create(messageEvent);
            }
            case UPDATE -> {
                update(messageEvent);
            }
            case FIND_ALL -> {
                findAll(messageEvent);
            }
            case FIND_BY_ID -> {
                findById(messageEvent);
            }
            case REMOVE_BY_ID -> {
                removeById(messageEvent);
            }
        }
    }

    public void create(MessageEvent messageEvent) {
        MessageResponseTo res = null;
        ErrorInfo errorInfo = null;

        try {
            res = service.create(messageEvent.getMessageRequestTo());
        } catch (ApiException e) {
            errorInfo = new ErrorInfo(e.getStatusCode(), e.getMessage());
        }

        MessageRequestTo created = res == null ? null : mapper.responseToRequest(res);
        List<MessageRequestTo> tos = new ArrayList<>();
        tos.add(created);

        MessageEvent newMessageEvent = new MessageEvent(
                messageEvent.getId(),
                messageEvent.getOperation(),
                messageEvent.getMessageRequestTo(),
                tos,
                errorInfo
        );

        kafkaTemplate.send(OUT_TOPIC, newMessageEvent);
    }

    public void findAll(MessageEvent messageEvent) {
        List<MessageResponseTo> res;

        res = service.getAll();

        MessageEvent newMessageEvent = new MessageEvent(
                messageEvent.getId(),
                messageEvent.getOperation(),
                messageEvent.getMessageRequestTo(),
                res.stream()
                        .map(mapper::responseToRequest)
                        .toList(),
                null
        );

        kafkaTemplate.send(OUT_TOPIC, newMessageEvent);
    }

    public void findById(MessageEvent messageEvent) {
        MessageResponseTo res = null;

        ErrorInfo errorInfo = null;

        Long id = messageEvent.getMessageRequestTo().id();

        try {
            res = service.get(id);
        } catch (ApiException e) {
            errorInfo = new ErrorInfo(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }

        List<MessageRequestTo> tos = new ArrayList<>();
        if (res != null) {
            tos.add(mapper.responseToRequest(res));
        }

        MessageEvent newMessageEvent = new MessageEvent(
                messageEvent.getId(),
                messageEvent.getOperation(),
                messageEvent.getMessageRequestTo(),
                tos,
                errorInfo
        );

        kafkaTemplate.send(OUT_TOPIC, newMessageEvent);
    }

    public void update(MessageEvent messageEvent) {
        MessageResponseTo res = null;

        ErrorInfo errorInfo = null;

        try {
            res = service.update(messageEvent.getMessageRequestTo());
        } catch (ApiException e) {
            errorInfo = new ErrorInfo(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }

        List<MessageRequestTo> tos = new ArrayList<>();
        if (res != null) {
            tos.add(mapper.responseToRequest(res));
        }

        MessageEvent newMessageEvent = new MessageEvent(
                messageEvent.getId(),
                messageEvent.getOperation(),
                messageEvent.getMessageRequestTo(),
                tos,
                errorInfo
        );

        kafkaTemplate.send(OUT_TOPIC, newMessageEvent);
    }

    public void removeById(MessageEvent messageEvent) {
        ErrorInfo errorInfo = null;

        try {
            service.delete(messageEvent.getMessageRequestTo().id());
        } catch (ApiException e) {
            errorInfo = new ErrorInfo(
                    e.getStatusCode(),
                    e.getMessage()
            );
        }

        MessageEvent newMessageEvent = new MessageEvent(
                messageEvent.getId(),
                messageEvent.getOperation(),
                messageEvent.getMessageRequestTo(),
                null,
                errorInfo
        );

        kafkaTemplate.send(OUT_TOPIC, newMessageEvent);
    }

}
