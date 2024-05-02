package by.bsuir.springapi.service.mapper;

import by.bsuir.springapi.model.entity.Issue;
import by.bsuir.springapi.model.request.IssueRequestTo;
import by.bsuir.springapi.model.response.IssueResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface IssueMapper {

    @Mapping(source = "i_content", target = "content")
    IssueResponseTo getResponseTo(Issue issue);

    @Mapping(source = "i_content", target = "content")
    List<IssueResponseTo> getListResponseTo(Iterable<Issue> issue);

    @Mapping(source = "content", target = "i_content")
    Issue getNews(IssueRequestTo issueRequestTo);
}
