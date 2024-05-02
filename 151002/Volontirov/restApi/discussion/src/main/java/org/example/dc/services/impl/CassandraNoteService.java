package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Note;
import org.example.dc.model.NoteDto;
import org.example.dc.model.NoteRepository;
import org.example.dc.services.NoteService;

import java.util.List;
import java.util.stream.Collectors;

public class CassandraNoteService implements NoteService {

    private NoteRepository noteRepository;

    private Converter converter;

    @Override
    public List<NoteDto> getNotes() {
        return noteRepository.findAll().stream()
                .map(note -> converter.convert(note))
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(int id) {
        return converter.convert(noteRepository.findById(id).get());
    }

    @Override
    public boolean delete(int id) throws Exception {
        getNoteById(id);
        noteRepository.deleteById(id);
        return true;
    }

    @Override
    public NoteDto create(NoteDto noteDto) {
        Note note = converter.convert(noteDto);
        int id = noteRepository.save(note).getId();
        noteDto.setId(id);
        return noteDto;
    }

    @Override
    public NoteDto update(NoteDto noteDto) {
        Note note = converter.convert(noteDto);
        noteRepository.deleteById(noteDto.getId());
        noteRepository.save(note);
        return noteDto;
    }
}
