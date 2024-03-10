package com.distributed_computing.rest.controller;

import com.distributed_computing.rest.exception.ErrorResponse;
import com.distributed_computing.rest.exception.IncorrectValuesException;
import com.distributed_computing.rest.exception.NoSuchComment;
import com.distributed_computing.bean.Comment;
import com.distributed_computing.bean.DTO.CommentRequestTo;
import com.distributed_computing.bean.DTO.CommentResponseTo;
import com.distributed_computing.rest.service.CommentService;
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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
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
        return new ResponseEntity<>(modelMapper.map(commentService.getById(id).get(), CommentResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchComment exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }
}
