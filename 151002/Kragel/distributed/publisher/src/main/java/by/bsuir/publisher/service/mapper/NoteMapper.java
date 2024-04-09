package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.dto.request.NoteRequestDto;
import by.bsuir.publisher.dto.response.NoteResponseDto;
import by.bsuir.publisher.event.NoteInTopicDto;
import by.bsuir.publisher.event.NoteOutTopicDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponseDto toDto(NoteOutTopicDto messageOutTopicEvent);

    List<NoteResponseDto> toDto(Collection<NoteOutTopicDto> noteOutTopicDto);

    @Mapping(target = "country", ignore = true)
    NoteInTopicDto toInTopicDto(NoteRequestDto noteResponseDto);

    List<NoteInTopicDto> toInTopicDto(Collection<NoteRequestDto> noteRequestDto);
}
