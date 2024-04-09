package service.tweetservicediscussion.serivces.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import service.tweetservicediscussion.domain.entity.Message;
import service.tweetservicediscussion.domain.entity.ValidationMarker;
import service.tweetservicediscussion.domain.mapper.MessageListMapper;
import service.tweetservicediscussion.domain.mapper.MessageMapper;
import service.tweetservicediscussion.domain.request.MessageRequestTo;
import service.tweetservicediscussion.domain.response.MessageResponseTo;
import service.tweetservicediscussion.exceptions.NoSuchMessageException;
import service.tweetservicediscussion.repositories.MessageRepository;
import service.tweetservicediscussion.serivces.MessageService;

import java.util.List;

@Service
@Transactional
@Validated
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageListMapper messageListMapper;

    @Autowired
    public MessageServiceImpl(@Lazy MessageRepository messageRepository, MessageMapper messageMapper, MessageListMapper messageListMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.messageListMapper = messageListMapper;
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
