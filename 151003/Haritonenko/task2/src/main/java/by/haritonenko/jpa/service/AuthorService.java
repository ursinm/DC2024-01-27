package by.haritonenko.jpa.service;

import by.haritonenko.jpa.model.Author;

import java.util.List;

public interface AuthorService {

    Author findById(Long id);

    void deleteById(Long id);

    Author save(Author author);

    Author update(Author author);

    List<Author> findAll();
}
