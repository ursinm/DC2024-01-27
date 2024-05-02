package com.example.rv.impl.issue;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IssueMapperImpl implements IssueMapper {
    @Override
    public IssueRequestTo tweetToRequestTo(Issue issue) {
        return new IssueRequestTo(
                issue.getId(),
                issue.getCreatorId(),
                issue.getTitle(),
                issue.getContent(),
                issue.getCreated(),
                issue.getModified()
        );
    }

    @Override
    public List<IssueRequestTo> tweetToRequestTo(Iterable<Issue> tweets) {
        return StreamSupport.stream(tweets.spliterator(), false)
                .map(this::tweetToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Issue dtoToEntity(IssueRequestTo issueRequestTo) {
        return new Issue(
                issueRequestTo.id(),
                issueRequestTo.creatorId(),
                issueRequestTo.title(),
                issueRequestTo.content(),
                issueRequestTo.created(),
                issueRequestTo.modified());
    }

    @Override
    public List<Issue> dtoToEntity(Iterable<IssueRequestTo> tweetRequestTos) {
        return StreamSupport.stream(tweetRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public IssueResponseTo tweetToResponseTo(Issue issue) {
        return new IssueResponseTo(
                issue.getId(),
                issue.getCreatorId(),
                issue.getTitle(),
                issue.getContent(),
                issue.getCreated(),
                issue.getModified()
        );
    }

    @Override
    public List<IssueResponseTo> tweetToResponseTo(Iterable<Issue> tweets) {
        return StreamSupport.stream(tweets.spliterator(), false)
                .map(this::tweetToResponseTo)
                .collect(Collectors.toList());
    }
}
