package by.haritonenko.rest.service.impl;

import by.haritonenko.rest.exception.EntityNotFoundException;
import by.haritonenko.rest.repository.AbstractRepository;
import by.haritonenko.rest.model.Author;
import by.haritonenko.rest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    public static final String AUTHOR_NOT_FOUND_MESSAGE = "Author with id '%d' doesn't exist";
    private final AbstractRepository<Author, Long> authorRepository;

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> {
            final String message = AUTHOR_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(AUTHOR_NOT_FOUND_MESSAGE));

        authorRepository.deleteById(id);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        authorRepository.findById(author.getId()).orElseThrow(()-> new EntityNotFoundException(AUTHOR_NOT_FOUND_MESSAGE));

        return authorRepository.update(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
