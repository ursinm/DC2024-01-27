package by.bsuir.discussion.model.mapper;

import by.bsuir.discussion.model.dto.PostRequestDto;
import by.bsuir.discussion.model.dto.PostResponseDto;
import by.bsuir.discussion.model.entity.Post;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    @Mapping(target = "id", source = "key.id")
    @Mapping(target = "issueId", source = "key.issueId")
    PostResponseDto toDto(Post Post);

    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.issueId", source = "issueId")
    Post toEntity(PostRequestDto PostRequestDto);

    List<PostResponseDto> toDto(List<Post> Posts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "key.issueId", source = "issueId")
    Post partialUpdate(PostRequestDto noteRequestDto, @MappingTarget Post post);

}
