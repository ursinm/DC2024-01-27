package by.rusakovich.discussion.dao;

import by.rusakovich.discussion.model.Note;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NoteRepository implements INoteRepository {
    @Override
    public Optional<Note> readById(Long id) {
        return Optional.empty();
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public Optional<Note> create(Note entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Note> update(Note entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Note> readAll() {
        return List.of();
    }
}
