package by.bsuir.dc.features.post;

import by.bsuir.dc.features.post.dto.PostResponseDto;
import by.bsuir.dc.features.post.dto.PostRequestDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponseDto toDto(Post post);

    List<PostResponseDto> toDtoList(List<Post> posts);

    Post toEntity(PostRequestDto postRequestDto);
}
