package by.bsuir.discussion.model.mapper;

import by.bsuir.discussion.event.PostInTopicDto;
import by.bsuir.discussion.event.PostOutTopicDto;
import by.bsuir.discussion.model.dto.PostResponseDto;
import by.bsuir.discussion.model.entity.Post;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostKafkaMapper {

    PostOutTopicDto responseDtoToOutTopicDto(PostResponseDto postResponseDto);

    List<PostOutTopicDto> responseDtoToOutTopicDto(Collection<PostResponseDto> postResponseDtoList);

    @Mapping(target = "key", source = ".")
    Post toEntity(PostInTopicDto dto);

    @Mapping(target = ".", source = "key")
    PostOutTopicDto entityToDto(Post entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "key.issueId", source = "issueId")
    Post partialUpdate(PostInTopicDto postInTopicDto, @MappingTarget Post post);

}
