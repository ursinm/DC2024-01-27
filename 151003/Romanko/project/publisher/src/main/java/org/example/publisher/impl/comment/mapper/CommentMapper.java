package org.example.publisher.impl.comment.mapper;

import org.example.publisher.impl.comment.Comment;
import org.example.publisher.impl.comment.dto.CommentAddedResponseTo;
import org.example.publisher.impl.comment.dto.CommentRequestTo;
import org.example.publisher.impl.comment.dto.CommentResponseTo;
import org.example.publisher.impl.story.Story;

import java.util.List;

public interface CommentMapper {
    CommentRequestTo commentToRequestTo(Comment comment);

    List<CommentRequestTo> commentToRequestTo(Iterable<Comment> comments);

    Comment dtoToEntity(CommentRequestTo commentRequestTo, Story story);

    CommentResponseTo commentToResponseTo(Comment comment);
    CommentAddedResponseTo commentToAddedResponesTo(CommentRequestTo comment, String status);

    List<CommentResponseTo> commentToResponseTo(Iterable<Comment> comments);
}
