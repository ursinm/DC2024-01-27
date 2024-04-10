package com.example.lab2.mapper;

import com.example.lab2.dto.NoteRequestTo;
import com.example.lab2.dto.NoteResponseTo;
import com.example.lab2.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note noteRequestToNote(NoteRequestTo noteRequestTo);
    @Mapping(target = "issueId", source = "note.issue.id")
    NoteResponseTo noteToNoteResponse(Note note);
}
