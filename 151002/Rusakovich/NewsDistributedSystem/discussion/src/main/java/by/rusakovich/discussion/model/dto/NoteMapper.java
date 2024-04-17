package by.rusakovich.discussion.model.dto;

import by.rusakovich.discussion.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    Note mapToEntity(NoteRequestTO request);

    NoteResponseTO mapToResponse(Note entity);
}
