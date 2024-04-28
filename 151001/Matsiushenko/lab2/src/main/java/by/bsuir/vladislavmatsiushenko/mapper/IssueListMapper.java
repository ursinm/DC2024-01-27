package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.IssueRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.IssueResponseTo;
import by.bsuir.vladislavmatsiushenko.model.Issue;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface IssueListMapper {
    List<Issue> toIssueList(List<IssueRequestTo> list);

    List<IssueResponseTo> toIssueResponseList(List<Issue> list);
}
