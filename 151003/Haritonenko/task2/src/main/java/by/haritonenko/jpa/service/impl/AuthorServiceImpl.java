package by.haritonenko.jpa.service.impl;

import by.haritonenko.jpa.exception.EntityNotFoundException;
import by.haritonenko.jpa.model.Author;
import by.haritonenko.jpa.repository.impl.AuthorRepository;
import by.haritonenko.jpa.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    public static final String AUTHOR_NOT_FOUND_MESSAGE = "Author with id '%d' doesn't exist";
    private final AuthorRepository authorRepository;

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

        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
