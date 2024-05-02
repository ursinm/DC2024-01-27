package org.example.publisher.impl.post.mapper;

import org.example.publisher.impl.post.Post;
import org.example.publisher.impl.post.dto.PostAddedResponseTo;
import org.example.publisher.impl.post.dto.PostRequestTo;
import org.example.publisher.impl.post.dto.PostResponseTo;
import org.example.publisher.impl.issue.Issue;

import java.util.List;

public interface PostMapper {
    PostRequestTo postToRequestTo(Post post);

    List<PostRequestTo> postToRequestTo(Iterable<Post> posts);

    Post dtoToEntity(PostRequestTo postRequestTo, Issue issues);

    PostResponseTo postToResponseTo(Post post);
    PostAddedResponseTo postToAddedResponesTo(PostRequestTo post, String status);

    List<PostResponseTo> postToResponseTo(Iterable<Post> posts);
}
