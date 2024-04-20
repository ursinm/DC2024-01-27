package com.distributed_computing.rest.controller;

import com.distributed_computing.bean.Creator;
import com.distributed_computing.bean.DTO.CreatorRequestTo;
import com.distributed_computing.bean.DTO.CreatorResponseTo;
import com.distributed_computing.bean.DTO.TweetRequestTo;
import com.distributed_computing.bean.DTO.TweetResponseTo;
import com.distributed_computing.bean.Tweet;
import com.distributed_computing.rest.exception.ErrorResponse;
import com.distributed_computing.rest.exception.IncorrectValuesException;
import com.distributed_computing.rest.exception.NoSuchCreator;
import com.distributed_computing.rest.exception.NoSuchTweet;
import com.distributed_computing.rest.service.TweetService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tweets")
public class TweetController {
    private final TweetService tweetService;

    private final ModelMapper modelMapper;

    public TweetController(TweetService tweetService, ModelMapper modelMapper) {
        this.tweetService = tweetService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TweetResponseTo>> getAllTweets(){
        List<TweetResponseTo> response = new ArrayList<>();
        for(Tweet tweet : tweetService.getAll()){
            response.add(modelMapper.map(tweet, TweetResponseTo.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<TweetResponseTo> createTweet(@RequestBody @Valid TweetRequestTo tweetRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Tweet tweet = tweetService.create(modelMapper.map(tweetRequestTo, Tweet.class));
        return new ResponseEntity<>(modelMapper.map(tweet, TweetResponseTo.class), HttpStatus.valueOf(201));
    }


    @PutMapping
    public ResponseEntity<TweetResponseTo> updateTweet(@RequestBody @Valid TweetRequestTo tweetRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Tweet tweet = tweetService.update(modelMapper.map(tweetRequestTo, Tweet.class));
        return new ResponseEntity<>(modelMapper.map(tweet, TweetResponseTo.class), HttpStatus.valueOf(200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTweet(@PathVariable int id){
        tweetService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseTo> getTweet(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(tweetService.getById(id).get(), TweetResponseTo.class), HttpStatus.valueOf(200));
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
