package org.education.controller;

import org.education.bean.Issue;
import org.education.bean.dto.IssueRequestTo;
import org.education.bean.dto.IssueResponseTo;
import org.education.exception.AlreadyExists;
import org.education.exception.ErrorResponse;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchIssue;
import org.education.service.IssueService;
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
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    private final ModelMapper modelMapper;

    public IssueController(IssueService issueService, ModelMapper modelMapper) {
        this.issueService = issueService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<IssueResponseTo>> getAllIssues(){
        List<IssueResponseTo> response = new ArrayList<>();
        for(Issue issue : issueService.getAll()){
            response.add(modelMapper.map(issue, IssueResponseTo.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<IssueResponseTo> createIssue(@RequestBody @Valid IssueRequestTo issueRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Issue issue = issueService.create(modelMapper.map(issueRequestTo, Issue.class), issueRequestTo.getCreatorId());
        return new ResponseEntity<>(modelMapper.map(issue, IssueResponseTo.class), HttpStatus.valueOf(201));
    }


    @PutMapping
    public ResponseEntity<IssueResponseTo> updateIssue(@RequestBody @Valid IssueRequestTo issueRequestTo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Issue issue = issueService.update(modelMapper.map(issueRequestTo, Issue.class));
        return new ResponseEntity<>(modelMapper.map(issue, IssueResponseTo.class), HttpStatus.valueOf(200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteIssue(@PathVariable int id){
        issueService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }


    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseTo> getIssue(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(issueService.getById(id), IssueResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchIssue exception){
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