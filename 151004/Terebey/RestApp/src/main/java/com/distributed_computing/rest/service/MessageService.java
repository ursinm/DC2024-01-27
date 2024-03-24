package com.distributed_computing.rest.service;

import com.distributed_computing.rest.repository.MessageRepository;
import com.distributed_computing.rest.bean.Message;
import com.distributed_computing.rest.exception.NoSuchMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private static int ind = 0;

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageService(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    public List<Message> getAll(){
        return messageRepository.getAll();
    }

    public Optional<Message> getById(int id){
        return messageRepository.getById(id);
    }

    public Message create(Message message){
        message.setId(ind++);
        messageRepository.save(message);
        return message;
    }

    public Message update(Message message){
        if(messageRepository.getById(message.getId()).isEmpty()) throw new NoSuchMessage("There is no such message with this id");
        messageRepository.save(message);
        return message;
    }

    public void delete(int id){
        if(messageRepository.delete(id) == null) throw new NoSuchMessage("There is no such message with this id");
    }

}