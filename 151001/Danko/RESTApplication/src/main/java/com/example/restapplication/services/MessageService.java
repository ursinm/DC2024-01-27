package com.example.restapplication.services;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MessageService extends CrudService<MessageRequestTo, MessageResponseTo> {
    List<MessageResponseTo> getByStoryId(Long storyId) throws NotFoundException;
}
