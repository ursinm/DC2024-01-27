package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.IssueRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.IssueResponseTo;
import by.bsuir.vladislavmatsiushenko.model.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueRequestToIssue(IssueRequestTo issueRequestTo);

    @Mapping(target = "userId", source = "issue.user.id")
    IssueResponseTo issueToIssueResponse(Issue issue);
}
