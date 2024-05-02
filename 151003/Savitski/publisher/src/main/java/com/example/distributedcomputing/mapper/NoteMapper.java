package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Note;
import com.example.distributedcomputing.model.request.NoteRequestTo;
import com.example.distributedcomputing.model.response.NoteResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note dtoToEntity(NoteRequestTo noteRequestTo);
    List<Note> dtoToEntity(Iterable<NoteRequestTo> notes);

    NoteResponseTo entityToDto(Note note);

    List<NoteResponseTo> entityToDto(Iterable<Note> notes);
}
