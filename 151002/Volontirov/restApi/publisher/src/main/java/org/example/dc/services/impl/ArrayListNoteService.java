package org.example.dc.services.impl;

import org.example.dc.model.Note;
import org.example.dc.model.NoteDto;
import org.example.dc.services.NoteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ArrayListNoteService implements NoteService {
    private static int id = 1;
    private List<NoteDto> notes = new ArrayList<>();

    @Override
    public List<NoteDto> getNotes() {
        return notes;
    }

    @Override
    public NoteDto getNoteById(int id) {
        return notes.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean delete(int id) throws Exception {
        NoteDto noteDto = notes.stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        notes.remove(noteDto);
        return true;
    }

    @Override
    public NoteDto create(NoteDto noteDto) {
        noteDto.setId(id++);
        notes.add(noteDto);
        return noteDto;
    }

    @Override
    public NoteDto update(NoteDto noteDto) {
        NoteDto note = notes.stream()
                .filter(n -> n.getId() == noteDto.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        note.setContent(noteDto.getContent());
        note.setIssueId(note.getIssueId());
        return note;
    }
}
