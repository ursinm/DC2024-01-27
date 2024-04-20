package by.bsuir.dc.repository;

import by.bsuir.dc.entity.Note;
import by.bsuir.dc.repository.common.InMemoryCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NoteRepository extends InMemoryCrudRepository<Note> {
}
