package org.education.controller;

import org.education.bean.Message;
import org.education.bean.dto.MessageRequestTo;
import org.education.bean.dto.MessageResponseTo;
import org.education.exception.*;
import org.education.service.MessageService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    private final ModelMapper modelMapper;

    public static String fromCache = "";

    @Value("${secret.key}")
    private String secretKey;

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

        try {
            fromCache = messageRequestTo.getContent();
            Message message = messageService.create(messageRequestTo);
            return new ResponseEntity<>(modelMapper.map(message, MessageResponseTo.class), HttpStatus.CREATED);
        } catch (Exception e) {
            // Вернуть ResponseEntity с кодом ошибки 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody @Valid MessageRequestTo messageRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Message message = messageService.update(messageRequestTo);
        return new ResponseEntity<>(modelMapper.map(message, MessageResponseTo.class), HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable int id){
        messageService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessage(@PathVariable int id){

        var body = messageService.getById(id);
        if (body.getContent().contains(secretKey)) body.setContent(fromCache);
        return new ResponseEntity<>(modelMapper.map(body, MessageResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchMessage exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IncorrectValuesException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(AlreadyExists exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 403),HttpStatus.valueOf(403));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(HttpClientErrorException.NotFound notFound){
        return new ResponseEntity<>(new ErrorResponse(notFound.getMessage(), 404),HttpStatus.valueOf(404));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(HttpClientErrorException.BadRequest badRequest) {
        return new ResponseEntity<>(new ErrorResponse(badRequest.getMessage(), 400), HttpStatus.valueOf(400));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchIssue exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 405),HttpStatus.valueOf(405));
    }

}