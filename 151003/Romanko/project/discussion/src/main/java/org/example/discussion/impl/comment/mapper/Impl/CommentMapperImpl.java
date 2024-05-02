package org.example.discussion.impl.comment.mapper.Impl;


import org.example.discussion.impl.comment.Comment;
import org.example.discussion.impl.comment.dto.CommentRequestTo;
import org.example.discussion.impl.comment.dto.CommentResponseTo;
import org.example.discussion.impl.comment.mapper.CommentMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentRequestTo commentToRequestTo(Comment comment) {
        return new CommentRequestTo(
                comment.getId(),
                comment.getStoryId(),
                comment.getContent()
        );
    }

    @Override
    public List<CommentRequestTo> commentToRequestTo(Iterable<Comment> comments) {
        return StreamSupport.stream(comments.spliterator(), false)
                .map(this::commentToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Comment dtoToEntity(CommentRequestTo commentRequestTo, String country) {
        return new Comment(
                commentRequestTo.getId(),
                commentRequestTo.getStoryId(),
                country,
                commentRequestTo.getContent()
        );
    }

    @Override
    public CommentResponseTo commentToResponseTo(Comment comment) {
        return new CommentResponseTo(
                comment.getId(),
                comment.getStoryId(),
                comment.getContent());
    }

    @Override
    public List<CommentResponseTo> commentToResponseTo(Iterable<Comment> comments) {
        return StreamSupport.stream(comments.spliterator(), false)
                .map(this::commentToResponseTo)
                .collect(Collectors.toList());
    }
}
