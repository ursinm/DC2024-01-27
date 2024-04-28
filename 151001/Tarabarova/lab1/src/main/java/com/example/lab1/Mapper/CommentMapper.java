package com.example.lab1.Mapper;

import com.example.lab1.DTO.CommentRequestTo;
import com.example.lab1.DTO.CommentResponseTo;
import com.example.lab1.Model.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentRequestToComment(CommentRequestTo commentRequestTo);

    CommentResponseTo commentToCommentResponse(Comment comment);
}
