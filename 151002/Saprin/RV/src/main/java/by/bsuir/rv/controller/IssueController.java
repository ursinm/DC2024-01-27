package by.bsuir.rv.controller;

import by.bsuir.rv.dto.IssueRequestTo;
import by.bsuir.rv.dto.IssueResponseTo;
import by.bsuir.rv.exception.EntititesNotFoundException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.service.issue.IIssueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IIssueService issueService;

    @Autowired
    public IssueController(IIssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponseTo> getIssues() throws EntititesNotFoundException {
        return issueService.getIssues();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo getIssueById(@PathVariable BigInteger id) throws EntititesNotFoundException, EntityNotFoundException {
        return issueService.getIssueById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public IssueResponseTo createIssue(@Valid @RequestBody IssueRequestTo issue) throws EntititesNotFoundException, EntityNotFoundException {
        return issueService.addIssue(issue);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo updateIssue(@Valid @RequestBody IssueRequestTo issue) throws EntititesNotFoundException, EntityNotFoundException {
        return issueService.updateIssue(issue);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssue(@PathVariable BigInteger id) throws EntityNotFoundException {
        issueService.deleteIssue(id);
    }
}
