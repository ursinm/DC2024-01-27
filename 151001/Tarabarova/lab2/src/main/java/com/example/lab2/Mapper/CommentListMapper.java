package com.example.lab2.Mapper;

import com.example.lab2.DTO.CommentRequestTo;
import com.example.lab2.DTO.CommentResponseTo;
import com.example.lab2.Model.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface CommentListMapper {
    List<Comment> toCommentList(List<CommentRequestTo> commentRequestToList);

    List<CommentResponseTo> toCommentResponseList(List<Comment> commentList);
}
