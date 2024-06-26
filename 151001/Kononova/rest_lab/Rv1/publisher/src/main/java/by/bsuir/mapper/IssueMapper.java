package by.bsuir.mapper;

import by.bsuir.dto.IssueRequestTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.entities.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    Issue issueRequestToIssue (IssueRequestTo issueRequestTo);

    @Mapping(target = "userId", source = "issue.user.id")
    IssueResponseTo issueToIssueResponse(Issue issue);
}
