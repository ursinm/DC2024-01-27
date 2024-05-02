package org.example.publisher.impl.comment.mapper.Impl;


import org.example.publisher.impl.comment.Comment;
import org.example.publisher.impl.comment.dto.CommentAddedResponseTo;
import org.example.publisher.impl.comment.dto.CommentRequestTo;
import org.example.publisher.impl.comment.dto.CommentResponseTo;
import org.example.publisher.impl.comment.mapper.CommentMapper;
import org.example.publisher.impl.story.Story;
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
                comment.getStory().getId(),
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
    public Comment dtoToEntity(CommentRequestTo commentRequestTo, Story story) {
        return new Comment(
                commentRequestTo.getId(),
                story,
                commentRequestTo.getContent()
        );
    }

    @Override
    public CommentResponseTo commentToResponseTo(Comment comment) {
        return new CommentResponseTo(
                comment.getId(),
                comment.getStory().getId(),
                comment.getContent());
    }

    @Override
    public CommentAddedResponseTo commentToAddedResponesTo(CommentRequestTo comment, String status) {
        return new CommentAddedResponseTo(
            comment.getId(),
            comment.getStoryId(),
            comment.getContent(),
            status
        );
    }

    @Override
    public List<CommentResponseTo> commentToResponseTo(Iterable<Comment> comments) {
        return StreamSupport.stream(comments.spliterator(), false)
                .map(this::commentToResponseTo)
                .collect(Collectors.toList());
    }
}
