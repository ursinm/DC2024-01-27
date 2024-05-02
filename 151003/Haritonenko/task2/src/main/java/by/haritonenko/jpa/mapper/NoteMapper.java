package by.haritonenko.jpa.mapper;

import by.haritonenko.jpa.dto.request.CreateNoteDto;
import by.haritonenko.jpa.dto.request.UpdateNoteDto;
import by.haritonenko.jpa.dto.response.NoteResponseDto;
import by.haritonenko.jpa.model.Note;
import org.mapstruct.*;

@Mapper
public interface NoteMapper {

    @Mapping(target = "story.id", source = "storyId")
    Note toNote(CreateNoteDto noteRequest);

    @Mapping(target="storyId", source="story.id")
    NoteResponseDto toNoteResponse(Note note);

    @Mapping(target = "story.id", source = "storyId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Note toNote(UpdateNoteDto noteRequest, @MappingTarget Note note);
}
