package by.bsuir.vladislavmatsiushenko.impl.controllers;

import by.bsuir.vladislavmatsiushenko.impl.service.IssueService;

import by.bsuir.vladislavmatsiushenko.impl.dto.IssueRequestTo;
import by.bsuir.vladislavmatsiushenko.impl.dto.IssueResponseTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @GetMapping("/issues")
    public ResponseEntity<List<IssueResponseTo>> getAllIssues() {
        List<IssueResponseTo> issueResponseToList = issueService.getAll();

        return new ResponseEntity<>(issueResponseToList, HttpStatus.OK);
    }

    @GetMapping("/issues/{id}")
    public ResponseEntity<IssueResponseTo> getIssue(@PathVariable long id) {
        IssueResponseTo issueResponseTo = issueService.get(id);

        return new ResponseEntity<>(issueResponseTo, issueResponseTo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("/issues")
    public ResponseEntity<IssueResponseTo> createIssue(@RequestBody IssueRequestTo IssueTo) {
        IssueResponseTo addedIssue = issueService.add(IssueTo);

        return new ResponseEntity<>(addedIssue, HttpStatus.CREATED);
    }

    @DeleteMapping("/issues/{id}")
    public ResponseEntity<IssueResponseTo> deleteIssue(@PathVariable long id) {
        IssueResponseTo deletedIssue = issueService.delete(id);

        return new ResponseEntity<>(deletedIssue, deletedIssue == null ? HttpStatus.NOT_FOUND : HttpStatus.NO_CONTENT);
    }

    @PutMapping("/issues")
    public ResponseEntity<IssueResponseTo> updateIssue(@RequestBody IssueRequestTo issueRequestTo) {
        IssueResponseTo issueResponseTo = issueService.update(issueRequestTo);

        return new ResponseEntity<>(issueResponseTo, issueResponseTo.getContent() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
