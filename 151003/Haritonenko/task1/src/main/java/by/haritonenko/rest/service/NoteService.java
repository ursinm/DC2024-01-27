package by.haritonenko.rest.service;

import by.haritonenko.rest.model.Note;

import java.util.List;

public interface NoteService {

    Note findById(Long id);

    void deleteById(Long id);

    Note save(Note note);

    List<Note> findAll();

    Note update(Note note);
}
