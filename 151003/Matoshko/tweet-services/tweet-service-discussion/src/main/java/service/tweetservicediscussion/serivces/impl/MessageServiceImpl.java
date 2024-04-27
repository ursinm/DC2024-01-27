package service.tweetservicediscussion.serivces.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import service.tweetservicediscussion.domain.entity.Message;
import service.tweetservicediscussion.domain.entity.ValidationMarker;
import service.tweetservicediscussion.domain.mapper.MessageListMapper;
import service.tweetservicediscussion.domain.mapper.MessageMapper;
import service.tweetservicediscussion.domain.request.MessageRequestTo;
import service.tweetservicediscussion.domain.response.MessageResponseTo;
import service.tweetservicediscussion.exceptions.ErrorResponseTo;
import service.tweetservicediscussion.exceptions.ExceptionStatus;
import service.tweetservicediscussion.exceptions.NoSuchMessageException;
import service.tweetservicediscussion.kafkadto.MessageActionDto;
import service.tweetservicediscussion.kafkadto.MessageActionTypeDto;
import service.tweetservicediscussion.repositories.MessageRepository;
import service.tweetservicediscussion.serivces.MessageService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    @Lazy
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageListMapper messageListMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, MessageActionDto> kafkaMessageActionTemplate;

    @Value("${topic.messageChangeTopic}")
    private String messageChangeTopic;

    @KafkaListener(topics = "${topic.inTopic}")
    @SendTo
    protected MessageActionDto receive(MessageActionDto messageActionDto) {
        System.out.println("Received message: " + messageActionDto);
        switch (messageActionDto.getAction()) {
            case CREATE -> {

                MessageRequestTo messageRequest = objectMapper.convertValue(messageActionDto.getData(),
                        MessageRequestTo.class);
                return MessageActionDto.builder().
                        action(MessageActionTypeDto.CREATE).
                        data(create(messageRequest)).
                        build();

              /*  catch (EntityExistsException e) {
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.CREATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.EntityExistsException).
                                    build()).
                            build();
                }

               */
            }
            case READ -> {
                Long id = Long.valueOf((String) messageActionDto.getData());
                return MessageActionDto.builder().
                        action(MessageActionTypeDto.READ).
                        data(findMessageById(id)).
                        build();
            }
            case READ_ALL -> {
                return MessageActionDto.builder().
                        action(MessageActionTypeDto.READ_ALL).
                        data(read()).
                        build();
            }
            case UPDATE -> {
                try {
                    MessageRequestTo messageRequest = objectMapper.convertValue(messageActionDto.getData(),
                            MessageRequestTo.class);
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.UPDATE).
                            data(update(messageRequest)).
                            build();
                } catch (NoSuchMessageException e) {
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.UPDATE).
                            data(ErrorResponseTo.builder().
                                    errorCode(HttpStatus.NOT_FOUND + ExceptionStatus.NO_SUCH_MESSAGE_EXCEPTION_STATUS.getValue()).
                                    errorMessage(e.getMessage()).
                                    build()).
                            build();
                }
            }
            case DELETE -> {
                try {
                    Long id = Long.valueOf((String) messageActionDto.getData());
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.DELETE).
                            data(delete(id)).
                            build();
                } catch (NoSuchMessageException e) {
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.UPDATE).
                            data(ErrorResponseTo.builder().
                                    errorCode(HttpStatus.NOT_FOUND + ExceptionStatus.NO_SUCH_MESSAGE_EXCEPTION_STATUS.getValue()).
                                    errorMessage(e.getMessage()).
                                    build()).
                            build();
                }
            }
        }
        return messageActionDto;
    }

    @Override
    @Validated(ValidationMarker.OnCreate.class)
    public MessageResponseTo create(@Valid MessageRequestTo entity) {
        Optional<Message> optionalMessage = entity.id() == null ? Optional.empty() : messageRepository.findMessageById(entity.id());
        if (optionalMessage.isEmpty()) {
            Message message = messageMapper.toMessage(entity);
            if (entity.id() == null) {
                message.setId((long) (Math.random() * 2_000_000_000L) + 1);
            }
            MessageResponseTo messageResponseTo = messageMapper.toMessageResponseTo(messageRepository.save(message));
            MessageActionDto messageActionDto = MessageActionDto.builder().
                    action(MessageActionTypeDto.CREATE).
                    data(messageResponseTo).
                    build();
            ProducerRecord<String, MessageActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                    System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                    messageActionDto);
            kafkaMessageActionTemplate.send(record);
            return messageResponseTo;
        } else {
            throw new NoSuchMessageException(entity.id());
        }
    }

    @Override
    public List<MessageResponseTo> read() {
        return messageListMapper.toMessageResponseToList(messageRepository.findAll());
    }

    @Override
    @Validated(ValidationMarker.OnUpdate.class)
    public MessageResponseTo update(@Valid MessageRequestTo entity) {
        Optional<Message> message = entity.id() == null || messageRepository.findMessageById(
                entity.id()).isEmpty() ?
                Optional.empty() : Optional.of(messageMapper.toMessage(entity));
        MessageResponseTo messageResponseDto = messageMapper.toMessageResponseTo(messageRepository.save(message.orElseThrow(() ->
                new NoSuchMessageException(entity.id()))));
        MessageActionDto messageActionDto = MessageActionDto.builder().
                action(MessageActionTypeDto.UPDATE).
                data(messageResponseDto).
                build();
        ProducerRecord<String, MessageActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                messageActionDto);
        kafkaMessageActionTemplate.send(record);
        return messageResponseDto;
    }

    @Override
    public Long delete(Long id) {
        Optional<Message> message = messageRepository.findMessageById(id);
        messageRepository.deleteMessageByIdAndTweetId(message.orElseThrow(
                () -> new NoSuchMessageException(id)).getId(), message.orElseThrow(
                () -> new NoSuchMessageException(id)).getTweetId());
        MessageActionDto messageActionDto = MessageActionDto.builder().
                action(MessageActionTypeDto.DELETE).
                data(String.valueOf(id)).
                build();
        ProducerRecord<String, MessageActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                messageActionDto);
        kafkaMessageActionTemplate.send(record);
        return message.get().getId();
    }

    @Override
    public MessageResponseTo findMessageById(Long id) {
        return messageMapper.toMessageResponseTo(messageRepository.findMessageById(id).orElseThrow(() -> new NoSuchMessageException(id)));
    }
}
