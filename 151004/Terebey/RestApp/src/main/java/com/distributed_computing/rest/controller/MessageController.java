package com.distributed_computing.rest.controller;

import com.distributed_computing.rest.exception.ErrorResponse;
import com.distributed_computing.rest.exception.IncorrectValuesException;
import com.distributed_computing.rest.exception.NoSuchMessage;
import com.distributed_computing.rest.bean.Message;
import com.distributed_computing.rest.bean.dto.MessageRequestTo;
import com.distributed_computing.rest.bean.dto.MessageResponseTo;
import com.distributed_computing.rest.service.MessageService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    private final ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getAllMessages(){
        List<MessageResponseTo> response = new ArrayList<>();
        for(Message message : messageService.getAll()){
            response.add(modelMapper.map(message, MessageResponseTo.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> createMessage(@RequestBody @Valid MessageRequestTo messageRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Message message = messageService.create(modelMapper.map(messageRequestTo, Message.class));
        return new ResponseEntity<>(modelMapper.map(message, MessageResponseTo.class), HttpStatus.valueOf(201));
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody @Valid MessageRequestTo messageRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Message message = messageService.update(modelMapper.map(messageRequestTo, Message.class));
        return new ResponseEntity<>(modelMapper.map(message, MessageResponseTo.class), HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable int id){
        messageService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessage(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(messageService.getById(id).get(), MessageResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchMessage exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }
}