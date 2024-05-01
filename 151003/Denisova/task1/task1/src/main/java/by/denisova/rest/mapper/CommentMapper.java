package by.denisova.rest.mapper;

import by.denisova.rest.dto.request.CreateCommentDto;
import by.denisova.rest.dto.request.UpdateCommentDto;
import by.denisova.rest.dto.response.CommentResponseDto;
import by.denisova.rest.model.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface CommentMapper {
    Comment toComment(CreateCommentDto commentRequest);

    CommentResponseDto toCommentResponse(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment toComment(UpdateCommentDto commentRequest, @MappingTarget Comment comment);
}
