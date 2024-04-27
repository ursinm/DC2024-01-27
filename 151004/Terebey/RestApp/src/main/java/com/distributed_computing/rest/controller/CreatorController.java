package com.distributed_computing.rest.controller;

import com.distributed_computing.rest.bean.Creator;
import com.distributed_computing.rest.bean.dto.CreatorRequestTo;
import com.distributed_computing.rest.bean.dto.CreatorResponseTo;
import com.distributed_computing.rest.bean.dto.IssueRequestTo;
import com.distributed_computing.rest.bean.dto.IssueResponseTo;
import com.distributed_computing.rest.bean.Issue;
import com.distributed_computing.rest.exception.ErrorResponse;
import com.distributed_computing.rest.exception.IncorrectValuesException;
import com.distributed_computing.rest.exception.NoSuchCreator;
import com.distributed_computing.rest.exception.NoSuchIssue;
import com.distributed_computing.rest.service.CreatorService;
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
@RequestMapping("/creators")
public class CreatorController {
    private final CreatorService creatorService;

    private final ModelMapper modelMapper;

    public CreatorController(CreatorService creatorService, ModelMapper modelMapper) {
        this.creatorService = creatorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> getAllCreators(){
        return new ResponseEntity<>(creatorService.getAll(), HttpStatus.OK);
    }


    @PostMapping("/creatorsByIssue")
    public ResponseEntity<CreatorResponseTo> getCreatorByIssueId(@RequestBody @Valid IssueRequestTo issueRequestTo, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors("id")){
            throw new IncorrectValuesException("Incorrect input values");
        }
        return new ResponseEntity<>(modelMapper.map(creatorService.getCreatorByIssueId(issueRequestTo.getId()).get(), CreatorResponseTo.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreatorResponseTo> createCreator(@RequestBody @Valid CreatorRequestTo creatorRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Creator creator = creatorService.create(modelMapper.map(creatorRequestTo, Creator.class));
        return new ResponseEntity<>(modelMapper.map(creator, CreatorResponseTo.class), HttpStatus.valueOf(201));
    }


    @PutMapping
    public ResponseEntity<CreatorResponseTo> updateCreator(@RequestBody @Valid CreatorRequestTo creatorRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Creator creator = creatorService.update(modelMapper.map(creatorRequestTo, Creator.class));
        return new ResponseEntity<>(modelMapper.map(creator, CreatorResponseTo.class), HttpStatus.valueOf(200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCreator(@PathVariable int id){
        creatorService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseTo> getCreator(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(creatorService.getById(id).get(), CreatorResponseTo.class), HttpStatus.valueOf(200));
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchCreator exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IncorrectValuesException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
}