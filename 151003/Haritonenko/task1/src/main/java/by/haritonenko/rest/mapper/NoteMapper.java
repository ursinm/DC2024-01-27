package by.haritonenko.rest.mapper;

import by.haritonenko.rest.dto.request.CreateNoteDto;
import by.haritonenko.rest.dto.request.UpdateNoteDto;
import by.haritonenko.rest.dto.response.NoteResponseDto;
import by.haritonenko.rest.model.Note;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface NoteMapper {
    Note toNote(CreateNoteDto noteRequest);

    NoteResponseDto toNoteResponse(Note note);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Note toNote(UpdateNoteDto noteRequest, @MappingTarget Note note);
}
