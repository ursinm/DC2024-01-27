package by.bsuir.springapi.service.mapper;

import by.bsuir.springapi.model.entity.Issue;
import by.bsuir.springapi.model.request.IssueRequestTo;
import by.bsuir.springapi.model.response.IssueResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface IssueMapper {
    IssueResponseTo getResponseTo(Issue issue);

    List<IssueResponseTo> getListResponseTo(Iterable<Issue> news);

    Issue getNews(IssueRequestTo issueRequestTo);
}
