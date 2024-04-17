package by.rusakovich.discussion.dao;

import by.rusakovich.discussion.model.Note;

import java.util.Optional;

public interface INoteRepository{
    Optional<Note> readById(Long id);
    void removeById(Long id);
    Optional<Note> create(Note entity);
    Optional<Note> update(Note entity);
    Iterable<Note> readAll();
}
