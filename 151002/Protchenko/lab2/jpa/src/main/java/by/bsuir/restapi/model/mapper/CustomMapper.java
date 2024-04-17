package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.entity.Author;
import by.bsuir.restapi.model.entity.Issue;
import by.bsuir.restapi.repository.AuthorRepository;
import by.bsuir.restapi.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMapper {

    private final IssueRepository issueRepository;
    private final AuthorRepository authorRepository;

    @Named("issueIdToIssueRef")
    public Issue issueIdToIssueRef(Long issueId) {
        return issueRepository.getReferenceById(issueId);
    }

    @Named("authorIdToAuthorRef")
    public Author authorIdToAuthorRef(Long authorId) {
        return authorRepository.getReferenceById(authorId);
    }
}
