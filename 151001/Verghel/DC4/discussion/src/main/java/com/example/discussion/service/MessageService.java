package com.example.discussion.service;

import com.example.discussion.dto.MessageRequestTo;
import com.example.discussion.dto.MessageResponseTo;
import com.example.discussion.exception.NotFoundException;
import com.example.discussion.mapper.MessageListMapper;
import com.example.discussion.mapper.MessageMapper;
import com.example.discussion.model.Message;
import com.example.discussion.repository.MessageRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class MessageService {
    @Autowired
    //MessageDao messageDao;
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageListMapper messageListMapper;

    public MessageResponseTo create(@Valid MessageRequestTo messageRequestTo, String country){
        Message message = messageMapper.messageRequestToMessage(messageRequestTo);
        message.setId(getId());
        message.setCountry(getCountry(country));
        return messageMapper.messageToMessageResponse(messageRepository.save(message));
    }

    public List<MessageResponseTo> readAll(){
        List<Message> res = messageRepository.findAll();
        return messageListMapper.toMessageResponseList(res);
    }

    public MessageResponseTo read(@Min(0) int id) throws NotFoundException {
        Optional<Message> message = messageRepository.findById(id).stream().findFirst();
        if(message.isPresent()){
            return messageMapper.messageToMessageResponse(message.get());
        }
        else
            return null;
            //throw new NotFoundException("Message not found", 404);
    }
    public MessageResponseTo update(@Valid MessageRequestTo messageRequestTo, String country) throws NotFoundException {
        Message message = messageMapper.messageRequestToMessage(messageRequestTo);
        message.setId(messageRequestTo.getId());
        message.setCountry(getCountry(country));
        Optional<Message> res = messageRepository.findById(messageRequestTo.getId()).stream().findFirst();
        if(res.isPresent()){
            messageRepository.deleteByCountryAndIssueIdAndId(res.get().getCountry(), res.get().getIssueId(), res.get().getId());
            return messageMapper.messageToMessageResponse(messageRepository.save(message));
        }
        else
            return null;

        //throw new NotFoundException("Message not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException {
        Optional<Message> message = messageRepository.findById(id).stream().findFirst();
        if(message.isPresent()){
            messageRepository.deleteByCountryAndIssueIdAndId(message.get().getCountry(), message.get().getIssueId(), message.get().getId());
            return true;
        }
        else
            //throw new NotFoundException("Message not found", 404);
            return false;

    }


    private int getId(){
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
