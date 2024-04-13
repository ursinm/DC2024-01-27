package com.example.lab1.Mapper;

import com.example.lab1.DTO.PostRequestTo;
import com.example.lab1.DTO.PostResponseTo;
import com.example.lab1.Model.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface PostListMapper {
    List<Post> toPostList(List<PostRequestTo> postRequestToList);

    List<PostResponseTo> toPostResponseList(List<Post> postList);
}
