package org.example.dc.model;

import org.springframework.stereotype.Service;

@Service
public class Converter {

    public NoteDto convert(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setContent(note.getContent());
        noteDto.setIssueId(note.getIssue_id());
        return noteDto;
    }

    public Note convert(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setContent(noteDto.getContent());
        note.setIssue_id(noteDto.getIssueId());
        return note;
    }
}