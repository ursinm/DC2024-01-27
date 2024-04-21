package by.bsuir.publisherservice.client.discussion;

import by.bsuir.publisherservice.client.discussion.mapper.DiscussionMessageMapper;
import by.bsuir.publisherservice.client.discussion.message.*;
import by.bsuir.publisherservice.client.discussion.request.DiscussionMessageRequestTo;
import by.bsuir.publisherservice.client.discussion.response.DiscussionMessageResponseTo;
import by.bsuir.publisherservice.dto.message.TopicMessage;
import by.bsuir.publisherservice.entity.MessageState;
import by.bsuir.publisherservice.service.kafka.KafkaClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageProducerService implements DiscussionServiceClient {
    private final KafkaClientService kafkaProducerService;
    private final DiscussionMessageMapper mapper;

    @Value("${spring.kafka.topic.request}")
    private String topicName;

    @Override
    public List<DiscussionMessageResponseTo> getAllMessages(Integer page, Integer size) {
        return Optional.of(kafkaProducerService.sendSyncMessage(
                        GetAllMessage.builder()
                                .page(page)
                                .size(size)
                                .build(),
                        topicName
                ))
                .filter(TopicMessage::isSuccessful)
                .map(GetAllMessage.class::cast)
                .map(GetAllMessage::getResult)
                .orElseThrow();
    }

    @Override
    public List<DiscussionMessageResponseTo> getMessagesByStoryId(Long id, Integer page, Integer size) {
        return Optional.of(kafkaProducerService.sendSyncMessage(
                        GetByStoryIdMessage.builder()
                                .storyId(id)
                                .page(page)
                                .size(size)
                                .build(),
                        topicName
                ))
                .filter(TopicMessage::isSuccessful)
                .map(GetByStoryIdMessage.class::cast)
                .map(GetByStoryIdMessage::getResult)
                .orElseThrow();
    }

    @Override
    public DiscussionMessageResponseTo getMessageById(Long id) {
        return Optional.of(kafkaProducerService.sendSyncMessage(
                        GetByIdMessage.builder()
                                .id(id)
                                .build(),
                        topicName
                ))
                .filter(TopicMessage::isSuccessful)
                .map(GetByIdMessage.class::cast)
                .map(GetByIdMessage::getResult)
                .orElseThrow();
    }

    @Override
    public DiscussionMessageResponseTo createMessage(DiscussionMessageRequestTo message) {
        kafkaProducerService.sendMessage(
                CreateMessage.builder()
                        .message(message)
                        .build(),
                topicName
        );
        return mapper.toDiscussionResponse(message, MessageState.PENDING);
    }

    @Override
    public DiscussionMessageResponseTo updateMessage(DiscussionMessageRequestTo message) {
        return Optional.of(kafkaProducerService.sendSyncMessage(
                        UpdateMessage.builder()
                                .message(message)
                                .build(),
                        topicName
                ))
                .filter(TopicMessage::isSuccessful)
                .map(UpdateMessage.class::cast)
                .map(UpdateMessage::getResult)
                .orElseThrow();
    }

    @Override
    public void deleteMessage(Long id) {
        Optional.of(kafkaProducerService.sendSyncMessage(
                        DeleteMessage.builder()
                                .id(id)
                                .build(),
                        topicName
                ))
                .filter(TopicMessage::isSuccessful)
                .orElseThrow();
    }
}
