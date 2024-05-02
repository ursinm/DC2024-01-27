package com.example.rv.api.Controllers;

import com.example.rv.impl.issue.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @RequestMapping(value = "/issues", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<IssueResponseTo> getTweets() {
        return issueService.tweetMapper.tweetToResponseTo(issueService.tweetCrudRepository.getAll());
    }

    @RequestMapping(value = "/issues", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    IssueResponseTo makeTweet(@RequestBody IssueRequestTo issueRequestTo) {

        var toBack = issueService.tweetCrudRepository.save(
                issueService.tweetMapper.dtoToEntity(issueRequestTo)
        );

        Issue issue = toBack.orElse(null);

        assert issue != null;
        return issueService.tweetMapper.tweetToResponseTo(issue);
    }

    @RequestMapping(value = "/issues/{id}", method = RequestMethod.GET)
    IssueResponseTo getTweet(@PathVariable Long id) {
        return issueService.tweetMapper.tweetToResponseTo(
                Objects.requireNonNull(issueService.tweetCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/issues", method = RequestMethod.PUT)
    IssueResponseTo updateTweet(@RequestBody IssueRequestTo issueRequestTo, HttpServletResponse response) {
        Issue issue = issueService.tweetMapper.dtoToEntity(issueRequestTo);
        var newTweet = issueService.tweetCrudRepository.update(issue).orElse(null);
        if (newTweet != null) {
            response.setStatus(200);
            return issueService.tweetMapper.tweetToResponseTo(newTweet);
        } else{
            response.setStatus(403);
            return issueService.tweetMapper.tweetToResponseTo(issue);
        }
    }

    @RequestMapping(value = "/issues/{id}", method = RequestMethod.DELETE)
    int deleteTweet(@PathVariable Long id, HttpServletResponse response) {
        Issue issueToDelete = issueService.tweetCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(issueToDelete)) {
            response.setStatus(403);
        } else {
            issueService.tweetCrudRepository.delete(issueToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
