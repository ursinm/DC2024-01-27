package by.bsuir.controllers;

import by.bsuir.dto.IssueRequestTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/issues")
public class IssueController {
    @Autowired
    IssueService issueService;

    @GetMapping
    public List<IssueResponseTo> getIssues() {
        return issueService.getIssues();
    }
    @GetMapping("/{id}")
    public IssueResponseTo getIssue(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Long id){issueService.deleteIssue(id);}

    @PostMapping
    public IssueResponseTo saveIssue(@RequestBody IssueRequestTo issue){
        return issueService.saveIssue(issue);
    }

    @PostMapping("/{id}")
    public IssueResponseTo updateIssue(@RequestBody IssueRequestTo issue, @PathVariable Long id){
        return issueService.updateIssue(issue, id);
    }
}