package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.dto.response.NoteResponseDto;
import by.bsuir.discussion.event.NoteInTopicDto;
import by.bsuir.discussion.event.NoteOutTopicDto;
import by.bsuir.discussion.model.Note;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteKafkaMapper {

    NoteOutTopicDto responseDtoToOutTopicDto(NoteResponseDto noteResponseDto);

    List<NoteOutTopicDto> responseDtoToOutTopicDto(Collection<NoteResponseDto> noteResponseDto);

    @Mapping(target = "key", source = ".")
    Note toEntity(NoteInTopicDto dto);

    @Mapping(target = ".", source = "key")
    NoteOutTopicDto entityToDto(Note entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "key.tweetId", source = "tweetId")
    Note partialUpdate(NoteInTopicDto noteInTopicDto, @MappingTarget Note note);
}
