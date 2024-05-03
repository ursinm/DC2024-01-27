package com.example.publisher.service;


import com.example.publisher.model.response.ErrorResponseTo;
import com.example.publisher.model.response.MessageResponseTo;
import com.example.publisher.service.exceptions.ResourceNotFoundException;
import com.example.publisher.service.exceptions.ResourceStateException;

import com.example.publisher.model.request.MessageRequestTo;

import com.example.publisher.service.IService;
import com.example.publisher.service.IssueService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final RestTemplate restTemplate;
    private final IssueService issueService;
    private final String URL = "http://localhost:24130/api/v1.0/messages";

    public MessageResponseTo findById(BigInteger id) {
        String getURL = String.format(Locale.ENGLISH, "%s/%d", URL, id);
        ResponseEntity<MessageResponseTo> response = restTemplate.exchange(getURL, HttpMethod.GET, null, MessageResponseTo.class);
        return Objects.requireNonNull(response.getBody());
    }

    public List<MessageResponseTo> findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageRequestTo[]> entity = new HttpEntity<>(headers);
        ResponseEntity<MessageResponseTo[]> response = restTemplate.exchange(URL, HttpMethod.GET, entity, MessageResponseTo[].class);
        return new ArrayList<>(List.of(Objects.requireNonNull(response.getBody())));

    }

    public MessageResponseTo create(MessageRequestTo request) {
        if(issueService.findById(request.issueId().longValue()) == null)
            throw createException();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageRequestTo> entity = new HttpEntity<>(request, headers);
        ResponseEntity<MessageResponseTo> response = restTemplate.exchange(URL, HttpMethod.POST, entity, MessageResponseTo.class);
        return response.getBody();
    }

    public MessageResponseTo update(MessageRequestTo request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageRequestTo> entity = new HttpEntity<>(request, headers);
        ResponseEntity<MessageResponseTo> response = restTemplate.exchange(URL, HttpMethod.PUT, entity, MessageResponseTo.class);
        return response.getBody();
    }

    public boolean removeById(BigInteger id) {
        String getURL = String.format(Locale.ENGLISH, "%s/%d", URL, id);
        ResponseEntity<MessageResponseTo> response = restTemplate.exchange(getURL, HttpMethod.DELETE, null, MessageResponseTo.class);
        return true;
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 31, "Can't find message by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 32, "Can't create message");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 33, "Can't update message");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 34, "Can't remove message");
    }
}