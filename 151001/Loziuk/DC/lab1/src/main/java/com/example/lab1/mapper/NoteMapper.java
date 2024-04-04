package com.example.lab1.mapper;

import com.example.lab1.dto.NoteRequestTo;
import com.example.lab1.dto.NoteResponseTo;
import com.example.lab1.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note noteRequestToNote(NoteRequestTo noteRequestTo);

    NoteResponseTo noteToNoteResponse(Note note);
}
