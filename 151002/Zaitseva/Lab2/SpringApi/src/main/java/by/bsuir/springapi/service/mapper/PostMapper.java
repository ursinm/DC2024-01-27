package by.bsuir.springapi.service.mapper;

import by.bsuir.springapi.model.entity.Post;
import by.bsuir.springapi.model.request.PostRequestTo;
import by.bsuir.springapi.model.response.PostResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface PostMapper {
    @Mapping(source = "p_content", target = "content")
    PostResponseTo getResponseTo(Post post);

    @Mapping(source = "p_content", target = "content")
    List<PostResponseTo> getListResponseTo(Iterable<Post> comments);

    @Mapping(source = "content", target = "p_content")
    Post getComment(PostRequestTo postRequestTo);
}
