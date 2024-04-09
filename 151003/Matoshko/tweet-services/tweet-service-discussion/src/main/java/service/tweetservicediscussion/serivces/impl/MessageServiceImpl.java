package service.tweetservicediscussion.serivces.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
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

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    @Lazy private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageListMapper messageListMapper;
    private final ObjectMapper objectMapper;

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
        Message message = messageMapper.toMessage(entity);
        message.setId((long) (Math.random() * 2000000000));
        return messageMapper.toMessageResponseTo(messageRepository.save(message));
    }

    @Override
    public List<MessageResponseTo> read() {
        return messageListMapper.toMessageResponseToList(messageRepository.findAll());
    }

    @Override
    @Validated(ValidationMarker.OnUpdate.class)
    public MessageResponseTo update(@Valid MessageRequestTo entity) {
        if (messageRepository.existsById(entity.id())) {
            return messageMapper.toMessageResponseTo(messageRepository.save(messageMapper.toMessage(entity)));
        } else {
            throw new NoSuchMessageException(entity.id());
        }
    }

    @Override
    public Long delete(Long id) {
        messageRepository.delete(messageRepository.findMessageById(id).orElseThrow(() -> new NoSuchMessageException(id)));
        return id;
    }

    @Override
    public MessageResponseTo findMessageById(Long id) {
        return messageMapper.toMessageResponseTo(messageRepository.findMessageById(id).orElseThrow(() -> new NoSuchMessageException(id)));
    }
}
