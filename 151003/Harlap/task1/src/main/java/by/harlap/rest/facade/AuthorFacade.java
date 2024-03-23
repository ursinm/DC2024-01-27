package by.harlap.rest.facade;

import by.harlap.rest.dto.request.CreateAuthorDto;
import by.harlap.rest.dto.request.UpdateAuthorDto;
import by.harlap.rest.dto.response.AuthorResponseDto;
import by.harlap.rest.mapper.AuthorMapper;
import by.harlap.rest.model.Author;
import by.harlap.rest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorFacade {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorResponseDto findById(Long id) {
        Author author = authorService.findById(id);
        return authorMapper.toAuthorResponse(author);
    }

    public List<AuthorResponseDto> findAll() {
        List<Author> authors = authorService.findAll();

        return authors.stream().map(authorMapper::toAuthorResponse).toList();
    }

    public AuthorResponseDto save(CreateAuthorDto authorRequest) {
        Author author = authorMapper.toAuthor(authorRequest);

        Author savedAuthor = authorService.save(author);

        return authorMapper.toAuthorResponse(savedAuthor);
    }

    public AuthorResponseDto update(UpdateAuthorDto authorRequest) {
        Author author = authorService.findById(authorRequest.getId());

        Author updatedAuthor = authorMapper.toAuthor(authorRequest, author);

        return authorMapper.toAuthorResponse(authorService.update(updatedAuthor));
    }

    public void delete(Long id) {
        authorService.deleteById(id);
    }
}
