package services.tweetservice.serivces.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.Message;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.mapper.MessageMapper;
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;
import services.tweetservice.exceptions.ErrorResponseTo;
import services.tweetservice.exceptions.NoSuchMessageException;
import services.tweetservice.exceptions.NoSuchTweetException;
import services.tweetservice.exceptions.ServiceException;
import services.tweetservice.kafkadto.MessageActionDto;
import services.tweetservice.kafkadto.MessageActionTypeDto;
import services.tweetservice.repositories.MessageCacheRepository;
import services.tweetservice.serivces.MessageServicePublisher;
import services.tweetservice.serivces.TweetService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MessageServicePublisherImplPublisher implements MessageServicePublisher {
    private final ObjectMapper objectMapper;
    private final ReplyingKafkaTemplate<String, MessageActionDto, MessageActionDto> replyingKafkaTemplate;
    private final TweetService tweetService;
    private final MessageMapper messageMapper;
    private final MessageCacheRepository messageCacheRepository;

    @Value("${topic.inTopic}")
    private String inTopic;

    @Value("${topic.outTopic}")
    private String outTopic;

    @KafkaListener(topics = "${topic.messageChangeTopic}")
    protected void receiveMessageChange(MessageActionDto messageActionDto) {
        switch (messageActionDto.getAction()) {
            case CREATE, UPDATE -> {
                MessageResponseTo messageResponseTo = objectMapper.
                        convertValue(messageActionDto.getData(), MessageResponseTo.class);
                messageCacheRepository.save(messageMapper.toMessage(messageResponseTo));
            }
            case DELETE -> {
                Long id = Long.valueOf((String) messageActionDto.getData());
                messageCacheRepository.deleteById(id);
            }
        }
    }

    protected MessageActionDto sendMessageAction(MessageActionDto messageActionDto) {

        ProducerRecord<String, MessageActionDto> record = new ProducerRecord<>(inTopic, null,
                System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                messageActionDto);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, outTopic.getBytes()));
        RequestReplyFuture<String, MessageActionDto, MessageActionDto> response = replyingKafkaTemplate.sendAndReceive(record);
        return response.orTimeout(1000, TimeUnit.MILLISECONDS).join().value();
    }

    @Override
    @Validated(ValidationMarker.OnCreate.class)
    public MessageResponseTo create(@Valid MessageRequestTo entity) throws ServiceException {
        if (!tweetService.existsById(entity.tweetId())) {
            throw new NoSuchTweetException(entity.tweetId());
        }
        MessageActionDto action = sendMessageAction(MessageActionDto.builder().
                action(MessageActionTypeDto.CREATE).
                data(entity).
                build());
        MessageResponseTo response = objectMapper.convertValue(action.getData(), MessageResponseTo.class);
        if (ObjectUtils.allNull(response.id(), response.tweetId(), response.content())) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorResponseTo.class));
        }
        messageCacheRepository.save(messageMapper.toMessage(response));
        return response;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<MessageResponseTo> read() {
        return (List<MessageResponseTo>) ((List) sendMessageAction(MessageActionDto.builder().
                action(MessageActionTypeDto.READ_ALL).
                build()).getData()).stream().map(v -> objectMapper.convertValue(v, MessageResponseTo.class)).toList();
    }

    @Override
    @Validated(ValidationMarker.OnUpdate.class)
    public MessageResponseTo update(@Valid MessageRequestTo entity) throws ServiceException {
        if (!tweetService.existsById(entity.tweetId())) {
            throw new NoSuchTweetException(entity.tweetId());
        }
        MessageActionDto action = sendMessageAction(MessageActionDto.builder().
                action(MessageActionTypeDto.UPDATE).
                data(entity).
                build());
        MessageResponseTo response = objectMapper.convertValue(action.getData(), MessageResponseTo.class);
        if (ObjectUtils.allNull(response.id(), response.content(), response.tweetId())) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorResponseTo.class));
        }
        messageCacheRepository.save(messageMapper.toMessage(response));
        return response;
    }

    @Override
    public Long delete(Long id) throws ServiceException {
        MessageActionDto action = sendMessageAction(MessageActionDto.builder().
                action(MessageActionTypeDto.DELETE).
                data(String.valueOf(id)).
                build());
        try {
            messageCacheRepository.deleteById(id);
            return Long.valueOf((Integer) action.getData());
        } catch (ClassCastException e) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorResponseTo.class));
        }
    }

    @Override
    public Optional<MessageResponseTo> findMessageById(Long id) {
        return Optional.ofNullable(messageCacheRepository.findById(id).map(messageMapper::toMessageResponseTo).orElseGet(() -> {
            MessageActionDto action = sendMessageAction(MessageActionDto.builder().
                    action(MessageActionTypeDto.READ).
                    data(String.valueOf(id)).
                    build());
            return objectMapper.convertValue(action.getData(), MessageResponseTo.class);
        }));
    }
}
