package org.education.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.education.bean.Comment;
import org.education.bean.DTO.CommentRequestTo;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchComment;
import org.education.exception.NoSuchTweet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService {

    private static int index = 0;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final TweetService tweetService;
    private final String commentResourceUrl = "http://localhost:24130/api/v1.0";

    public CommentService(RestTemplate restTemplate, ObjectMapper objectMapper, TweetService tweetService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.tweetService = tweetService;
    }

    public List<Comment> getAll(){
        ResponseEntity<String> response = restTemplate.getForEntity(commentResourceUrl + "/comments", String.class);
        TypeReference<List<Comment>> listType = new TypeReference<>() {};
        try{
            return objectMapper.readValue(response.getBody(), listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Comment getById(int id){
        ResponseEntity<String> response = restTemplate.getForEntity(commentResourceUrl + "/comments/" + id, String.class);
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new NoSuchComment("No such comment with this id");
        try{
            return objectMapper.readValue(response.getBody(), Comment.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Comment create(CommentRequestTo comment){
        if(!tweetService.existsWithId(comment.getTweetId())) throw new NoSuchTweet("No such tweet with this id");
        comment.setId(index);
        index++;
        ResponseEntity<String> response = restTemplate.postForEntity(commentResourceUrl + "/comments", comment, String.class);
        if(response.getStatusCode() == HttpStatus.BAD_REQUEST) throw new IncorrectValuesException("incorrect values");
        try{
            return objectMapper.readValue(response.getBody(), Comment.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Comment update(CommentRequestTo comment){
        ResponseEntity<String> response = restTemplate.exchange(commentResourceUrl + "/comments", HttpMethod.PUT, new HttpEntity<>(comment), String.class);
        if(response.getStatusCode() == HttpStatus.BAD_REQUEST) throw new IncorrectValuesException("incorrect values");
        try{
            return objectMapper.readValue(response.getBody(), Comment.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id){
        ResponseEntity<String> response = restTemplate.exchange(commentResourceUrl + "/comments/" + id , HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new NoSuchComment("No such comment with this id");
    }

}
