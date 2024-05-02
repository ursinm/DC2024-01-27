package by.haritonenko.jpa.service;

import by.haritonenko.jpa.model.Note;

import java.util.List;

public interface NoteService {

    Note findById(Long id);

    void deleteById(Long id);

    Note save(Note note);

    List<Note> findAll();

    Note update(Note note);
}
