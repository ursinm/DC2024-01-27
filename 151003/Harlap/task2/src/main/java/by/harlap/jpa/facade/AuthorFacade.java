package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateAuthorDto;
import by.harlap.jpa.dto.request.UpdateAuthorDto;
import by.harlap.jpa.dto.response.AuthorResponseDto;
import by.harlap.jpa.mapper.AuthorMapper;
import by.harlap.jpa.model.Author;
import by.harlap.jpa.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorFacade {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public AuthorResponseDto findById(Long id) {
        Author author = authorService.findById(id);
        return authorMapper.toAuthorResponse(author);
    }

    @Transactional(readOnly = true)
    public List<AuthorResponseDto> findAll() {
        List<Author> authors = authorService.findAll();

        return authors.stream().map(authorMapper::toAuthorResponse).toList();
    }

    @Transactional
    public AuthorResponseDto save(CreateAuthorDto authorRequest) {
        Author author = authorMapper.toAuthor(authorRequest);

        Author savedAuthor = authorService.save(author);

        return authorMapper.toAuthorResponse(savedAuthor);
    }

    @Transactional
    public AuthorResponseDto update(UpdateAuthorDto authorRequest) {
        Author author = authorService.findById(authorRequest.getId());

        Author updatedAuthor = authorMapper.toAuthor(authorRequest, author);

        return authorMapper.toAuthorResponse(authorService.update(updatedAuthor));
    }

    @Transactional
    public void delete(Long id) {
        authorService.deleteById(id);
    }
}
