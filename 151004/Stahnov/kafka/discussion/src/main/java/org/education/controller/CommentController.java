package org.education.controller;


import jakarta.validation.Valid;
import org.education.bean.Comment;
import org.education.bean.DTO.CommentMessageRequest;
import org.education.bean.DTO.CommentMessageResponse;
import org.education.bean.DTO.CommentRequestTo;
import org.education.bean.DTO.CommentResponseTo;
import org.education.exception.AlreadyExists;
import org.education.exception.ErrorResponse;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchComment;
import org.education.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @Autowired
    private KafkaTemplate<String, CommentMessageResponse> commentKafkaTemplate;

    @Value("${kafka.query-topic}")
    private String queryTopic;

    @Value("${kafka.result-topic}")
    private String resultTopic;

    @KafkaListener(topics = "${kafka.query-topic}", groupId = "discussion-group",
            containerFactory = "userKafkaListenerContainerFactory")
    void listenerWithMessageConverter(CommentMessageRequest commentMessage) {
        switch (commentMessage.getMessage()){
            case "GET" -> {
                if(commentMessage.getComments().isEmpty()){
                    List<CommentResponseTo> response = new ArrayList<>();
                    for(Comment comment : commentService.getAll()){
                        response.add(modelMapper.map(comment, CommentResponseTo.class));
                    }
                    commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("SUCCESS", commentMessage.getIndex(), response));
                }
                else{
                    try{
                        Comment comment = commentService.getById(commentMessage.getComments().get(0).getKey());
                        commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("SUCCESS", commentMessage.getIndex(), List.of(modelMapper.map(comment, CommentResponseTo.class))));
                    }
                    catch (NoSuchComment e){
                        commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("NO SUCH COMMENT", commentMessage.getIndex(), new ArrayList<>()));
                    }
                }
            }
            case "CREATE" -> {
                try{
                    Comment comment = commentService.create(modelMapper.map(commentMessage.getComments().get(0), Comment.class));
                    commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("SUCCESS", commentMessage.getIndex(), List.of(modelMapper.map(comment, CommentResponseTo.class))));
                }
                catch (AlreadyExists e){
                    commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("ALREADY EXISTS", commentMessage.getIndex(), new ArrayList<>()));
                }
            }
            case "DELETE" -> {
                try{
                    commentService.delete(commentMessage.getComments().get(0).getKey());
                    commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("SUCCESS", commentMessage.getIndex(), new ArrayList<>()));
                }
                catch (NoSuchComment e){
                    commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("NO SUCH COMMENT", commentMessage.getIndex(), new ArrayList<>()));
                }
            }
            case "PUT" ->   {
                try{
                    Comment comment = commentService.update(modelMapper.map(commentMessage.getComments().get(0), Comment.class));
                    commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("SUCCESS", commentMessage.getIndex(), List.of(modelMapper.map(comment, CommentResponseTo.class))));
                }
                catch (NoSuchComment e){
                    commentKafkaTemplate.send(resultTopic, new CommentMessageResponse("NO SUCH COMMENT", commentMessage.getIndex(), new ArrayList<>()));
                }
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseTo>> getAllComments(){
        List<CommentResponseTo> response = new ArrayList<>();
        for(Comment comment : commentService.getAll()){
            response.add(modelMapper.map(comment, CommentResponseTo.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponseTo> createComment(@RequestBody @Valid CommentRequestTo commentRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Comment comment = commentService.create(modelMapper.map(commentRequestTo, Comment.class));
        return new ResponseEntity<>(modelMapper.map(comment, CommentResponseTo.class), HttpStatus.valueOf(201));
    }

    @PutMapping
    public ResponseEntity<CommentResponseTo> updateComment(@RequestBody @Valid CommentRequestTo commentRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Comment comment = commentService.update(modelMapper.map(commentRequestTo, Comment.class));
        return new ResponseEntity<>(modelMapper.map(comment, CommentResponseTo.class), HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable int id){
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getComment(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(commentService.getById(id), CommentResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchComment exception){
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
}
