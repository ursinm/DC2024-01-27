package com.example.publisher.service;


import com.example.publisher.consumer.KafkaCommentClient;
import com.example.publisher.event.InTopicMessage;
import com.example.publisher.event.MessageInTopicTo;
import com.example.publisher.event.MessageOutTopicTo;
import com.example.publisher.event.OutTopicMessage;
import com.example.publisher.model.response.MessageCreateResponseTo;
import com.example.publisher.model.entity.MessageState;
import com.example.publisher.model.response.MessageResponseTo;

import com.example.publisher.service.exceptions.ResourceException;
import com.example.publisher.service.exceptions.ResourceNotFoundException;
import com.example.publisher.service.exceptions.ResourceStateException;

import com.example.publisher.model.request.MessageRequestTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final RestTemplate restTemplate;
    private final IssueService issueService;
   // private final KafkaMessageClient kafkaMessageClient;
    private final KafkaCommentClient kafkaCommentClient;
    private final String URL = "http://localhost:24130/api/v1.0/messages";

    /*public MessageService(KafkaCommentClient kafkaCommentClient, RestTemplate templ, IssueService serv) {
        this.kafkaCommentClient = kafkaCommentClient;
        this.issueService = serv;
        this.restTemplate = templ;
    }*/
    public MessageResponseTo findById(BigInteger id) throws  InterruptedException, ExecutionException {
        MessageInTopicTo inTopicDto = new MessageInTopicTo();
        inTopicDto.setId(id.longValue());
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaCommentClient.sync(inTopicMsg);
        MessageOutTopicTo noteOut = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(400, "Comment with id = " + id + " is not found"));
        return new MessageResponseTo(new BigInteger(noteOut.id().toString()),new BigInteger(noteOut.issueId().toString()), noteOut.content());
  }

    public List<MessageResponseTo> findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageRequestTo[]> entity = new HttpEntity<>(headers);
        ResponseEntity<MessageResponseTo[]> response = restTemplate.exchange(URL, HttpMethod.GET, entity, MessageResponseTo[].class);
        return new ArrayList<>(List.of(Objects.requireNonNull(response.getBody())));

    }

    public MessageResponseTo create(MessageRequestTo request) throws JsonProcessingException, InterruptedException {
        if(request.getId() == null)
            request.setId(generateBigIntId(32));
        MessageInTopicTo inTopicDto = new MessageInTopicTo(request.getId().longValue(), request.getIssueId().longValue(), Locale.US, request.getContent(), MessageState.PENDING);
        inTopicDto.setState(MessageState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.CREATE, inTopicDto);
        System.err.println("\n\n" + inTopicMsg.operation() + "  " + inTopicMsg.commentDto().getContent()
                + "  " + inTopicMsg.commentDto().getState() + "  " + inTopicMsg.commentDto().getCountry() + "  " +
                inTopicMsg.commentDto().getId()  + "\n\n");
        OutTopicMessage outTopicMsg = kafkaCommentClient.sync(inTopicMsg);
        MessageOutTopicTo created = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceException(400, "Getting note with id = " + inTopicDto.getId() + " failed"));
        return new MessageResponseTo(new BigInteger(created.id().toString()),new BigInteger(created.issueId().toString()), created.content());
    }

    public MessageResponseTo update(MessageRequestTo request) throws JsonProcessingException, InterruptedException {
        MessageInTopicTo inTopicDto = new MessageInTopicTo(request.getId().longValue(), request.getIssueId().longValue(), Locale.US, request.getContent(), MessageState.PENDING);
        //inTopicDto.setState(MessageState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.UPDATE, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaCommentClient.sync(inTopicMsg);
        MessageOutTopicTo updated = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceException(400, "Getting message with id = "+ inTopicDto.getId() + " failed"));
        return new MessageResponseTo(new BigInteger(updated.id().toString()),new BigInteger(updated.issueId().toString()), updated.content());
    }

    public boolean removeById(BigInteger id) throws InterruptedException{
        MessageInTopicTo inTopicDto = new MessageInTopicTo();
        inTopicDto.setId(id.longValue());
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.DELETE_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaCommentClient.sync(inTopicMsg);
        MessageOutTopicTo deleted = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(400, "comment with id = " + id + " is not found"));
        if (!Objects.equals(id, deleted.id())) {
            throw new ResourceException(400, "Request return invalid id: expected = " + id + ", returned = " + deleted.id());
        }
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

    public static BigInteger generateBigIntId(int numBits) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[numBits / 8];
        secureRandom.nextBytes(bytes);
        return new BigInteger(1, bytes);
    }
}