package org.example.discussion.impl.comment.mapper;

import org.example.discussion.impl.comment.Comment;
import org.example.discussion.impl.comment.dto.CommentRequestTo;
import org.example.discussion.impl.comment.dto.CommentResponseTo;

import java.util.List;

public interface CommentMapper {
    CommentRequestTo commentToRequestTo(Comment comment);

    List<CommentRequestTo> commentToRequestTo(Iterable<Comment> comments);

    Comment dtoToEntity(CommentRequestTo commentRequestTo, String country);

    CommentResponseTo commentToResponseTo(Comment comment);

    List<CommentResponseTo> commentToResponseTo(Iterable<Comment> comments);
}
