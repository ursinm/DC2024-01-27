package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.dto.request.NoteRequestDto;
import by.bsuir.discussion.dto.response.NoteResponseDto;
import by.bsuir.discussion.model.Note;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mapping(target = "id", source = "key.id")
    @Mapping(target = "tweetId", source = "key.tweetId")
    NoteResponseDto toDto(Note entity);

    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.tweetId", source = "tweetId")
    Note toEntity(NoteRequestDto noteRequestDto);

    List<NoteResponseDto> toDto(Collection<Note> entities);

    List<Note> toEntity(Collection<NoteRequestDto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "key.tweetId", source = "tweetId")
    Note partialUpdate(NoteRequestDto noteRequestDto, @MappingTarget Note note);
}
