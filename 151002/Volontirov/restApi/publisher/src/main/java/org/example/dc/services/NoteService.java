package org.example.dc.services;

import org.example.dc.model.Note;
import org.example.dc.model.NoteDto;

import java.util.List;

public interface NoteService {
    List<NoteDto> getNotes();
    NoteDto getNoteById(int id);
    boolean delete(int id) throws Exception;
    NoteDto create(NoteDto noteDto);
    NoteDto update(NoteDto noteDto);
}
