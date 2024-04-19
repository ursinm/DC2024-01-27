package com.distributed_computing.jpa.service;

import com.distributed_computing.jpa.exception.IncorrectValuesException;
import com.distributed_computing.jpa.repository.IssueRepository;
import com.distributed_computing.jpa.repository.MessageRepository;
import com.distributed_computing.jpa.bean.Message;
import com.distributed_computing.jpa.exception.NoSuchMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, IssueRepository issueRepository) {
        this.messageRepository = messageRepository;
        this.issueRepository = issueRepository;
    }

    public List<Message> getAll(){
        return messageRepository.findAll();
    }

    public Message getById(int id){
        return messageRepository.getReferenceById(id);
    }

    public Message create(Message message, int ownerId){
        if(!issueRepository.existsById(ownerId)) throw new IncorrectValuesException("There is no issues with this id");
        messageRepository.save(message);
        return message;
    }

    public Message update(Message message){
        if(!messageRepository.existsById(message.getId())) throw new NoSuchMessage("There is no such message with this id");
        messageRepository.save(message);
        return message;
    }

    public void delete(int id){
        if(!messageRepository.existsById(id)) throw new NoSuchMessage("There is no such message with this id");
        messageRepository.deleteById(id);
    }

}