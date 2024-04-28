package com.distributed_computing.jpa.controller;

import com.distributed_computing.jpa.exception.AlreadyExists;
import com.distributed_computing.jpa.exception.ErrorResponse;
import com.distributed_computing.jpa.exception.IncorrectValuesException;
import com.distributed_computing.jpa.exception.NoSuchLabel;
import com.distributed_computing.jpa.bean.dto.LabelRequestTo;
import com.distributed_computing.jpa.bean.dto.LabelResponseTo;
import com.distributed_computing.jpa.bean.Label;
import com.distributed_computing.jpa.service.LabelService;
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
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    private final ModelMapper modelMapper;

    @Autowired
    public LabelController(LabelService labelService, ModelMapper modelMapper) {
        this.labelService = labelService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<LabelResponseTo>> getAllLabels(){
        List<LabelResponseTo> response = new ArrayList<>();
        for(Label label : labelService.getAll()){
            response.add(modelMapper.map(label, LabelResponseTo.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LabelResponseTo> createLabel(@RequestBody @Valid LabelRequestTo labelRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Label label = labelService.create(modelMapper.map(labelRequestTo, Label.class));
        return new ResponseEntity<>(modelMapper.map(label, LabelResponseTo.class), HttpStatus.valueOf(201));
    }

    @PutMapping
    public ResponseEntity<LabelResponseTo> updateLabel(@RequestBody @Valid LabelRequestTo labelRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Label label = labelService.update(modelMapper.map(labelRequestTo, Label.class));
        return new ResponseEntity<>(modelMapper.map(label, LabelResponseTo.class), HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLabel(@PathVariable int id){
        labelService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelResponseTo> getLabel(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(labelService.getById(id), LabelResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchLabel exception){
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