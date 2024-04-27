package org.education.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.education.bean.Message;
import org.education.bean.dto.MessageRequestTo;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchMessage;
import org.education.exception.NoSuchIssue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MessageService {

    private static int index = 0;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final IssueService issueService;
    private final String messageResourceUrl = "http://localhost:24130/api/v1.0";

    public MessageService(RestTemplate restTemplate, ObjectMapper objectMapper, IssueService issueService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.issueService = issueService;
    }

    public List<Message> getAll(){
        ResponseEntity<String> response = restTemplate.getForEntity(messageResourceUrl + "/messages", String.class);
        TypeReference<List<Message>> listType = new TypeReference<>() {};
        try{
            return objectMapper.readValue(response.getBody(), listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Message getById(int id){
        ResponseEntity<String> response = restTemplate.getForEntity(messageResourceUrl + "/messages/" + id, String.class);
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new NoSuchMessage("No such message with this id");
        try{
            return objectMapper.readValue(response.getBody(), Message.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Message create(MessageRequestTo message){
        if(!issueService.existsWithId(message.getIssueId())) throw new NoSuchIssue("No such issue with this id");
        message.setId(index);
        index++;
        ResponseEntity<String> response = restTemplate.postForEntity(messageResourceUrl + "/messages", message, String.class);
        if(response.getStatusCode() == HttpStatus.BAD_REQUEST) throw new IncorrectValuesException("incorrect values");
        try{
            return objectMapper.readValue(response.getBody(), Message.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Message update(MessageRequestTo message){
        ResponseEntity<String> response = restTemplate.exchange(messageResourceUrl + "/messages", HttpMethod.PUT, new HttpEntity<>(message), String.class);
        if(response.getStatusCode() == HttpStatus.BAD_REQUEST) throw new IncorrectValuesException("incorrect values");
        try{
            return objectMapper.readValue(response.getBody(), Message.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id){
        ResponseEntity<String> response = restTemplate.exchange(messageResourceUrl + "/messages/" + id , HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new NoSuchMessage("No such message with this id");
    }

}