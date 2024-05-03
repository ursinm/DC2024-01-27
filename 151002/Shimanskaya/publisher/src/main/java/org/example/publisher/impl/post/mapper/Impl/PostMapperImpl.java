package org.example.publisher.impl.post.mapper.Impl;


import org.example.publisher.impl.post.Post;
import org.example.publisher.impl.post.dto.PostAddedResponseTo;
import org.example.publisher.impl.post.dto.PostRequestTo;
import org.example.publisher.impl.post.dto.PostResponseTo;
import org.example.publisher.impl.post.mapper.PostMapper;
import org.example.publisher.impl.issue.Issue;
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
                post.getIssue().getId(),
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
    public Post dtoToEntity(PostRequestTo postRequestTo, Issue issue) {
        return new Post(
                postRequestTo.getId(),
                issue,
                postRequestTo.getContent()
        );
    }

    @Override
    public PostResponseTo postToResponseTo(Post post) {
        return new PostResponseTo(
                post.getId(),
                post.getIssue().getId(),
                post.getContent());
    }

    @Override
    public PostAddedResponseTo postToAddedResponesTo(PostRequestTo post, String status) {
        return new PostAddedResponseTo(
            post.getId(),
            post.getIssueId(),
            post.getContent(),
            status
        );
    }

    @Override
    public List<PostResponseTo> postToResponseTo(Iterable<Post> posts) {
        return StreamSupport.stream(posts.spliterator(), false)
                .map(this::postToResponseTo)
                .collect(Collectors.toList());
    }
}
