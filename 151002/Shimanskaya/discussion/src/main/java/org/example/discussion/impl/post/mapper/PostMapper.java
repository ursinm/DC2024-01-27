package org.example.discussion.impl.post.mapper;

import org.example.discussion.impl.post.Post;
import org.example.discussion.impl.post.dto.PostRequestTo;
import org.example.discussion.impl.post.dto.PostResponseTo;

import java.util.List;

public interface PostMapper {
    PostRequestTo postToRequestTo(Post post);

    List<PostRequestTo> postToRequestTo(Iterable<Post> posts);

    Post dtoToEntity(PostRequestTo postRequestTo, String country);

    PostResponseTo postToResponseTo(Post post);

    List<PostResponseTo> postToResponseTo(Iterable<Post> posts);
}
