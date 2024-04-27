package com.example.discussion.services;

import com.example.discussion.dto.MessageRequestTo;
import com.example.discussion.dto.MessageResponseTo;
import com.example.discussion.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService extends CrudService<MessageRequestTo, MessageResponseTo>{
    List<MessageResponseTo> getByStoryId(Long storyId) throws NotFoundException;
}
