package com.example.restapplication.services.impl;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.entites.Message;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.NotFoundException;
import com.example.restapplication.exceptions.UpdateException;
import com.example.restapplication.mappers.MessageListMapper;
import com.example.restapplication.mappers.MessageMapper;
import com.example.restapplication.repository.MessageRepository;
import com.example.restapplication.repository.StoryRepository;
import com.example.restapplication.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    MessageRepository messageRepository;

    @Autowired
    MessageListMapper messageListMapper;

    @Autowired
    StoryRepository storyRepository;


    @Override
    public MessageResponseTo getById(Long id) throws NotFoundException {
        Optional<Message> message = messageRepository.findById(id);
        return message.map(value -> messageMapper.toMessageResponse(value)).orElseThrow(() -> new NotFoundException("Message not found", 40004L));
    }

    @Override
    public List<MessageResponseTo> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Message> messages = messageRepository.findAll(pageable);
        return messageListMapper.toMessageResponseList(messages.toList());
    }

    @Override
    public MessageResponseTo save(@Valid MessageRequestTo requestTo) {
        Message messageToSave = messageMapper.toMessage(requestTo);
        if (requestTo.getStoryId()!=null) {
            messageToSave.setStory(storyRepository.findById(requestTo.getStoryId()).orElseThrow(() -> new NotFoundException("Story not found!", 40004L)));
        }
        return messageMapper.toMessageResponse(messageRepository.save(messageToSave));
    }

    @Override
    public void delete(Long id) throws DeleteException {
        if (!messageRepository.existsById(id)) {
            throw new DeleteException("Message not found!", 40004L);
        } else {
            messageRepository.deleteById(id);
        }
    }

    @Override
    public MessageResponseTo update(@Valid MessageRequestTo requestTo) throws UpdateException {
        Message messageToUpdate = messageMapper.toMessage(requestTo);
        if (!messageRepository.existsById(requestTo.getId())) {
            throw new UpdateException("Message not found!", 40004L);
        } else {
            if (requestTo.getStoryId()!=null) {
                messageToUpdate.setStory(storyRepository.findById(requestTo.getStoryId()).orElseThrow(() -> new NotFoundException("Story not found!", 40004L)));
            }
            return messageMapper.toMessageResponse(messageRepository.save(messageToUpdate));
        }
    }

    @Override
    public List<MessageResponseTo> getByStoryId(Long storyId) throws NotFoundException {
        List<Message> messages = messageRepository.findMessageByStoryId(storyId);
        if (messages.isEmpty()){
            throw new NotFoundException("Messages not found!", 40004L);
        }
        return messageListMapper.toMessageResponseList(messages);
    }
}
