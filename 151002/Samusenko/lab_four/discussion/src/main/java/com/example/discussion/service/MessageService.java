package com.example.discussion.service;

import com.example.discussion.model.entity.Message;
import com.example.discussion.service.mapper.MessageMapperImpl;
import com.example.discussion.dao.repository.MessageRepository;
import com.example.discussion.model.request.MessageRequestTo;
import com.example.discussion.model.response.MessageResponseTo;
import com.example.discussion.service.exceptions.ResourceNotFoundException;
import com.example.discussion.service.exceptions.ResourceStateException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService{
    private final MessageRepository messageRepository;
    private final MessageMapperImpl messageMapper;
    private final String ISSUE_PATH = "http://localhost:24110/api/v1.0/issues/";

    public MessageResponseTo findById(BigInteger id) {
        return messageRepository.getById("local", id).map(messageMapper::messageToResponseTo).orElseThrow(() -> findByIdException(id));
    }


    public List<MessageResponseTo> findAll() {
        List<MessageResponseTo> res = new ArrayList<>();
        for(Message curr:messageRepository.findAll()){
            res.add(messageMapper.messageToResponseTo(curr));
        }
        return res;
    }


    public MessageResponseTo create(MessageRequestTo request) {
        Message message = messageMapper.dtoToEntity(request, "local");
        if (message.getId() == null) {
            message.setId(BigInteger.valueOf(UUID.randomUUID().getMostSignificantBits()).abs());
        }
        try {
            messageRepository.saveMessage(message);
        } catch (DataIntegrityViolationException e) {
            createException();
        }
        return messageMapper.messageToResponseTo(message);
    }


    public MessageResponseTo update(MessageRequestTo request) throws ResourceStateException{
        if (messageRepository.getById("local", new BigInteger(request.getId().toString())).isEmpty()) {
            updateException();
        }
        Message entity = messageMapper.dtoToEntity(request, "local");
        try {
            messageRepository.updateMessage(entity);
        } catch (DataIntegrityViolationException e) {
            updateException();
        }
        return messageMapper.messageToResponseTo(entity);
    }


    public boolean removeById(BigInteger id) {

        messageRepository.removeById("local", id);
        return true;
    }

    private static ResourceNotFoundException findByIdException(BigInteger id) {
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