package com.example.restapplication.services.impl;

import com.example.restapplication.dao.MessageDAO;
import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.entites.Message;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.NotFoundException;
import com.example.restapplication.exceptions.UpdateException;
import com.example.restapplication.mappers.MessageListMapper;
import com.example.restapplication.mappers.MessageMapper;
import com.example.restapplication.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ImplMessageService implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    MessageListMapper messageListMapper;


    @Override
    public MessageResponseTo getById(Long id) throws NotFoundException {
        Optional<Message> message = messageDAO.findById(id);
        return message.map(value -> messageMapper.toMessageResponse(value)).orElseThrow(() -> new NotFoundException("Message not found", 40004L));
    }

    @Override
    public List<MessageResponseTo> getAll() {
        return messageListMapper.toMessageResponseList(messageDAO.findAll());
    }

    @Override
    public MessageResponseTo save(@Valid MessageRequestTo requestTo) {
        Message messageToSave = messageMapper.toMessage(requestTo);
        return messageMapper.toMessageResponse(messageDAO.save(messageToSave));
    }

    @Override
    public void delete(Long id) throws DeleteException {
        messageDAO.delete(id);
    }

    @Override
    public MessageResponseTo update(@Valid MessageRequestTo requestTo) throws UpdateException {
        Message messageToUpdate = messageMapper.toMessage(requestTo);
        return messageMapper.toMessageResponse(messageDAO.update(messageToUpdate));
    }

    @Override
    public MessageResponseTo getByStoryId(Long storyId) throws NotFoundException {
        Optional<Message> message = messageDAO.getByStoryId(storyId);
        return message.map(value -> messageMapper.toMessageResponse(value)).orElseThrow(() -> new NotFoundException("Message not found!", 40004L));
    }
}
