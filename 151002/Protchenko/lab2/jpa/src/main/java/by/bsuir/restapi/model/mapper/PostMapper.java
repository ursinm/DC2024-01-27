package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.PostRequestDto;
import by.bsuir.restapi.model.dto.response.PostResponseDto;
import by.bsuir.restapi.model.entity.Post;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CustomMapper.class)
public interface PostMapper {

    @Mapping(target = "issueId", source = "issue.id")
    PostResponseDto toDto(Post Post);

    @Mapping(target = "issue", source = "issueId", qualifiedByName = "issueIdToIssueRef")
    Post toEntity(PostRequestDto PostRequestDto);

    List<PostResponseDto> toDto(List<Post> Posts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "issue", source = "issueId", qualifiedByName = "issueIdToIssueRef")
    Post partialUpdate(PostRequestDto noteRequestDto, @MappingTarget Post post);

}
