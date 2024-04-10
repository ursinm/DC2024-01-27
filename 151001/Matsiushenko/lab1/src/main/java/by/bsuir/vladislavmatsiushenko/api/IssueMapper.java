package by.bsuir.vladislavmatsiushenko.api;

import by.bsuir.vladislavmatsiushenko.impl.bean.Issue;
import by.bsuir.vladislavmatsiushenko.impl.dto.IssueRequestTo;
import by.bsuir.vladislavmatsiushenko.impl.dto.IssueResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IssueMapper {
    IssueMapper INSTANCE = Mappers.getMapper(IssueMapper.class);
    IssueRequestTo IssueToIssueRequestTo(Issue issue);
    IssueResponseTo IssueToIssueResponseTo(Issue issue);
    Issue IssueResponseToToIssue(IssueResponseTo responseTo);
    Issue IssueRequestToToIssue(IssueRequestTo requestTo);
}
