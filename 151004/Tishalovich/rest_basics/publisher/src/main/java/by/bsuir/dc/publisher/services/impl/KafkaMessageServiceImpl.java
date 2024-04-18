package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.entities.Message;
import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.exceptions.MessageSubCode;
import by.bsuir.dc.publisher.services.impl.mappers.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class KafkaMessageServiceImpl {

    private static final String IN_TOPIC= "InTopic";

    private static final String OUT_TOPIC = "OutTopic";

    private final MessageMapper mapper;

    private final KafkaTemplate<String, MessageEvent> kafkaTemplate;

    private final Map<UUID, Exchanger<MessageEvent>> exchangers = new HashMap<>();

    private String getCountry(String lang) {
        if (lang == null) {
            lang = "ru";
        }

        int commaIndex = lang.indexOf(',');
        String country = lang;

        if (commaIndex != -1) {
            country = lang.substring(0, commaIndex);
        }

        return country;
    }

    private MessageEvent getResponseEvent(MessageEvent messageEvent) throws ApiException {
        Exchanger<MessageEvent> exchanger = new Exchanger<>();
        exchangers.put(messageEvent.getId(), exchanger);
        kafkaTemplate.send(IN_TOPIC, messageEvent);

        MessageEvent responseEvent;

        try {
            responseEvent = exchanger.exchange(messageEvent, 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new ApiException(
                    HttpStatus.GATEWAY_TIMEOUT.value(),
                    MessageSubCode.DISCUSSION_TIMEOUT.getSubCode(),
                    MessageSubCode.DISCUSSION_TIMEOUT.getMessage()
            );
        }

        if (responseEvent.getErrorInfo() != null) {
            throw new ApiException(
                    responseEvent.getErrorInfo()
            );
        }

        return responseEvent;
    }

    public MessageResponseTo create(MessageRequestTo messageRequestTo, String lang)
            throws ApiException {

        String country = getCountry(lang);

        Message message = mapper.requestToModel(messageRequestTo);
        message.setCountry(country);

        MessageEvent messageEvent = new MessageEvent(
                UUID.randomUUID(),
                Operation.CREATE,
                mapper.modelToRequest(message),
                null,
                null
        );

        messageEvent = getResponseEvent(messageEvent);

        MessageRequestTo res = messageEvent.getMessageRequestTos().getFirst();
        return mapper.modelToResponse(mapper.requestToModel(res));
    }

    public List<MessageResponseTo> getAll() throws ApiException {
        MessageEvent messageEvent = new MessageEvent(
                UUID.randomUUID(),
                Operation.FIND_ALL,
                null,
                null,
                null
        );

        messageEvent = getResponseEvent(messageEvent);

        List<MessageRequestTo> res = messageEvent.getMessageRequestTos();
        return res
                .stream()
                .map(mapper::requestToModel)
                .map(mapper::modelToResponse)
                .toList();
    }


    public MessageResponseTo get(Long id) throws ApiException {
        MessageEvent messageEvent = new MessageEvent(
                UUID.randomUUID(),
                Operation.FIND_BY_ID,
                new MessageRequestTo(id, null, null, null),
                null,
                null
        );

        messageEvent = getResponseEvent(messageEvent);

        return mapper.modelToResponse(
                mapper.requestToModel(
                        messageEvent
                                .getMessageRequestTos()
                                .getFirst()
                )
        );
    }


    public MessageResponseTo update(MessageRequestTo requestTo) throws ApiException {
        MessageEvent messageEvent = new MessageEvent(
                UUID.randomUUID(),
                Operation.UPDATE,
                requestTo,
                null,
                null
        );

        messageEvent = getResponseEvent(messageEvent);

        return mapper.modelToResponse(
                mapper.requestToModel(
                        messageEvent
                                .getMessageRequestTos()
                                .getFirst()
                )
        );
    }


    public void delete(Long id) throws ApiException {
        MessageEvent messageEvent = new MessageEvent(
                UUID.randomUUID(),
                Operation.REMOVE_BY_ID,
                new MessageRequestTo(id, null, null, null),
                null,
                null
        );

        messageEvent = getResponseEvent(messageEvent);
    }

    @KafkaListener(topics = OUT_TOPIC, groupId = "1", containerFactory = "messageKafkaListenerFactory")
    public void listenDiscussionResponses(MessageEvent messageEvent) {
        Exchanger<MessageEvent> exchanger = exchangers.get(messageEvent.getId());
        try {
            exchanger.exchange(messageEvent);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
