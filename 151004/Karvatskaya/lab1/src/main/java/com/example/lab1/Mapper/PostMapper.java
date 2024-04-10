package com.example.lab1.Mapper;

import com.example.lab1.DTO.PostRequestTo;
import com.example.lab1.DTO.PostResponseTo;
import com.example.lab1.Model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post postRequestToPost(PostRequestTo postRequestTo);

    PostResponseTo postToPostResponse(Post post);
}
