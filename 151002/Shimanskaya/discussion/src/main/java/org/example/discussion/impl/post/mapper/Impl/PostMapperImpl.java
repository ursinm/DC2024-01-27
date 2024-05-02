package org.example.discussion.impl.post.mapper.Impl;


import org.example.discussion.impl.post.Post;
import org.example.discussion.impl.post.dto.PostRequestTo;
import org.example.discussion.impl.post.dto.PostResponseTo;
import org.example.discussion.impl.post.mapper.PostMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostMapperImpl implements PostMapper {
    @Override
    public PostRequestTo postToRequestTo(Post post) {
        return new PostRequestTo(
                post.getId(),
                post.getIssueId(),
                post.getContent()
        );
    }

    @Override
    public List<PostRequestTo> postToRequestTo(Iterable<Post> posts) {
        return StreamSupport.stream(posts.spliterator(), false)
                .map(this::postToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Post dtoToEntity(PostRequestTo postRequestTo, String country) {
        return new Post(
                postRequestTo.getId(),
                postRequestTo.getIssueId(),
                country,
                postRequestTo.getContent()
        );
    }

    @Override
    public PostResponseTo postToResponseTo(Post post) {
        return new PostResponseTo(
                post.getId(),
                post.getIssueId(),
                post.getContent());
    }

    @Override
    public List<PostResponseTo> postToResponseTo(Iterable<Post> posts) {
        return StreamSupport.stream(posts.spliterator(), false)
                .map(this::postToResponseTo)
                .collect(Collectors.toList());
    }
}
