package com.example.discussion.controller;

import com.example.discussion.KafkaSender;
import com.example.discussion.dto.MessageRequestTo;
import com.example.discussion.dto.MessageResponseTo;
import com.example.discussion.exception.NotFoundException;
import com.example.discussion.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    private KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup", containerFactory = "messageRequestToConcurrentKafkaListenerContainerFactory")
    void listener(@Payload MessageRequestTo messageRequestTo){
        System.out.println(messageRequestTo.toString());
        if(Objects.equals(messageRequestTo.getMethod(), "GET")){
            //if(messageRequestTo.getId() != null){

            //}
            //else{
            MessageResponseTo msg= readById(messageRequestTo.getId());
            kafkaSender.sendMessage(msg, topic);
            if(msg != null)
                System.out.println(msg.toString());
            else
                System.out.println("null");
            // }
        }
        else if(Objects.equals(messageRequestTo.getMethod(), "POST")){
            MessageResponseTo msg= create(messageRequestTo);
            kafkaSender.sendMessage(msg, topic);
            System.out.println(msg.toString());
        }
        else if (Objects.equals(messageRequestTo.getMethod(), "PUT")){
            MessageResponseTo msg= update(messageRequestTo);
            kafkaSender.sendMessage(msg, topic);
            System.out.println(msg.toString());
        }
        else if(Objects.equals(messageRequestTo.getMethod(), "DELETE")){
            MessageResponseTo msg= delete(messageRequestTo.getId());
            kafkaSender.sendMessage(msg, topic);
            System.out.println(msg.toString());
        }
    }

    @PostMapping(value = "messages")
    public MessageResponseTo create(@RequestBody MessageRequestTo messageRequestTo) {
        return messageService.create(messageRequestTo, messageRequestTo.getCountry());
    }

    @GetMapping(value = "messages", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read()
    {
        final List<MessageResponseTo> list = messageService.readAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "messages/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(@PathVariable(name = "id") int id)
    {
        final MessageResponseTo message = messageService.read(id);
        if(message == null){
            return new ResponseEntity<>(new NotFoundException("Message not found", 404), HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public MessageResponseTo readById(int id) {
        return messageService.read(id);
    }

    @PutMapping(value = "messages")
    public MessageResponseTo update(@RequestBody MessageRequestTo messageRequestTo) {
        return messageService.update(messageRequestTo, messageRequestTo.getCountry());
    }

    @DeleteMapping(value = "messages/{id}")
    public MessageResponseTo delete(@PathVariable(name = "id") int id) {
        boolean res = messageService.delete(id);
        if(res)
            return new MessageResponseTo();
        else
            return null;
    }
}
