package by.bsuir.discussion.service;

import by.bsuir.discussion.dao.MessageRepository;
import by.bsuir.discussion.model.entity.Message;
import by.bsuir.discussion.model.entity.MessageState;
import by.bsuir.discussion.model.request.MessageRequestTo;
import by.bsuir.discussion.model.response.MessageResponseTo;
import by.bsuir.discussion.service.exceptions.ResourceNotFoundException;
import by.bsuir.discussion.service.mapper.MessageMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class MessageService implements RestService<MessageRequestTo, MessageResponseTo>{
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private static final SecureRandom random;

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

    @Override
    public List<MessageResponseTo> findAll() {
        return messageMapper.getListResponseTo(messageRepository.findAll());
    }

    @Override
    public MessageResponseTo findById(Long id) {
        MessageResponseTo response =  messageMapper.getResponseTo(messageRepository
                .findByKeyId(id)
                .orElseThrow(() -> messageNotFoundException(id)));
        return response;
    }

    public MessageResponseTo create(@Valid MessageRequestTo messageTo, Locale locale) {
        Message newMessage = messageMapper.getMessage(messageTo);
        newMessage.getKey().setCountry(locale);
        newMessage.getKey().setId(getTimeBasedId());
        newMessage.setState(MessageState.APPROVE);
        return messageMapper.getResponseTo(messageRepository.save(newMessage));
    }

    @Override
    public MessageResponseTo create(MessageRequestTo messageTo) {
        return create(messageTo, Locale.ENGLISH);
    }

    @Override
    public MessageResponseTo update(@Valid MessageRequestTo messageTo) {
        Message message = messageRepository
                .findByKeyId(messageTo.id())
                .orElseThrow(() -> messageNotFoundException(messageTo.id()));
        return messageMapper.getResponseTo(messageRepository.save(messageMapper.partialUpdate(messageTo, message)));
    }

    @Override
    public void removeById(Long id) {
        Message message = messageRepository
                .findByKeyId(id)
                .orElseThrow(() -> messageNotFoundException(id));
        messageRepository.delete(message);
    }

    private static ResourceNotFoundException messageNotFoundException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 31, "Can't find message by id = " + id);
    }

}
