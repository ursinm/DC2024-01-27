package by.bsuir.publisher.model.mapper;

import by.bsuir.publisher.event.PostInTopicDto;
import by.bsuir.publisher.event.PostOutTopicDto;
import by.bsuir.publisher.model.dto.request.PostRequestDto;
import by.bsuir.publisher.model.dto.response.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    PostResponseDto toDto(PostOutTopicDto messageOutTopicEvent);

    List<PostResponseDto> toDto(Collection<PostOutTopicDto> postOutTopicDto);

    @Mapping(target = "country", ignore = true)
    PostInTopicDto toInTopicDto(PostRequestDto postResponseDto);

    List<PostInTopicDto> toInTopicDto(Collection<PostRequestDto> postRequestDto);
}
