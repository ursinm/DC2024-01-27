package by.rusakovich.discussion.model.dto;

import by.rusakovich.discussion.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    @Mapping(target = "country", expression = "java(\"ru\")")
    Note mapToEntity(NoteRequestTO request);
    NoteResponseTO mapToResponse(Note entity);
}
