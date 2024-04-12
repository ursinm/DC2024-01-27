package by.bsuir.messageapp.service;

import by.bsuir.messageapp.dao.repository.MessageRepository;
import by.bsuir.messageapp.model.entity.Message;
import by.bsuir.messageapp.model.request.MessageRequestTo;
import by.bsuir.messageapp.model.response.MessageResponseTo;
import by.bsuir.messageapp.service.exceptions.ResourceNotFoundException;
import by.bsuir.messageapp.service.exceptions.ResourceStateException;
import by.bsuir.messageapp.service.mapper.MessageMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class MessageService implements IService<MessageRequestTo, MessageResponseTo>{
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponseTo findById(Long id) {
        return messageRepository.findById(id).map(messageMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<MessageResponseTo> findAll() {
        return messageMapper.getListResponse(messageRepository.findAll());
    }

    @Override
    public MessageResponseTo create(MessageRequestTo request) {
        MessageResponseTo response = messageMapper.getResponse(messageRepository.save(messageMapper.getMessage(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @Override
    public MessageResponseTo update(MessageRequestTo request) {
        MessageResponseTo response = messageMapper.getResponse(messageRepository.save(messageMapper.getMessage(request)));
        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @Override
    public void removeById(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(MessageService::removeException);

        messageRepository.delete(message);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 31, "Can't find message by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 32, "Can't create message");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 33, "Can't update message");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 34, "Can't remove message");
    }
}
