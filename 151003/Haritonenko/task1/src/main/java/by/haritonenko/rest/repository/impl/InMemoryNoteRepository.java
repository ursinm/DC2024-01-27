package by.haritonenko.rest.repository.impl;

import by.haritonenko.rest.model.Note;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryNoteRepository extends AbstractInMemoryRepository<Note> {
}
