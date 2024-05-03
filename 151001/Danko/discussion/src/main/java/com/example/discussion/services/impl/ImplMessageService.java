package com.example.discussion.services.impl;

import com.example.discussion.dto.MessageRequestTo;
import com.example.discussion.dto.MessageResponseTo;
import com.example.discussion.entities.Message;
import com.example.discussion.exceptions.DeleteException;
import com.example.discussion.exceptions.NotFoundException;
import com.example.discussion.exceptions.UpdateException;
import com.example.discussion.repository.MessageRepository;
import com.example.discussion.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class ImplMessageService implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Override
    public MessageResponseTo getById(Long id) throws NotFoundException {
        Optional<Message> message = messageRepository.findById(id).stream().findFirst();
        return message.map(this::messageToMessageResponse).orElseThrow(() -> new NotFoundException("Message not found", 40004L));
    }

    @Override
    public List<MessageResponseTo> getAll() {
        return toMessageResponseList(messageRepository.findAll());
    }

    @Override
    public MessageResponseTo save(MessageRequestTo requestTo, String country) {
        Message messageToSave = messageRequestToMessage(requestTo);
        messageToSave.setId(getId());
        messageToSave.setCountry(getCountry(country));
        return messageToMessageResponse(messageRepository.save(messageToSave));
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Optional<Message> message = messageRepository.findById(id).stream().findFirst();
        if (message.isEmpty()) {
            throw new DeleteException("Message not found!", 40004L);
        } else {
            messageRepository.deleteByCountryAndStoryIdAndId(message.get().getCountry(), message.get().getStoryId(), message.get().getId());
        }
    }

    @Override
    public MessageResponseTo update(MessageRequestTo requestTo, String country) throws UpdateException {
        Message messageToUpdate = messageRequestToMessage(requestTo);
        messageToUpdate.setId(requestTo.getId());
        messageToUpdate.setCountry(getCountry(country));
        Optional<Message> message = messageRepository.findById(requestTo.getId()).stream().findFirst();
        try {
            messageRepository.deleteByCountryAndStoryIdAndId(message.get().getCountry(), message.get().getStoryId(), message.get().getId());
            return messageToMessageResponse(messageRepository.save(messageToUpdate));
        } catch (UpdateException e) {
            throw new UpdateException("Message not found!", 40004L);
        }
    }

    @Override
    public List<MessageResponseTo> getByStoryId(Long storyId) throws NotFoundException {
        List<Message> messages = messageRepository.findByStoryId(storyId);
        if (messages.isEmpty()) {
            throw new NotFoundException("Message not found!", 40004L);
        }
        return toMessageResponseList(messages);
    }

    private MessageResponseTo messageToMessageResponse(Message message) {
        MessageResponseTo messageResponseTo = new MessageResponseTo();
        messageResponseTo.setStoryId(message.getStoryId());
        messageResponseTo.setId(message.getId());
        messageResponseTo.setContent(message.getContent());
        return messageResponseTo;
    }

    private List<MessageResponseTo> toMessageResponseList(List<Message> messages) {
        List<MessageResponseTo> response = new ArrayList<>();
        for (Message message : messages) {
            response.add(messageToMessageResponse(message));
        }
        return response;
    }

    private Message messageRequestToMessage(MessageRequestTo requestTo) {
        Message message = new Message();
        message.setId(requestTo.getId());
        message.setContent(requestTo.getContent());
        message.setStoryId(requestTo.getStoryId());
        return message;
    }

    private long getId (){
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return Math.abs(shiftedTime | randomBits);
    }

    private String getCountry(String requestHeader){
        Map<String, Double> languageMap = getStringDoubleMap(requestHeader);
        Map<String, Double> loadMap = new HashMap<>();
        for (String country: languageMap.keySet()){
            loadMap.put(country, messageRepository.countByCountry(country)*(1-languageMap.get(country)));
        }
        double minValue = Double.MAX_VALUE;
        String minKey = null;

        for (Map.Entry<String, Double> entry : loadMap.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }
        return minKey;
    }

    private static Map<String, Double> getStringDoubleMap(String requestHeader) {
        String[] languages = requestHeader.split(",");
        Map<String, Double> languageMap = new HashMap<>();
        for (String language : languages) {
            String[] parts = language.split(";");
            String lang = parts[0].trim();
            double priority = 1.0; // По умолчанию
            if (parts.length > 1) {
                String[] priorityParts = parts[1].split("=");
                priority = Double.parseDouble(priorityParts[1]);
            }
            languageMap.put(lang, priority);
        }
        return languageMap;
    }

}
