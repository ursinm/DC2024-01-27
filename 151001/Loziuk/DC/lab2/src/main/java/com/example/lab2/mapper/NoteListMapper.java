package com.example.lab2.mapper;

import com.example.lab2.dto.NoteRequestTo;
import com.example.lab2.dto.NoteResponseTo;
import com.example.lab2.model.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NoteMapper.class)
public interface NoteListMapper {
    List<Note> toNoteList(List<NoteRequestTo> noteRequestToList);

    List<NoteResponseTo> toNoteResponseList(List<Note> noteList);
}
