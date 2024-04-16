package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.PostRequestTo;
import by.bsuir.restapi.model.dto.response.PostResponseTo;
import by.bsuir.restapi.model.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    PostResponseTo toDto(Post Post);

    Post toEntity(PostRequestTo PostRequestTo);
}
