package com.example.publisher.controller;

import com.example.publisher.model.request.IssueRequestTo;
import com.example.publisher.model.response.IssueResponseTo;
import com.example.publisher.service.IssueService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/issues")
@Data
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo findById(@Valid @PathVariable("id") Long id) {
        return issueService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponseTo> findAll() {
        return issueService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IssueResponseTo create(@Valid @RequestBody IssueRequestTo request) {
        return issueService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo update(@Valid @RequestBody IssueRequestTo request) {
        return issueService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean removeById(@Valid @PathVariable("id") Long id) {
        return issueService.removeById(id);
    }
}
