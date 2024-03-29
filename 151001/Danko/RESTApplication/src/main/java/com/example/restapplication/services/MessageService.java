package com.example.restapplication.services;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.exceptions.NotFoundException;

public interface MessageService extends CrudService<MessageRequestTo, MessageResponseTo> {
    MessageResponseTo getByStoryId(Long storyId) throws NotFoundException;
}
