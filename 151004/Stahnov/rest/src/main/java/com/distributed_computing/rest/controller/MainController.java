package com.distributed_computing.rest.controller;

import com.distributed_computing.rest.exception.*;
import com.distributed_computing.rest.bean.Creator;
import com.distributed_computing.rest.bean.DTO.*;
import com.distributed_computing.rest.bean.Tweet;
import com.distributed_computing.rest.service.CreatorService;
import com.distributed_computing.rest.service.TweetService;
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
@RequestMapping("/")
public class MainController {

    private final TweetService tweetService;
    private final CreatorService creatorService;

    private final ModelMapper modelMapper;

    @Autowired
    public MainController(TweetService tweetService, CreatorService creatorService, ModelMapper modelMapper) {
        this.tweetService = tweetService;
        this.creatorService = creatorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/creators")
    public ResponseEntity<List<CreatorResponseTo>> getAllCreators(){
        return new ResponseEntity<>(creatorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/tweets")
    public ResponseEntity<List<TweetResponseTo>> getAllTweets(){
        List<TweetResponseTo> response = new ArrayList<>();
        for(Tweet tweet : tweetService.getAll()){
            response.add(modelMapper.map(tweet, TweetResponseTo.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("/creators")
    public ResponseEntity<CreatorResponseTo> createCreator(@RequestBody @Valid CreatorRequestTo creatorRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Creator creator = creatorService.create(modelMapper.map(creatorRequestTo, Creator.class));
        return new ResponseEntity<>(modelMapper.map(creator, CreatorResponseTo.class), HttpStatus.valueOf(201));
    }

    @PostMapping("/tweets")
    public ResponseEntity<TweetResponseTo> createTweet(@RequestBody @Valid TweetRequestTo tweetRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Tweet tweet = tweetService.create(modelMapper.map(tweetRequestTo, Tweet.class));
        return new ResponseEntity<>(modelMapper.map(tweet, TweetResponseTo.class), HttpStatus.valueOf(201));
    }





    @PutMapping("/creators")
    public ResponseEntity<CreatorResponseTo> updateCreator(@RequestBody @Valid CreatorRequestTo creatorRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Creator creator = creatorService.update(modelMapper.map(creatorRequestTo, Creator.class));
        return new ResponseEntity<>(modelMapper.map(creator, CreatorResponseTo.class), HttpStatus.valueOf(200));
    }

    @PutMapping("/tweets")
    public ResponseEntity<TweetResponseTo> updateTweet(@RequestBody @Valid TweetRequestTo tweetRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Tweet tweet = tweetService.update(modelMapper.map(tweetRequestTo, Tweet.class));
        return new ResponseEntity<>(modelMapper.map(tweet, TweetResponseTo.class), HttpStatus.valueOf(200));
    }

    @DeleteMapping("/creators/{id}")
    public ResponseEntity<HttpStatus> deleteCreator(@PathVariable int id){
        creatorService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<HttpStatus> deleteTweet(@PathVariable int id){
        tweetService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }





    @GetMapping("/creators/{id}")
    public ResponseEntity<CreatorResponseTo> getCreator(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(creatorService.getById(id), CreatorResponseTo.class), HttpStatus.valueOf(200));
    }

    @GetMapping("/tweets/{id}")
    public ResponseEntity<TweetResponseTo> getTweet(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(tweetService.getById(id), TweetResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchCreator exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchTweet exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IncorrectValuesException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

}
