package com.example.discussion.mapper;

import com.example.discussion.model.entity.Note;
import com.example.discussion.model.request.NoteRequestTo;
import com.example.discussion.model.response.NoteResponseTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapperDisc {
    NoteResponseTo entityToResponse(Note entity);
    Note requestToEntity(NoteRequestTo request);
}
