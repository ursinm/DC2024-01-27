package com.distributed_computing.jpa.controller;

import com.distributed_computing.jpa.bean.Creator;
import com.distributed_computing.jpa.bean.DTO.CreatorRequestTo;
import com.distributed_computing.jpa.bean.DTO.CreatorResponseTo;
import com.distributed_computing.jpa.exception.AlreadyExists;
import com.distributed_computing.jpa.exception.ErrorResponse;
import com.distributed_computing.jpa.exception.IncorrectValuesException;
import com.distributed_computing.jpa.exception.NoSuchCreator;
import com.distributed_computing.jpa.service.CreatorService;
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
        List<CreatorResponseTo> res = new ArrayList<>();
        for(Creator creator : creatorService.getAll()){
            res.add(modelMapper.map(creator, CreatorResponseTo.class));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreatorResponseTo> createCreator(@RequestBody @Valid CreatorRequestTo creatorRequestTo, BindingResult bindingResult){
        Creator newCreator = modelMapper.map(creatorRequestTo, Creator.class);
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Creator creator = creatorService.create(newCreator);
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
        return new ResponseEntity<>(modelMapper.map(creatorService.getById(id), CreatorResponseTo.class), HttpStatus.valueOf(200));
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchCreator exception){
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
