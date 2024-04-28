package by.bsuir.restapi.service;

import by.bsuir.restapi.exception.ResourceNotFoundException;
import by.bsuir.restapi.model.dto.request.AuthorRequestDto;
import by.bsuir.restapi.model.dto.response.AuthorResponseDto;
import by.bsuir.restapi.model.entity.Author;
import by.bsuir.restapi.model.mapper.AuthorMapper;
import by.bsuir.restapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponseDto create(AuthorRequestDto authorRequestDto) {
        Author author = authorMapper.toEntity(authorRequestDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    public AuthorResponseDto update(AuthorRequestDto authorRequestDto) {
        Author author = authorRepository.findById(authorRequestDto.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with id %s not found", authorRequestDto.id())));
        final Author updatedAuthor = authorMapper.partialUpdate(authorRequestDto, author);
        return authorMapper.toDto(authorRepository.save(updatedAuthor));
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with id %s not found", id)));
        authorRepository.delete(author);
    }

    @Transactional(readOnly = true)
    public AuthorResponseDto get(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with id %s not found", id)));
        return authorMapper.toDto(author);
    }

    @Transactional(readOnly = true)
    public List<AuthorResponseDto> getAll() {
        return authorMapper.toDto(authorRepository.findAll());
    }

}
