package by.bsuir.publisher.model.mapper;

import by.bsuir.publisher.model.entity.Author;
import by.bsuir.publisher.model.entity.Issue;
import by.bsuir.publisher.repository.AuthorRepository;
import by.bsuir.publisher.repository.IssueRepository;
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
