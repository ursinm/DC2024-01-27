package by.haritonenko.rest.facade;

import by.haritonenko.rest.mapper.AuthorMapper;
import by.haritonenko.rest.service.AuthorService;
import by.haritonenko.rest.dto.request.CreateAuthorDto;
import by.haritonenko.rest.dto.request.UpdateAuthorDto;
import by.haritonenko.rest.dto.response.AuthorResponseDto;
import by.haritonenko.rest.model.Author;
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
