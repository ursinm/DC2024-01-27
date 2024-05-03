package com.example.lab2.Mapper;

import com.example.lab2.DTO.CommentRequestTo;
import com.example.lab2.DTO.CommentResponseTo;
import com.example.lab2.Model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentRequestToComment(CommentRequestTo commentRequestTo);
    @Mapping(target = "storyId", source = "comment.story.id")
    CommentResponseTo commentToCommentResponse(Comment comment);
}
