package by.haritonenko.rest.repository.impl;

import by.haritonenko.rest.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAuthorRepository extends AbstractInMemoryRepository<Author> {
}
