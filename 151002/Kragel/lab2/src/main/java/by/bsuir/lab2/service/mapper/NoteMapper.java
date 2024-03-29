package by.bsuir.lab2.service.mapper;

import by.bsuir.lab2.dto.request.NoteRequestDto;
import by.bsuir.lab2.dto.response.NoteResponseDto;
import by.bsuir.lab2.entity.Note;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface NoteMapper {

    @Mapping(target = "tweetId", source = "tweet.id")
    NoteResponseDto toDto(Note entity);

    @Mapping(target = "tweet", source = "tweetId", qualifiedByName = "tweetIdToTweetRef")
    Note toEntity(NoteRequestDto noteRequestDto);

    List<NoteResponseDto> toDto(Collection<Note> entities);

    List<Note> toEntity(Collection<NoteRequestDto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tweet", source = "tweetId", qualifiedByName = "tweetIdToTweetRef")
    Note partialUpdate(NoteRequestDto noteRequestDto, @MappingTarget Note note);
}
