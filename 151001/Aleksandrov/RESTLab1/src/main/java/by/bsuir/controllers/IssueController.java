package by.bsuir.controllers;

import by.bsuir.dto.EditorResponseTo;
import by.bsuir.dto.IssueRequestTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.exceptions.editor.EditorDeleteException;
import by.bsuir.exceptions.editor.EditorUpdateException;
import by.bsuir.exceptions.issue.IssueDeleteException;
import by.bsuir.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/issues")
public class IssueController {
    @Autowired
    IssueService issueService;

    @GetMapping
    public ResponseEntity<List<IssueResponseTo>> getIssues() {
        return ResponseEntity.status(200).body(issueService.getIssues());
    }
    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseTo> getIssue(@PathVariable Long id) {
        return ResponseEntity.status(200).body(issueService.getIssueById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id){
        try {
            issueService.deleteIssue(id);
        } catch (IssueDeleteException exception){
            return ResponseEntity.status(exception.getStatus()).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<IssueResponseTo> saveIssue(@RequestBody IssueRequestTo issue){
        IssueResponseTo savedIssue = issueService.saveIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIssue);
    }

    @PutMapping()
    public ResponseEntity<IssueResponseTo> updateIssue(@RequestBody IssueRequestTo issue){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(issue));
        } catch (EditorUpdateException exception){
            return ResponseEntity.status(exception.getStatus()).build();
        }
    }
}