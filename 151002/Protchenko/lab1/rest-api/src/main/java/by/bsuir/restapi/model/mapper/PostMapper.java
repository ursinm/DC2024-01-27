package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.PostRequestDto;
import by.bsuir.restapi.model.dto.response.PostResponseDto;
import by.bsuir.restapi.model.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    PostResponseDto toDto(Post Post);

    Post toEntity(PostRequestDto PostRequestDto);

    List<PostResponseDto> toDto(List<Post> Posts);
}
