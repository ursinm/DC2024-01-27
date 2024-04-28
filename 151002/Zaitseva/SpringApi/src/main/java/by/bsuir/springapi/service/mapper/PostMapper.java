package by.bsuir.springapi.service.mapper;

import by.bsuir.springapi.model.entity.Post;
import by.bsuir.springapi.model.request.PostRequestTo;
import by.bsuir.springapi.model.response.PostResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface PostMapper {
    PostResponseTo getResponseTo(Post post);

    List<PostResponseTo> getListResponseTo(Iterable<Post> comments);

    Post getComment(PostRequestTo postRequestTo);
}
