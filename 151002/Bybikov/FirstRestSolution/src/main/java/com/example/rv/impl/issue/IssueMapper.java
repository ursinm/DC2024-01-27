package com.example.rv.impl.issue;

import java.util.List;

public interface IssueMapper {

    IssueRequestTo tweetToRequestTo(Issue issue);

    List<IssueRequestTo> tweetToRequestTo(Iterable<Issue> tweets);

    Issue dtoToEntity(IssueRequestTo issueRequestTo);

    List<Issue> dtoToEntity(Iterable<IssueRequestTo> tweetRequestTos);

    IssueResponseTo tweetToResponseTo(Issue issue);

    List<IssueResponseTo> tweetToResponseTo(Iterable<Issue> tweets);
}
