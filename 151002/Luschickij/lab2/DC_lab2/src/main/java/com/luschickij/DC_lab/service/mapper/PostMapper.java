package com.luschickij.DC_lab.service.mapper;

import com.luschickij.DC_lab.model.entity.Post;
import com.luschickij.DC_lab.model.request.PostRequestTo;
import com.luschickij.DC_lab.model.response.PostResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface PostMapper {
    @Mapping(source = "news.id", target = "newsId")
    PostResponseTo getResponse(Post post);
    @Mapping(source = "news.id", target = "newsId")
    List<PostResponseTo> getListResponse(Iterable<Post> posts);
    @Mapping(source = "newsId", target = "news", qualifiedByName = "newsRefFromNewsId")
    Post getPost(PostRequestTo postRequestTo);
}
